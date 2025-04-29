/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package ClassManagedBean;

import com.mycompany.project.entities.Rooms;
import com.mycompany.project.services.SchoolSessionBeanLocal;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import static java.util.Arrays.stream;
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
@Named(value = "classManagedBean")
@ViewScoped
public class ClassManagedBean implements Serializable {

    //inject objects that you use
    @EJB
    private SchoolSessionBeanLocal schoolLocal;
    private List<Rooms> classList;
    private Rooms selectedClass;

    private Rooms oldRoom;

    public Rooms getOldRoom() {
        return oldRoom;
    }

    public void setOldRoom(Rooms oldRoom) {
        this.oldRoom = oldRoom;
    }

    @PostConstruct
    public void intiate() {
        classList = schoolLocal.getAllClass();
    }

    public void open() {
        selectedClass = new Rooms();
    }

    public void saveClass() {
        if (selectedClass == null || selectedClass.getRoomName() == null || selectedClass.getRoomDescription() == null || selectedClass.getRoomName().trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Fields  are required"));
            PrimeFaces.current().ajax().update("form:messages");
            return;
        }

        boolean exists = classList.stream().anyMatch(c -> c.getRoomName().equalsIgnoreCase(selectedClass.getRoomName())
                && c.getRoomDescription().equalsIgnoreCase(selectedClass.getRoomDescription()));

        if (exists) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Class Already Exists"));
            PrimeFaces.current().ajax().update("form:messages");
            return;
        }

        boolean isNew = selectedClass.getRoomID() == null;

        schoolLocal.addClass(selectedClass);

        if (isNew) {
            classList.add(selectedClass);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Class Added Successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Class Updated"));
        }

        selectedClass = new Rooms();
        PrimeFaces.current().executeScript("PF('manageClassDialog').hide()");
        PrimeFaces.current().ajax().update("form:messages", "form:dt-classes");
    }

    public void deleteClass() {
        if (!selectedClass.getStudentList().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Class has students"));
            PrimeFaces.current().ajax().update("form:messages", "form:dt-classes");
            return;
        }
        schoolLocal.removeClass(selectedClass);
        classList.remove(selectedClass);
        selectedClass = new Rooms();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Class Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-classes");

    }

    public void update() {
        boolean exist = classList.stream().anyMatch(c -> c.getRoomName().equalsIgnoreCase(selectedClass.getRoomName())
                && c.getRoomID() != selectedClass.getRoomID());

        if (exist) {
            selectedClass.setRoomName(oldRoom.getRoomName());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Class with same name already exists");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("form:dt-classes");
            oldRoom = null;
            return;
        }
        try {
            schoolLocal.updateRoom(selectedClass);
            if(classList.stream().noneMatch(c-> c.getRoomID().equals(selectedClass.getRoomID()))){
                classList.add(selectedClass);
            }
            
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Student updated successfully");
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to update class");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
        PrimeFaces.current().executeScript("PF('manageClassDialog').hide()");

        PrimeFaces.current().ajax().update("form:messages", "form:dt-classes");
    }
        public void saveOrUpdateClass() {
        if (selectedClass.getRoomID() == null) {
            saveClass();  // call existing save
        } else {
            update();  // call existing update
        }
    }

    public Rooms OldRoom(Rooms room) {
        Rooms oldRoom = new Rooms();
        oldRoom.setRoomName(room.getRoomName());
        return oldRoom;
    }

    public void loadClass(Rooms updatedRoom) {
        this.selectedClass = updatedRoom;
        oldRoom = OldRoom(this.selectedClass);
    }

    public void prepareToDelete(Rooms c) {
        this.selectedClass = c;
    }

    public List<Rooms> getClassList() {
        return classList;
    }

    public void setClassList(List<Rooms> classList) {
        this.classList = classList;
    }

    public Rooms getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(Rooms selectedClass) {
        this.selectedClass = selectedClass;
    }

}
