/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;

import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.MaquinaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class MaquinaDetailDTO extends MaquinaDTO implements Serializable
{
    
    private List<EjercicioDTO> ejercicios;

    /**
     * Constructor por defecto
     */
    public MaquinaDetailDTO() 
    {
        
    }

    /**
     * Crea un nuevo objeto MaquinaDetailDTO a partir de un objeto
     * MaquinaEntity incluyecdo los atributos de MaquinaDTO
     *
     * @param maquinaEntity Entidad Maquinaentity desde la cual se va a
     * crear el nuevo objeto
     */
    public MaquinaDetailDTO(MaquinaEntity maquinaEntity) {
        super(maquinaEntity);
        if (maquinaEntity != null) {
            ejercicios = new ArrayList<EjercicioDTO>();
            for (EjercicioEntity entityEjercicios : maquinaEntity.getEjercicios( )) {
                ejercicios.add(new EjercicioDTO(entityEjercicios));
            }
        }
    }
    
    /**
     * Convierte un objeto MaquinaDetailDTO a MaquinaEntity incluyendo los
     * atributos de MaquinaDTO
     *
     * @return Nuevo objeto MaquinaEntity
     */
    @Override
    public MaquinaEntity toEntity() {
        MaquinaEntity maquina = super.toEntity();
        if (ejercicios != null) {
            List<EjercicioEntity> ejerciciosEntity = new ArrayList<EjercicioEntity>();
            for (EjercicioDTO dtoEjercicio : ejercicios) {
                ejerciciosEntity.add(dtoEjercicio.toEntity());
            }
            maquina.setEjercicios(ejerciciosEntity);
        }
        return maquina;
    }

   
    /**
     * Devuelve la lista de ejercicios de la maquina
     *
     * @return lista de ejercicios
     */
    public List<EjercicioDTO> getEjercicios() 
    {
        return ejercicios;
    }

    /**
     * Modifica la lista de ejercicios de la maquina
     *
     * @param ejercicios nueva lista de ejercicios 
     */
    public void setEjercicios(List<EjercicioDTO> ejercicios) 
    {
        this.ejercicios = ejercicios;
    }  
}
