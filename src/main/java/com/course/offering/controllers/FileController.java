package com.course.offering.controllers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.course.offering.models.Course;
import com.course.offering.models.Section;
import com.course.offering.models.Student;

public class FileController {

    private static ArrayList<Course> finishedCourses = new ArrayList<>();
    private static ArrayList<Course> degreePlanCourses = new ArrayList<>();
    private static ArrayList<Course> eligibleCourses = new ArrayList<>();
    private static ArrayList<Section> validSections = new ArrayList<>();

    private static String binaryFilePath = "output";

    public static ArrayList<Course> getFinishedCourses() {
        return finishedCourses;
    }

    public static ArrayList<Course> getDegreePlanCourses() {
        return degreePlanCourses;
    }

    public static ArrayList<Course> getEligibleCourses() {
        return eligibleCourses;
    }

    public static ArrayList<Section> getValidSections() {
        return validSections;
    }

    public static void Initialize(Student student) {
        System.out.println("Reading Files...");
        // FinishedCourses.csv code
        getFinishedCourses(student);

        // DegreePlan.csv code
        // Assings the courses for the degreePlanCourses and the eligibleCourses
        getDegreePlanAndEligileCourses(student);

        // CourseOffering.csv Code
        getValidSections(student);
    }

    private static void getValidSections(Student student) {
        String file = "src\\main\\resources\\CourseOffering.csv";

        try (Scanner scanner = new Scanner(new FileReader(file))) {
            scanner.nextLine();
            for (int i = 0; i < getNumOfLines(file) - 1; i++) {
                String[] line = scanner.nextLine().split(",");
                if (!line[6].contains("-") && !line[6].equals("None")) {
                    // System.out.println("Skipping line " + i + " Cuz time is bad " + line[6]);
                    continue;
                }
                Section section = new Section(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7]);
                if (section.isEligible(student)) {
                    validSections.add(section);
                }
            }
            student.setValidSections(validSections);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getDegreePlanAndEligileCourses(Student student) {
        String file = "src\\main\\resources\\DegreePlan.csv";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            bufferedReader.readLine();
            for (int i = 0; i < getNumOfLines(file) - 1; i++) {
                String[] line = bufferedReader.readLine().split(",");
                // System.out.println(Arrays.toString(line));
                Course course = new Course(line[0].strip(), line[1].strip(), line[2].strip(), line[3].strip());
                degreePlanCourses.add(course);
                if (course.isEligible(student)) {
                    eligibleCourses.add(course);
                }
            }
            student.setDegreePlanCourses(degreePlanCourses);
            student.setEligibleCourses(eligibleCourses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void getFinishedCourses(Student student) {
        String file = "src\\main\\resources\\FinishedCourses.csv";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

            for (int i = 0; i < getNumOfLines(file); i++) {
                String[] line = bufferedReader.readLine().split(",");
                Course course = new Course(line[0], line[1], line[2]);
                finishedCourses.add(course);
            }
            student.setFinishedCourses(finishedCourses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getNumOfLines(String fileName) {
        try {
            Path file = Paths.get(fileName);
            long count = Files.lines(file).count();
            return (int) count;
        } catch (Exception e) {
            e.getStackTrace();
            return 0;
        }
    }

    public static void saveScheduleSections(ArrayList<Section> sections) {
        // Convert to array, so that checking when reading is easier
        Section[] sectionsArr = sections.toArray(new Section[sections.size()]);

        // System.out.println("Saving..." + sectionsArr[0]);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(binaryFilePath))) {
            outputStream.writeObject(sectionsArr);
            System.out.println("Success!");
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static ArrayList<Section> readScheduleSections() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(binaryFilePath))) {
            Object object = inputStream.readObject();
            if (object instanceof Section[]) {
                System.out.println(Arrays.toString((Section[]) object));
                return new ArrayList<Section>(Arrays.asList((Section[]) object));
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e);
        }
        return null;

    }
}