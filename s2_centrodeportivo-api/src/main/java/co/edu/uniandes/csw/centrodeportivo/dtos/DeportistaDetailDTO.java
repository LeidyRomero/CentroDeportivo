/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;

import java.util.List;
import co.edu.uniandes.csw.centrodeportivo.entities.*;
import java.io.Serializable;
import java.util.ArrayList;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Leidy Romero
 */
public class DeportistaDetailDTO extends DeportistaDTO implements Serializable {

    private List<ObjetivoDTO> objetivos;

    /**
     * Constructor por defecto
     */
    public DeportistaDetailDTO() 
    {
        super();
    }
    
    /**
     * Crea un nuevo objeto DepoortistaDetailDTO a partir de un objeto
     * DeportistaEntity incluyecdo los atributos de DeportistaDTO
     *
     * @param deportistaEntity Entidad DeportistaEntity desde la cual se va a
     * crear el nuevo objeto
     */
    public DeportistaDetailDTO(DeportistaEntity deportistaEntity) {
        super(deportistaEntity);
        if (deportistaEntity != null) {
            if (deportistaEntity.getObjetivos() != null)
            {
                objetivos = new ArrayList<>();
                for (ObjetivoEntity entityObjetivos : deportistaEntity.getObjetivos()) {
                    objetivos.add(new ObjetivoDTO(entityObjetivos));
                }
            }
        }
    }

    /**
     * Convierte un objeto DeportistaDetailDTO a DeportistaEntity incluyendo los
     * atributos de DeportistaDTO
     *
     * @return Nuevo objeto DeportistaEntity
     */
    @Override
    public DeportistaEntity toEntity() {
        DeportistaEntity deportista = super.toEntity();
        if (objetivos != null) {
            List<ObjetivoEntity> objetivosEntity = new ArrayList<>();
            for (ObjetivoDTO dtoObjetivo : objetivos) {
                objetivosEntity.add(dtoObjetivo.toEntity());
            }
            deportista.setObjetivos(objetivosEntity);
        }
        return deportista;
    }
    
    /**
     * Obtiene la lista de objetivos del deportista
     * @return los objetivos
     */
    public List<ObjetivoDTO> getObjetivos()
    {
        return objetivos;
    }
    
     /* Modificala lista de objetivos del deportista
     * @param pObjetivos objetivos nuevos
     */
    public void setObjetivos(List<ObjetivoDTO> pObjetivos)
    {
        this.objetivos = pObjetivos;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
