/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;

import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ZonaCuerpoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *aa
 * @author Daniel Pardo
 */
public class ZonaCuerpoDTO implements Serializable 
{
    /**
     * Id de la zona del cuerpo
     */
    private Long id;
    /**
     * Nombre de la zona del cuerpo
     */
    private String nombre;

    public ZonaCuerpoDTO()
    {
        
    }
    
    public ZonaCuerpoDTO(ZonaCuerpoEntity zonaCuerpoEntity) {

        if (zonaCuerpoEntity != null) {
            this.id = zonaCuerpoEntity.getId();
            this.nombre = zonaCuerpoEntity.getNombre();
               
        }
    }
    
    
    //Metodos
    
    /**
     * Metodo que retorna el id unico de la zona del cuerpo
     *
     * @return Id unico de la zona del cuerpo
     */
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Metodo que retorna el nombre de la zona del cuerpo
     *
     * @return el nombre de la zona del cuerpo
     */
    public String getNombre( )
    {
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public ZonaCuerpoEntity toEntity() 
    {        
        ZonaCuerpoEntity zonaCuerpoEntity = new ZonaCuerpoEntity();
        zonaCuerpoEntity.setId(this.id);
        zonaCuerpoEntity.setNombre(this.nombre);
        return zonaCuerpoEntity;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}
