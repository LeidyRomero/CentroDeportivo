/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;

import co.edu.uniandes.csw.centrodeportivo.dtos.EjercicioDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.EjercicioDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.EjercicioLogic;
import co.edu.uniandes.csw.centrodeportivo.ejb.RutinaEjerciciosLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
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
 * @author Francisco Jose MacAlliter
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class RutinaEjerciciosResource {
     private static final Logger LOGGER = Logger.getLogger(RutinaEjerciciosResource.class.getName());

    @Inject
    private RutinaEjerciciosLogic rutinaEjerciciosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private EjercicioLogic ejercicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un libro dentro de una rutina con la informacion que recibe el
     * la URL. Se devuelve el libro que se guarda en la rutina.
     *
     * @param rutinasId Identificador de la rutina que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param ejerciciosId Identificador del libro que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link EjercicioDTO} - El libro guardado en la rutina.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{ejerciciosId: \\d+}")
    public EjercicioDTO addEjercicio(@PathParam("rutinasId") Long rutinasId, @PathParam("ejerciciosId") Long ejerciciosId) {
        LOGGER.log(Level.INFO, "RutinaEjerciciosResource addEjercicio: input: rutinasID: {0} , ejerciciosId: {1}", new Object[]{rutinasId, ejerciciosId});
        if (ejercicioLogic.getEjercicio(ejerciciosId) == null) {
            throw new WebApplicationException("El recurso /ejercicios/" + ejerciciosId + " no existe.", 404);
        }
        EjercicioDTO ejercicioDTO = new EjercicioDTO(rutinaEjerciciosLogic.addEjercicio(ejerciciosId, rutinasId));
        LOGGER.log(Level.INFO, "RutinaEjerciciosResource addEjercicio: output: {0}", ejercicioDTO.toString());
        return ejercicioDTO;
    }

    /**
     * Busca y devuelve todos los libros que existen en la rutina.
     *
     * @param rutinasId Identificador de la rutina que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link EjercicioDetailDTO} - Los libros encontrados en la
     * rutina. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EjercicioDetailDTO> getEjercicios(@PathParam("rutinasId") Long rutinasId) {
        LOGGER.log(Level.INFO, "RutinaEjerciciosResource getEjercicios: input: {0}", rutinasId);
        List<EjercicioDetailDTO> listaDetailDTOs = ejerciciosListEntity2DTO(rutinaEjerciciosLogic.getEjercicios(rutinasId));
        LOGGER.log(Level.INFO, "RutinaEjerciciosResource getEjercicios: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Busca el libro con el id asociado dentro de la rutina con id asociado.
     *
     * @param rutinasId Identificador de la rutina que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param ejerciciosId Identificador del libro que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link EjercicioDetailDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro en la
     * rutina.
     */
    @GET
    @Path("{ejerciciosId: \\d+}")
    public EjercicioDetailDTO getEjercicio(@PathParam("rutinasId") Long rutinasId, @PathParam("ejerciciosId") Long ejerciciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RutinaEjerciciosResource getEjercicio: input: rutinasID: {0} , ejerciciosId: {1}", new Object[]{rutinasId, ejerciciosId});
        if (ejercicioLogic.getEjercicio(ejerciciosId) == null) {
            throw new WebApplicationException("El recurso /rutinas/" + rutinasId + "/ejercicios/" + ejerciciosId + " no existe.", 404);
        }
        EjercicioDetailDTO ejercicioDetailDTO = new EjercicioDetailDTO(rutinaEjerciciosLogic.getEjercicio(rutinasId, ejerciciosId));
        LOGGER.log(Level.INFO, "RutinaEjerciciosResource getEjercicio: output: {0}", ejercicioDetailDTO.toString());
        return ejercicioDetailDTO;
    }

    /**
     * Remplaza las instancias de Ejercicio asociadas a una instancia de Rutina
     *
     * @param rutinasId Identificador de la rutina que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param ejercicios JSONArray {@link EjercicioDTO} El arreglo de libros nuevo para la
     * rutina.
     * @return JSON {@link EjercicioDTO} - El arreglo de libros guardado en la
     * rutina.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @PUT
    public List<EjercicioDetailDTO> replaceEjercicios(@PathParam("rutinasId") Long rutinasId, List<EjercicioDetailDTO> ejercicios) {
        LOGGER.log(Level.INFO, "RutinaEjerciciosResource replaceEjercicios: input: rutinasId: {0} , ejercicios: {1}", new Object[]{rutinasId, ejercicios.toString()});
        for (EjercicioDetailDTO ejercicio : ejercicios) {
            if (ejercicioLogic.getEjercicio(ejercicio.getId()) == null) {
                throw new WebApplicationException("El recurso /ejercicios/" + ejercicio.getId() + " no existe.", 404);
            }
        }
        List<EjercicioDetailDTO> listaDetailDTOs = ejerciciosListEntity2DTO(rutinaEjerciciosLogic.replaceEjercicios(rutinasId, ejerciciosListDTO2Entity(ejercicios)));
        LOGGER.log(Level.INFO, "RutinaEjerciciosResource replaceEjercicios: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de EjercicioEntity a una lista de EjercicioDetailDTO.
     *
     * @param entityList Lista de EjercicioEntity a convertir.
     * @return Lista de EjercicioDTO convertida.
     */
    private List<EjercicioDetailDTO> ejerciciosListEntity2DTO(List<EjercicioEntity> entityList) {
        List<EjercicioDetailDTO> list = new ArrayList();
        for (EjercicioEntity entity : entityList) {
            list.add(new EjercicioDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de EjercicioDetailDTO a una lista de EjercicioEntity.
     *
     * @param dtos Lista de EjercicioDetailDTO a convertir.
     * @return Lista de EjercicioEntity convertida.
     */
    private List<EjercicioEntity> ejerciciosListDTO2Entity(List<EjercicioDetailDTO> dtos) {
        List<EjercicioEntity> list = new ArrayList<>();
        for (EjercicioDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
