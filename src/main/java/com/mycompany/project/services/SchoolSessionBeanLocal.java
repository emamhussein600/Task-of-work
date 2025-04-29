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
public interface SchoolSessionBeanLocal {

    //Class Entity CRUD
    List<Rooms> getAllClass();

    void addClass(Rooms newClass);

    public boolean classNameExists(String className, Integer excludeId);

    void removeClass(Rooms removedClass);

    Rooms findRoomById(Integer classId);
    
    Rooms getClassWithStudents(Integer roomId) ;
   
    public void updateRoom(Rooms room);

    
}
