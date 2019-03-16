/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;

import co.edu.uniandes.csw.centrodeportivo.entities.EspecialistaEntity;
import java.io.Serializable;



/**
 *
 * @author Francisco Jose MacAllister
 */

public class EspecialistaDTO implements Serializable {
    private Long id;
    private int cedula;
    private String nombre;
    private String especialidad;
    
    public EspecialistaDTO()
    {
        
    }

    public EspecialistaDTO(EspecialistaEntity nuevoEspecialistaEntity) {
         if (nuevoEspecialistaEntity != null) {
            this.id = nuevoEspecialistaEntity.getId();
            this.nombre = nuevoEspecialistaEntity.getNombre();
            this.cedula=nuevoEspecialistaEntity.getCedula();
            this.especialidad=nuevoEspecialistaEntity.getEspecialidad();
        }
    }
    public long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id=id;
    }
    public int getCedula() {
        return cedula;
    }

    public void setCedula(int pCedula) {
        this.cedula=pCedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public EspecialistaEntity toEntity() {

        EspecialistaEntity especialistaEntity = new EspecialistaEntity();
        especialistaEntity.setId(this.id);
        especialistaEntity.setCedula(this.cedula);
        especialistaEntity.setNombre(this.nombre);
        especialistaEntity.setEspecialidad(this.especialidad);
        return especialistaEntity;

    }
   
}
