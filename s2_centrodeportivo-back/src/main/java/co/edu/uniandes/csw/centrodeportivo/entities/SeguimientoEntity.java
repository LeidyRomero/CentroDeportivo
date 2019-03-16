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
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Clase que representa un seguimiento en la persistencia y permite su
 * serialización.
 *
 * @author Lina Cardozo
 */
@Entity
public class SeguimientoEntity extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @PodamExclude
    @OneToOne
    private DeportistaEntity deportista;
    
    @PodamExclude
    @OneToMany(mappedBy = "seguimiento")
    private List<MaquinaEntity> maquinas = new ArrayList<MaquinaEntity>();
    
    private Integer tiempo;
    private Double calorias;
    private Double velocidadPromedio;
    private Double ritmoCardiacoPromedio;
    
    public SeguimientoEntity()
    {
        
    }
    
    /**
     * Devuelve el deportista del seguimiento.
     *
     * @return Deportista que realiza el Seguimiento.
     */
    public DeportistaEntity getDeportista() 
    {
        return deportista;
    }

    /**
     * Modifica el deportista del seguimiento.
     *
     * @param deportista El nuevo deportista.
     */
    public void setDeportista(DeportistaEntity deportista) 
    {
        this.deportista = deportista;
    }
    
    /**
     * Devuelve las máquinas del seguimiento.
     *
     * @return Lista de máquinas de Seguimiento.
     */
    public List<MaquinaEntity> getMaquinas() 
    {
        return this.maquinas;
    }

    /**
     * Modifica las máquinas del seguimiento.
     *
     * @param maquinas Las nuevas máquinas.
     */
    public void setMaquinas(List<MaquinaEntity> maquinas) 
    {
        this.maquinas = maquinas;
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
    
}
