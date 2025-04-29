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
import javax.persistence.TypedQuery;

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
    public boolean classNameExists(String name, Integer excludeId) {
        String jpql;
        TypedQuery<Long> query;

        if (excludeId == null) {
            jpql = "SELECT COUNT(r) FROM Rooms r WHERE r.roomName = :name";
            query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("name", name);
        } else {
            jpql = "SELECT COUNT(r) FROM Rooms r WHERE r.roomName = :name AND r.roomID <> :id";
            query = entityManager.createQuery(jpql, Long.class);
            query.setParameter("name", name);
            query.setParameter("id", excludeId);
        }

        Long count = query.getSingleResult();
        return count > 0;
    }

    @Override
    public void removeClass(Rooms removedClass) {
        removedClass = entityManager.merge(removedClass);
        entityManager.remove(removedClass);
    }

    @Override
    public Rooms findRoomById(Integer classId) {
        if (classId == null) {
            return null; // أو يمكن إرجاع كائن فارغ حسب الحاجة
        }
        Rooms mergedRoom = entityManager.find(Rooms.class, classId);
        return mergedRoom;

    }

    @Override
    public Rooms getClassWithStudents(Integer roomId) {
        Rooms room = entityManager.find(Rooms.class, roomId);
        room.getStudentList().size(); 
        return room;
    }

}
