/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;

import co.edu.uniandes.csw.centrodeportivo.entities.DeportistaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.EspecialistaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;



/**
 *
 * @author Francisco Jose MacAllister
 */

public class EspecialistaDetailDTO extends EspecialistaDTO implements Serializable {
    /*
    * Esta lista de tipo DeportistaDTO contiene las máquinas que estan asociadas a un especialista
     */
    private List<DeportistaDTO> deportistas;
    private List<ObjetivoDTO> objetivos;

    /**
     * Constructor por defecto
     */
    public EspecialistaDetailDTO() 
    {
        
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param especialistaEntity La entidad del especialista para transformar a DTO.
     */
    public EspecialistaDetailDTO(EspecialistaEntity especialistaEntity) {
        super(especialistaEntity);
        if (especialistaEntity != null) {
            if (especialistaEntity.getDeportistas() != null) {
                deportistas = new ArrayList<>();
                for (DeportistaEntity entityDeportista : especialistaEntity.getDeportistas()) {
                    deportistas.add(new DeportistaDTO(entityDeportista));
                }
            }
            if (especialistaEntity.getObjetivos() != null) {
                objetivos = new ArrayList<>();
                for (ObjetivoEntity entityObjetivo : especialistaEntity.getObjetivos()) {
                    objetivos.add(new ObjetivoDTO(entityObjetivo));
                }
            }
        }
    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return El DTO del especialista para transformar a Entity
     */
    /*@Override
    public EspecialistaEntity toEntity() {
        EspecialistaEntity especialistaEntity = super.toEntity();
        if (deportistas != null) {
            List<DeportistaEntity> deportistasEntity = new ArrayList<>();
            for (DeportistaDTO dtoDeportista : deportistas) {
                deportistasEntity.add(dtoDeportista.toEntity());
            }
            especialistaEntity.setDeportistas(deportistasEntity);
        }
        return especialistaEntity;
    }*/

    /**
     * Devuelve la lista de máquinas del especialista.
     *
     * @return las máquinas
     */
    public List<DeportistaDTO> getDeportistas() 
    {
        return deportistas;
    }

    /**
     * Modifica la lista de máquinas del especialista.
     *
     * @param deportistas las máquinas a modificar
     */
    public void setDeportistas(List<DeportistaDTO> deportistas) 
    {
        this.deportistas = deportistas;
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
}
