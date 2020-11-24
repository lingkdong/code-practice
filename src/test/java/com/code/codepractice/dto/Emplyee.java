package com.code.codepractice.dto;

public class Emplyee extends Person {
    private String jobTile;

    public Emplyee(String code, String name) {
        super(code, name);
    }

    public String getJobTile() {
        return jobTile;
    }

    public void setJobTile(String jobTile) {
        this.jobTile = jobTile;
    }


}
