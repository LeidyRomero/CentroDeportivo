/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;

import co.edu.uniandes.csw.centrodeportivo.entities.ImplementoEntity;
import java.io.Serializable;

/**
 *
 * @author Lina Cardozo
 */
public class ImplementoDTO implements Serializable {
    
    private Long id;
    private String nombre;
    private Integer cantidadExistencias;
    
    /**
     * Constructor por defecto
     */
    public ImplementoDTO()
    {
        
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param implementoEntity
     */
    public ImplementoDTO(ImplementoEntity implementoEntity) {
        if (implementoEntity != null) {
            this.id = implementoEntity.getId();
            this.nombre = implementoEntity.getNombre();
            this.cantidadExistencias = implementoEntity.getCantidadExistencias();
        }
    }
    
    /**
     * Modifica el ID del implemento.
     *
     * @param id el Id a modificar
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    
    /**
     * Devuelve el ID del implemento.
     *
     * @return el Id del implemento
     */
    public Long getId()
    {
        return id;
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
     * Devuelve el nombre del implemento.
     *
     * @return nombre del implemento
     */
    public String getNombre()
    {
        return nombre;
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
     * @param cantidadExistencias El nombre a modificar
     */
    public void setCantidadExistencias(Integer cantidadExistencias) {
        this.cantidadExistencias = cantidadExistencias;
    }
    
     /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public ImplementoEntity toEntity() {
        ImplementoEntity implementoEntity = new ImplementoEntity();
        implementoEntity.setId(this.id);
        implementoEntity.setNombre(this.nombre);
        implementoEntity.setCantidadExistencias(this.cantidadExistencias);
        return implementoEntity;
    }
    
}