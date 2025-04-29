/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.mycompany.project.services;

import com.mycompany.project.entities.Rooms;
import com.mycompany.project.entities.Student;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author e.hussien
 */
@Local
public interface StudentSessionBeanLocal {

    List<Student> getAllStudents();

    void addStudent(Student student);

    void updateStudent(Student student);

    void deleteStudent(Integer studentId);

    Rooms removeRoomFromStudent(Rooms room, Student student);

    Rooms findRoomById(Integer classId);

}
