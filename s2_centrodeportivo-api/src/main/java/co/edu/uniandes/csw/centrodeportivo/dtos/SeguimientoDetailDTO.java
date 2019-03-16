/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.dtos;

import co.edu.uniandes.csw.centrodeportivo.entities.MaquinaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.SeguimientoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Lina Cardozo
 */
public class SeguimientoDetailDTO extends SeguimientoDTO implements Serializable {
    
    /*
    * Esta lista de tipo MaquinaDTO contiene las máquinas que estan asociadas a un seguimiento
     */
    private List<MaquinaDTO> maquinas;
    
    // relación uno a uno deportista
    private DeportistaDTO deportista;

    /**
     * Constructor por defecto
     */
    public SeguimientoDetailDTO() 
    {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param seguimientoEntity La entidad del seguimiento para transformar a DTO.
     */
    public SeguimientoDetailDTO(SeguimientoEntity seguimientoEntity) {
        super(seguimientoEntity);
        if (seguimientoEntity != null) {
            if (seguimientoEntity.getMaquinas() != null)
            {
                maquinas = new ArrayList<>();
                for (MaquinaEntity entityMaquina : seguimientoEntity.getMaquinas()) {
                    maquinas.add(new MaquinaDTO(entityMaquina));
                }
            }
            if (seguimientoEntity.getDeportista() != null) {
                this.deportista = new DeportistaDTO(seguimientoEntity.getDeportista());
            }
        }
    }

    /**
     * Convierte un objeto SeguimientoDetailDTO a SeguimientoEntity incluyendo los
     * atributos de SeguimientoDTO.
     *
     * @return Nueva objeto SeguimientoEntity.
     */
    @Override
    public SeguimientoEntity toEntity() {
        SeguimientoEntity seguimientoEntity = super.toEntity();
        if (maquinas != null) {
            List<MaquinaEntity> maquinasEntity = new ArrayList<>();
            for (MaquinaDTO dtoMaquina : maquinas) {
                maquinasEntity.add(dtoMaquina.toEntity());
            }
            seguimientoEntity.setMaquinas(maquinasEntity);
        }
        return seguimientoEntity;
    }

    /**
     * Devuelve la lista de máquinas del seguimiento.
     *
     * @return las máquinas
     */
    public List<MaquinaDTO> getMaquinas() 
    {
        return maquinas;
    }

    /**
     * Modifica la lista de máquinas del seguimiento.
     *
     * @param maquinas las máquinas a modificar
     */
    public void setMaquinas(List<MaquinaDTO> maquinas) 
    {
        this.maquinas = maquinas;
    }

    @Override
    public String toString() 
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
    
}