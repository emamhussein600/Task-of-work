/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Converter;


import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


/**
 *
 * @author e.hussien
 */
@FacesConverter(value = "entityConverter")
public class EntityConverter implements Converter {

    private static Map<Object, String> entities = new WeakHashMap<Object, String>();

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object entity) {
        synchronized (entities) {
            if (!entities.containsKey(entity)) {
                String uuid = UUID.randomUUID().toString();
                entities.put(entity, uuid);
                return uuid;
            } else {
                return entities.get(entity);
            }
        }
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String uuid) {
        for (Map.Entry<Object, String> entry : entities.entrySet()) {
            if (entry.getValue().equals(uuid)) {
                return entry.getKey();
            }
        }
        return null;
    }
}

