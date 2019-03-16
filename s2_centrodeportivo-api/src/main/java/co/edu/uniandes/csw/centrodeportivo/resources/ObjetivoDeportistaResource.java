/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;

import co.edu.uniandes.csw.centrodeportivo.dtos.DeportistaDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.DeportistaDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.DeportistaLogic;
import co.edu.uniandes.csw.centrodeportivo.ejb.ObjetivoDeportistaLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.DeportistaEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.mappers.WebApplicationExceptionMapper;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 * Clase que implementa el recurso "deportista/{id}/objetivos".
 * @author Leidy Romero
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ObjetivoDeportistaResource {

    private static final Logger LOGGER = Logger.getLogger(ObjetivoDeportistaResource.class.getName());

    @Inject
    private ObjetivoDeportistaLogic objetivoDeportistaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private DeportistaLogic deportistaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un deportista dentro de un objetivo con la informacion que recibe el la
     * URL.
     *
     * @param objetivosId Identificador de el objetivo que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param deportistasId Identificador del deportista que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link DeportistaDTO} - El deportista guardado en el objetivo.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el deportista.
     */
    @POST
    @Path("{deportistasId: \\d+}")
    public DeportistaDTO addDeportista(@PathParam("objetivosId") Long objetivosId, @PathParam("deportistasId") Long deportistasId) {
        LOGGER.log(Level.INFO, "ObjetivoDeportistaResource addDeportista: input: objetivosID: {0} , deportistasId: {1}", new Object[]{objetivosId, deportistasId});
        if (deportistaLogic.getDeportista(deportistasId) == null) {
            throw new WebApplicationException("El recurso /deportistas/" + deportistasId + " no existe.", 404);
        }
        DeportistaDTO deportistaDTO = new DeportistaDTO(objetivoDeportistaLogic.addDeportista(deportistasId, objetivosId));
        LOGGER.log(Level.INFO, "ObjetivoDeportistaResource addDeportista: output: {0}", deportistaDTO.toString());
        return deportistaDTO;
    }

    /**
     * Busca el deportista dentro de el objetivo con id asociado.
     *
     * @param objetivosId Identificador de el objetivo que se esta buscando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link DeportistaDetailDTO} - El deportista buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando el objetivo no tiene deportista.
     */
    @GET
    public DeportistaDetailDTO getDeportista(@PathParam("objetivosId") Long objetivosId) {
        LOGGER.log(Level.INFO, "ObjetivoDeportistaResource getDeportista: input: {0}", objetivosId);
        DeportistaEntity deportistaEntity = objetivoDeportistaLogic.getDeportista(objetivosId);
        if (deportistaEntity == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + "/deportista no existe.", 404);
        }
        DeportistaDetailDTO deportistaDetailDTO = new DeportistaDetailDTO(deportistaEntity);
        LOGGER.log(Level.INFO, "ObjetivoDeportistaResource getDeportista: output: {0}", deportistaDetailDTO.toString());
        return deportistaDetailDTO;
    }

    /**
     * Remplaza la instancia de Deportista asociada a una instancia de Objetivo
     *
     * @param objetivosId Identificador de el objetivo que se esta actualizando. Este
     * debe ser una cadena de dígitos.
     * @param deportistasId Identificador de el deportista que se esta remplazando. Este
     * debe ser una cadena de dígitos.
     * @return JSON {@link DeportistaDetailDTO} - El deportista actualizado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el deportista.
     */
    @PUT
    @Path("{deportistasId: \\d+}")
    public DeportistaDetailDTO replaceDeportista(@PathParam("objetivosId") Long objetivosId, @PathParam("deportistasId") Long deportistasId) {
        LOGGER.log(Level.INFO, "ObjetivoDeportistaResource replaceDeportista: input: objetivosId: {0} , deportistasId: {1}", new Object[]{objetivosId, deportistasId});
        if (deportistaLogic.getDeportista(deportistasId) == null) {
            throw new WebApplicationException("El recurso /deportistas/" + deportistasId + " no existe.", 404);
        }
        DeportistaDetailDTO deportistaDetailDTO = new DeportistaDetailDTO(objetivoDeportistaLogic.replaceDeportista(objetivosId, deportistasId));
        LOGGER.log(Level.INFO, "ObjetivoDeportistaResource replaceDeportista: output: {0}", deportistaDetailDTO.toString());
        return deportistaDetailDTO;
    }

    /**
     * Elimina la conexión entre el deportista y el objetivo recibido en la URL.
     *
     * @param objetivosId El ID del objetivo al cual se le va a desasociar el deportista
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     * Error de lógica que se genera cuando el objetivo no tiene deportista.
     */
    @DELETE
    public void removeDeportista(@PathParam("objetivosId") Long objetivosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ObjetivoDeportistaResource removeDeportista: input: {0}", objetivosId);
        objetivoDeportistaLogic.removeDeportista(objetivosId);
        LOGGER.info("ObjetivoDeportistaResource removeDeportista: output: void");
    }
}