/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;

import co.edu.uniandes.csw.centrodeportivo.dtos.ObjetivoDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.ObjetivoDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.DeportistaObjetivoLogic;
import co.edu.uniandes.csw.centrodeportivo.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
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
 * @author Leidy Romero
 */
public class DeportistaObjetivoResource {
    
    private static final Logger LOGGER = Logger.getLogger(DeportistaObjetivoResource.class.getName());

    @Inject
    private DeportistaObjetivoLogic deportistaObjetivoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ObjetivoLogic objetivoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un objetivo dentro de un deportista con la informacion que recibe el la
     * URL.
     *
     * @param deportistasId Identificador de el deportista que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param objetivosId Identificador del objetivo que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ObjetivoDTO} - El objetivo guardado en el deportista.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el objetivo.
     */
    @POST
    @Path("{objetivosId: \\d+}")
    public ObjetivoDTO addObjetivo(@PathParam("deportistasId") Long deportistasId, @PathParam("objetivosId") Long objetivosId) {
        LOGGER.log(Level.INFO, "DeportistaObjetivoResource addObjetivo: input: deportistasID: {0} , objetivosId: {1}", new Object[]{deportistasId, objetivosId});
        if (objetivoLogic.getObjetivo(objetivosId) == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);
        }
        ObjetivoDTO objetivoDTO = new ObjetivoDTO(deportistaObjetivoLogic.addObjetivo(objetivosId, deportistasId));
        LOGGER.log(Level.INFO, "DeportistaObjetivoResource addObjetivo: output: {0}", objetivoDTO.toString());
        return objetivoDTO;
    }

    /**
     * Busca el objetivo dentro de el deportista con id asociado.
     *
     * @param deportistasId Identificador de el deportista que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ObjetivoDetailDTO} - El objetivo buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando el deportista no tiene objetivo.
     */
    @GET
    public ObjetivoDetailDTO getObjetivo(@PathParam("deportistasId") Long deportistasId) {
        LOGGER.log(Level.INFO, "DeportistaObjetivoResource getObjetivo: input: {0}", deportistasId);
        ObjetivoEntity objetivoEntity = deportistaObjetivoLogic.getObjetivo(deportistasId);
        if (objetivoEntity == null) {
            throw new WebApplicationException("El recurso /deportistas/" + deportistasId + "/objetivo no existe.", 404);
        }
        ObjetivoDetailDTO objetivoDetailDTO = new ObjetivoDetailDTO(objetivoEntity);
        LOGGER.log(Level.INFO, "DeportistaObjetivoResource getObjetivo: output: {0}", objetivoDetailDTO.toString());
        return objetivoDetailDTO;
    }

    /**
     * Remplaza la instancia de Objetivo asociada a una instancia de Deportista
     *
     * @param deportistasId Identificador de el deportista que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param objetivosId Identificador de el objetivo que se esta remplazando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link ObjetivoDetailDTO} - El objetivo actualizado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el objetivo.
     */
    @PUT
    @Path("{objetivosId: \\d+}")
    public ObjetivoDetailDTO replaceObjetivo(@PathParam("deportistasId") Long deportistasId, @PathParam("objetivosId") Long objetivosId) {
        LOGGER.log(Level.INFO, "DeportistaObjetivoResource replaceObjetivo: input: deportistasId: {0} , objetivosId: {1}", new Object[]{deportistasId, objetivosId});
        if (objetivoLogic.getObjetivo(objetivosId) == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);
        }
        ObjetivoDetailDTO objetivoDetailDTO = new ObjetivoDetailDTO(deportistaObjetivoLogic.replaceObjetivo(deportistasId, objetivosId));
        LOGGER.log(Level.INFO, "DeportistaObjetivoResource replaceObjetivo: output: {0}", objetivoDetailDTO.toString());
        return objetivoDetailDTO;
    }

    /**
     * Elimina la conexión entre el objetivo y el deportista recibido en la URL.
     *
     * @param deportistasId El ID del deportista al cual se le va a desasociar el objetivo
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     * Error de lógica que se genera cuando el deportista no tiene objetivo.
     */
    @DELETE
    public void removeObjetivo(@PathParam("deportistasId") Long deportistasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "DeportistaObjetivoResource removeObjetivo: input: {0}", deportistasId);
        deportistaObjetivoLogic.removeObjetivo(deportistasId);
        LOGGER.info("DeportistaObjetivoResource removeObjetivo: output: void");
    }
}