/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;
import co.edu.uniandes.csw.centrodeportivo.dtos.EjercicioDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.EjercicioDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.EjercicioLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
/**
 *aa
 * @author Daniel Pardo
 */
@Path("ejercicios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class EjercicioResource {
    private static final Logger LOGGER = Logger.getLogger(EjercicioResource.class.getName());

    @Inject
    EjercicioLogic ejercicioLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea una nueva ejercicio con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param ejercicio {@link EjercicioDTO} - La ejercicio que se desea
     * guardar.
     * @return JSON {@link EjercicioDTO} - La ejercicio guardada con el atributo
     * id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe la ejercicio.
     */
    @POST
    public EjercicioDTO createEjercicio(EjercicioDTO ejercicio) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EjercicioResource createEjercicio: input: {0}", ejercicio.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        EjercicioEntity ejercicioEntity = ejercicio.toEntity();
        // Invoca la lógica para crear la ejercicio nueva
        EjercicioEntity nuevoEjercicioEntity = ejercicioLogic.createEjercicio(ejercicioEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        EjercicioDTO nuevoEjercicioDTO = new EjercicioDTO(nuevoEjercicioEntity);
        LOGGER.log(Level.INFO, "EjercicioResource createEjercicio: output: {0}", nuevoEjercicioDTO.toString());
        return nuevoEjercicioDTO;
    }

    /**
     * Busca y devuelve todas las ejercicioes que existen en la aplicacion.
     *
     * @return JSONArray {@link EjercicioDetailDTO} - Las ejercicioes
     * encontradas en la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<EjercicioDetailDTO> getEjercicios() {
        LOGGER.info("EjercicioResource getEjercicios: input: void");
        List<EjercicioDetailDTO> listaEjercicioes = listEntity2DetailDTO(ejercicioLogic.getEjercicios());
        LOGGER.log(Level.INFO, "EjercicioResource getEjercicios: output: {0}", listaEjercicioes.toString());
        return listaEjercicioes;
    }

    /**
     * Busca la ejercicio con el id asociado recibido en la URL y la devuelve.
     *
     * @param ejerciciosId Identificador de la ejercicio que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link EjercicioDetailDTO} - La ejercicio buscada
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la ejercicio.
     */
    @GET
    @Path("{ejerciciosId: \\d+}")
    public EjercicioDetailDTO getEjercicio(@PathParam("ejerciciosId") Long ejerciciosId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "EjercicioResource getEjercicio: input: {0}", ejerciciosId);
        EjercicioEntity ejercicioEntity = ejercicioLogic.getEjercicio(ejerciciosId);
        if (ejercicioEntity == null) {
            throw new WebApplicationException("El recurso /ejercicios/" + ejerciciosId + " no existe.", 404);
        }
        EjercicioDetailDTO detailDTO = new EjercicioDetailDTO(ejercicioEntity);
        LOGGER.log(Level.INFO, "EjercicioResource getEjercicio: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la ejercicio con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param ejerciciosId Identificador de la ejercicio que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param ejercicio {@link EjercicioDetailDTO} La ejercicio que se desea
     * guardar.
     * @return JSON {@link EjercicioDetailDTO} - La ejercicio guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la ejercicio a
     * actualizar.
     */
    @PUT
    @Path("{ejerciciosId: \\d+}")
    public EjercicioDetailDTO updateEjercicio(@PathParam("ejerciciosId") Long ejerciciosId, EjercicioDetailDTO ejercicio) throws WebApplicationException {
        LOGGER.log(Level.INFO, "EjercicioResource updateEjercicio: input: id:{0} , ejercicio: {1}", new Object[]{ejerciciosId, ejercicio.toString()});
        ejercicio.setId(ejerciciosId);
        if (ejercicioLogic.getEjercicio(ejerciciosId) == null) {
            throw new WebApplicationException("El recurso /ejercicios/" + ejerciciosId + " no existe.", 404);
        }
        EjercicioDetailDTO detailDTO = new EjercicioDetailDTO(ejercicioLogic.updateEjercicio(ejerciciosId, ejercicio.toEntity()));
        LOGGER.log(Level.INFO, "EjercicioResource updateEjercicio: output: {0}", detailDTO.toString());
        return detailDTO;

    }

    /**
     * Borra la ejercicio con el id asociado recibido en la URL.
     *
     * @param ejerciciosId Identificador de la ejercicio que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar la ejercicio.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la ejercicio.
     */
    @DELETE
    @Path("{ejerciciosId: \\d+}")
    public void deleteEjercicio(@PathParam("ejerciciosId") Long ejerciciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "EjercicioResource deleteEjercicio: input: {0}", ejerciciosId);
        if (ejercicioLogic.getEjercicio(ejerciciosId) == null) {
            throw new WebApplicationException("El recurso /ejercicios/" + ejerciciosId + " no existe.", 404);
        }
        ejercicioLogic.deleteEjercicio(ejerciciosId);
        LOGGER.info("EjercicioResource deleteEjercicio: output: void");
    }

    

    
    
    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos EjercicioEntity a una lista de
     * objetos EjercicioDetailDTO (json)
     *
     * @param entityList corresponde a la lista de ejercicioes de tipo Entity
     * que vamos a convertir a DTO.
     * @return la lista de ejercicioes en forma DTO (json)
     */
    private List<EjercicioDetailDTO> listEntity2DetailDTO(List<EjercicioEntity> entityList) {
        List<EjercicioDetailDTO> list = new ArrayList<>();
        for (EjercicioEntity entity : entityList) {
            list.add(new EjercicioDetailDTO(entity));
        }
        return list;
    }

}
