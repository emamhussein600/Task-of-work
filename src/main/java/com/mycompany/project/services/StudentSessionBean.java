/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package com.mycompany.project.services;

import com.mycompany.project.entities.Rooms;
import com.mycompany.project.entities.Student;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author e.hussien
 */
@Stateless
public class StudentSessionBean implements StudentSessionBeanLocal {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager entityManager;

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = entityManager.createQuery(
                "select s from Student s join fetch s.roomID", Student.class)
                .getResultList();
        return students;
    }

    @Override
    public void addStudent(Student student) {
        if (student.getStudentID() == null) {
            entityManager.persist(student);
        } else {
            entityManager.merge(student);
        }
    }

    @Override
    public void updateStudent(Student student) {
        entityManager.merge(student);
        entityManager.flush();
    }

    @Override
    public void deleteStudent(Integer studentId) {
        try {
            Student student = entityManager.find(Student.class, studentId);
            if (student != null) {
                entityManager.remove(student);
            }
        } catch (Exception e) {
            // Handle exception
        }
    }

    @Override
    public Rooms removeRoomFromStudent(Rooms room, Student student) {
        Rooms deletedRoom = entityManager.find(Rooms.class, room.getRoomID());
        Student deletedStudent = entityManager.find(Student.class, student.getStudentID());

        if (deletedRoom != null) {
            deletedRoom.getStudentList().remove(deletedStudent);
        }
        return entityManager.merge(deletedRoom);
    }

    @Override
    public Rooms findRoomById(Integer classId) {
        if (classId == null) {
            return null; 
        }
        Rooms mergedRoom = entityManager.find(Rooms.class, classId);
        return (mergedRoom);

    }


}
