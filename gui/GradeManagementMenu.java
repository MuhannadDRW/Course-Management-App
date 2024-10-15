package com.example.coursesmanegment.gui;

import com.example.coursesmanegment.logic.Course;
import com.example.coursesmanegment.logic.CourseManagement;
import com.example.coursesmanegment.logic.Student;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.example.coursesmanegment.gui.CourseManagementMenu.*;
import static com.example.coursesmanegment.gui.HelloAelloApplication.*;
import static com.example.coursesmanegment.gui.StudentManagementMenu.*;


public class GradeManagementMenu {

    static Label lb_std_num;
    static Label lb_CourseNum;

    static TextField tf_assignGrade;

    public static void assignGrade_Scene(Stage mainStage){

        // Title
        Label lb_title = new Label("- Assign Grade to Student -");
        lb_title.setFont(Font.font("Times new Roman", 30));


        HBox hTitle = new HBox(20);
        hTitle.getChildren().add(lb_title);
        hTitle.setAlignment(Pos.TOP_CENTER);


        // Total Student
        lb_std_num = new Label("Total Students: " + Student.counter);
        lb_std_num.setFont(Font.font("Trebuchet MS", 15));


        // Student List Text
        Label lb_std_chose = new Label("Chose Student from the list");


        // Available Students
        ComboBox<String> cmBox_students = new ComboBox<>();
        if (CourseManagement.getStudents().size() == 0){
            cmBox_students.getItems().add("No Students Added");

        }else {
            for (Student n : CourseManagement.getStudents()) {
                cmBox_students.getItems().addAll(n.getName().strip());
            }
        }
        cmBox_students.setPromptText("Chose");
        cmBox_students.setPrefSize(200,25);


        // Container for Student Chose Stuff
        VBox vRightSide = new VBox(20);
        vRightSide.getChildren().addAll(lb_std_num, lb_std_chose, cmBox_students);




        // Total Courses
        lb_CourseNum= new Label("Total Courses : " + CourseManagement.getCourses().size());
        lb_CourseNum.setFont(Font.font("Trebuchet MS", 15));


        //Courses List Text
        Label lb_courseChose = new Label("Chose Course from the list");

        //Available Courses
        ComboBox<String> cmBox_Courses = new ComboBox<>();
        if (CourseManagement.getCourses().size() == 0){
            cmBox_Courses.getItems().add("No Course Added");

        }else {
            for (Course c : CourseManagement.getCourses()) {
                cmBox_Courses.getItems().addAll(c.getName().strip());
            }
        }
        cmBox_Courses.setPromptText("Chose");
        cmBox_Courses.setPrefSize(200,25);


        // Container Left Side
        VBox vLeftSide = new VBox(20);
        vLeftSide.getChildren().addAll(lb_CourseNum, lb_courseChose, cmBox_Courses);



        //Left Side + Right Side Container
        HBox hLeftRight = new HBox(50);
        hLeftRight.getChildren().addAll(vLeftSide, vRightSide);
        hLeftRight.setAlignment(Pos.CENTER);
        hLeftRight.setPadding(new Insets(50));


        // Assign Grade Stuff
        Label lb_assignGrade = new Label("Enter The Grade");
        lb_assignGrade.setFont(Font.font("Times new Roman", 15));

        tf_assignGrade= new TextField();

        HBox hAssignGradeBox = new HBox(20);
        hAssignGradeBox.getChildren().addAll(lb_assignGrade, tf_assignGrade);
        hAssignGradeBox.setPadding(new Insets(20));
        hAssignGradeBox.setAlignment(Pos.BASELINE_CENTER);


        // Button
        Button btn_assignGrade = new Button("Assign Grade");
        btn_assignGrade.setPrefSize(100, 30);

        VBox vBtn  = new VBox(20);
        vBtn.getChildren().add(btn_assignGrade);
        vBtn.setAlignment(Pos.BOTTOM_CENTER);




        VBox root = new VBox(20);
        root.getChildren().addAll(menuBar, hTitle, hAssignGradeBox, vBtn, hLeftRight);


        Scene s = new Scene(root, 850, 500);

        mainStage.setScene(s);
        mainStage.show();


        String courseSelected = cmBox_Courses.getValue();
        String StudentSelected = cmBox_students.getValue();

        btn_assignGrade.setOnAction(e -> btn_assignGradeAction(cmBox_students, cmBox_Courses));

        //First Menu
        it_addStudent.setOnAction(e -> add_sdt_Scene(mainStage));
        it_updateDetails.setOnAction(e -> update_std_Scene(mainStage));
        it_viewDetails.setOnAction(e -> view_std_Scene(mainStage));

        // Second Menu
        it_addCourse.setOnAction(e -> add_course_Scene(mainStage));
        it_enrollStudent.setOnAction(e -> enrollInCourse_scene(mainStage));

    }

