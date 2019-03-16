/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Daniel Pardo
 */
@Entity
public class EjercicioEntity extends BaseEntity implements Serializable
{  
    //Atributos---------------
    private String nombre;
    
    private String categoria;
    
    private Integer duracion;
    
    private Integer numeroDeSeries;
    
    private String descripcion;
    
    private String explicacion;
    
    
    
    @PodamExclude
    @ManyToOne 
    private RutinaEntity rutina;
    
   @PodamExclude
    @OneToOne
   private MaquinaEntity maquina;
    
    @PodamExclude
   @OneToMany(mappedBy = "ejercicio",fetch=FetchType.LAZY)
    private List<ZonaCuerpoEntity> zonasCuerpo = new ArrayList<ZonaCuerpoEntity>();
    
    @PodamExclude
   @OneToMany(mappedBy = "ejercicio",fetch=FetchType.LAZY)
    private List<MaquinaEntity> maquinas = new ArrayList<MaquinaEntity  >();
      
   @PodamExclude
   @OneToMany(mappedBy = "ejercicio",fetch=FetchType.LAZY)
    private List<ImplementoEntity> implementos = new ArrayList<ImplementoEntity>();

    public RutinaEntity getRutina() {
        return rutina;
    }

    public void setRutina(RutinaEntity rutina) {
        this.rutina = rutina;
    }

   public MaquinaEntity getMaquina() {
        return maquina;
    }

    public void setMaquina(MaquinaEntity maquinaEntity) {
        this.maquina = maquinaEntity;
    }

    public List<ZonaCuerpoEntity> getZonasCuerpo()
    {
      return this.zonasCuerpo;  
    }
    
    public void setZonasCuerpo(List<ZonaCuerpoEntity> pZonasCuerpo)
    {
        this.zonasCuerpo = pZonasCuerpo;
    }
    
    public List<MaquinaEntity> getMaquinas()
    {
      return this.maquinas;  
    }
    
    public void setMaquinas(List<MaquinaEntity> pMaquina)
    {
        this.maquinas = pMaquina;
    }

    public List<ImplementoEntity> getImplementos() {
       return implementos;
   }

    public void setImplementos(List<ImplementoEntity> implementos) {
        this.implementos = implementos;
    }
    
    
    
    //---------------------------------------------------------------
    
    public String getNombre() {
        return nombre;
    }
   
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
     
    public String getCategoria() {
        return categoria;
    }
    
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public Integer getDuracion() {
        return duracion;
    }
    
     public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
     
    public Integer getNumeroDeSeries() {
        return numeroDeSeries;
    }
    
    public void setNumeroDeSeries(Integer numeroDeSeries) {
        this.numeroDeSeries = numeroDeSeries;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getExplicacion() {
        return explicacion;
    }
    
    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }
}
