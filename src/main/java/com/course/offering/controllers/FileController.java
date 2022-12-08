package com.course.offering.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.course.offering.models.Course;
import com.course.offering.models.Section;
import com.course.offering.models.Student;

public class FileController {

    private static ArrayList<Course> finishedCourses = new ArrayList<>();
    private static ArrayList<Course> degreePlanCourses = new ArrayList<>();
    private static ArrayList<Course> eligibleCourses = new ArrayList<>();
    private static ArrayList<Section> validSections = new ArrayList<>();

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

    public static void Initialize() {
        Student student = new Student();

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

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            for (int i = 0; i < getNumOfLines(file); i++) {
                String[] line = bufferedReader.readLine().split(",");
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

            for (int i = 0; i < getNumOfLines(file); i++) {
                String[] line = bufferedReader.readLine().split(",");
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

    // TODO save scheduleSections to a binary file
    public static void saveSchedule(ArrayList<Section> scheduleSections) {
    }

    // TODO read the scheduleSections from the binary file
    public static void readScheduleSections(String fileName) throws IOException {
        try {

        } catch (Exception e) {
            // TODO: handle saving exception
        }
    }
}