    public static void btn_assignGradeAction(ComboBox<String> cmBox_students, ComboBox<String> cmBox_Courses){
        if(checkGrade(tf_assignGrade.getText()) == 0) {
            String selectedStudent = cmBox_students.getValue();
            String selectedCourse = cmBox_Courses.getValue();

            Student s  = CourseManagement.stdExist(selectedStudent);
            Course c = CourseManagement.courseExist(selectedCourse);

            if(c != null && s != null) {
                if(CourseManagement.assignGrade(s.getId(), c.getCode(), Integer.parseInt(tf_assignGrade.getText())) == 1){
                    std_notEnrollCourseError(s, c);

                }else {
                    gradeAssignedSuccessfully(s, c);
                }

            }
        }
    }

    public static int checkGrade(String grade){
        if(grade.isBlank()){
            gradeError();
            return 1;

        }else {
            try {
                if(Integer.parseInt(grade) >= 0 && Integer.parseInt(grade) <= 100){
                    return 0;

                }else {
                    gradeError();
                    return 1;
                }

            }catch (NumberFormatException e ){
                gradeError();
                return 1;
            }
        }
    }

    public static void gradeError(){

        // Error Message
        Label nameError = new Label("Invalid Grade: Grade can't contains Characters, be blank,\n be less than 0, or larger than 100");
        nameError.setTextFill(Color.RED);

        // OK Button
        Button btn_ok = new Button("OK");
        btn_ok.setPrefSize(50, 30);

        VBox root = new VBox(20);
        root.getChildren().addAll(nameError, btn_ok);

        root.setAlignment(Pos.CENTER);

        Scene s = new Scene(root, 400, 150);
        Stage stage = new Stage();
        stage.setScene(s);
        stage.setTitle("Grade Error");

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();

        btn_ok.setOnAction(e -> stage.close());

    }

    public static void std_notEnrollCourseError(Student s, Course c){

        // Error Message
        Label lb_nameError = new Label("Error: Student '" + s.getName() + "' doesn't enrolled in course '" + c.getName()+"'");
        lb_nameError.setFont(Font.font("Times new Roman", 15));
        lb_nameError.setTextFill(Color.RED);

        // OK Button
        Button btn_ok = new Button("OK");
        btn_ok.setPrefSize(50, 30);

        VBox root = new VBox(20);
        root.getChildren().addAll(lb_nameError, btn_ok);

        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 150);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Error");

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();

        btn_ok.setOnAction(e -> stage.close());

    }

    public static void gradeAssignedSuccessfully(Student std, Course c){

        if (c != null && std != null) {

            // successful message
            Label lb_message = new Label("Course Grade Assigned Successfully");
            lb_message.setFont(Font.font("Trebuchet MS", 24));


            // Student ID
            Label lb_stdID = new Label("Student ID: " + std.getId());

            // Student Name
            Label lb_stdName = new Label("Student Name: " + std.getName());

            // Student Info Container
            HBox hStd = new HBox(50);
            hStd.getChildren().addAll(lb_stdID, lb_stdName);
            hStd.setAlignment(Pos.CENTER);


            // Course Code
            Label lb_courseCode = new Label("Course Code: " + c.getCode());

            // Course name
            Label lb_courseName = new Label("Course Name: " + c.getName());


            // Course Info Container
            HBox hCourse = new HBox(30);
            hCourse.getChildren().addAll(lb_courseCode, lb_courseName);
            hCourse.setAlignment(Pos.CENTER);


            // Grade Label
            Label lb_grade = new Label("Grade: " + std.getGrades().get(c));

            // OK Button
            Button btn_ok = new Button("OK");
            btn_ok.setPrefSize(100, 20);


            VBox root = new VBox(15);
            root.getChildren().addAll(lb_message, hStd, hCourse, lb_grade,  btn_ok);

            root.setAlignment(Pos.CENTER);


            Scene s = new Scene(root, 400, 250);

            Stage stage = new Stage();
            stage.setScene(s);
            stage.setTitle("Course Grade Assigned Successfully");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();


            btn_ok.setOnAction(e -> stage.close());
        }
    }
}
