package com.example.coursesmanegment.logic;




public class Course {
    private String name;
    private int capacity;
    private String code;
    private int enrolment = 0;
    public static int counter = 0;




    Course(String name, String code, int capacity){
        this.code = code;
        this.name = name;
        this.capacity = capacity;
        counter++;
    }

    public double getGrades(Course c, Student s){
        return s.getGrades().get(c);
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getCode() {
        return code;
    }

    public int getEnrolment() {
        return enrolment;
    }


    public static int getCounter(){
        return counter;
    }


    public void enrolmentPlus(){
        enrolment++;
    }

}
