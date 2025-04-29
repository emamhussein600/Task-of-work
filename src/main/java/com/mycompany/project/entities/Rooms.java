/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.project.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author e.hussien
 */
@Entity
@Table(name = "Rooms")
@NamedQueries({
    @NamedQuery(name = "Rooms.findAll", query = "SELECT r FROM Rooms r "),
    @NamedQuery(name = "Rooms.findByRoomID", query = "SELECT r FROM Rooms r WHERE r.roomID = :roomID"),
    @NamedQuery(name = "Rooms.findByRoomName", query = "SELECT r FROM Rooms r WHERE r.roomName = :roomName"),
    @NamedQuery(name = "Rooms.findByRoomDescription", query = "SELECT r FROM Rooms r WHERE r.roomDescription = :roomDescription")})
public class Rooms implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Room_ID")
    private Integer roomID;
    @Size(max = 100)
    @Column(name = "Room_Name")
    private String roomName;
    @Size(max = 255)
    @Column(name = "Room_Description")
    private String roomDescription;
    @OneToMany(mappedBy = "roomID", fetch = FetchType.EAGER)
    private List<Student> studentList = new ArrayList<>();

    public Rooms() {
    }

    public Rooms(Integer roomID) {
        this.roomID = roomID;
    }

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.roomName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rooms other = (Rooms) obj;
        return Objects.equals(this.roomName, other.roomName);
    }


    
}
