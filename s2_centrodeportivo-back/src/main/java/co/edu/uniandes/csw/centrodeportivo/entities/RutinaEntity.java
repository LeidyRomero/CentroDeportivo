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
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Francisco Jose MacAllister
 */
@Entity
public class RutinaEntity extends BaseEntity implements Serializable {
    private Integer identificadorRutina;
    private String nombre;
    private Boolean estadoTerminado;

    public Integer getIdentificadorRutina() {
        return identificadorRutina;
    }

    public void setIdentificadorRutina(Integer identificadorRutina) {
        this.identificadorRutina = identificadorRutina;
    }

    public Boolean getEstadoTerminado() {
        return estadoTerminado;
    }

    public void setEstadoTerminado(Boolean estadoTerminado) {
        this.estadoTerminado = estadoTerminado;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
     /**
     * Modela la asociacion 1...* entre las clases Especialista y Deportista
     * se especifica fetch para especificar que no se deben cargar las
     * dos entidades al  mismo tiempo
     */
    @PodamExclude
    @OneToMany(mappedBy = "rutina", fetch = javax.persistence.FetchType.LAZY)
    private List<ObjetivoEntity> objetivos = new ArrayList<ObjetivoEntity>();
    
    
    /**
     * Devuelve los deportistas que son asesorados por este deportista
     * @return Collection los deportistas
     */
    public List<ObjetivoEntity> getObjetivos()
    {
      return this.objetivos;  
    }
    public void setObjetivos(List<ObjetivoEntity> deportistas)
    {
        this.objetivos=deportistas;
    }
    
    
    
    @PodamExclude
    @OneToMany(mappedBy = "rutina", fetch = javax.persistence.FetchType.LAZY)
    List<EjercicioEntity> ejercicios =new ArrayList<EjercicioEntity>();
    
    
    /**
     * Devuelve los deportistas que son asesorados por este deportista
     * @return Collection los deportistas
     */
    public List<EjercicioEntity> getEjercicios()
    {
      return this.ejercicios;  
    }
    public void setEjercicios(List<EjercicioEntity> ejercicios)
    {
        this.ejercicios=ejercicios;
    }
}
