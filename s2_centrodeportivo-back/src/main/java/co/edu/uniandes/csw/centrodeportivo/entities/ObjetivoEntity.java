/* 

* To change this license header, choose License Headers in Project Properties. 

* To change this template file, choose Tools | Templates 

* and open the template in the editor. 

 */
package co.edu.uniandes.csw.centrodeportivo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**  *
 *
 *
 * @author Leidy Romero  *
 */
@Entity
public class ObjetivoEntity extends BaseEntity implements Serializable {

    private String descripcion;

    private Boolean cumplio;
    private String fechaLimite;

    @PodamExclude
    @ManyToOne
    private DeportistaEntity deportista;
    
    @PodamExclude
    @ManyToOne
    private RutinaEntity rutina;   
     /**
     * Modela la asociacion 1...* entre las clases Objetivo Y (hacia) Deportista
     * se especifica fetch para especificar que no se deben cargar las
     * dos entidades al  mismo tiempo
     */
    @PodamExclude
    @OneToMany(mappedBy = "objetivo")
    private List<DeportistaEntity> casosExitosos = new ArrayList<DeportistaEntity>();

    @PodamExclude
    @ManyToOne
    private EspecialistaEntity especialista;
    
    /**
     * Devuelve los deportistas que en el historial, han completado este objetivo (casos exitosos)
     * @return Collection los deportistas
     */
    public List<DeportistaEntity> getCasosExitosos()
    {
      return this.casosExitosos;  
    }
    
    public void setCasosExitosos(List<DeportistaEntity> pDeportistas)
    {
      this.casosExitosos = pDeportistas;  
    }
    
    public void setDeportista(DeportistaEntity pDeportista)
    {
        this.deportista = pDeportista;
    }
    public DeportistaEntity getDeportista()
    {
       return deportista;
    }
        
    public void setEspecialista(EspecialistaEntity pEspecialista)
    {
        this.especialista = pEspecialista;
    }
    public EspecialistaEntity getEspecialista()
    {
       return especialista;
    }
    
    /**      *
     * Devuelve el DESCRIPCION del objetivo.      *
     * @return the id      *
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**      *
     * Modifica la DESCRIPCION del objetivo.      *
     * @param pDescripcion descripcion nueva      *
     */
    public void setDescripcion(String pDescripcion) {
        this.descripcion = pDescripcion;
    }

    /**      *
     * Devuelve el estado del objetivo del objetivo. 
     * @return si o no      *
     */
    public Boolean getCumplio() {
        return cumplio;
    }

    /**
     * Modifica el estado del objetivo.
     * @param pCumplio nuevo estado
     */
    public void setCumplio(Boolean pCumplio) {
        this.cumplio = pCumplio;
    }

    /**
     * Devuelve la fecha limite del objetivo del objetivo.
     * @return fecha
     */
    public String getFechaLimite() {
        return fechaLimite;
    }

    /**
     * Modifica la fecha limite del objetivo. 
     * @param pFechaLimite nueva fecha limite 
     */
    public void setFechaLimite(String pFechaLimite) {
        this.fechaLimite = pFechaLimite;
    }
    
 
    

}
