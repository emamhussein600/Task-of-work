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
public class SchoolSessionBean implements SchoolSessionBeanLocal {

    @PersistenceContext(name = "my_persistence_unit")
    private EntityManager entityManager;

    @Override
    public List<Rooms> getAllClass() {
        return entityManager.createNamedQuery("Rooms.findAll", Rooms.class).getResultList();
    }

    @Override
    public void addClass(Rooms newClass) {
        entityManager.persist(newClass);
    }

    @Override
    public void updateRoom(Rooms room) {
        entityManager.merge(room);
    }
    
        @Override
    public Rooms getClassWithStudents(Integer roomId) {
        Rooms room = entityManager.find(Rooms.class, roomId);
        room.getStudentList().size();
        return room;
    }


    @Override
    public void removeClass(Rooms removedClass) {
        removedClass = entityManager.merge(removedClass);
        entityManager.remove(removedClass);
    }

    @Override
    public Rooms findRoomById(Integer classId) {
        if (classId == null) {
            return null;
        }
        Rooms mergedRoom = entityManager.find(Rooms.class, classId);
        return mergedRoom;

    }


}
