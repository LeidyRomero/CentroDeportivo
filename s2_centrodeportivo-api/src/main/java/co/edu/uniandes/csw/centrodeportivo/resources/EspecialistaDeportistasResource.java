/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;


import co.edu.uniandes.csw.centrodeportivo.dtos.DeportistaDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.DeportistaDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.DeportistaLogic;
import co.edu.uniandes.csw.centrodeportivo.ejb.EspecialistaDeportistasLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.DeportistaEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Francisco Jose MacAllister
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class EspecialistaDeportistasResource {
     private static final Logger LOGGER = Logger.getLogger(EspecialistaDeportistasResource.class.getName());

    @Inject
    private EspecialistaDeportistasLogic especialistaDeportistasLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private DeportistaLogic deportistaLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un libro dentro de una especialista con la informacion que recibe el
     * la URL. Se devuelve el libro que se guarda en la especialista.
     *
     * @param especialistasId Identificador de la especialista que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param deportistasId Identificador del libro que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link DeportistaDTO} - El libro guardado en la especialista.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{deportistasId: \\d+}")
    public DeportistaDTO addDeportista(@PathParam("especialistasId") Long especialistasId, @PathParam("deportistasId") Long deportistasId) {
        LOGGER.log(Level.INFO, "EspecialistaDeportistasResource addDeportista: input: especialistasID: {0} , deportistasId: {1}", new Object[]{especialistasId, deportistasId});
        if (deportistaLogic.getDeportista(deportistasId) == null) {
            throw new WebApplicationException("El recurso /deportistas/" + deportistasId + " no existe.", 404);
        }
        DeportistaDTO deportistaDTO = new DeportistaDTO(especialistaDeportistasLogic.addDeportista(deportistasId, especialistasId));
        LOGGER.log(Level.INFO, "EspecialistaDeportistasResource addDeportista: output: {0}", deportistaDTO.toString());
        return deportistaDTO;
    }

    /**
     * Busca y devuelve todos los libros que existen en la especialista.
     *
     * @param especialistasId Identificador de la especialista que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link DeportistaDetailDTO} - Los libros encontrados en la
     * especialista. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<DeportistaDetailDTO> getDeportistas(@PathParam("especialistasId") Long especialistasId) {
        LOGGER.log(Level.INFO, "EspecialistaDeportistasResource getDeportistas: input: {0}", especialistasId);
        List<DeportistaDetailDTO> listaDetailDTOs = deportistasListEntity2DTO(especialistaDeportistasLogic.getDeportistas(especialistasId));
        LOGGER.log(Level.INFO, "EspecialistaDeportistasResource getDeportistas: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Busca el libro con el id asociado dentro de la especialista con id asociado.
     *
     * @param especialistasId Identificador de la especialista que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param deportistasId Identificador del libro que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link DeportistaDetailDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro en la
     * especialista.
     */
    @GET
    @Path("{deportistasId: \\d+}")
    public DeportistaDetailDTO getDeportista(@PathParam("especialistasId") Long especialistasId, @PathParam("deportistasId") Long deportistasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EspecialistaDeportistasResource getDeportista: input: especialistasID: {0} , deportistasId: {1}", new Object[]{especialistasId, deportistasId});
        if (deportistaLogic.getDeportista(deportistasId) == null) {
            throw new WebApplicationException("El recurso /especialistas/" + especialistasId + "/deportistas/" + deportistasId + " no existe.", 404);
        }
        DeportistaDetailDTO deportistaDetailDTO = new DeportistaDetailDTO(especialistaDeportistasLogic.getDeportista(especialistasId, deportistasId));
        LOGGER.log(Level.INFO, "EspecialistaDeportistasResource getDeportista: output: {0}", deportistaDetailDTO.toString());
        return deportistaDetailDTO;
    }

    /**
     * Remplaza las instancias de Deportista asociadas a una instancia de Especialista
     *
     * @param especialistasId Identificador de la especialista que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param deportistas JSONArray {@link DeportistaDTO} El arreglo de libros nuevo para la
     * especialista.
     * @return JSON {@link DeportistaDTO} - El arreglo de libros guardado en la
     * especialista.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @PUT
    public List<DeportistaDetailDTO> replaceDeportistas(@PathParam("especialistasId") Long especialistasId, List<DeportistaDetailDTO> deportistas) {
        LOGGER.log(Level.INFO, "EspecialistaDeportistasResource replaceDeportistas: input: especialistasId: {0} , deportistas: {1}", new Object[]{especialistasId, deportistas.toString()});
        for (DeportistaDetailDTO deportista : deportistas) {
            if (deportistaLogic.getDeportista(deportista.getId()) == null) {
                throw new WebApplicationException("El recurso /deportistas/" + deportista.getId() + " no existe.", 404);
            }
        }
        List<DeportistaDetailDTO> listaDetailDTOs = deportistasListEntity2DTO(especialistaDeportistasLogic.replaceDeportistas(especialistasId, deportistasListDTO2Entity(deportistas)));
        LOGGER.log(Level.INFO, "EspecialistaDeportistasResource replaceDeportistas: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de DeportistaEntity a una lista de DeportistaDetailDTO.
     *
     * @param entityList Lista de DeportistaEntity a convertir.
     * @return Lista de DeportistaDTO convertida.
     */
    private List<DeportistaDetailDTO> deportistasListEntity2DTO(List<DeportistaEntity> entityList) {
        List<DeportistaDetailDTO> list = new ArrayList();
        for (DeportistaEntity entity : entityList) {
            list.add(new DeportistaDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de DeportistaDetailDTO a una lista de DeportistaEntity.
     *
     * @param dtos Lista de DeportistaDetailDTO a convertir.
     * @return Lista de DeportistaEntity convertida.
     */
    private List<DeportistaEntity> deportistasListDTO2Entity(List<DeportistaDetailDTO> dtos) {
        List<DeportistaEntity> list = new ArrayList<>();
        for (DeportistaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
