/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;

import co.edu.uniandes.csw.centrodeportivo.dtos.EjercicioDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.EjercicioDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.EjercicioLogic;
import co.edu.uniandes.csw.centrodeportivo.ejb.MaquinaEjerciciosLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
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
 * Clase que implementa el recurso "maquina/{id}/ejercicios".
 *
 * @author Diany Quintero
 */
public class MaquinaEjerciciosResource {
    
    private static final Logger LOGGER = Logger.getLogger(MaquinaEjerciciosResource.class.getName());

    @Inject
    private MaquinaEjerciciosLogic maquinaEjerciciosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private EjercicioLogic ejercicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.
    
    /**
     * Guarda un ejercicio dentro de una maquina con la informacion que recibe
     * la URL. Se devuelve el ejercicio que se guarda en la editorial.
     *
     * @param maquinasId Identificador de la maquina que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param ejerciciosId Identificador del ejercicio que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BookDTO} - El ejercicio guardado en la editorial.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ejercicio.
     */
    @POST
    @Path("{ejerciciosId: \\d+}")
    public EjercicioDTO addEjercicio(@PathParam("maquinasId") Long maquinasId, @PathParam("ejerciciosId") Long ejerciciosId) {
        LOGGER.log(Level.INFO, "MaquinaEjerciciosResource addEjercicio: input: maquinasId: {0} , ejerciciosId: {1}", new Object[]{maquinasId, ejerciciosId});
        if (ejercicioLogic.getEjercicio(ejerciciosId) == null) {
            throw new WebApplicationException("El recurso /ejercicios/" + ejerciciosId + " no existe.", 404);
        }
        EjercicioDTO ejercicioDTO = new EjercicioDTO(maquinaEjerciciosLogic.addEjercicio(ejerciciosId, maquinasId));
        LOGGER.log(Level.INFO, "MaquinaEjerciciosResource addEjercicio: output: {0}", ejercicioDTO.toString());
        return ejercicioDTO;
    }

    /**
     * Busca y devuelve todos los ejercicios que existen en la maquina.
     *
     * @param maquinasId Identificador de la maquina que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link BookDetailDTO} - Los ejercicios encontrados en la
     * editorial. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<EjercicioDetailDTO> getEjercicios(@PathParam("maquinasId") Long maquinasId) {
        LOGGER.log(Level.INFO, "MaquinaEjerciciosResource getEjercicios: input: {0}", maquinasId);
        List<EjercicioDetailDTO> listaDetailDTOs = ejerciciosListEntity2DTO(maquinaEjerciciosLogic.getEjercicios(maquinasId));
        LOGGER.log(Level.INFO, "MaquinaEjerciciosResource getEjercicios: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }
    
    /**
     * Busca el ejercicio con el id asociado dentro de la maquina con id asociado.
     *
     * @param maquinasId Identificador de la maquinas que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param ejerciciosId Identificador del ejercicio que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link BookDetailDTO} - El ejrcicio buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ejercicio.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ejercicio en la
     * maquina.
     */
    @GET
    @Path("{ejerciciosId: \\d+}")
    public EjercicioDetailDTO getEjercicio(@PathParam("maquinasId") Long maquinasId, @PathParam("ejerciciosId") Long ejerciciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "MaquinaEjerciciosResource getEjercicio: input: maquinasId: {0} , ejerciciosID: {1}", new Object[]{maquinasId, ejerciciosId});
        if (ejercicioLogic.getEjercicio(ejerciciosId) == null) {
            throw new WebApplicationException("El recurso /maquinas/" + maquinasId + "/ejercicios/" + ejerciciosId + " no existe.", 404);
        }
        EjercicioDetailDTO ejercicioDetailDTO = new EjercicioDetailDTO (maquinaEjerciciosLogic.getEjercicio(maquinasId, ejerciciosId));
        LOGGER.log(Level.INFO, "MaquinaEjerciciosResource getEjercicio: output: {0}", ejercicioDetailDTO.toString());
        return ejercicioDetailDTO;
    }
    
    /**
     * Remplaza las instancias de Ejercicio asociadas a una instancia de Maquina
     *
     * @param maquinasId Identificador de la maquina que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param ejercicios JSONArray {@link BookDTO} El arreglo de ejercicios nuevo para la
     * editorial.
     * @return JSON {@link BookDTO} - El arreglo de ejercicios guardado en la
     * maquina.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el ejercicio.
     */
    @PUT
    public List<EjercicioDetailDTO> replaceEjercicios(@PathParam("maquinasId") Long maquinasId, List<EjercicioDetailDTO> ejercicios) {
        LOGGER.log(Level.INFO, "MaquinaEjerciciosResource replaceEjercicios: input: maquinasId: {0} , ejercicios: {1}", new Object[]{maquinasId, ejercicios.toString()});
        for (EjercicioDTO ejercicio : ejercicios) {
            if (ejercicioLogic.getEjercicio(ejercicio.getId()) == null) {
                throw new WebApplicationException("El recurso /ejercicios/" + ejercicio.getId() + " no existe.", 404);
            }
        }
        List<EjercicioDetailDTO> listaDetailDTOs = ejerciciosListEntity2DTO(maquinaEjerciciosLogic.replaceEjercicios(maquinasId, ejerciciosListDTO2Entity(ejercicios)));
        LOGGER.log(Level.INFO, "MaquinaEjerciciosResource replaceEjercicios: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de EjercicioEntity a una lista de EjecicioDetailDTO.
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


