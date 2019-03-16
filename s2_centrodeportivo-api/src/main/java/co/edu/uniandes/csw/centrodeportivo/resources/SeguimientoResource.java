/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;

import co.edu.uniandes.csw.centrodeportivo.dtos.SeguimientoDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.SeguimientoDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.SeguimientoLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.SeguimientoEntity;
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
 * @author Lina Cardozo
 */
@Path("seguimientos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class SeguimientoResource {
    
    private static final Logger LOGGER = Logger.getLogger(SeguimientoResource.class.getName());

    @Inject
    private SeguimientoLogic seguimientoLogic;

    /**
     * Crea un nuevo seguimiento con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto idéntico con un id auto-generado por la
     * base de datos.
     *
     * @param seguimiento {@link SeguimientoDTO} - EL seguimiento que se desea guardar.
     * @return JSON {@link SeguimientoDTO} - El seguimiento guardado con el atributo id
     * autogenerado.
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el seguimiento 
     */
    @POST
    public SeguimientoDTO createSeguimiento(SeguimientoDTO seguimiento) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SeguimientoResource createSeguimiento: input: {0}", seguimiento.toString());
        SeguimientoDTO seguimientoDTO = new SeguimientoDTO(seguimientoLogic.createSeguimiento(seguimiento.toEntity()));
        LOGGER.log(Level.INFO, "SeguimientoResource createSeguimiento: output: {0}", seguimientoDTO.toString());
        return seguimientoDTO;
    }

    /**
     * Busca el seguimiento con el id asociado recibido en la URL y lo devuelve.
     *
     * @param seguimientosId Identificador del seguimiento que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link SeguimientoDetailDTO} - El seguimiento buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el seguimiento.
     */
    @GET
    @Path("{seguimientosId: \\d+}")
    public SeguimientoDetailDTO getSeguimiento(@PathParam("seguimientosId") Long seguimientosId) {
        LOGGER.log(Level.INFO, "SeguimientoResource getSeguimiento: input: {0}", seguimientosId);
        SeguimientoEntity seguimientoEntity = seguimientoLogic.getSeguimiento(seguimientosId);
        if (seguimientoEntity == null) {
            throw new WebApplicationException("El recurso /seguimientos/" + seguimientosId + " no existe.", 404);
        }
        SeguimientoDetailDTO detailDTO = new SeguimientoDetailDTO(seguimientoEntity);
        LOGGER.log(Level.INFO, "SeguimientoResource getSeguimiento: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza el seguimiento con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param seguimientosId Identificador del seguimiento que se desea actualizar. Este
     * debe ser una cadena de dígitos.
     * @param seguimiento {@link SeguimientoDetailDTO} El seguimiento que se desea guardar.
     * @return JSON {@link SeguimientoDetailDTO} - El seguimiento guardado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el seguimiento a
     * actualizar.
     */
    @PUT
    @Path("{seguimientosId: \\d+}")
    public SeguimientoDetailDTO updateSeguimiento(@PathParam("seguimientosId") Long seguimientosId, SeguimientoDetailDTO seguimiento) {
        LOGGER.log(Level.INFO, "SeguimientoResource updateSeguimiento: input: seguimientosId: {0} , seguimiento: {1}", new Object[]{seguimientosId, seguimiento.toString()});
        seguimiento.setId(seguimientosId);
        if (seguimientoLogic.getSeguimiento(seguimientosId) == null) {
            throw new WebApplicationException("El recurso /seguimientos/" + seguimientosId + " no existe.", 404);
        }
        SeguimientoDetailDTO detailDTO = new SeguimientoDetailDTO(seguimientoLogic.updateSeguimiento(seguimientosId, seguimiento.toEntity()));
        LOGGER.log(Level.INFO, "SeguimientoResource updateSeguimiento: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Borra el seguimiento con el id asociado recibido en la URL.
     *
     * @param seguimientosId Identificador del seguimiento que se desea borrar. Este debe
     * ser una cadena de dígitos.
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el seguimiento a borrar.
     */
    @DELETE
    @Path("{seguimientosId: \\d+}")
    public void deleteSeguimiento(@PathParam("seguimientosId") Long seguimientosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "SeguimientoResource deleteSeguimiento: input: {0}", seguimientosId);
        if (seguimientoLogic.getSeguimiento(seguimientosId) == null) {
            throw new WebApplicationException("El recurso /seguimientos/" + seguimientosId + " no existe.", 404);
        }
        seguimientoLogic.deleteSeguimiento(seguimientosId);
        LOGGER.info("SeguimientoResource deleteSeguimiento: output: void");
    }

    /**
     * Conexión con el servicio de máquinas para un seguimiento.
     * {@link SeguimientoMaquinasResource}
     *
     * Este método conecta la ruta de /seguimientos con las rutas de /maquinas que
     * dependen del seguimiento, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los libros.
     *
     * @param seguimientosId El ID del seguimiento con respecto al cual se accede al
     * servicio.
     * @return El servicio de máquinas para ese seguimiento en particular.
     */
    @Path("{seguimientosId: \\d+}/maquinas")
    public Class<SeguimientoMaquinasResource> getSeguimientoMaquinasResource(@PathParam("seguimientosId") Long seguimientosId) {
        if (seguimientoLogic.getSeguimiento(seguimientosId) == null) {
            throw new WebApplicationException("El recurso /seguimientos/" + seguimientosId + " no existe.", 404);
        }
        return SeguimientoMaquinasResource.class;
    }

    /**
     * Convierte una lista de SeguimientoEntity a una lista de SeguimientoDetailDTO.
     *
     * @param entityList Lista de SeguimientoEntity a convertir.
     * @return Lista de SeguimientoDetailDTO convertida.
     */
    private List<SeguimientoDetailDTO> listEntity2DTO(List<SeguimientoEntity> entityList) {
        List<SeguimientoDetailDTO> list = new ArrayList<>();
        for (SeguimientoEntity entity : entityList) {
            list.add(new SeguimientoDetailDTO(entity));
        }
        return list;
    }
}