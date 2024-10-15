package com.example.coursesmanegment.gui;

import com.example.coursesmanegment.logic.Course;
import com.example.coursesmanegment.logic.CourseManagement;

import com.example.coursesmanegment.logic.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


import static com.example.coursesmanegment.gui.GradeManagementMenu.assignGrade_Scene;
import static com.example.coursesmanegment.gui.HelloAelloApplication.*;
import static com.example.coursesmanegment.gui.StudentManagementMenu.add_sdt_Scene;
import static com.example.coursesmanegment.gui.StudentManagementMenu.update_std_Scene;
import static com.example.coursesmanegment.gui.StudentManagementMenu.view_std_Scene;



public class CourseManagementMenu {

    static TextField tf_courseCode;
    static TextField tf_courseName;
    static TextField tf_courseCapacity;

    static Label lb_Course_num;

    public static void add_course_Scene(Stage mainStage){

        // Title
        Label lb_title = new Label("- Add a new Course -");
        lb_title.setFont(Font.font("Times new Roman", 30));


        // Course Code Stuff
        Label lb_courseCode = new Label("Course Code");
        lb_courseCode.setFont(Font.font("Times new Roman", 15));

        tf_courseCode = new TextField();

        // Container Code Stuff
        HBox hCourseCode = new HBox(20);
        hCourseCode.getChildren().addAll(lb_courseCode, tf_courseCode);
        hCourseCode.setAlignment(Pos.CENTER);


        // Course Name Stuff
        Label lb_courseName = new Label("Course Name");
        lb_courseName.setFont(Font.font("Times new Roman", 15));

        tf_courseName = new TextField();

        // Container Name Stuff
        HBox hCourseName = new HBox(20);
        hCourseName.getChildren().addAll(lb_courseName, tf_courseName);
        hCourseName.setAlignment(Pos.CENTER);


        // Course Capacity Stuff
        Label lb_courseCapacity = new Label("Course Capacity");
        lb_courseCapacity.setFont(Font.font("Times new Roman", 15));

        tf_courseCapacity= new TextField();

        // Container Capacity Stuff
        HBox hCourseCapacity = new HBox(20);
        hCourseCapacity.getChildren().addAll(lb_courseCapacity, tf_courseCapacity);
        hCourseCapacity.setAlignment(Pos.CENTER);


        // Add Button
        Button btn_add = new Button("Add");
        btn_add.setPrefSize(100,20);


        // Total Courses
        lb_Course_num = new Label("Total Courses: " + CourseManagement.getCourses().size());
        lb_Course_num.setFont(Font.font("Trebuchet MS", 15));


        TableView<Course> courseTableView = new TableView<>();
        courseTableView.setMaxWidth(700);
        courseTableView.setMaxHeight(150);


        TableColumn<Course, Integer> tc_courseNum = new TableColumn<>("#");
        tc_courseNum.setPrefWidth(100);
        tc_courseNum.setCellValueFactory(new PropertyValueFactory<Course, Integer>("counter"));


        TableColumn<Course, String> tc_courseCode = new TableColumn<>("Course Code");
        tc_courseCode.setPrefWidth(200);
        tc_courseCode.setCellValueFactory(new PropertyValueFactory<Course, String>("code"));


        TableColumn<Course, String> tc_courseName = new TableColumn<>("Course Name");
        tc_courseName.setPrefWidth(225);
        tc_courseName.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));

        TableColumn<Course, String> tc_courseCapacity = new TableColumn<>("Course Capacity");
        tc_courseCapacity.setPrefWidth(175);
        tc_courseCapacity.setCellValueFactory(new PropertyValueFactory<Course, String>("capacity"));

        courseTableView.getColumns().add(tc_courseNum);
        courseTableView.getColumns().add(tc_courseCode);
        courseTableView.getColumns().add(tc_courseName);
        courseTableView.getColumns().add(tc_courseCapacity);

        ObservableList<Course> courseData = FXCollections.observableArrayList();
        courseTableView.setItems(courseData);

        if(CourseManagement.getCourses().size() != 0){
            courseData.addAll(CourseManagement.getCourses());
        }


        VBox root = new VBox(20);
        root.getChildren().addAll(menuBar,lb_title, hCourseCode,hCourseName,hCourseCapacity,btn_add, lb_Course_num,courseTableView);
        root.setAlignment(Pos.CENTER);

        Scene s = new Scene(root, 850, 500);

        mainStage.setScene(s);
        mainStage.show();

        btn_add.setOnAction(e -> addButtonAction(courseData));

        // First Menu
        it_addStudent.setOnAction(e -> add_sdt_Scene(mainStage));
        it_updateDetails.setOnAction(e -> update_std_Scene(mainStage));
        it_viewDetails.setOnAction(e -> view_std_Scene(mainStage));

        // Second Menu
        it_enrollStudent.setOnAction(e -> enrollInCourse_scene(mainStage));

        // Third Menu
        it_assignGrade.setOnAction(e -> assignGrade_Scene(mainStage));

    }

    // Add Button Action
    public static void addButtonAction(ObservableList<Course> courseData){
        if(checkCode(tf_courseCode.getText().strip()) == 0){
            if(checkName(tf_courseName.getText().strip()) == 0){
                if(checkCapacity(tf_courseCapacity.getText()) == 0){
                    if(CourseManagement.findCourse(tf_courseCode.getText().strip()) == null) {

                        int n = CourseManagement.addCourse(tf_courseCode.getText().strip(), tf_courseName.getText().strip(),
                                Integer.parseInt(tf_courseCapacity.getText().strip()));

                        if (n == 0) {
                            Course c = CourseManagement.findCourse(tf_courseCode.getText().strip());

                            addSuccessfully(c);
                            courseData.add(c);
                            lb_Course_num.setText("Total Courses: " + CourseManagement.getCourses().size());

                            tf_courseCode.clear();
                            tf_courseName.clear();
                            tf_courseCapacity.clear();

                        }else {
                            CourseExistError();
                        }


                    }else {
                        CourseExistError();
                    }
                }
            }
        }
    }


    // Check Course Name
    public static int checkName(String name){
        if(name.isBlank()){
            nameError();
            return 1;
        }
        return 0;
    }

    public static void nameError(){

        // Error Message
        Label nameError = new Label("Invalid Name: name can't be blank");
        nameError.setTextFill(Color.RED);

        // OK Button
        Button btn_ok = new Button("OK");
        btn_ok.setPrefSize(50, 30);

        VBox root = new VBox(20);
        root.getChildren().addAll(nameError, btn_ok);

        root.setAlignment(Pos.CENTER);

        Scene s = new Scene(root, 300, 150);
        Stage stage = new Stage();
        stage.setScene(s);
        stage.setTitle("Name Error");

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();

        btn_ok.setOnAction(e -> stage.close());




    }

    // Check Course Code
    public static int checkCode(String code){
        if(code.isBlank()){
            codeError();
            return 1;
        }
        return 0;
    }

    public static void codeError(){

        // Error Message
        Label nameError = new Label("Invalid Code: Code can't be blank");
        nameError.setTextFill(Color.RED);

        // OK Button
        Button btn_ok = new Button("OK");
        btn_ok.setPrefSize(50, 30);

        VBox root = new VBox(20);
        root.getChildren().addAll(nameError, btn_ok);

        root.setAlignment(Pos.CENTER);

        Scene s = new Scene(root, 300, 150);
        Stage stage = new Stage();
        stage.setScene(s);
        stage.setTitle("Code Error");

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();

        btn_ok.setOnAction(e -> stage.close());

    }


    // Check Course Capacity
    public static int checkCapacity(String capacity){
        if(capacity.isBlank()){
            capacityError();
            return 1;

        } else {
            try {
                if(Integer.parseInt(capacity) > 0){
                    return 0;

                }else {
                    capacityError();
                    return 1;
                }

            }catch (NumberFormatException e){
                capacityError();
                return 1;
            }

        }
    }

    public static void capacityError(){

        // Error Message
        Label nameError = new Label("Invalid Capacity: capacity can't contains Characters of be blank or less than 1");
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
        stage.setTitle("Capacity Error");

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();

        btn_ok.setOnAction(e -> stage.close());

    }


    // Course Already Exist Error
    public static void CourseExistError(){

        // Error Message
        Label nameError = new Label("Error: Course Already Exist");
        nameError.setTextFill(Color.RED);

        // OK Button
        Button btn_ok = new Button("OK");
        btn_ok.setPrefSize(50, 30);

        VBox root = new VBox(20);
        root.getChildren().addAll(nameError, btn_ok);

        root.setAlignment(Pos.CENTER);

        Scene s = new Scene(root, 300, 150);
        Stage stage = new Stage();
        stage.setScene(s);
        stage.setTitle("Exist Error");

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();

        btn_ok.setOnAction(e -> stage.close());

    }

    // successful add message
    public static void addSuccessfully(Course c ){

        if(c != null) {
            // successful message
            Label lb_message = new Label("Student Added Successfully");
            lb_message.setFont(Font.font("Trebuchet MS", 24));


            // Course Code
            Label lb_courseCode = new Label("Course Code: " + c.getCode());

            // Course name
            Label lb_courseName = new Label("Student Name: " + c.getName());

            // Course Capacity
            Label lb_courseCapacity = new Label("Course Capacity: " + c.getCapacity());

            // OK Button
            Button btn_ok = new Button("OK");
            btn_ok.setPrefSize(100, 20);


            VBox root = new VBox(15);
            root.getChildren().addAll(lb_message, lb_courseCode, lb_courseName, lb_courseCapacity, btn_ok);

            root.setAlignment(Pos.CENTER);


            Scene s = new Scene(root, 400, 200);

            Stage stage = new Stage();
            stage.setScene(s);
            stage.setTitle("Added Successfully");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();


            btn_ok.setOnAction(e -> stage.close());

        }
    }

    //----------------------------------------------------------------------------------------------------


    public static void enrollInCourse_scene(Stage mainStage){

        // Title
        Label lb_title = new Label("- Enroll Student In Course -");
        lb_title.setFont(Font.font("Times new Roman", 30));

        HBox hTitle = new HBox(20);
        hTitle.getChildren().add(lb_title);
        hTitle.setAlignment(Pos.TOP_CENTER);



        // Total Student
        Label lb_std_num = new Label("Total Student : " + Student.counter);
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
        Label lb_CourseNum = new Label("Total Courses : " + CourseManagement.getCourses().size());
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



        Button btn_enroll = new Button("Enroll");
        btn_enroll.setPrefSize(100, 30);

        HBox hBtn = new HBox(20);
        hBtn.getChildren().add(btn_enroll);
        hBtn.setAlignment(Pos.BOTTOM_CENTER);



        VBox root = new VBox(20);
        root.getChildren().addAll(menuBar, hTitle, hLeftRight, hBtn);


        Scene s = new Scene(root, 850, 500);

        mainStage.setScene(s);
        mainStage.show();


        String courseSelected = cmBox_Courses.getValue();
        String StudentSelected = cmBox_students.getValue();

        btn_enroll.setOnAction(e -> btn_enrollAction(cmBox_students, cmBox_Courses));


        // First Menu
        it_addStudent.setOnAction(e -> add_sdt_Scene(mainStage));
        it_updateDetails.setOnAction(e -> update_std_Scene(mainStage));
        it_viewDetails.setOnAction(e -> view_std_Scene(mainStage));

        // Second Menu
        it_addCourse.setOnAction(e -> add_course_Scene(mainStage));

        // Third Menu
        it_assignGrade.setOnAction(e -> assignGrade_Scene(mainStage));

    }

    public static void btn_enrollAction(ComboBox<String> cmBox_students, ComboBox<String> cmBox_Courses){
        Student s = CourseManagement.stdExist(cmBox_students.getValue());
        Course c = CourseManagement.courseExist(cmBox_Courses.getValue());


        if(s != null) {
            if(c != null ) {
                int n = CourseManagement.enrollStudent(s.getId(), c.getCode());

                if (n == 0) {
                    enrolledSuccessfully(s, c);

                } else if (n == 1){
                    courseFullError(c);

                }else {
                    alreadyEnrolledError(s, c);
                }
            }
        }
    }

    public static void enrolledSuccessfully(Student std, Course c) {

        if (c != null && std != null) {

            // successful message
            Label lb_message = new Label("Course Enrolled Successfully");
            lb_message.setFont(Font.font("Trebuchet MS", 24));


            // Student ID
            Label lb_stdID = new Label("Student ID: " + std.getId());

            // Student Name
            Label lb_stdName = new Label("Student Name: " + std.getName());


            // Course Code
            Label lb_courseCode = new Label("Course Code: " + c.getCode());

            // Course name
            Label lb_courseName = new Label("Course Name: " + c.getName());


            // OK Button
            Button btn_ok = new Button("OK");
            btn_ok.setPrefSize(100, 20);


            VBox root = new VBox(15);
            root.getChildren().addAll(lb_message,lb_stdID, lb_stdName, lb_courseCode, lb_courseName, btn_ok);

            root.setAlignment(Pos.CENTER);


            Scene s = new Scene(root, 400, 250);

            Stage stage = new Stage();
            stage.setScene(s);
            stage.setTitle("Enrolled Successfully");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();


            btn_ok.setOnAction(e -> stage.close());

        }
    }

    public static void courseFullError(Course c) {

        if (c != null) {

            // Error message
            Label lb_message = new Label("Course with '" + c.getCode() + "' code and '" + c.getName() + "' name is FULL");
            lb_message.setFont(Font.font("Times new Roman", 15));
            lb_message.setTextFill(Color.RED);


            // OK Button
            Button btn_ok = new Button("OK");
            btn_ok.setPrefSize(100, 20);


            VBox root = new VBox(30);
            root.getChildren().addAll(lb_message, btn_ok);

            root.setAlignment(Pos.CENTER);


            Scene s = new Scene(root, 400, 200);

            Stage stage = new Stage();
            stage.setScene(s);
            stage.setTitle("Course is FULL");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();


            btn_ok.setOnAction(e -> stage.close());

        }
    }

    public static void alreadyEnrolledError(Student std, Course c) {


        if (c != null && std != null) {

            // Error message
            Label lb_message = new Label("Student '" + std.getName() + "' is already enrolled in '" + c.getName() + "'");
            lb_message.setFont(Font.font("Times new Roman", 15));
            lb_message.setTextFill(Color.RED);


            // OK Button
            Button btn_ok = new Button("OK");
            btn_ok.setPrefSize(100, 20);


            VBox root = new VBox(30);
            root.getChildren().addAll(lb_message, btn_ok);

            root.setAlignment(Pos.CENTER);


            Scene s = new Scene(root, 400, 200);

            Stage stage = new Stage();
            stage.setScene(s);
            stage.setTitle("Enroll Error");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();


            btn_ok.setOnAction(e -> stage.close());

        }
    }
}










