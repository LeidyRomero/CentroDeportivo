/* 
* To change this license header, choose License Headers in Project Properties. 
* To change this template file, choose Tools | Templates 
* and open the template in the editor. 
*/ 
package co.edu.uniandes.csw.centrodeportivo.resources; 

import co.edu.uniandes.csw.centrodeportivo.dtos.ObjetivoDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.ObjetivoDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.centrodeportivo.mappers.WebApplicationExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped; 
import javax.inject.Inject;
import javax.ws.rs.Consumes; 
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path; 
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces; 
import javax.ws.rs.WebApplicationException;

/** 
* Clase que implementa el recurso "objetivos". 
* @author Leidy Romero 
*/ 
@Path("objetivos") 
@Produces("application/json") 
@Consumes("application/json") 
@RequestScoped 
public class ObjetivoResource { 

    private static final Logger LOGGER = Logger.getLogger(DeportistaResource.class.getName());
    @Inject
    ObjetivoLogic objetivoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias. 

    /**
     * Crea un nuevo objetivo con la informacion que se recibe en el cuerpo de
     * la petición y se regresa un objeto identico con un id auto-generado por
     * la base de datos.
     *
     * @param objetivo {@link ObjetivoDTO} - El objetivo que se desea
     * guardar.
     * @return JSON {@link ObjetivoDTO} - El objetivo guardada con el
     * atributo id autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el objetivo.
     */
    @POST
    public ObjetivoDTO createObjetivo(ObjetivoDTO objetivo) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ObjetivoResource createObjetivo: input: {0}", objetivo.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica. 
        ObjetivoEntity objetivoEntity = objetivo.toEntity();
        // Invoca la lógica para crear el objetivo nuevo
        ObjetivoEntity nuevoObjetivoEntity = objetivoLogic.createObjetivo(objetivoEntity); 
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo 
        ObjetivoDTO nuevoObjetivoDTO = new ObjetivoDTO(nuevoObjetivoEntity); 
        LOGGER.log(Level.INFO, "ObjetivoResource createObjetivo: output: {0}", nuevoObjetivoDTO.toString()); 
        return nuevoObjetivoDTO; 
    }
    
    /**
     * Busca y devuelve todas los objetivos que existen en la aplicacion.
     *
     * @return JSONArray {@link ObjetivoDTO} - Los objetivos encontrados en
     * la aplicación. Si no hay ninguna retorna una lista vacía.
     */
    @GET
    public List<ObjetivoDetailDTO> getObjetivos() {
        LOGGER.info("ObjetivoResource getObjetivos: input: void");
        List<ObjetivoDetailDTO> listaObjetivos = listEntity2DetailDTO(objetivoLogic.getObjetivos()); 
        LOGGER.log(Level.INFO, "ObjetivoResource getObjetivos: output: {0}", listaObjetivos.toString()); 
        return listaObjetivos; 
    }
    
     /**
     * Busca el objetivo con el id asociado recibido en la URL y la devuelve.
     *
     * @param objetivosId Identificador del objetivo que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSON {@link ObjetivoDTO} - El objetivo buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el objetivo.
     */
    @GET
    @Path("{objetivosId: \\d+}")
    public ObjetivoDTO getObjetivo(@PathParam("objetivosId") Long objetivosId) throws WebApplicationException {
        LOGGER.log(Level.INFO, "ObjetivoResource getObjetivo: input: {0}", objetivosId);
        ObjetivoEntity objetivoEntity = objetivoLogic.getObjetivo(objetivosId); 
        if (objetivoEntity == null)  
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404); 
         
        ObjetivoDTO detailDTO = new ObjetivoDTO(objetivoEntity); 
        LOGGER.log(Level.INFO, "ObjetivoResource getObjetivo: output: {0}", detailDTO.toString()); 
        return detailDTO; 
    }
       /**
     * Actualiza el objetivo con el id recibido en la URL con la informacion
     * que se recibe en el cuerpo de la petición.
     *
     * @param objetivosId Identificador del objetivo que se desea
     * actualizar. Este debe ser una cadena de dígitos.
     * @param objetivo {@link ObjetivoDTO} El objetivo que se desea
     * guardar.
     * @return JSON {@link ObjetivoDTO} - El objetivo guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el objetivo a
     * actualizar.
     */
    @PUT
    @Path("{objetivosId: \\d+}")
    public ObjetivoDTO updateObjetivo(@PathParam("objetivosId") Long objetivosId, ObjetivoDTO objetivo) throws WebApplicationException {

        LOGGER.log(Level.INFO, "ObjetivoResource updateObjetivo: input: id:{0} , objetivo: {1}", new Object[]{objetivosId, objetivo.toString()});
        objetivo.setId(objetivosId);
        if (objetivoLogic.getObjetivo(objetivosId) == null)  
            throw new WebApplicationException("El recurso /objetivos/" + objetivosId + " no existe.", 404);

        ObjetivoDTO detailDTO = new ObjetivoDTO(objetivoLogic.updateObjetivo(objetivosId, objetivo.toEntity())); 
        LOGGER.log(Level.INFO, "ObjetivoResource updateObjetivo: output: {0}", detailDTO.toString()); 
        return detailDTO; 
    }
    
    /**
     * Convierte una lista de Entity a una lista de DetailDTO.
     *
     * @param entityList Lista de Entity a convertir.
     * @return Lista de DetailDTO convertida.
     */
    private List<ObjetivoDetailDTO> listEntity2DetailDTO(List<ObjetivoEntity> entityList) {
        List<ObjetivoDetailDTO> list = new ArrayList<>();
        for (ObjetivoEntity entity : entityList) {
            list.add(new ObjetivoDetailDTO(entity));
        }
        return list;
    }
    
    
    //ELIMINAR?
    
} 