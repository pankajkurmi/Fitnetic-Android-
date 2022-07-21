package com.example.fitnetic;

public class Users {

    String Name,Experience,Fees,Qualification,Time,Pastwork,Achivements;

    public Users(){}

    public Users(String name, String experience, String fees,String qualification,String time,String pastwork,String achivements) {
        Name = name;
        Time = time;
        Experience = experience;
        Fees = fees;
        Qualification = qualification;
        Pastwork = pastwork;
        Achivements = achivements;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getQualification() {return Qualification;}

    public void setQualification (String qualification) {Qualification = qualification;}

    public String getExperience() {
        return Experience;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getFees() {
        return Fees;
    }

    public void setFees(String fees) {
        Fees = fees;
    }
    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getPastwork() {
        return Pastwork;
    }

    public void setPastwork(String pastwork) {
        Pastwork = pastwork;
    }

    public String getAchivements() {
        return Achivements;
    }

    public void setAchivements(String achivements) {
        Achivements = achivements;
    }
}
