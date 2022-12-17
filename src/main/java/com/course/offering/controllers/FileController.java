package com.course.offering.controllers;

import java.io.BufferedReader;
import java.io.File;
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

import com.course.offering.models.DegreePlanCourse;
import com.course.offering.models.FinishedCourse;
import com.course.offering.models.Section;
import com.course.offering.models.Student;

import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FileController {

    private static ArrayList<FinishedCourse> finishedCourses = new ArrayList<>();
    private static ArrayList<DegreePlanCourse> degreePlanCourses = new ArrayList<>();
    private static ArrayList<DegreePlanCourse> eligibleCourses = new ArrayList<>();
    private static ArrayList<Section> validSections = new ArrayList<>();

    public static ArrayList<FinishedCourse> getFinishedCourses() {
        return finishedCourses;
    }

    public static ArrayList<DegreePlanCourse> getDegreePlanCourses() {
        return degreePlanCourses;
    }

    public static ArrayList<DegreePlanCourse> getEligibleCourses() {
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

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            for (int i = 0; i < getNumOfLines(file); i++) {
                String[] line = bufferedReader.readLine().split(",");
                if (!line[6].contains("-") && !line[6].equals("None")) {
                    // System.out.println("Skipping line " + i + " Cuz time is bad " + line[6]);
                    continue;
                }
                Section section = new Section(line[0], line[1], line[2], line[3], line[4], line[5], line[6], line[7]);
                if (section.isEligable(student)) {
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
                DegreePlanCourse course = new DegreePlanCourse(line[0].strip(), line[1].strip(), line[2].strip(),
                        line[3].strip());
                degreePlanCourses.add(course);
            }
            student.setDegreePlanCourses(degreePlanCourses);
            student.setFinishedHours();
            for (DegreePlanCourse courseObj : degreePlanCourses) {
                if (courseObj.isEligable(student)) {
                    eligibleCourses.add(courseObj);
                }
            }
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
                FinishedCourse course = new FinishedCourse(line[0], line[1], line[2]);
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

    public static void saveScheduleSections(ArrayList<Section> sections, Stage stage) {
        // Convert to array, so that checking when reading is easier
        Section[] sectionsArr = sections.toArray(new Section[sections.size()]);

        File savingLocation = chooseSavingLocation(stage);
        if (savingLocation == null)
            return;

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(savingLocation))) {
            outputStream.writeObject(sectionsArr);
            System.out.println("Saving Success!");
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static ArrayList<Section> readScheduleSections(Stage stage, Button openDialogButton) {
        File scheduleFile = chooseSavedFile(stage, openDialogButton);
        if (scheduleFile == null)
            return new ArrayList<>();

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(scheduleFile))) {
            Object object = inputStream.readObject();
            if (object instanceof Section[]) {
                System.out.println(Arrays.toString((Section[]) object));
                return new ArrayList<Section>(Arrays.asList((Section[]) object));
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e);
        }
        return new ArrayList<>();
    }

    private static String lastVisitedDirectory = System.getProperty("user.home");

    private static File chooseSavedFile(Stage stage, Button button) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(new ExtensionFilter("All Files", "*.dat"));
        fileChooser.setInitialDirectory(new File(lastVisitedDirectory));
        // get the file selected
        File file = fileChooser.showOpenDialog(stage);

        lastVisitedDirectory = (file != null) ? file.getParent() : System.getProperty("user.home");
        if (file != null)
            button.setText(file.getName() + "  selected");

        return file;
    }

    private static File chooseSavingLocation(Stage stage) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(new ExtensionFilter("All Files", "*.dat"));
        fileChooser.setInitialDirectory(new File(lastVisitedDirectory));
        // get the file selected
        File file = fileChooser.showSaveDialog(stage);

        lastVisitedDirectory = (file != null) ? file.getParent() : System.getProperty("user.home");
        return file;
    }
}