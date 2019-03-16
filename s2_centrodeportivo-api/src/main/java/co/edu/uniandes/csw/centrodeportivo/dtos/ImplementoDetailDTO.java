/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;

import co.edu.uniandes.csw.centrodeportivo.entities.ImplementoEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Lina Cardozo
 */
public class ImplementoDetailDTO extends ImplementoDTO implements Serializable {

    private EjercicioDTO ejercicio;

    /**
     * Constructor por defecto
     */
    public ImplementoDetailDTO() {
        super();
    }

    /**
     * Crea un objeto ImplementoDetailDTO a partir de un objeto ImplementoEntity
     * incluyendo los atributos de ImplementoDTO.
     *
     * @param implementoEntity Entidad ImplementoEntity desde la cual se va a crear el
     * nuevo objeto.
     *
     */
    public ImplementoDetailDTO(ImplementoEntity implementoEntity) {
        super(implementoEntity);
        if (implementoEntity.getEjercicio() != null) {
            this.ejercicio = new EjercicioDTO(implementoEntity.getEjercicio());
        }
    }

    /**
     * Convierte un objeto ImplementoDetailDTO a ImplementoEntity incluyendo los atributos
     * de ImplementoDTO.
     *
     * @return Nueva objeto ImplementoEntity.
     *
     */
    @Override
    public ImplementoEntity toEntity() {
        ImplementoEntity implementoEntity = super.toEntity();
        if (ejercicio != null) {
            implementoEntity.setEjercicio(ejercicio.toEntity());
        }
        return implementoEntity;
    }

    /**
     * Modifica el ejercicio asociado a este implemento.
     *
     * @param ejercicio the ejercicio to set
     */
    public void setEjercicio(EjercicioDTO ejercicio) {
        this.ejercicio = ejercicio;
    }

    /**
     * Devuelve el ejercicio asociado a este implemento
     *
     * @return DTO de Ejercicio
     */
    public EjercicioDTO getEjercicio() {
        return ejercicio;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}