package com.example.vis.model;

public class ProjectModel {
    public String email;
    public String projectName;
    public String slots;

    public ProjectModel() {
    }

    public ProjectModel(String email, String projectName, String slots) {
        this.email = email;
        this.projectName = projectName;
        this.slots = slots;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }
}
