package com.example.coursesmanegment.logic;


import java.util.ArrayList;
import java.util.HashMap;


public class CourseManagement {
    // Available
    private static ArrayList<Student> students = new ArrayList<>();

    // Available courses
    private static ArrayList<Course> courses = new ArrayList<>();


    private static HashMap<Student, Integer> totalStdScore = new HashMap<>();


    public static ArrayList<Course> getCourses() {
        return courses;
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }



    public static void addStudent(String name, String email){
        Student n = stdExist(name);
        if(n == null) {
            students.add(new Student(name, email));
        }
    }

    public static int addCourse(String code, String name, int capacity){
        if (findCourse(code) == null) {
            courses.add(new Course(name, code, capacity));
            return 0;
        }
        return 1;
    }




    public static int enrollStudent(int stdId, String course_code){
        Student s = findStudent(stdId);
        Course C = findCourse(course_code);
        if (s != null && C != null) {
            for(Course c : s.getEnrolledCourses()) {
                if (c.equals(C)) {
                    return 2;
                }
            }
            if (s.courseEnrol(C) == 0) {
                return 0;
            }

        }
        return 1;
    }




    public static int assignGrade(int std_id, String code, int grade) {
        Student s = findStudent(std_id);
        Course c = findCourse(code);
        if(s != null) {
            if (c != null) {
                if(s.getEnrolledCourses().contains(c)) {
                    s.assignGrade(c, grade);
                    return 0;
                }
            }
        }
        return 1;
    }

    public static double calculateOverallGrade(String name){
        Student s = stdExist(name);
        if(s != null) {
            if(s.getGrades().size() > 0 ) {
                if (s.getEnrolledCourses().size() > 0) {
                    double average = 0;
                    for (Course i : s.getEnrolledCourses()) {
                        average = average + s.getGrades().get(i);
                    }
                    average = (average / (s.getGrades().size()));
                    totalStdScore.put(s, (int) average);
                    return average;
                }
            }
        }
        return 0.0;
    }


    public static String convertGradeToLetter(double grade){
        if (grade >= 94) {
            return "A+";
        } else if (grade >= 90) {
            return "A";
        } else if (grade >= 84) {
            return "B+";
        } else if (grade >= 80) {
            return "B";
        } else if (grade >= 74) {
            return "C+";
        }else if (grade >= 70){
            return "C";
        } else if (grade >= 60) {
            return "D";
        } else if (grade >= 50) {
            return "E";
        }else {
            return "F";
        }

    }

    public static double convertGradeToPoints(double grade){
        if (grade >= 94){
            return 4;
        }
        else if (grade >= 90) {
            return 3.7;
        }
        else if (grade >= 84) {
            return 3.3;
        }
        else if (grade >= 80) {
            return 3;
        }
        else if (grade >= 74) {
            return 2.7;
        }
        else if(grade >= 70){
            return 2.3;
        }
        else if (grade >= 60) {
            return 2;
        }
        else if (grade >= 50) {
            return 1;
        }
        else {
            return 0;
        }

    }







    public static Course findCourse(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }

    static Student findStudent(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public static Student stdExist(String name){
        for (Student s : students){
            if ( s.getName().equalsIgnoreCase(name) ){
                return s;
            }
        }
        return null;
    }

    public static Course courseExist(String name){
        for (Course c : courses){
            if ( c.getName().equalsIgnoreCase(name) ){
                return c;
            }
        }
        return null;
    }


    public static boolean isAlpah(String n){
        for(int i = 0; i<n.length(); i++){
            if (!Character.isLetter(n.charAt(i))){
                return false;
            }
        }
        return true;
    }
}