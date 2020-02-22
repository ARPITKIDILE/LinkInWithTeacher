package com.example.newapp.ui.main;

public class statusItem  {

    private String professorName;
    private String status;
    private String image;
    private String email;
    private String password;
    private String occupation;

    public statusItem(String professorName, String status, String image, String email, String password, String occupation) {
        this.professorName = professorName;
        this.status = status;
        this.image = image;
        this.email = email;
        this.password = password;
        this.occupation = occupation;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }
}
