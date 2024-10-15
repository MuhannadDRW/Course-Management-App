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
import javafx.util.Callback;

import static com.example.coursesmanegment.gui.CourseManagementMenu.*;
import static com.example.coursesmanegment.gui.GradeManagementMenu.assignGrade_Scene;
import static com.example.coursesmanegment.gui.HelloAelloApplication.*;


public class StudentManagementMenu {


    // All
    static TextField tf_id;
    static TextField tf_stdName;
    static TextField tf_stdEmail;

    static Label lb_std_num;

    static Label lb_GPA;

    // Add Student Scene
    public static void add_sdt_Scene(Stage mainStage){

        // massage
        Label lb_title = new Label("- Add Student Section -");
        lb_title.setFont( Font.font("Times new Roman", 30));
        lb_title.setPadding(new Insets(20,0,0,10));


        // Note
        Label lb_note = new Label("All Fields are required");
        lb_note.setFont( Font.font("Times new Roman", 20));

        // Total Student
        lb_std_num = new Label("Total Student : " + Student.counter);
        lb_std_num.setFont(Font.font("Trebuchet MS", 15));

        // Text for Name TextFiled
        Label lb_stdName = new Label("Student Name:");
        lb_stdName.setFont( Font.font("Times new Roman", 15));

        tf_stdName = new TextField();


        // Text for email TextFiled
        Label lb_stdEmail = new Label("Student Email: ");
        lb_stdEmail.setFont( Font.font("Times new Roman", 15));

        tf_stdEmail = new TextField();


        /// Student name stuff
        HBox hName = new HBox(10);
        hName.getChildren().addAll(lb_stdName,tf_stdName);
        hName.setAlignment(Pos.TOP_CENTER);


        // Student email stuff
        HBox hEmail = new HBox(10);
        hEmail.getChildren().addAll(lb_stdEmail,tf_stdEmail);
        hEmail.setAlignment(Pos.TOP_CENTER);

        HBox nameMail = new HBox(40);
        nameMail.getChildren().addAll(hName,hEmail);
        nameMail.setAlignment(Pos.CENTER);


        // add button
        Button btn_add = new Button("Add");
        btn_add.setPrefSize(100,20);


        TableView<Student> tableView = new TableView<>();


        // Student number column
        TableColumn<Student, Integer> idColumn = new TableColumn<>("Student ID");
        idColumn.setPrefWidth(170);
        idColumn.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));


        // Name Column
        TableColumn<Student, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setPrefWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));

        // Email Column
        TableColumn<Student, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setPrefWidth(325);
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));


        tableView.getColumns().add(idColumn);
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(emailColumn);
        tableView.setMaxWidth(700);
        tableView.setMaxHeight(180);

        tableView.setSelectionModel(null);


        ObservableList<Student> data = FXCollections.observableArrayList();
        tableView.setItems(data);

        if(CourseManagement.getStudents().size() != 0){
            data.addAll(CourseManagement.getStudents());
        }




        VBox root = new VBox(20);
        root.getChildren().addAll(menuBar,lb_title,lb_note,nameMail,btn_add,lb_std_num, tableView);
        root.setAlignment(Pos.TOP_CENTER);


        Scene addStdScene = new Scene(root,850, 500, Color.BEIGE);


        mainStage.setScene(addStdScene);
        mainStage.show();

        btn_add.setOnAction(e -> addButtonAction(data));


        // First Menu
        it_updateDetails.setOnAction(e -> update_std_Scene(mainStage));
        it_viewDetails.setOnAction(e -> view_std_Scene(mainStage));

        // Second Menu
        it_addCourse.setOnAction(e -> add_course_Scene(mainStage));
        it_enrollStudent.setOnAction(e -> enrollInCourse_scene(mainStage));

        // Third Menu
        it_assignGrade.setOnAction(e -> assignGrade_Scene(mainStage));

    }

    public static void addButtonAction(ObservableList<Student> data){
        if( checkName(tf_stdName.getText().strip()) == 0) {
            if(checkEmail(tf_stdEmail.getText().strip()) == 0 ) {
                CourseManagement.addStudent(tf_stdName.getText().strip(), tf_stdEmail.getText().strip());

                Student n = CourseManagement.stdExist(tf_stdName.getText().strip());

                if(n != null) {
                    addSuccessfully(n);
                    data.add(n);

                    lb_std_num.setText("Total Student : " + Student.counter);

                    tf_stdName.clear();
                    tf_stdEmail.clear();

                }else {
                    foundError();
                }

            }else {
                emailError();
                tf_stdName.clear();
                tf_stdEmail.clear();
            }


        }else {
            nameError();
            tf_stdName.clear();
            tf_stdEmail.clear();
        }

    }

    // successful add message
    public static void addSuccessfully(Student n){


        // successful message
        Label lb_message = new Label("Student Added Successfully");
        lb_message.setFont(Font.font("Trebuchet MS", 24));

        // student id
        Label lb_std_id = new Label("Student ID: " + n.getId());

        // student name
        Label lb_std_name = new Label("Student Name: " + n.getName());

        // student email
        Label lb_std_email = new Label("Student Email: " + n.getEmail());

        // OK Button
        Button btn_ok = new Button("OK");
        btn_ok.setPrefSize(100,20);


        VBox root = new VBox(15);
        root.getChildren().addAll(lb_message,lb_std_id, lb_std_name, lb_std_email, btn_ok);

        root.setAlignment(Pos.CENTER);


        Scene s  = new Scene(root, 400, 200);

        Stage stage = new Stage();
        stage.setScene(s);
        stage.setTitle("Added Successfully");

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();


        btn_ok.setOnAction(e -> stage.close());



    }

    public static void foundError(){

        // Message
        Label message = new Label("Student Is Already Exist");

        // ok button
        Button btn_ok = new Button("OK");
        btn_ok.setPrefSize(100, 20);


        VBox root = new VBox(20);
        root.getChildren().addAll(message,btn_ok);

        Scene s = new Scene(root, 400, 250);

        Stage stage = new Stage();
        stage.setScene(s);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Found Student Error");

        stage.show();

        btn_ok.setOnAction(e -> stage.close());


    }


    // check name (used in 'Add Scene' + 'Updated Scene')

    public static int checkName(String name){
        if(!CourseManagement.isAlpah(name) || name.isBlank()){
            return 1;
        }
        return 0;
    }

    public static void nameError(){

        // Error Message
        Label nameError = new Label("Invalid Name: name can't contains numbers or be blank");
        nameError.setTextFill(Color.RED);

        // OK Button
        Button btn_ok = new Button("OK");
        btn_ok.setPrefSize(50, 30);

        VBox root = new VBox(20);
        root.getChildren().addAll(nameError, btn_ok);

        root.setAlignment(Pos.CENTER);

        Scene s = new Scene(root, 450, 150);
        Stage stage = new Stage();
        stage.setScene(s);
        stage.setTitle("Name Error");

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();

        btn_ok.setOnAction(e -> stage.close());




    }


    // check email (used in 'Add Scene' + 'Updated Scene')

    public static int checkEmail(String email){
        if (email.contains("@") && email.endsWith(".com")){
            return 0;
        }
        return 1;
    }

    public static void emailError(){

        // Error Message
        Label emailError = new Label("Invalid Email: Email should contain '@' and ends with '.com'");
        emailError.setTextFill(Color.RED);

        // OK Button
        Button btn_ok = new Button("OK");
        btn_ok.setPrefSize(50, 30);

        VBox root = new VBox(20);
        root.getChildren().addAll(emailError, btn_ok);

        root.setAlignment(Pos.CENTER);


        Scene s = new Scene(root, 450, 150);
        Stage stage = new Stage();
        stage.setScene(s);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Email Error");

        stage.show();

        btn_ok.setOnAction(e -> stage.close());




    }


    // ---------------------------------------------------------------------------------------------------------

    public static void update_std_Scene(Stage mainStage){

        // Title
        Label lb_title = new Label("- Update Student Information -");
        lb_title.setFont( Font.font("Times new Roman", 30));
        lb_title.setPadding(new Insets(20));


        // Text for ID TextFiled
        Label lb_id = new Label("Student ID:");
        lb_id.setFont( Font.font("Times new Roman", 15));
        lb_title.setPadding(new Insets(0,30,0,0));

        tf_id = new TextField();
        tf_id.setDisable(true);


        // Text for Name TextFiled
        Label lb_stdName = new Label("Student Name:");
        lb_stdName.setFont( Font.font("Times new Roman", 15));

        tf_stdName = new TextField();


        // Text for email TextFiled
        Label lb_stdEmail = new Label("Student Email: ");
        lb_stdEmail.setFont( Font.font("Times new Roman", 15));

        tf_stdEmail = new TextField();

        // ID container
        HBox hID = new HBox(40);
        hID.getChildren().addAll(lb_id, tf_id);
        hID.setPadding(new Insets(10));


        // Name Container
        HBox hName = new HBox(20);
        hName.getChildren().addAll(lb_stdName, tf_stdName);
        hName.setPadding(new Insets(10));


        // Email container
        HBox hEmail = new HBox(16);
        hEmail.getChildren().addAll(lb_stdEmail, tf_stdEmail);
        hEmail.setPadding(new Insets(10));

        // Contain all the Containers at Right side of the Scene
        VBox vRight = new VBox(20);
        vRight.getChildren().addAll(hID,hName,hEmail);
        vRight.setPadding(new Insets(20));



        // List Text
        Label lb_listText = new Label("Chose Student from the list: ");
        lb_listText.setFont(Font.font("Times new Roman", 15));


        // available student
        ComboBox<String> cmb_std_available = new ComboBox<>();
        if (CourseManagement.getStudents().size() == 0){
            cmb_std_available.getItems().add("No Students Added");

        }else {
            for (Student n : CourseManagement.getStudents()) {
                cmb_std_available.getItems().addAll(n.getName());
            }
        }
        cmb_std_available.setPromptText("Chose");
        cmb_std_available.setPrefSize(200,25);


        // Container of Left side of the Scene
        VBox vLift = new VBox(20);
        vLift.setPadding(new Insets(30,0,0,0));
        vLift.getChildren().addAll(lb_listText, cmb_std_available);
        vLift.setPadding(new Insets(20));


        // Update Button
        Button btn_update = new Button("Update Button");
        btn_update.setPrefSize(100,30);



        // Contain the Left side of the Scene and the Right side
        HBox hBox = new HBox(30);
        hBox.getChildren().addAll(vLift, vRight);
        hBox.setAlignment(Pos.CENTER);

        Label lb_std_num = new Label("Total Student : " + Student.counter);
        lb_std_num.setFont(Font.font("Trebuchet MS", 15));


        // The root contains the 'hBox' + the 'Update Button' + the 'Number of Student Label'
        VBox root = new VBox(20);
        root.getChildren().addAll(menuBar,lb_title, hBox,lb_std_num, btn_update);
        root.setAlignment(Pos.TOP_CENTER);


        Scene update_std_scene = new Scene(root,850, 500, Color.BEIGE);

        mainStage.setScene(update_std_scene);

        mainStage.show();


        cmb_std_available.setOnAction(e -> selected(cmb_std_available));
        btn_update.setOnAction(e -> updateButtonAction(cmb_std_available));

        // First Menu
        it_addStudent.setOnAction(e -> add_sdt_Scene(mainStage));
        it_viewDetails.setOnAction(e -> view_std_Scene(mainStage));

        // Second Menu
        it_addCourse.setOnAction(e -> add_course_Scene(mainStage));
        it_enrollStudent.setOnAction(e -> enrollInCourse_scene(mainStage));

        // Third Menu
        it_assignGrade.setOnAction(e -> assignGrade_Scene(mainStage));
    }

    public static void selected(ComboBox<String> cmb_std_available){
        String selectedName = cmb_std_available.getValue();

        Student s = CourseManagement.stdExist(selectedName);
        if (s != null) {
            tf_id.setText(Integer.toString(s.getId()));
            tf_stdName.setText(s.getName());
            tf_stdEmail.setText(s.getEmail());
        }
    }

    public static void updateButtonAction(ComboBox<String> cmb_std_available){
        if(checkName(tf_stdName.getText()) == 1){
            nameError();

        }else if(checkEmail(tf_stdEmail.getText()) == 1){
            emailError();

        }else {
            String selectedName = cmb_std_available.getValue();

            Student s = CourseManagement.stdExist(selectedName);

            if (s != null) {
                s.setName(tf_stdName.getText());
                s.setEmail(tf_stdEmail.getText());

                cmb_std_available.getItems().clear();
                if (CourseManagement.getStudents().size() == 0) {
                    cmb_std_available.getItems().add("No Students Added");

                } else {
                    for (Student n : CourseManagement.getStudents()) {
                        cmb_std_available.getItems().addAll(n.getName());
                    }
                }
                updatedSuccessfully(s);
                tf_stdName.clear();
                tf_stdEmail.clear();
            }
        }
    }

    public static void updatedSuccessfully(Student n){
        // successful message
        Label lb_message = new Label("Student Information Updated Successfully");
        lb_message.setFont(Font.font("Trebuchet MS", 20));

        // student id
        Label lb_std_id = new Label("Student ID: " + n.getId());

        // student name
        Label lb_std_name = new Label("Student Name: " + n.getName());

        // student email
        Label lb_std_email = new Label("Student Email: " + n.getEmail());

        // OK Button
        Button btn_ok = new Button("OK");
        btn_ok.setPrefSize(100,20);


        VBox root = new VBox(15);
        root.getChildren().addAll(lb_message,lb_std_id, lb_std_name, lb_std_email, btn_ok);

        root.setAlignment(Pos.CENTER);


        Scene s  = new Scene(root, 400, 200);

        Stage stage = new Stage();
        stage.setScene(s);
        stage.setTitle("Updated Successfully");

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();


        btn_ok.setOnAction(e -> stage.close());
    }

    // -------------------------------------------------------------------------------------------------

    public static void view_std_Scene(Stage mainStage){

        Label lb_title = new Label("- View Student Details -");
        lb_title.setFont(Font.font(24));

        Label lb_std_num = new Label("Total Student : " + Student.counter);
        lb_std_num.setFont(Font.font("Trebuchet MS", 15));



        ComboBox<String> cmb_std_available = new ComboBox<>();
        if (CourseManagement.getStudents().size() == 0){
            cmb_std_available.getItems().add("No Students Added");

        }else {
            for (Student n : CourseManagement.getStudents()) {
                cmb_std_available.getItems().addAll(n.getName());
            }
        }
        cmb_std_available.setPromptText("Chose");
        cmb_std_available.setPrefSize(200,25);


        VBox vTop = new VBox(20);
        vTop.getChildren().addAll(lb_title, lb_std_num);
        vTop.setAlignment(Pos.TOP_CENTER);



        // Table View to see  all Student info
        TableView<Course> tv_stdCourseInfo = new TableView<>();
        tv_stdCourseInfo.setMaxWidth(670);
        tv_stdCourseInfo.setMaxHeight(250);


        TableColumn<Course, String> courseCode = new TableColumn<>("Course Code");
        courseCode.setPrefWidth(200);
        courseCode.setCellValueFactory(new PropertyValueFactory<Course, String>("code"));


        TableColumn<Course, String> courseName = new TableColumn<>("Course name");
        courseName.setPrefWidth(280);
        courseName.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));


        // I couldn't link it to the 'grades' HashMap in the Student Class (If you know please let me know)
        TableColumn<Course, String> grade = new TableColumn<>("Course Grade");
        grade.setPrefWidth(190);
        grade.setCellValueFactory(new PropertyValueFactory<Course, String>("grades"));





        tv_stdCourseInfo.getColumns().add(courseCode);
        tv_stdCourseInfo.getColumns().add(courseName);
        tv_stdCourseInfo.getColumns().add(grade);

        ObservableList<Course> ol_stdCourseData = FXCollections.observableArrayList();
        tv_stdCourseInfo.setItems(ol_stdCourseData);


        VBox vStdCourseInfo = new VBox(20);
        vStdCourseInfo.getChildren().add(cmb_std_available);
        vStdCourseInfo.setAlignment(Pos.CENTER);


        lb_GPA = new Label("Overall GPA: " );
        lb_GPA.setFont(Font.font("Trebuchet MS", 15));



        VBox vCourseInfoGpa = new VBox(20);
        vCourseInfoGpa.getChildren().addAll(tv_stdCourseInfo, lb_GPA);
        vCourseInfoGpa.setAlignment(Pos.BOTTOM_CENTER);

        VBox root = new VBox(20);
        root.getChildren().addAll(menuBar, vTop, vStdCourseInfo, vCourseInfoGpa);


        Scene s = new Scene(root,850, 500, Color.BEIGE);

        mainStage.setScene(s);
        mainStage.show();

        cmb_std_available.setOnAction(e -> cmb_action(cmb_std_available, ol_stdCourseData));


        // First Menu
        it_addStudent.setOnAction(e -> add_sdt_Scene(mainStage));
        it_updateDetails.setOnAction(e -> update_std_Scene(mainStage));

        // Second Menu
        it_addCourse.setOnAction(e -> add_course_Scene(mainStage));
        it_enrollStudent.setOnAction(e -> enrollInCourse_scene(mainStage));

        // Third Menu
        it_assignGrade.setOnAction(e -> assignGrade_Scene(mainStage));

    }

    public static void cmb_action(ComboBox<String> cmb_std_available, ObservableList<Course> ol_stdCourseData){
        String selectedStudent = cmb_std_available.getValue();
        Student s = CourseManagement.stdExist(selectedStudent);

        ol_stdCourseData.clear();

        double gpa = CourseManagement.calculateOverallGrade(selectedStudent);
        if(s != null) {
            for (Course c : s.getEnrolledCourses()) {
                lb_GPA.setText("Overall GPA: "+ CourseManagement.convertGradeToPoints(gpa) + " (" + CourseManagement.convertGradeToLetter(gpa) + ")");
                ol_stdCourseData.addAll(c);
            }
        }
    }
}
