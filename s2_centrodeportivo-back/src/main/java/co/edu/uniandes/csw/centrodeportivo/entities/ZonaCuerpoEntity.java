/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Daniel Pardo
 */
@Entity
public class ZonaCuerpoEntity  extends BaseEntity implements Serializable
{    
    @PodamExclude
    @ManyToOne
    private EjercicioEntity ejercicio;
    
    private String nombre;
    
    /**
     * Devuelve el ejercicio que corresponde al zonaCuerpo.
     *
     * @return Ejercicio que corresponde al ZonaCuerpo.
     */
    public EjercicioEntity getEjercicio() 
    {
        return ejercicio;
    }

    /**
     * Modifica el ejercicio perteneciente al zonaCuerpo.
     *
     * @param ejercicio El nuevo ejercicio.
     */
    public void setEjercicio(EjercicioEntity ejercicio) 
    {
        this.ejercicio = ejercicio;
    }
  
    /**
     * Devuelve el nombre del zonaCuerpo.
     *
     * @return nombre del zonaCuerpo
     */
    public String getNombre()
    {
        return nombre;
    }
    
    /**
     * Modifica el nombre del zonaCuerpo.
     *
     * @param nombre El nombre a modificar
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

}
