package com.code.codepractice.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class Student extends Person implements Serializable {

    private String grade;

    public Student(String code, String name) {
        super(code, name);
    }

    public Student(String grade) {
        this.grade = grade;
    }
}
