/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author e.hussien
 */
@Entity
@Table(name = "Student")
@NamedQueries({
    @NamedQuery(name = "Student.findAll", query = "SELECT s FROM Student s"),
    @NamedQuery(name = "Student.findByStudentID", query = "SELECT s FROM Student s WHERE s.studentID = :studentID"),
    @NamedQuery(name = "Student.findByStudentName", query = "SELECT s FROM Student s WHERE s.studentName = :studentName"),
    @NamedQuery(name = "Student.findByGender", query = "SELECT s FROM Student s WHERE s.gender = :gender")})
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Student_ID")
    private Integer studentID;
    @Size(max = 100)
    @Column(name = "Student_Name")
    private String studentName;
    @Size(max = 10)
    @Column(name = "Gender")
    private String gender;
    @JoinColumn(name = "Room_ID", referencedColumnName = "Room_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Rooms roomID;

    public Student() {
    }

    public Student(Integer studentID) {
        this.studentID = studentID;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Rooms getRoomID() {
        return roomID;
    }

    public void setRoomID(Rooms roomID) {
        this.roomID = roomID;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Student other = (Student) obj;
        return Objects.equals(this.studentID, other.studentID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentID);
    }

}
