package com.cg.dev.context.xml;

import java.util.List;

public class Student {
    private String name;

    private int age;

    private List list;

    public List getList() {
        return list;
    }


    public void setList(List list) {
        this.list = list;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }

    public Student(String name) {
        this.name = name;
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", list=" + list +
                '}';
    }

    public Student(List list) {
        this.list = list;
    }

    public Student(List list, String name) {
        this.name = name;
        this.list = list;
    }
}
