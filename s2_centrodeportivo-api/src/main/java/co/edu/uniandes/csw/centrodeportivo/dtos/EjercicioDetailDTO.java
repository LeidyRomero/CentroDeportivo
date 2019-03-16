/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;


import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ImplementoEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.MaquinaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ZonaCuerpoEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ZonaCuerpoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author estudiante
 */
public class EjercicioDetailDTO extends EjercicioDTO implements Serializable
{
   /*
    * Esta lista de tipo ZonaCuerpoDTO contiene las implementos que estan asociadas a un rutina
     */
    private List<ZonaCuerpoDTO> zonasCuerpo;
    private List<ImplementoDTO> implementos;

    /**
     * Constructor por defecto
     */
    public EjercicioDetailDTO() 
    {
      
    }
    
     /**
     * Crea un nuevo objeto EjercicioDetailDTO a partir de un objeto
     * EjercicioEntity incluyecdo los atributos de EjercicioDTO
     *
     * @param ejercicioEntity Entidad DeportistaEntity desde la cual se va a
     * crear el nuevo objeto
     */
    public EjercicioDetailDTO(EjercicioEntity ejercicioEntity) {
        super(ejercicioEntity);
        if (ejercicioEntity != null) {
            zonasCuerpo = new ArrayList<ZonaCuerpoDTO>();
            for (ZonaCuerpoEntity entityZonasCuerpos : ejercicioEntity.getZonasCuerpo()) {
                zonasCuerpo.add(new ZonaCuerpoDTO(entityZonasCuerpos));
            }
            
            implementos = new ArrayList<ImplementoDTO>();
            for (ImplementoEntity entityImplementos : ejercicioEntity.getImplementos()) {
                implementos.add(new ImplementoDTO(entityImplementos));
            }
        }
    }
   
    /**
     * Devuelve la lista de zonasCuerpo del rutina.
     *
     * @return las zonasCuerpo
     */
    public List<ZonaCuerpoDTO> getZonasCuerpo() 
    {
        return zonasCuerpo;
    }

    /**
     * Modifica la lista de implementos del rutina.
     *
     * @param zonasCuerpo las implementos a modificar
     */
    public void setZonasCuerpo(List<ZonaCuerpoDTO> zonasCuerpo) 
    {
        this.zonasCuerpo = zonasCuerpo;
    }

    /**
     * Devuelve la lista de implementos del rutina.
     *
     * @return los implementos
     */
    public List<ImplementoDTO> getImplementos() 
    {
        return implementos;
    }

    /**
     * Modifica la lista de implementos del seguimiento.
     *
     * @param implementos las implementos a modificar
     */
    public void setImplementos(List<ImplementoDTO> implementos) 
    {
        this.implementos = implementos;
    }
    @Override
    public EjercicioEntity toEntity() {
        EjercicioEntity ejercicioEntity = super.toEntity();
        if (implementos != null) {
            List<ImplementoEntity> implementosEntity = new ArrayList<>();
            for (ImplementoDTO dtoImplemento : implementos) {
                implementosEntity.add(dtoImplemento.toEntity());
            }
            ejercicioEntity.setImplementos(implementosEntity);
        }
        if (zonasCuerpo != null) {
            List<ZonaCuerpoEntity> zonasCuerpoEntity = new ArrayList<>();
            for (ZonaCuerpoDTO dtoZonaCuerpo : zonasCuerpo) {
                zonasCuerpoEntity.add(dtoZonaCuerpo.toEntity());
            }
            ejercicioEntity.setZonasCuerpo(zonasCuerpoEntity);
        }
        return ejercicioEntity;
    }
    
    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
