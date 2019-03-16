/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un implemento en la persistencia y permite su
 * serialización.
 *
 * @author Lina Cardozo
 */
@Entity
public class ImplementoEntity extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID =1L;
    
    @PodamExclude
    @ManyToOne
    private EjercicioEntity ejercicio;
    
    private String nombre;
    
    private Integer cantidadExistencias;
    
    public ImplementoEntity()
    {
        
    }
    
    /**
     * Devuelve el ejercicio que corresponde al implemento.
     *
     * @return Ejercicio que corresponde al Implemento.
     */
    public EjercicioEntity getEjercicio() 
    {
        return ejercicio;
    }

    /**
     * Modifica el ejercicio perteneciente al implemento.
     *
     * @param ejercicio El nuevo ejercicio.
     */
    public void setEjercicio(EjercicioEntity ejercicio) 
    {
        this.ejercicio = ejercicio;
    }
  
    /**
     * Devuelve el nombre del implemento.
     *
     * @return nombre del implemento
     */
    public String getNombre()
    {
        return nombre;
    }
    
    /**
     * Modifica el nombre del implemento.
     *
     * @param nombre El nombre a modificar
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * Devuelve la cantidad de existencias del implemento.
     *
     * @return cantidad de existencias del implemento
     */
    public Integer getCantidadExistencias() {
        return cantidadExistencias;
    }

    /**
     * Modifica la cantidad de existencias del implemento.
     *
     * @param cantidadExistencias La cantidad de existencias a modificar
     */
    public void setCantidadExistencias(Integer cantidadExistencias) {
        this.cantidadExistencias = cantidadExistencias;
    }
    
}
