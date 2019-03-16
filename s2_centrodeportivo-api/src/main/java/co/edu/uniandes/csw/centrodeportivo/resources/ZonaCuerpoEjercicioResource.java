/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;

import co.edu.uniandes.csw.centrodeportivo.dtos.EjercicioDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.EjercicioDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.EjercicioLogic;
import co.edu.uniandes.csw.centrodeportivo.ejb.ZonaCuerpoEjercicioLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.centrodeportivo.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Daniel Pardo
 */
public class ZonaCuerpoEjercicioResource {
   private static final Logger LOGGER = Logger.getLogger(ZonaCuerpoEjercicioResource.class.getName());

    @Inject
    private ZonaCuerpoEjercicioLogic zonaCuerpoEjercicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private EjercicioLogic ejercicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un ejercicio dentro de un zonaCuerpo con la informacion que recibe el la
     * URL.
     *
     * @param zonasCuerpoId Identificador de el zonaCuerpo que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param ejerciciosId Identificador del ejercicio que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link EjercicioDTO} - El ejercicio guardado en el zonaCuerpo.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ejercicio.
     */
    @POST
    @Path("{ejerciciosId: \\d+}")
    public EjercicioDTO addEjercicio(@PathParam("zonasCuerpoId") Long zonasCuerpoId, @PathParam("ejerciciosId") Long ejerciciosId) {
        LOGGER.log(Level.INFO, "ZonaCuerpoEjercicioResource addEjercicio: input: zonasCuerpoID: {0} , ejerciciosId: {1}", new Object[]{zonasCuerpoId, ejerciciosId});
        if (ejercicioLogic.getEjercicio(ejerciciosId) == null) {
            throw new WebApplicationException("El recurso /ejercicios/" + ejerciciosId + " no existe.", 404);
        }
        EjercicioDTO ejercicioDTO = new EjercicioDTO(zonaCuerpoEjercicioLogic.addEjercicio(ejerciciosId, zonasCuerpoId));
        LOGGER.log(Level.INFO, "ZonaCuerpoEjercicioResource addEjercicio: output: {0}", ejercicioDTO.toString());
        return ejercicioDTO;
    }

    /**
     * Busca el ejercicio dentro de el zonaCuerpo con id asociado.
     *
     * @param zonasCuerpoId Identificador de el zonaCuerpo que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link EjercicioDetailDTO} - El ejercicio buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando el zonaCuerpo no tiene ejercicio.
     */
    @GET
    public EjercicioDetailDTO getEjercicio(@PathParam("zonasCuerpoId") Long zonasCuerpoId) {
        LOGGER.log(Level.INFO, "ZonaCuerpoEjercicioResource getEjercicio: input: {0}", zonasCuerpoId);
        EjercicioEntity ejercicioEntity = zonaCuerpoEjercicioLogic.getEjercicio(zonasCuerpoId);
        if (ejercicioEntity == null) {
            throw new WebApplicationException("El recurso /zonasCuerpo/" + zonasCuerpoId + "/ejercicio no existe.", 404);
        }
        EjercicioDetailDTO ejercicioDetailDTO = new EjercicioDetailDTO(ejercicioEntity);
        LOGGER.log(Level.INFO, "ZonaCuerpoEjercicioResource getEjercicio: output: {0}", ejercicioDetailDTO.toString());
        return ejercicioDetailDTO;
    }

    /**
     * Remplaza la instancia de Ejercicio asociada a una instancia de ZonaCuerpo
     *
     * @param zonasCuerpoId Identificador de el zonaCuerpo que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param ejerciciosId Identificador de el ejercicio que se esta remplazando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link EjercicioDetailDTO} - El ejercicio actualizado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ejercicio.
     */
    @PUT
    @Path("{ejerciciosId: \\d+}")
    public EjercicioDetailDTO replaceEjercicio(@PathParam("zonasCuerpoId") Long zonasCuerpoId, @PathParam("ejerciciosId") Long ejerciciosId) {
        LOGGER.log(Level.INFO, "ZonaCuerpoEjercicioResource replaceEjercicio: input: zonasCuerpoId: {0} , ejerciciosId: {1}", new Object[]{zonasCuerpoId, ejerciciosId});
        if (ejercicioLogic.getEjercicio(ejerciciosId) == null) {
            throw new WebApplicationException("El recurso /ejercicios/" + ejerciciosId + " no existe.", 404);
        }
        EjercicioDetailDTO ejercicioDetailDTO = new EjercicioDetailDTO(zonaCuerpoEjercicioLogic.replaceEjercicio(zonasCuerpoId, ejerciciosId));
        LOGGER.log(Level.INFO, "ZonaCuerpoEjercicioResource replaceEjercicio: output: {0}", ejercicioDetailDTO.toString());
        return ejercicioDetailDTO;
    }

    /**
     * Elimina la conexión entre el ejercicio y el zonaCuerpo recibido en la URL.
     *
     * @param zonasCuerpoId El ID del zonaCuerpo al cual se le va a desasociar el ejercicio
     */
    @DELETE
    public void removeEjercicio(@PathParam("zonasCuerpoId") Long zonasCuerpoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ZonaCuerpoEjercicioResource removeEjercicio: input: {0}", zonasCuerpoId);
        zonaCuerpoEjercicioLogic.removeEjercicio(zonasCuerpoId);
        LOGGER.info("ZonaCuerpoEjercicioResource removeEjercicio: output: void");
    }
}
