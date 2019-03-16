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
import javax.persistence.*;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * @author Leidy Romero
 */
@Entity
public class DeportistaEntity extends BaseEntity implements Serializable {

    private String nombre;
    private Boolean sexo;
    private Double altura;
    private Double presionSanguinea;
    private Double peso;
    private Integer ritmoCardiaco;
    private Double medidaPiernas;
    private Double medidaCintura;
    private Double medidaBrazos;
    private Integer cedula;
    private String ultimaActualizacionDatos;
    private String fechaNacimiento;

    @PodamExclude
    @ManyToOne
    private EspecialistaEntity especialista;
    
    @PodamExclude
    @OneToMany(mappedBy = "deportista")
    private List<ObjetivoEntity> objetivos = new ArrayList<ObjetivoEntity>();
    
    @PodamExclude
    @ManyToOne
    private ObjetivoEntity objetivo;
    
    @PodamExclude
    @OneToOne(mappedBy = "deportista")
    private SeguimientoEntity seguimiento;
    
    /**
     * Devuelve los objetivos del deportista
     * @return Collection los objetivos
     */
    public List<ObjetivoEntity> getObjetivos()
    {
      return this.objetivos;  
    }
    
    public void setObjetivo(ObjetivoEntity pObjetivo)
    {
        this.objetivo = pObjetivo;
    }
    public void setSeguimiento(SeguimientoEntity pSeguimiento )
    {
        this.seguimiento = pSeguimiento;
    }
    public SeguimientoEntity getSeguimiento()
    {
       return seguimiento;
    }
    public void setObjetivos(List<ObjetivoEntity> pObjetivos)
    {
        this.objetivos = pObjetivos;
    }
    
    public ObjetivoEntity getObjetivo()
    {
       return objetivo;
    }
    
    public void setEspecialista(EspecialistaEntity pEspecialista)
    {
        this.especialista = pEspecialista;
    }
    public EspecialistaEntity getEspecialista()
    {
       return especialista;
    }
    
    /**
     * Devuelve el NOMBRE del deportista.
     *
     * @return el nombre *
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el NOMBRE del deportista.
     *
     * @param pNombre el nombre nuevo *
     */
    public void setNombre(String pNombre) {
        this.nombre = pNombre;
    }

    /**
     * *
     * Devuelve el SEXO del deportista
     *
     * @return el sexo *
     */
    public Boolean getSexo() {

        return sexo;

    }

    /**
     * *
     * Modifica el SEXO del deportista.
     *
     *
     * @param pSexo sexo nuevo *
     */
    public void setSexo(Boolean pSexo) {

        this.sexo = pSexo;

    }

    /**
     * *
     * Devuelve la ALTURA del deportista.
     *
     *
     * @return la altura *
     */
    public Double getAltura() {

        return altura;

    }

    /**
     * *
     * Modifica la ALTURA del deportista.
     *
     *
     * @param pAltura altura nueva *
     */
    public void setAltura(Double pAltura) {

        this.altura = pAltura;

    }

    /**
     * *
     * Devuelve el RITMO CARDIACO del deportista.
     *
     *
     * @return el ritmo cardiaco *
     */
    public Integer getRitmoCardiaco() {

        return ritmoCardiaco;

    }

    /**
     * *
     * Modifica el RITMO CARDIACO del deportista.
     *
     *
     * @param pRitmo el ritmo cardiaco nuevo *
     */
    public void setRitmoCardiaco(Integer pRitmo) {

        this.ritmoCardiaco = pRitmo;

    }

    /**
     * *
     * Devuelve la MEDIDA DE CINTURA del deportista.
     *
     *
     * @return la medida de cintura *
     */
    public Double getMedidaCintura() {

        return medidaCintura;

    }

    /**
     * *
     * Modifica la MEDIDA DE LA CINTURA del deportista.
     *
     *
     * @param pMedida la nueva medida *
     */
    public void setMedidaCintura(Double pMedida) {

        this.medidaCintura = pMedida;

    }

    /**
     * *
     * Devuelve la MEDIDA DE PIERNAS del deportista.
     *
     *
     * @return la medida de piernas *
     */
    public Double getMedidaPiernas() {

        return medidaPiernas;

    }

    /**
     * *
     * Modifica la MEDIDA DE PIERNAS del deportista.
     *
     *
     * @param pMedida la nueva medida *
     */
    public void setMedidaPiernas(Double pMedida) {

        this.medidaPiernas = pMedida;

    }

    /**
     * *
     * Devuelve la MEDIDA DE BRAZOS del deportista.
     *
     *
     * @return la medida de brazos *
     */
    public Double getMedidaBrazos() {

        return medidaBrazos;

    }

    /**
     * *
     * Modifica la MEDIDA DE BRAZOS del deportista.
     *
     *
     * @param pMedida la nueva medida *
     */
    public void setMedidaBrazos(Double pMedida) {

        this.medidaBrazos = pMedida;

    }

    /**
     * *
     * Devuelve el numero de identificacion del deportista.
     *
     *
     * @return la cedula *
     */
    public Integer getCedula() {

        return cedula;

    }

    /**
     * *
     * Devuelve el PESO del deportista.
     *
     *
     * @return el peso *
     */
    public Double getPeso() {

        return peso;

    }

    /**
     * *
     * Modifica el PESO del deportista.
     *
     *
     * @param pPeso el nuevo peso *
     */
    public void setPeso(Double pPeso) {

        this.peso = pPeso;

    }

    /**
     * *
     * Devuelve la PRESION SANGUINEA del deportista.
     *
     *
     * @return la presion sanguinea *
     */
    public Double getPresionSanguinea() {

        return presionSanguinea;

    }

    /**
     * *
     * Modifica la PRESION SANGUINEA del deportista.
     *
     *
     * @param pPresion presion nueva *
     */
    public void setPresionSanguinea(Double pPresion) {

        this.presionSanguinea = pPresion;

    }

    /**
     * *
     * Devuelve la FECHA DE NACIMIENTO del deportista.
     *
     *
     * @return la fecha *
     */
    public String getFechaNacimiento() {

        return fechaNacimiento;

    }
/**
     * *
     * odifica la FECHA DE NACIMIENTO del deportista.
     *
     *@pFecha pFecha fecha nueva
     */
    public void setFechaNacimiento(String pFecha) {

        this.fechaNacimiento=pFecha;
    }
    /**
     * *
     * Devuelve la ULTIMA FECHA DE CTUALIZACION DE DATOS del deportista.
     *
     * @return la fecha *
     */
    public String getUltimaActualizacionDatos() {
        return ultimaActualizacionDatos;
    }

    /**
     * *
     * Modifica la ULTIMA FECHA DE ACTUALIZACION DE DATOS del deportista.
     *
     * @param pUltimaActualizacionDatos fecha nueva
     */
    public void setUltimaActualizacionDatos(String pUltimaActualizacionDatos) {
        this.ultimaActualizacionDatos = pUltimaActualizacionDatos;
    }

}
