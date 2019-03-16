/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;

import co.edu.uniandes.csw.centrodeportivo.entities.SeguimientoEntity;
import java.io.Serializable;

/**
 *
 * @author Lina Cardozo
 */
public class SeguimientoDTO implements Serializable {
    
    private Long id;
    private Integer tiempo;
    private Double calorias;
    private Double velocidadPromedio;
    private Double ritmoCardiacoPromedio;
    
    /**
     * Constructor por defecto
     */
    public SeguimientoDTO()
    {
        
    }
    
    /**
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param seguimientoEntity
     */
    public SeguimientoDTO(SeguimientoEntity seguimientoEntity) {
        if (seguimientoEntity != null) {
            this.id = seguimientoEntity.getId();
            this.tiempo = seguimientoEntity.getTiempo();
            this.calorias = seguimientoEntity.getCalorias();
            this.ritmoCardiacoPromedio = seguimientoEntity.getRitmoCardiacoPromedio();
            this.velocidadPromedio = seguimientoEntity.getVelocidadPromedio();
        }
    }

    /**
     * Devuelve el ID del seguimiento.
     *
     * @return el Id del seguimiento
     */
    public Long getId() 
    {
        return id;
    }
    
    /**
     * Devuelve el tiempo total invertido del deportista haciendo los ejercicios.
     *
     * @return el tiempo invertido
     */
    public Integer getTiempo()
    {
        return tiempo;
    }
    
    /**
     * Devuelve la cantidad total de calorías quemadas por un deportista.
     *
     * @return el las calorías quemadas
     */
    public Double getCalorias()
    {
        return calorias;
    }
    
    /**
     * Devuelve la velocidad promedio de un deportista haciendo los ejercicios.
     *
     * @return la velocidad promedio del deportista
     */
    public Double getVelocidadPromedio()
    {
        return velocidadPromedio;
    }
    
    /**
     * Devuelve el ritmo cardíaco promedio del deportista al hacer los ejercicios.
     *
     * @return el ritmo cardíaco promedio del deportista
     */
    public Double getRitmoCardiacoPromedio()
    {
        return ritmoCardiacoPromedio;
    }

    /**
     * Modifica el ID del seguimiento.
     *
     * @param id el Id a modificar
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Modifica el tiempo invertido por el deportista.
     *
     * @param tiempo el tiempo a modificar
     */
    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * Modifica la cantidad de calorías quemadas por el deportista.
     *
     * @param calorias la cantidad de calorías a modificar
     */
    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }

    /**
     * Modifica la velocidad promedio del deportista.
     *
     * @param velocidadPromedio la velocidad a modificar
     */
    public void setVelocidadPromedio(Double velocidadPromedio) {
        this.velocidadPromedio = velocidadPromedio;
    }

    /**
     * Modifica el ritmo cardíaco promedio del deportista.
     *
     * @param ritmoCardiacoPromedio el ritmo cardíaco a modificar
     */
    public void setRitmoCardiacoPromedio(Double ritmoCardiacoPromedio) {
        this.ritmoCardiacoPromedio = ritmoCardiacoPromedio;
    }
    
     /**
     * Convertir DTO a Entity
     *
     * @return Un Entity con los valores del DTO
     */
    public SeguimientoEntity toEntity() {
        SeguimientoEntity seguimientoEntity = new SeguimientoEntity();
        seguimientoEntity.setId(this.id);
        seguimientoEntity.setTiempo(this.tiempo);
        seguimientoEntity.setCalorias(this.calorias);
        seguimientoEntity.setRitmoCardiacoPromedio(this.ritmoCardiacoPromedio);
        seguimientoEntity.setVelocidadPromedio(this.velocidadPromedio);
        return seguimientoEntity;
    }
    
}