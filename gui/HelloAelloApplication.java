package com.example.coursesmanegment.gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.coursesmanegment.gui.GradeManagementMenu.assignGrade_Scene;
import static com.example.coursesmanegment.gui.StudentManagementMenu.*;
import static com.example.coursesmanegment.gui.CourseManagementMenu.*;


public class HelloAelloApplication extends Application {

    static MenuBar menuBar;

    // First Menu
    static MenuItem it_addStudent;
    static MenuItem it_updateDetails;
    static MenuItem it_viewDetails;

    // Second Menu
    static MenuItem it_addCourse;
    static MenuItem it_enrollStudent;

    // Third Menu
    static MenuItem it_assignGrade;


    public static void main(String[] args) {
        launch();
    }

    // The main Scene
    @Override
    public void start(Stage mainStage) throws IOException {
        // Student Management Items
        it_addStudent = new MenuItem("Add Student");
        it_updateDetails = new MenuItem("Update Student");
        it_viewDetails = new MenuItem("View Student Details");

        // Student Management Menu
        Menu mn_studentManagement = new Menu("Student Management");
        mn_studentManagement.getItems().addAll(it_addStudent,new SeparatorMenuItem(), it_updateDetails, new SeparatorMenuItem(), it_viewDetails);


        // Course Enrollment Items
        it_addCourse = new MenuItem("Add Course");
        it_enrollStudent = new MenuItem("Enroll Student");

        // Course Enrollment Menu
        Menu mn_courseEnrollment = new Menu("Course Enrollment");
        mn_courseEnrollment.getItems().addAll(it_addCourse,new SeparatorMenuItem(), it_enrollStudent);


        // Grade Management Items
        it_assignGrade = new MenuItem("Assign Grade");

        // Grade Management Menu
        Menu mn_gradeManagement = new Menu("Grade Management");

        mn_gradeManagement.getItems().add(it_assignGrade);

        // Add Menus to Menu Bar
        menuBar = new MenuBar(mn_studentManagement, mn_courseEnrollment, mn_gradeManagement);

        // Text
        Label lb_firstText = new Label("Welcome to My Courses Management System");
        lb_firstText.setFont(Font.font("Times new Roman", 25));


        Label lb_secondText = new Label("Chose Process you want from the Menu Bar");
        lb_secondText.setFont(Font.font("Times new Roman", 20));
        lb_secondText.setPadding(new Insets(20,0,0,20));


        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(lb_firstText, lb_secondText);
        vbox.setPadding(new Insets(0,0,40,0));
        vbox.setAlignment(Pos.CENTER);


        // BorderPane layout
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(vbox);



        Scene mainScene = new Scene(root,850, 500, Color.BEIGE);

        mainStage.setScene(mainScene);

        mainStage.setTitle("Student Course Management App");
        mainStage.setResizable(false);
        mainStage.show();


        // First Menu
        it_addStudent.setOnAction(e -> add_sdt_Scene(mainStage));
        it_updateDetails.setOnAction(e -> update_std_Scene(mainStage));
        it_viewDetails.setOnAction(e -> view_std_Scene(mainStage));

        // Second Menu
        it_addCourse.setOnAction(e -> add_course_Scene(mainStage));
        it_enrollStudent.setOnAction(e -> enrollInCourse_scene(mainStage));

        // Third Menu
        it_assignGrade.setOnAction(e -> assignGrade_Scene(mainStage));
    }

}












