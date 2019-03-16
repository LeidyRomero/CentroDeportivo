/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;

import co.edu.uniandes.csw.centrodeportivo.dtos.MaquinaDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.MaquinaDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.MaquinaLogic;
import co.edu.uniandes.csw.centrodeportivo.ejb.SeguimientoMaquinasLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.MaquinaEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "seguimiento/{id}/maquinas".
 *
 * @author Diany Quintero y Lina Cardozo
 */
public class SeguimientoMaquinasResource {
    
    private static final Logger LOGGER = Logger.getLogger(SeguimientoMaquinasResource.class.getName());

    @Inject
    private SeguimientoMaquinasLogic seguimientoMaquinasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private MaquinaLogic maquinasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Guarda una maquina dentro de un seguimiento con la informacion que recibe
     * la URL. Se devuelve la máquina que se guarda en el seguimiento.
     *
     * @param seguimientosId Identificador del seguimiento que se está
     * actualizando. Este debe ser una cadena de dígitos.
     * @param maquinasId Identificador de la máquina que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BookDTO} - La máquina guardada en el seguimiento.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la máquina.
     */
    @POST
    @Path("{maquinasId: \\d+}")
    public MaquinaDTO addMaquina(@PathParam("seguimientosId") Long seguimientosId, @PathParam("maquinasId") Long maquinasId) {
        LOGGER.log(Level.INFO, "SeguimientoMaquinasResource addMaquina: input: seguimientosId: {0} , maquinasId: {1}", new Object[]{seguimientosId, maquinasId});
        if (maquinasLogic.getMaquina(maquinasId) == null) {
            throw new WebApplicationException("El recurso /maquinas/" + maquinasId + " no existe.", 404);
        }
        MaquinaDTO maquinaDTO = new MaquinaDTO(seguimientoMaquinasLogic.addMaquina(maquinasId, seguimientosId));
        LOGGER.log(Level.INFO, "SeguimientoMaquinasResource addMaquina: output: {0}", maquinaDTO.toString());
        return maquinaDTO;
    }

    /**
     * Busca y devuelve todas las máquinas que existen en el seguimiento.
     *
     * @param seguimientosId Identificador de la máquina que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link BookDetailDTO} - Las máquinas encontradas en el
     * seguimiento. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<MaquinaDetailDTO> getMaquinas(@PathParam("seguimientosId") Long seguimientosId) {
        LOGGER.log(Level.INFO, "SeguimientoMaquinasResource getMaquinas: input: {0}", seguimientosId);
        List<MaquinaDetailDTO> listaDetailDTOs = maquinasListEntity2DTO(seguimientoMaquinasLogic.getMaquinas(seguimientosId));
        LOGGER.log(Level.INFO, "SeguimientoMaquinasResource getMaquinas: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }
    
    /**
     * Busca la máquina con el id asociado dentro del seguimiento con id asociado.
     *
     * @param seguimientosId Identificador del seguimiento que se está buscando.
     * Este debe ser una cadena de dígitos.
     * @param maquinasId Identificador de la máquina que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link maquinaDetailDTO} - La máquina buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la máquina.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la máquina en el
     * seguimiento.
     */
    @GET
    @Path("{maquinasId: \\d+}")
    public MaquinaDetailDTO getMaquina(@PathParam("seguimientosId") Long seguimientosId, @PathParam("maquinasId") Long maquinasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SeguimientoMaquinasResource getMaquina: input: seguimientosId: {0} , maquinasID: {1}", new Object[]{seguimientosId, maquinasId});
        if (maquinasLogic.getMaquina(maquinasId) == null) {
            throw new WebApplicationException("El recurso /seguimientos/" + seguimientosId + "/maquinas/" + maquinasId + " no existe.", 404);
        }
        MaquinaDetailDTO maquinaDetailDTO = new MaquinaDetailDTO (seguimientoMaquinasLogic.getMaquina(seguimientosId, maquinasId));
        LOGGER.log(Level.INFO, "SeguimientoMaquinasResource getMaquina: output: {0}", maquinaDetailDTO.toString());
        return maquinaDetailDTO;
    }
    
    /**
     * Remplaza las instancias de Maquina asociadas a una instancia de Seguimiento
     *
     * @param seguimientosId Identificador del seguimiento que se está
     * remplazando. Este debe ser una cadena de dígitos.
     * @param maquinas JSONArray {@link BookDTO} El arreglo de máquinas nuevo para el
     * seguimiento.
     * @return JSON {@link BookDTO} - El arreglo de máquinas guardado en el
     * seguimiento.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la máquina.
     */
    @PUT
    public List<MaquinaDetailDTO> replaceMaquinas(@PathParam("seguimientosId") Long seguimientosId, List<MaquinaDetailDTO> maquinas) {
        LOGGER.log(Level.INFO, "SeguimientoMaquinasResource replaceMaquinas: input: seguimientosId: {0} , maquinas: {1}", new Object[]{seguimientosId, maquinas.toString()});
        for (MaquinaDetailDTO maquina : maquinas) {
            if (maquinasLogic.getMaquina(maquina.getId()) == null) {
                throw new WebApplicationException("El recurso /maquinas/" + maquina.getId() + " no existe.", 404);
            }
        }
        List<MaquinaDetailDTO> listaDetailDTOs = maquinasListEntity2DTO(seguimientoMaquinasLogic.replaceMaquinas(seguimientosId, maquinasListDTO2Entity(maquinas)));
        LOGGER.log(Level.INFO, "SeguimientoMaquinasResource replaceMaquinas: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de MaquinaEntity a una lista de MaquinaDetailDTO.
     *
     * @param entityList Lista de MaquinaEntity a convertir.
     * @return Lista de MaquinaDTO convertida.
     */
    private List<MaquinaDetailDTO> maquinasListEntity2DTO(List<MaquinaEntity> entityList) {
        List<MaquinaDetailDTO> list = new ArrayList();
        for (MaquinaEntity entity : entityList) {
            list.add(new MaquinaDetailDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de MaquinaDetailDTO a una lista de MaquinaEntity.
     *
     * @param dtos Lista de MaquinaDetailDTO a convertir.
     * @return Lista de MaquinaEntity convertida.
     */
    private List<MaquinaEntity> maquinasListDTO2Entity(List<MaquinaDetailDTO> dtos) {
        List<MaquinaEntity> list = new ArrayList<>();
        for (MaquinaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}