/* 
* To change this license header, choose License Headers in Project Properties. 
* To change this template file, choose Tools | Templates 
* and open the template in the editor. 
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;

import java.io.Serializable;
import co.edu.uniandes.csw.centrodeportivo.entities.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Leidy Romero
 */
public class ObjetivoDTO implements Serializable {

    private Long id;
    private String descripcion;
    public Boolean cumplio;
    public String fechaLimite;

    /**      *
     * Constructor por defecto.      *
     */
    public ObjetivoDTO() {
    }

    /**      *
     * Conviertir Entity a DTO (Crea un nuevo DTO con los valores que recibe en      *
     * la entidad que viene de argumento.      *
     *
     * @param objetivoEntity: Es la entidad que se va a convertir a DTO      *
     */
    public ObjetivoDTO(ObjetivoEntity objetivoEntity) {

        if (objetivoEntity != null) {

            this.id = objetivoEntity.getId();
            this.descripcion = objetivoEntity.getDescripcion();
            this.cumplio = objetivoEntity.getCumplio();
            this.fechaLimite = objetivoEntity.getFechaLimite();

        }

    }

    /**      *
     * Devuelve el ID del objetivo.      *
     * @return the id      *
     */
    public Long getId() {
        return id;
    }

    /**      *
     * Modifica el ID del objetivo.      *
     * @param id el id nuevo      *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**      *
     * Devuelve la FECHA LIMITE del objetivo.      *
     * @return la fecha limite      *
     */
    public String getFechaLimite() {
        return fechaLimite;
    }

    /**      *
     * Modifica la FECHA LIMITE del objetivo.      *
     * @param pFecha la nueva fecha      *
     */
    public void setId(String pFecha) {
        this.fechaLimite = pFecha;
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
     * Devuelve el estado del objetivo del objetivo.      *
     * @return si o no      *
     */
    public Boolean getCumplio() {
        return cumplio;
    }

    /**      *
     * Modifica el estado del objetivo.      *
     */
    public void setCumplio() {
        this.cumplio = !cumplio;
    }

    /**      *
     * Convertir DTO a Entity      *
     *
     *
     * @return Un Entity con los valores del DTO      *
     */
    public ObjetivoEntity toEntity() {

        ObjetivoEntity objetivoEntity = new ObjetivoEntity();
        objetivoEntity.setId(this.id);
        objetivoEntity.setDescripcion(this.descripcion);
        objetivoEntity.setCumplio(this.cumplio);
        objetivoEntity.setFechaLimite(this.fechaLimite);
        return objetivoEntity;

    }

    @Override

    public String toString() {

        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);

    }

}
