/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package ClassManagedBean;

import com.mycompany.project.entities.Rooms;
import com.mycompany.project.entities.Student;
import com.mycompany.project.services.SchoolSessionBeanLocal;
import com.mycompany.project.services.StudentSessionBeanLocal;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author e.hussien
 */
@Named(value = "studentManagedBean")
@ViewScoped
public class StudentManagedBean implements Serializable {

    @EJB
    private StudentSessionBeanLocal studentServicesLocal;
    @EJB
    private SchoolSessionBeanLocal schoolSessionBeanLocal;

    private List<Student> studentList;

    private List<Rooms> classList;

    private Student selectedStudent;

    private Student old;

    @PostConstruct
    public void start() {
        studentList = studentServicesLocal.getAllStudents();
        classList = schoolSessionBeanLocal.getAllClass();
    }

    public void open() {
        selectedStudent = new Student();
    }

    public void saveStudent() {
        try {
            // Check if the student has a class
            if (selectedStudent == null || selectedStudent.getRoomID() == null) {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Class is required");
                FacesContext.getCurrentInstance().addMessage(null, message);
                return;
            }
            boolean exists = studentList.stream()
                    .anyMatch(s -> s.getStudentName().equalsIgnoreCase(selectedStudent.getStudentName())
                    && s.getRoomID().equals(selectedStudent.getRoomID())
                    && (selectedStudent.getStudentID() == null || !s.getStudentID().equals(selectedStudent.getStudentID())));

            if (exists) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Student with same name already exists in this class");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }

            // Save or update the student in the database
            if (selectedStudent.getStudentID() == null) {
                studentServicesLocal.addStudent(selectedStudent);

                // Reload the student list
                studentList.add(selectedStudent);

                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Student added successfully");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
            selectedStudent = new Student();  // Reset selectedStudent for new data entry
            // Close the dialog
            PrimeFaces.current().executeScript("PF('manageStudentDialog').hide()");
            // Update UI components
            PrimeFaces.current().ajax().update("form:messages", "form:dt-students", "dialogs:manage-student-content");
        } catch (Exception e) {
            // Log the error for debugging
            e.printStackTrace();

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save student");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void deleteStudent(Student student) {
        try {
            if (student != null && student.getStudentID() != null) {

                Rooms deletedRoom = studentServicesLocal.findRoomById(student.getRoomID().getRoomID());
                // Remove from room list if applicable
                if (deletedRoom != null) {
                    studentServicesLocal.removeRoomFromStudent(deletedRoom, student);
                }
                studentServicesLocal.deleteStudent(student.getStudentID());
                studentList = studentServicesLocal.getAllStudents();
            }
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to delete student");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Student Removed Successfully"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-students");
    }

    public Student oldStudent(Student std) {
        Student oldStd = new Student();
        oldStd.setStudentName(std.getStudentName());
        return oldStd;
    }

    public void update() {

        // تحقق من وجود طالب بنفس الاسم في نفس الغرفة
        boolean exists = studentList.stream()
                .anyMatch(s -> s.getStudentName().equalsIgnoreCase(selectedStudent.getStudentName())
                && s.getStudentID() != selectedStudent.getStudentID());

        if (exists) {
            selectedStudent.setStudentName(old.getStudentName());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Student with same name already exists in this class");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("form:dt-students");
            old = null;
            return;
        }

        try {

            studentServicesLocal.updateStudent(selectedStudent);

            if (studentList.stream().noneMatch(s -> s.getStudentID().equals(selectedStudent.getStudentID()))) {
                studentList.add(selectedStudent);
                selectedStudent.getRoomID().getStudentList().add(selectedStudent);
            }
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Student updated successfully");
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update student");
            FacesContext.getCurrentInstance().addMessage(null, message);

        }

        PrimeFaces.current().executeScript("PF('manageStudentDialog').hide()");

        PrimeFaces.current().ajax().update("form:messages", "form:dt-students");
    }
    public void loadStudent(Student updateStudent) {
        this.selectedStudent = updateStudent;
        old = oldStudent(selectedStudent);
    }

    public void saveOrUpdateStudent() {
        if (selectedStudent.getStudentID() == null) {
            saveStudent();  // call existing save
        } else {
            update();  // call existing update
        }
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public Student getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Student selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public List<Rooms> getClassList() {
        return classList;
    }

    public void setClassList(List<Rooms> classList) {
        this.classList = classList;
    }

    public Student getOld() {
        return old;
    }

    public void setOld(Student old) {
        this.old = old;
    }

}
