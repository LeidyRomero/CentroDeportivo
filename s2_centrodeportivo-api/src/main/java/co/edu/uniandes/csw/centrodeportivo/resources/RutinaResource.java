/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;

import co.edu.uniandes.csw.centrodeportivo.dtos.RutinaDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.RutinaDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.RutinaDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.RutinaLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.RutinaEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
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

/**
 *
 * @author Francisco Jose MacAllister
 */
@Path("rutinas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class RutinaResource {
     private static final Logger LOGGER = Logger.getLogger(RutinaResource.class.getName());
      
    @Inject
    RutinaLogic rutinaLogic;
      @POST
    public RutinaDTO createRutina(RutinaDTO rutina) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "RutinaResource createRutina: input: {0}", rutina.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        RutinaEntity rutinaEntity = rutina.toEntity();
        // Invoca la lógica para crear la rutina nueva
        RutinaEntity nuevoRutinaEntity = rutinaLogic.createRutina(rutinaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        RutinaDTO nuevoRutinaDTO = new RutinaDTO(nuevoRutinaEntity);
        LOGGER.log(Level.INFO, "RutinaResource createRutina: output: {0}", nuevoRutinaDTO.toString());
        return nuevoRutinaDTO;
    }
    /**
     * Busca y devuelve todos los rutinas que existen en la aplicacion.
     *
     * @return JSONArray {@link RutinaDetailDTO} - Los rutinas
     * encontrados en la aplicación. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<RutinaDetailDTO> getRutinas() {
        LOGGER.info("RutinaResource getRutinas: input: void");
        List<RutinaDetailDTO> listaRutinas = listRutinaEntity2DTO(rutinaLogic.getRutinas());
        LOGGER.log(Level.INFO, "RutinaResource getRutinas: output: {0}", listaRutinas.toString());
        return listaRutinas;
        
    }
    
    /**
     * Busca la rutina con el id asociado recibido en la URL y lo devuelve.
     *
     * @param rutinasId Identificador del rutina que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link RutinaDetailDTO} - El rutina buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el rutina.
     */
    @GET
    @Path("{rutinasId: \\d+}")
    public RutinaDetailDTO getRutina(@PathParam("rutinasId") Long rutinasId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "RutinaResource getRutina: input: {0}", rutinasId);
        RutinaEntity rutinaEntity = rutinaLogic.getRutina(rutinasId);
        if (rutinaEntity == null) {
            throw new WebApplicationException("El recurso /rutina/" + rutinasId + " no existe.", 404);
        }
        RutinaDetailDTO rutinaDTO = new RutinaDetailDTO(rutinaEntity);
        LOGGER.log(Level.INFO, "RutinaResource getRutina: output: {0}", rutinaDTO.toString());
        return rutinaDTO;
        
    }
    
    /**
     * Actualiza el rutina con el id recibido en la URL con la información
     * que se recibe en el cuerpo de la petición.
     *
     * @param rutinasId Identificador del rutina que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param rutina {@link RutinaDetailDTO} El rutina que se desea
     * guardar.
     * @return JSON {@link RutinaDetailDTO} - El rutina guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el rutina a
     * actualizar.
     */
    @PUT
    @Path("{rutinasId: \\d+}")
    public RutinaDetailDTO updateRutina(@PathParam("rutinasId") Long rutinasId, RutinaDetailDTO rutina) throws WebApplicationException 
    {
          LOGGER.log(Level.INFO, "RutinaResource updateRutina: input: id:{0} , rutina: {1}", new Object[]{rutinasId, rutina.toString()});
        rutina.setId(rutinasId);
        if (rutinaLogic.getRutina(rutinasId) == null) {
            throw new WebApplicationException("El recurso /rutinas/" + rutinasId + " no existe.", 404);
        }
        RutinaDetailDTO detailDTO = new RutinaDetailDTO(rutinaLogic.updateRutina(rutinasId, rutina.toEntity()));
        LOGGER.log(Level.INFO, "RutinaResource updateRutina: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    /**
     * Borra el rutina con el id asociado recibido en la URL.
     *
     * @param rutinasId Identificador del rutina que se desea borrar.
     * Este debe ser una cadena de dígitos.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede eliminar el rutina.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el rutina.
     */
    @DELETE
    @Path("{rutinasId: \\d+}")
    public void deleteRutina(@PathParam("rutinasId") Long rutinasId) throws BusinessLogicException {
         LOGGER.log(Level.INFO, "RutinaResource deleteRutina: input: {0}", rutinasId);
        if (rutinaLogic.getRutina(rutinasId) == null) {
            throw new WebApplicationException("El recurso /rutinas/" + rutinasId + " no existe.", 404);
        }
        rutinaLogic.deleteRutina(rutinasId);
        LOGGER.info("RutinaResource deleteRutina: output: void");
    }
    /**
     * Conexión con el servicio de ejercicios para una rutina.
     * {@link RutinaEjerciciosResource}
     *
     * Este método conecta la ruta de /rutinas con las rutas de /ejercicios que
     * dependen de la rutina, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los libros de una rutina.
     *
     * @param rutinasId El ID de la rutina con respecto a la cual se
     * accede al servicio.
     * @return El servicio de libros para esta rutina en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la rutina.
     */
    @GET
    @Path("{rutinasId: \\d+}/ejercicios")
    public Class<RutinaEjerciciosResource> getRutinaEjerciciosResource(@PathParam("rutinasId") Long rutinasId) {
        if (rutinaLogic.getRutina(rutinasId) == null) {
            throw new WebApplicationException("El recurso /rutinas/" + rutinasId + " no existe.", 404);
        }
        return RutinaEjerciciosResource.class;
    }
    
   /**
     * Conexión con el servicio de libros para una Rutina.
     * {@link RutinaObjetivoResource}
     *
     * Este método conecta la ruta de /Rutinas con las rutas de /Objetivo que
     * dependen de la Rutina, es una redirección al servicio que maneja el
     * segmento de la URL que se encarga de los libros de una Rutina.
     *
     * @param RutinasId El ID de la Rutina con respecto a la cual se
     * accede al servicio.
     * @return El servicio de libros para esta Rutina en paricular.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra la Rutina.
     */
    @GET
    @Path("{RutinasId: \\d+}/Objetivo")
    public Class<RutinaObjetivosResource> getRutinaObjetivosResource(@PathParam("RutinasId") Long RutinasId) {
        if (rutinaLogic.getRutina(RutinasId) == null) {
            throw new WebApplicationException("El recurso /Rutinas/" + RutinasId + " no existe.", 404);
        }
        return RutinaObjetivosResource.class;
    }

    
    private List<RutinaDetailDTO> listRutinaEntity2DTO(List<RutinaEntity> entityList) {
        List<RutinaDetailDTO> list = new ArrayList();
        for (RutinaEntity entity : entityList) {
            list.add(new RutinaDetailDTO(entity));
        }
        return list;
    }
    
}
