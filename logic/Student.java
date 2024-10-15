package com.example.coursesmanegment.logic;


import java.util.ArrayList;
import java.util.HashMap;

public class Student {
    private String name;
    private int id;
    private String email;
    private ArrayList<Course> enrolledCourses;
    private HashMap<Course, Double> grades;
    public static int counter = 0;




    Student(String name, String email){
        counter++;
        setName(name);
        this.id = counter;
        setEmail(email);
        this.enrolledCourses = new ArrayList<>();
        this.grades = new HashMap<>();
    }


    public void setName(String name) {
        if (CourseManagement.isAlpah(name)) {
            this.name = name;
        }
    }

    public void setEmail(String email) {
        if (email.contains("@") && email.endsWith(".com")) {
            this.email = email;
        }
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public int getId() {
        return id;
    }

    public HashMap<Course, Double> getGrades() {
        return grades;
    }


    public ArrayList<Course> getEnrolledCourses() {
        return enrolledCourses;
    }


    public int courseEnrol(Course course){
        if((course.getEnrolment()) < course.getCapacity()) {
            enrolledCourses.add(course);
            course.enrolmentPlus();
            return 0;
        }
        return 1;
    }


    public void assignGrade(Course course, double grade){
        if(enrolledCourses.contains(course)){
            if (grade >= 0 && grade <= 100) {
                grades.put(course, grade);
            }
        }

    }

}
