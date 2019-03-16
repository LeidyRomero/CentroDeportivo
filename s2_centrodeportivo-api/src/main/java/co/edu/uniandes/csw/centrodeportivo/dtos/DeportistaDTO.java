/* 
* To change this license header, choose License Headers in Project Properties. 
* To change this template file, choose Tools | Templates 
* and open the template in the editor. 
*/
package co.edu.uniandes.csw.centrodeportivo.dtos;

import java.io.Serializable;
import co.edu.uniandes.csw.centrodeportivo.entities.*;
import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Leidy Romero
 */
public class DeportistaDTO implements Serializable {

    private Long id;
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

    /** 
     * Constructor por defecto.
     */
    public DeportistaDTO() {}

    /** 
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en
     * la entidad que viene de argumento.
     *
     * @param deportistaEntity: Es la entidad que se va a convertir a DTO
     */
    public DeportistaDTO(DeportistaEntity deportistaEntity) {

        if (deportistaEntity != null) {
            this.id = deportistaEntity.getId();
            this.nombre = deportistaEntity.getNombre();
            this.sexo = deportistaEntity.getSexo();
            this.altura = deportistaEntity.getAltura();
            this.presionSanguinea = deportistaEntity.getPresionSanguinea();
            this.peso = deportistaEntity.getPeso();
            this.ritmoCardiaco = deportistaEntity.getRitmoCardiaco();
            this.medidaPiernas = deportistaEntity.getMedidaPiernas();
            this.medidaCintura = deportistaEntity.getMedidaCintura();
            this.medidaBrazos = deportistaEntity.getMedidaBrazos();
            this.cedula = deportistaEntity.getCedula();
            this.fechaNacimiento = deportistaEntity.getFechaNacimiento();
            this.ultimaActualizacionDatos = deportistaEntity.getUltimaActualizacionDatos();
        }
    }

    /**   
     * Devuelve el ID del deportista.  
     * @return the id  
     */
    public Long getId() { return id;}

    /**      *
     * Modifica el ID del deportista.      *
     * @param id the id to set      *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**      *
     * Devuelve la FECHA DE NACIMIENTO del deportista.      *
     * @return la fecha      *
     */
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**      *
     * Modifica la FECHA DE NACIMIENTO del deportista.      *
     * @param pFecha nueva fecha      *
     */
    public void setFechaNacimiento(String pFecha) {
        this.fechaNacimiento = pFecha;
    }

    /**      *
     * Devuelve la UULTIMA FECHA DE ACTUALIZACION DE DATOS del deportista.      *
     * @return la fecha      *
     */
    public String getUltimaActualizacionDatos() {
        return ultimaActualizacionDatos;
    }

    /**      *
     * Modifica la ULTIMA FECHA DE CTUALIZACION DE DATOS del deportista.      *
     * @param pFecha nueva fecha      *
     */
    public void setUltimaActualizacionDatos(String pFecha) {
        this.ultimaActualizacionDatos = pFecha;
    }

    /**      *
     * Devuelve el NOMBRE del deportista.      *
     * @return el nombre      *
     */
    public String getNombre() {
        return nombre;
    }

    /**      *
     * Modifica el NOMBRE del deportista.      *
     * @param pNombre el nombre nuevo      *
     */
    public void setNombre(String pNombre) {

        this.nombre = pNombre;

    }

    /**      *
     * Devuelve el SEXO del deportista.      *
     * @return el sexo      *
     */
    public Boolean getSexo() {

        return sexo;

    }

    /**      *
     * Modifica el SEXO del deportista.      *
     * @param pSexo sexo nuevo      *
     */
    public void setSexo(Boolean pSexo) {

        this.sexo = pSexo;

    }

    /**      *
     * Devuelve la ALTURA del deportista.      *
     * @return la altura      *
     */
    public Double getAltura() {

        return altura;

    }

    /**      *
     * Modifica la ALTURA del deportista.      *
     * @param pAltura altura nueva      *
     */
    public void setAltura(Double pAltura) {

        this.altura = pAltura;

    }

    /**      *
     * Devuelve el RITMO CARDIACO del deportista.      *
     * @return el ritmo cardiaco      *
     */
    public Integer getRitmoCardiaco() {

        return ritmoCardiaco;

    }

    /**      *
     * Modifica el RITMO CARDIACO del deportista.      *
     * @param pRitmo el ritmo cardiaco nuevo      *
     */
    public void setRitmoCardiaco(Integer pRitmo) {

        this.ritmoCardiaco = pRitmo;

    }

    /**      *
     * Devuelve la MEDIDA DE CINTURA del deportista.      *
     * @return la medida de cintura      *
     */
    public Double getMedidaCintura() {

        return medidaCintura;

    }

    /**      *
     * Modifica la MEDIDA DE LA CINTURA del deportista.      *
     * @param pMedida la nueva medida      *
     */
    public void setMedidaCintura(Double pMedida) {

        this.medidaCintura = pMedida;

    }

    /**      *
     * Devuelve la MEDIDA DE PIERNAS del deportista.      *
     * @return la medida de piernas      *
     */
    public Double getMedidaPiernas() {

        return medidaPiernas;

    }

    /**      *
     * Modifica la MEDIDA DE PIERNAS del deportista.      *
     * @param pMedida la nueva medida      *
     */
    public void setMedidaPiernas(Double pMedida) {

        this.medidaPiernas = pMedida;

    }

    /**      *
     * Devuelve la MEDIDA DE BRAZOS del deportista.      *
     * @return la medida de brazos      *
     */
    public Double getMedidaBrazos() {

        return medidaBrazos;

    }

    /**      *
     * Modifica la MEDIDA DE BRAZOS del deportista.      *
     * @param pMedida la nueva medida      *
     */
    public void setMedidaBrazos(Double pMedida) {

        this.medidaBrazos = pMedida;

    }

    /**      *
     * Devuelve el numero de identificacion del deportista.      *
     * @return la cedula      *
     */
    public Integer getCedula() {

        return cedula;

    }

    /**      *
     * Devuelve el PESO del deportista.      *
     * @return el peso      *
     */
    public Double getPeso() {

        return peso;

    }

    /**      *
     * Modifica el PESO del deportista.      *
     * @param pPeso el nuevo peso      *
     */
    public void setPeso(Double pPeso) {

        this.peso = pPeso;

    }

    /**      *
     * Convertir DTO a Entity      *
     *
     *
     * @return Un Entity con los valores del DTO      *
     */
    public DeportistaEntity toEntity() {

        DeportistaEntity deportistaEntity = new DeportistaEntity();
        deportistaEntity.setId(this.id);
        deportistaEntity.setNombre(this.nombre);
        deportistaEntity.setAltura(this.altura);
        deportistaEntity.setPresionSanguinea(this.presionSanguinea);
        deportistaEntity.setPeso(this.peso);
        deportistaEntity.setRitmoCardiaco(this.ritmoCardiaco);
        deportistaEntity.setMedidaCintura(this.medidaCintura);
        deportistaEntity.setMedidaBrazos(this.medidaBrazos);
        deportistaEntity.setMedidaPiernas(this.medidaPiernas);
        deportistaEntity.setUltimaActualizacionDatos(this.ultimaActualizacionDatos);

        return deportistaEntity;

    }

    @Override

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
