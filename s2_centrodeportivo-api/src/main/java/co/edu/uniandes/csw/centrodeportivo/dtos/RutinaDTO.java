/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;

import co.edu.uniandes.csw.centrodeportivo.entities.RutinaEntity;
import java.io.Serializable;



/**
 *
 * @author Francisco Jose MacAllister
 */
public class RutinaDTO implements Serializable {
    
    private Long id;
    private Integer identificadorRutina;
    private String nombre;
    private Boolean estadoTerminado;
   
    public RutinaDTO()
    {
        
    }

    public RutinaDTO(RutinaEntity createRutina) {
        if(createRutina!=null)
        {
            this.estadoTerminado=createRutina.getEstadoTerminado();
            this.id=createRutina.getId();
            this.nombre=createRutina.getNombre();
        }
    }

    public int getIdentificadorRutina() {
        return identificadorRutina;
    }

    public void setIdentificadorRutina(int identificadorRutina) {
        this.identificadorRutina = identificadorRutina;
    }
      public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstadoTerminado() {
        return estadoTerminado;
    }

    public void setEstadoTerminado(boolean estadoTerminado) {
        this.estadoTerminado = estadoTerminado;
    }
     public RutinaEntity toEntity() {
        RutinaEntity rutinaEntity = new RutinaEntity();      
        rutinaEntity.setId(this.id);
        rutinaEntity.setNombre(this.nombre);
        rutinaEntity.setEstadoTerminado(this.estadoTerminado);
        return rutinaEntity;
    }
}
