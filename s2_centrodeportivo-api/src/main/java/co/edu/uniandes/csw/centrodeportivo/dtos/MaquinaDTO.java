/*
 * To change this license header, choose License Headers in Project Properties.|
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;

import co.edu.uniandes.csw.centrodeportivo.entities.MaquinaEntity;
import java.io.Serializable;

/**
 *
 * @author dy.quintero
 */
public class MaquinaDTO implements Serializable{
    
    private Long id;

   
    private String nombre;
    private String referencia;
    private Double calorias;
    private Integer tiempo;
    private Double velocidad;
    private Double velocidadPromedio;
    
    public MaquinaDTO()
    {
        
    }
    
    
    public MaquinaDTO(MaquinaEntity maquinaEntity)
    {
        if(maquinaEntity != null)
        {
            this.id = maquinaEntity.getId();
            this.nombre = maquinaEntity.getNombre();
            this.calorias = maquinaEntity.getCalorias();
            this.tiempo = maquinaEntity.getTiempo();
            this.velocidad = maquinaEntity.getVelocidad();
            this.velocidadPromedio = maquinaEntity.getVelocidadPromedio();
            this.referencia = maquinaEntity.getReferencia();
        }
    }
    
    /**
    *
    * Id unico de una maquina
     * @return 
    */
    public Long getId(){
        return id;
    }
     public void setId(Long id) {
        this.id = id;
    }
    /**
    * Metodo que devuelve las calorias quemadas en la maquina
    * @return calorias quemadas en la maquina
    */
    public Double getCalorias(){
        return calorias;
    }
    
    /**
    * Metodo que devuelve el tiempo de uso de la maquina
    * @return tiempo de uso de la maquina
    */
    public Integer getTiempo(){
        return tiempo;
    }
    
   /**
    * Metodo que devuelve la velocidad alcanzada en la la maquina
    * @return velocidad alcanzada en la maquina
    */
    public Double getVelocidad()
    {
        return velocidad;
    }
    
    /**
    * Metodo que devuelve la velocidad promedio
    * @return la velocidad promedio
    */
    public Double getVelocidadPromedio(){
        return velocidadPromedio;
    }
    /**
    * Metodo que devuelve el nombre de la maquina
    * @return la velocidad promedio
    */
    public String getNombre() {
        return nombre;
    }

     /**
    *
    * Metodo que cambia el nombre de la maquina
     * @param nombre
    */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
    *
    * Metodo que cambia la cantidad de calorias
     * @param pCalorias
    */
    public void setCalorias( Double pCalorias ){
        this.calorias = pCalorias;
    }
    
    /**
    *
    * Metodo que cambia el tiempo
     * @param pTiempo
    */
    public void setTiempo( Integer pTiempo ){
        this.tiempo = pTiempo;
    }
    
    /**
    *
    * Metodo que cambia la velocidad
     * @param pVelocidad
    */
    public void setVelocidad( Double pVelocidad )
    {
        this.velocidad = pVelocidad;
    }
    
    /**
    *
    * Metodo que cambia la velocidad promedio
     * @param pVelocidadPromedio
    */
    public void setVelocidadPromedio( Double pVelocidadPromedio ){
        this.velocidadPromedio = pVelocidadPromedio;
    }  
    
    /**
    * Metodo que devuelve la referencia de la maquina
    * @return la velocidad promedio
    */
    public String getReferencia() {
        return referencia;
    }

    
     /**
    *
    * Metodo que cambia la referencia promedio
     * @param pReferencia
    */
    public void setReferencia(String pReferencia) {
        this.referencia = pReferencia;
    }
    
    
    /**      *
     * Convertir DTO a Entity      *
     *
     *
     * @return Un Entity con los valores del DTO      *
     */
    public MaquinaEntity toEntity() 
    {
        MaquinaEntity maquinaEntity = new MaquinaEntity();
        maquinaEntity.setId(this.id);
        maquinaEntity.setNombre(this.nombre);
        maquinaEntity.setCalorias(this.calorias);
        maquinaEntity.setTiempo(this.tiempo);
        maquinaEntity.setVelocidad(this.velocidad);
        maquinaEntity.setVelocidadPromedio(this.velocidadPromedio);
        maquinaEntity.setReferencia(this.referencia);
        return maquinaEntity;
    }
}   
