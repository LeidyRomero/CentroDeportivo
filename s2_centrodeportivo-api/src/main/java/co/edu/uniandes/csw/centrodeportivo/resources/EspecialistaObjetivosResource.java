/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;

import co.edu.uniandes.csw.centrodeportivo.ejb.EspecialistaObjetivosLogic;
import co.edu.uniandes.csw.centrodeportivo.dtos.ObjetivoDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.ObjetivoDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.ObjetivoLogic;

import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
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
 *
 * @author Francisco Jose MacAllister
 */
class EspecialistaObjetivosResource {
     private static final Logger LOGGER = Logger.getLogger(EspecialistaObjetivosResource.class.getName());

    @Inject
    private EspecialistaObjetivosLogic especialistaObjetivosLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private ObjetivoLogic objetivoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Guarda un libro dentro de una especialista con la informacion que recibe el
     * la URL. Se devuelve el libro que se guarda en la especialista.
     *
     * @param especialistasId Identificador de la especialista que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param objetivosId Identificador del libro que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ObjetivoDTO} - El libro guardado en la especialista.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @POST
    @Path("{objetivosId: \\d+}")
    public ObjetivoDTO addObjetivo(@PathParam("especialistasId") Long especialistasId, @PathParam("objetivosId") Long objetivosId) {
        LOGGER.log(Level.INFO, "EspecialistaObjetivosResource addObjetivo: input: especialistasID: {0} , objetivosId: {1}", new Object[]{especialistasId, objetivosId});
        if (objetivoLogic.getObjetivo(objetivosId) == null) {
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);
        }
        ObjetivoDTO objetivoDTO = new ObjetivoDTO(especialistaObjetivosLogic.addObjetivo(objetivosId, especialistasId));
        LOGGER.log(Level.INFO, "EspecialistaObjetivosResource addObjetivo: output: {0}", objetivoDTO.toString());
        return objetivoDTO;
    }

    /**
     * Busca y devuelve todos los libros que existen en la especialista.
     *
     * @param especialistasId Identificador de la especialista que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link ObjetivoDetailDTO} - Los libros encontrados en la
     * especialista. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ObjetivoDetailDTO> getObjetivos(@PathParam("especialistasId") Long especialistasId) {
        LOGGER.log(Level.INFO, "EspecialistaObjetivosResource getObjetivos: input: {0}", especialistasId);
        List<ObjetivoDetailDTO> listaDetailDTOs = objetivosListEntity2DTO(especialistaObjetivosLogic.getObjetivos(especialistasId));
        LOGGER.log(Level.INFO, "EspecialistaObjetivosResource getObjetivos: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Busca el libro con el id asociado dentro de la especialista con id asociado.
     *
     * @param especialistasId Identificador de la especialista que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @param objetivosId Identificador del libro que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link ObjetivoDetailDTO} - El libro buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro en la
     * especialista.
     */
    @GET
    @Path("{objetivosId: \\d+}")
    public ObjetivoDetailDTO getObjetivo(@PathParam("especialistasId") Long especialistasId, @PathParam("objetivosId") Long objetivosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EspecialistaObjetivosResource getObjetivo: input: especialistasID: {0} , objetivosId: {1}", new Object[]{especialistasId, objetivosId});
        if (objetivoLogic.getObjetivo(objetivosId) == null) {
            throw new WebApplicationException("El recurso /especialistas/" + especialistasId + "/objetivos/" + objetivosId + " no existe.", 404);
        }
        ObjetivoDetailDTO objetivoDetailDTO = new ObjetivoDetailDTO(especialistaObjetivosLogic.getObjetivo(especialistasId, objetivosId));
        LOGGER.log(Level.INFO, "EspecialistaObjetivosResource getObjetivo: output: {0}", objetivoDetailDTO.toString());
        return objetivoDetailDTO;
    }

    /**
     * Remplaza las instancias de Objetivo asociadas a una instancia de Especialista
     *
     * @param especialistasId Identificador de la especialista que se esta
     * remplazando. Este debe ser una cadena de dígitos.
     * @param objetivos JSONArray {@link ObjetivoDTO} El arreglo de libros nuevo para la
     * especialista.
     * @return JSON {@link ObjetivoDTO} - El arreglo de libros guardado en la
     * especialista.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el libro.
     */
    @PUT
    public List<ObjetivoDetailDTO> replaceObjetivos(@PathParam("especialistasId") Long especialistasId, List<ObjetivoDetailDTO> objetivos) {
        LOGGER.log(Level.INFO, "EspecialistaObjetivosResource replaceObjetivos: input: especialistasId: {0} , objetivos: {1}", new Object[]{especialistasId, objetivos.toString()});
        for (ObjetivoDetailDTO objetivo : objetivos) {
            if (objetivoLogic.getObjetivo(objetivo.getId()) == null) {
                throw new WebApplicationException("El recurso /objetivos/" + objetivo.getId() + " no existe.", 404);
            }
        }
        List<ObjetivoDetailDTO> listaDetailDTOs = objetivosListEntity2DTO(especialistaObjetivosLogic.replaceObjetivos(especialistasId, objetivosListDTO2Entity(objetivos)));
        LOGGER.log(Level.INFO, "EspecialistaObjetivosResource replaceObjetivos: output: {0}", listaDetailDTOs.toString());
        return listaDetailDTOs;
    }

    /**
     * Convierte una lista de ObjetivoEntity a una lista de ObjetivoDetailDTO.
     *
     * @param entityList Lista de ObjetivoEntity a convertir.
     * @return Lista de ObjetivoDTO convertida.
     */
    private List<ObjetivoDetailDTO> objetivosListEntity2DTO(List<ObjetivoEntity> entityList) {
        List<ObjetivoDetailDTO> list = new ArrayList();
        for (ObjetivoEntity entity : entityList) {
            list.add(new ObjetivoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ObjetivoDetailDTO a una lista de ObjetivoEntity.
     *
     * @param dtos Lista de ObjetivoDetailDTO a convertir.
     * @return Lista de ObjetivoEntity convertida.
     */
    private List<ObjetivoEntity> objetivosListDTO2Entity(List<ObjetivoDetailDTO> dtos) {
        List<ObjetivoEntity> list = new ArrayList<>();
        for (ObjetivoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
