/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;

import co.edu.uniandes.csw.centrodeportivo.dtos.EjercicioDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.EjercicioDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.EjercicioLogic;
import co.edu.uniandes.csw.centrodeportivo.ejb.ImplementoEjercicioLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.mappers.WebApplicationExceptionMapper;
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
 * @ejercicio estudiante
 */
public class ImplementoEjercicioResource {
    private static final Logger LOGGER = Logger.getLogger(ImplementoEjercicioResource.class.getName());

    @Inject
    private ImplementoEjercicioLogic implementoEjercicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private EjercicioLogic ejercicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un ejercicio dentro de un implemento con la informacion que recibe el la
     * URL.
     *
     * @param implementosId Identificador de el implemento que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param ejerciciosId Identificador del ejercicio que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link EjercicioDTO} - El ejercicio guardado en el implemento.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ejercicio.
     */
    @POST
    @Path("{ejerciciosId: \\d+}")
    public EjercicioDTO addEjercicio(@PathParam("implementosId") Long implementosId, @PathParam("ejerciciosId") Long ejerciciosId) {
        LOGGER.log(Level.INFO, "ImplementoEjercicioResource addEjercicio: input: implementosID: {0} , ejerciciosId: {1}", new Object[]{implementosId, ejerciciosId});
        if (ejercicioLogic.getEjercicio(ejerciciosId) == null) {
            throw new WebApplicationException("El recurso /ejercicios/" + ejerciciosId + " no existe.", 404);
        }
        EjercicioDTO ejercicioDTO = new EjercicioDTO(implementoEjercicioLogic.addEjercicio(ejerciciosId, implementosId));
        LOGGER.log(Level.INFO, "ImplementoEjercicioResource addEjercicio: output: {0}", ejercicioDTO.toString());
        return ejercicioDTO;
    }

    /**
     * Busca el ejercicio dentro de el implemento con id asociado.
     *
     * @param implementosId Identificador de el implemento que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link EjercicioDetailDTO} - El ejercicio buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando el implemento no tiene ejercicio.
     */
    @GET
    public EjercicioDetailDTO getEjercicio(@PathParam("implementosId") Long implementosId) {
        LOGGER.log(Level.INFO, "ImplementoEjercicioResource getEjercicio: input: {0}", implementosId);
        EjercicioEntity ejercicioEntity = implementoEjercicioLogic.getEjercicio(implementosId);
        if (ejercicioEntity == null) {
            throw new WebApplicationException("El recurso /implementos/" + implementosId + "/ejercicio no existe.", 404);
        }
        EjercicioDetailDTO ejercicioDetailDTO = new EjercicioDetailDTO(ejercicioEntity);
        LOGGER.log(Level.INFO, "ImplementoEjercicioResource getEjercicio: output: {0}", ejercicioDetailDTO.toString());
        return ejercicioDetailDTO;
    }

    /**
     * Remplaza la instancia de Ejercicio asociada a una instancia de Implemento
     *
     * @param implementosId Identificador de el implemento que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param ejerciciosId Identificador de el ejercicio que se esta remplazando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link EjercicioDetailDTO} - El ejercicio actualizado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ejercicio.
     */
    @PUT
    @Path("{ejerciciosId: \\d+}")
    public EjercicioDetailDTO replaceEjercicio(@PathParam("implementosId") Long implementosId, @PathParam("ejerciciosId") Long ejerciciosId) {
        LOGGER.log(Level.INFO, "ImplementoEjercicioResource replaceEjercicio: input: implementosId: {0} , ejerciciosId: {1}", new Object[]{implementosId, ejerciciosId});
        if (ejercicioLogic.getEjercicio(ejerciciosId) == null) {
            throw new WebApplicationException("El recurso /ejercicios/" + ejerciciosId + " no existe.", 404);
        }
        EjercicioDetailDTO ejercicioDetailDTO = new EjercicioDetailDTO(implementoEjercicioLogic.replaceEjercicio(implementosId, ejerciciosId));
        LOGGER.log(Level.INFO, "ImplementoEjercicioResource replaceEjercicio: output: {0}", ejercicioDetailDTO.toString());
        return ejercicioDetailDTO;
    }

    /**
     * Elimina la conexión entre el ejercicio y el implemento recibido en la URL.
     *
     * @param implementosId El ID del implemento al cual se le va a desasociar el ejercicio
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     * Error de lógica que se genera cuando el implemento no tiene ejercicio.
     */
    @DELETE
    public void removeEjercicio(@PathParam("implementosId") Long implementosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ImplementoEjercicioResource removeEjercicio: input: {0}", implementosId);
        implementoEjercicioLogic.removeEjercicio(implementosId);
        LOGGER.info("ImplementoEjercicioResource removeEjercicio: output: void");
    }
}
