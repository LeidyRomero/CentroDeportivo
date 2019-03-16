/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;

import co.edu.uniandes.csw.centrodeportivo.dtos.ZonaCuerpoDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.ZonaCuerpoLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.ZonaCuerpoEntity;
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
 * @author Daniel Pardo
 */
@Path("zonasCuerpo")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ZonaCuerpoResource  
{
    private static final Logger LOGGER = Logger.getLogger(ZonaCuerpoResource.class.getName());
    @Inject
    ZonaCuerpoLogic zonaCuerpoLogic;
    
    
    @POST
    public ZonaCuerpoDTO createZonaCuerpo(ZonaCuerpoDTO zonaCuerpo) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ZonaCuerpoResource createZonaCuerpo: input: {0}", zonaCuerpo.toString());
        ZonaCuerpoDTO nuevoZonaCuerpoDTO = new ZonaCuerpoDTO(zonaCuerpoLogic.createZonaCuerpo(zonaCuerpo.toEntity()));
        LOGGER.log(Level.INFO, "ZonaCuerpoResource createZonaCuerpo: output: {0}", nuevoZonaCuerpoDTO.toString());
        return nuevoZonaCuerpoDTO;
    }

    @GET
    public List<ZonaCuerpoDTO> getZonasCuerpo() {
       LOGGER.info("ZonaCuerpoResource getZonasCuerpo: input: void");
        List<ZonaCuerpoDTO> listaZonasCuerpo = listEntity2DetailDTO(zonaCuerpoLogic.getZonasCuerpo());
        LOGGER.log(Level.INFO, "ZonaCuerpoResource getZonasCuerpo: output: {0}", listaZonasCuerpo.toString());
        return listaZonasCuerpo;
    }
    
    @GET
    @Path("{zonasCuerpoId: \\d+}")
    public ZonaCuerpoDTO getZonaCuerpo(@PathParam("zonasCuerpoId") Long zonasCuerpoId) throws WebApplicationException
    {
        LOGGER.log(Level.INFO, "ZonaCuerpoResource getZonaCuerpo: input: {0}", zonasCuerpoId);
        ZonaCuerpoEntity entity = zonaCuerpoLogic.getZonaCuerpo(zonasCuerpoId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /zonasCuerpo/" + zonasCuerpoId + " no existe.", 404);
        }
        ZonaCuerpoDTO zonaCuerpoDTO = new ZonaCuerpoDTO(zonaCuerpoLogic.getZonaCuerpo(zonasCuerpoId));
        LOGGER.log(Level.INFO, "ZonaCuerpoResource getZonaCuerpo: output: {0}", zonaCuerpoDTO.toString());
        return zonaCuerpoDTO;
    }
   
    @PUT
    @Path("{zonasCuerpoId: \\d+}")
    public ZonaCuerpoDTO updateZonaCuerpo(@PathParam("zonasCuerpoId") Long zonasCuerpoId, ZonaCuerpoDTO zonaCuerpo) throws WebApplicationException {

        LOGGER.log(Level.INFO, "ZonaCuerpoResource updateZonaCuerpo: input: zonasCuerpoId: {0} , zonaCuerpo: {1}", new Object[]{zonasCuerpoId, zonaCuerpo.toString()});
        zonaCuerpo.setId(zonasCuerpoId);
        ZonaCuerpoEntity entity = zonaCuerpoLogic.getZonaCuerpo(zonasCuerpoId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /zonasCuerpo/" + zonasCuerpoId + " no existe.", 404);
        }
        ZonaCuerpoDTO detailDTO = new ZonaCuerpoDTO(zonaCuerpoLogic.updateZonaCuerpo(zonasCuerpoId, zonaCuerpo.toEntity()));
        LOGGER.log(Level.INFO, "ZonaCuerpoResource updateZonaCuerpo: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    @DELETE
    @Path("{zonasCuerpoId: \\d+}")
    public void deleteZonaCuerpo(@PathParam("zonasCuerpoId") Long zonasCuerpoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ZonaCuerpoResource deleteZonaCuerpo: input: {0}", zonasCuerpoId);
        ZonaCuerpoEntity entity = zonaCuerpoLogic.getZonaCuerpo(zonasCuerpoId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /zonasCuerpo/" + zonasCuerpoId + " no existe.", 404);
        }
        
        zonaCuerpoLogic.deleteZonaCuerpo(zonasCuerpoId);
        LOGGER.info("ZonaCuerpoResource deleteZonaCuerpo: output: void");
    }
    
    private List<ZonaCuerpoDTO> listEntity2DetailDTO(List<ZonaCuerpoEntity> entityList) {
         List<ZonaCuerpoDTO> list = new ArrayList();
        for (ZonaCuerpoEntity entity : entityList) {
            list.add(new ZonaCuerpoDTO(entity));
        }
        return list;
    }
    @Path("{zonasCuerpoId: \\d+}/ejercicios")
    public Class<ZonaCuerpoEjercicioResource> getZonaCuerpoEjercicioResource(@PathParam("zonasCuerpoId") Long zonasCuerpoId) {
        if (zonaCuerpoLogic.getZonaCuerpo(zonasCuerpoId) == null) {
            throw new WebApplicationException("El recurso /zonasCuerpo/" + zonasCuerpoId + " no existe.", 404);
        }
        return ZonaCuerpoEjercicioResource.class;
    }
}
