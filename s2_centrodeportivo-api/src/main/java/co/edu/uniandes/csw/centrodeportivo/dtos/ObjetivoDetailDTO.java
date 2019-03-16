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
public class ObjetivoDetailDTO extends ObjetivoDTO implements Serializable{
   
    private List<DeportistaDTO> casosExitosos;
    
    /**
     * Constructor por defecto
     */
    public ObjetivoDetailDTO() {
        super();
    }
    
    /**
     * Crea un nuevo objeto ObjetivoDetailDTO a partir de un objeto
     * ObjetivoEntity incluyecdo los atributos de ObjetivoDTO
     *
     * @param objetivoEntity Entidad ObjetivoEntity desde la cual se va a
     * crear el nuevo objeto
     */
    public ObjetivoDetailDTO (ObjetivoEntity objetivoEntity)
    {
        super(objetivoEntity);
        if(objetivoEntity != null)
        {
            casosExitosos = new ArrayList<DeportistaDTO>();
            for(DeportistaEntity entityDeportista: objetivoEntity.getCasosExitosos())
            {
                casosExitosos.add(new DeportistaDTO(entityDeportista));
            }
        }
    }
    
     /**
     * Convierte un objeto ObjetivoDetailDTO a ObjetivoEntity incluyendo los
     * atributos de ObjetivoDTO
     *
     * @return Nuevo objeto ObjetivoEntity
     */
    @Override
    public ObjetivoEntity toEntity() {
        ObjetivoEntity objetivo = super.toEntity();
        if (casosExitosos != null) {
            List<DeportistaEntity> deportistasEntity = new ArrayList<DeportistaEntity>();
            for (DeportistaDTO dtoDeportista : casosExitosos) {
                deportistasEntity.add(dtoDeportista.toEntity());
            }
            objetivo.setCasosExitosos(deportistasEntity);
        }
        return objetivo;
    }
    
    /**
     * Obtiene la lista de casos exitosos del objetivo
     * @return los casos exitosos
     */
    public List<DeportistaDTO> getCasosExitosos()
    {
        return casosExitosos;
    }
    
     /* Modificala lista de casos exitosos del objetivo
     * @param pDeportistas deportistas nuevos
     */
    public void setCasosExitosos(List<DeportistaDTO> pDeportistas)
    {
        this.casosExitosos = pDeportistas;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
