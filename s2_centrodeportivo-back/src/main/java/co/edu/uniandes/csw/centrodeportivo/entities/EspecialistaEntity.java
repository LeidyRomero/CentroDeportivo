/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @authorFrancisco Jose MacAllister y Leidy Romero
 */
@Entity
public class EspecialistaEntity extends BaseEntity implements Serializable {
  

    private String nombre;
    private String especialidad;
    private Integer cedula;

    @PodamExclude
    @OneToMany(mappedBy = "especialista", fetch = javax.persistence.FetchType.LAZY)
    private List<DeportistaEntity> deportistas = new ArrayList<DeportistaEntity>();

    /**
     * Devuelve los deportistas que son asesorados por este deportista
     * @return Collection los deportistas
     */
    public List<DeportistaEntity> getDeportistas()
    {
      return this.deportistas;  
    }
    public void setDeportistas(List<DeportistaEntity> deportistas)
    {
        this.deportistas=deportistas;
    }
    
     /**
     * Modela la asociacion 1...* entre las clases Especialista y Objetivo
     * se especifica fetch para especificar que no se deben cargar las
     * dos entidades al  mismo tiempo
     */
    @PodamExclude
    @OneToMany(mappedBy = "especialista", fetch = javax.persistence.FetchType.LAZY)
    private List <ObjetivoEntity> objetivos = new ArrayList<ObjetivoEntity>();
    /**
     * Devuelve los objetivos
     * @return Collection los objetivos
     */
    public List<ObjetivoEntity> getObjetivos()
    {
      return this.objetivos;  
    }
    public void getObjetivos(List<ObjetivoEntity> listaObjetivos)
    {
      objetivos=listaObjetivos;  
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

    public void setCedula(Integer cedula) {
        this.cedula=cedula;
    }
      public Integer getCedula() {
        return cedula;
    }

    
}
