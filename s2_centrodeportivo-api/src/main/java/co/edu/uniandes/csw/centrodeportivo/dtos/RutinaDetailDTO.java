/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;

import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.RutinaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;



/**
 *
 * @author Francisco Jose MacAllister
 */
public class RutinaDetailDTO  extends RutinaDTO implements Serializable {
    
    /*
    * Esta lista de tipo EjercicioDTO contiene las máquinas que estan asociadas a un rutina
     */
    private List<EjercicioDTO> ejercicios;
    private List<ObjetivoDTO> objetivos;

    /**
     * Constructor por defecto
     */
    public RutinaDetailDTO() 
    {
        
    }

    public RutinaDetailDTO(RutinaEntity rutinaEntity) {
        super(rutinaEntity);
        if (rutinaEntity != null) {
            if (rutinaEntity.getEjercicios() != null) {
                ejercicios = new ArrayList<>();
                for (EjercicioEntity entityEjercicio : rutinaEntity.getEjercicios()) {
                    ejercicios.add(new EjercicioDTO(entityEjercicio));
                }
            }
            if (rutinaEntity.getObjetivos() != null) {
                objetivos = new ArrayList<>();
                for (ObjetivoEntity entityObjetivo : rutinaEntity.getObjetivos()) {
                    objetivos.add(new ObjetivoDTO(entityObjetivo));
                }
            }
        }
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param rutinaEntity La entidad del rutina para transformar a DTO.
     */
    /*public RutinaDetailDTO(RutinaEntity rutinaEntity) {
        super(rutinaEntity);
        if (rutinaEntity != null) {
            if (rutinaEntity.getEjercicios() != null) {
                ejercicios = new ArrayList<>();
                for (EjercicioEntity entityEjercicio : rutinaEntity.getEjercicios()) {
                    ejercicios.add(new EjercicioDTO(entityEjercicio));
                }
            }
        }
    }*/

    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO del rutina para transformar a Entity
     */
    /*@Override
    public RutinaEntity toEntity() {
        RutinaEntity rutinaEntity = super.toEntity();
        if (ejercicios != null) {
            List<EjercicioEntity> ejerciciosEntity = new ArrayList<>();
            for (EjercicioDTO dtoEjercicio : ejercicios) {
                ejerciciosEntity.add(dtoEjercicio.toEntity());
            }
            rutinaEntity.setEjercicios(ejerciciosEntity);
        }
        return rutinaEntity;
    }*/

    /**
     * Devuelve la lista de máquinas del rutina.
     *
     * @return las máquinas
     */
    public List<EjercicioDTO> getEjercicios() 
    {
        return ejercicios;
    }

    /**
     * Modifica la lista de máquinas del rutina.
     *
     * @param ejercicios las máquinas a modificar
     */
    public void setEjercicios(List<EjercicioDTO> ejercicios) 
    {
        this.ejercicios = ejercicios;
    }

    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    public List<ObjetivoDTO> getObjetivos() 
    {
        return objetivos;
    }

    /**
     * Modifica la lista de máquinas del seguimiento.
     *
     * @param objetivos las máquinas a modificar
     */
    public void setObjetivos(List<ObjetivoDTO> objetivos) 
    {
        this.objetivos = objetivos;
    }

    public RutinaEntity toEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setId(Long rutinasId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
