/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.resources;
import co.edu.uniandes.csw.centrodeportivo.dtos.MaquinaDTO;
import co.edu.uniandes.csw.centrodeportivo.dtos.MaquinaDetailDTO;
import co.edu.uniandes.csw.centrodeportivo.ejb.MaquinaLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.MaquinaEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;


/**
 *
 * @author dy.quintero
 */
@Path("maquinas")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class MaquinaResource implements Serializable{
    
    private static final Logger LOGGER = Logger.getLogger(MaquinaResource.class.getName());
    
    @Inject
    private MaquinaLogic maquinaLogic;
    
    @POST
    public MaquinaDTO createMaquina(MaquinaDTO maquina)
    {
        LOGGER.log(Level.INFO, "MaquinaResource createMaquina: input: {0}", maquina.toString());
        // Convierte el DTO (json) en un objeto Entity para ser manejado por la lógica.
        MaquinaEntity maquinaEntity = maquina.toEntity();
        // Invoca la lógica para crear la editorial nueva
        MaquinaEntity nuevaMaquinaEntity = maquinaLogic.createMaquina(maquinaEntity);
        // Como debe retornar un DTO (json) se invoca el constructor del DTO con argumento el entity nuevo
        MaquinaDTO nuevaMaquinaDTO = new MaquinaDTO(nuevaMaquinaEntity);
        LOGGER.log(Level.INFO, "MaquinaResource createMaquina: output: {0}", nuevaMaquinaDTO.toString());
        return nuevaMaquinaDTO;
    }
    
    @GET
    public List<MaquinaDetailDTO> getMaquinas()
    {
        LOGGER.info("MaquinaReosurce getMaquinas: input: void");
        List<MaquinaDetailDTO> listaMaquinas = listEntity2DetailDTO(maquinaLogic.getMaquinas());
        LOGGER.log(Level.INFO, "MaquinaReosurce getMaquinas: olistEntity2DetailDTOutput: {0}", listaMaquinas.toString());
        return listaMaquinas;
    }
    
    @GET
    @Path("{maquinasId: \\d+}")
    public MaquinaDTO getMaquina(@PathParam("maquinasId") Long maquinasId) {
       
        LOGGER.log(Level.INFO, "MaquinaResource getMaquina: input: {0}", maquinasId);
        MaquinaEntity maquinaEntity = maquinaLogic.getMaquina(maquinasId);
        if (maquinaEntity == null) {
            throw new WebApplicationException("El recurso /maquinas/" + maquinasId + " no existe.", 404);
        }
        MaquinaDetailDTO detailDTO = new MaquinaDetailDTO(maquinaEntity);
        LOGGER.log(Level.INFO, "MaquinaResource getMaquina: output: {0}", detailDTO.toString());
        return detailDTO;    }
    
    @PUT
    @Path("{maquinasId: \\d+}")
    public MaquinaDTO actualizarAtributos(@PathParam("maquinasId") Long maquinasId, MaquinaDTO maquina)
    {
        LOGGER.log(Level.INFO, "MaquinaResource updateMaquina: input: id:{0} , maquina: {1}", new Object[]{maquinasId, maquina.toString()});
        maquina.setId(maquinasId);
        if (maquinaLogic.getMaquina(maquinasId) == null) {
            throw new WebApplicationException("El recurso /maquinas/" + maquinasId + " no existe.", 404);
        }
        MaquinaDetailDTO detailDTO = new MaquinaDetailDTO(maquinaLogic.updateMaquina(maquinasId, maquina.toEntity()));
        LOGGER.log(Level.INFO, "MaquinaResource updateMaquina: output: {0}", detailDTO.toString());
        return detailDTO;
    }
    
    @DELETE
    @Path("{maquinasId: \\d+}")
    public void eliminarMaquina(@PathParam("maquinasId") Long maquinasId)
    {
        LOGGER.log(Level.INFO, "MaquinaResource deleteMaquina: input: {0}", maquinasId);
        if (maquinaLogic.getMaquina(maquinasId) == null) {
            throw new WebApplicationException("El recurso /maquinas/" + maquinasId + " no existe.", 404);
        }
        maquinaLogic.deleteMaquina(maquinasId);

        LOGGER.info("MaquinaResource deleteMaquina: output: void");
    }
    
    
    private List<MaquinaDetailDTO> listEntity2DetailDTO(List<MaquinaEntity> entityList) {
    List<MaquinaDetailDTO> list = new ArrayList<>();
    for (MaquinaEntity entity : entityList) {
        list.add(new MaquinaDetailDTO(entity));
    }
        return list;
    }
    
    @Path("{maquinasId: \\d+}/ejercicios")
    public Class<MaquinaEjerciciosResource> getMaquinaEjerciciosReosurce(@PathParam("maquinasId") Long maquinasId) {
        if (maquinaLogic.getMaquina(maquinasId) == null) {
            throw new WebApplicationException("El recurso /maquinas/" + maquinasId + " no existe.", 404);
        }
            return MaquinaEjerciciosResource.class;
}
}