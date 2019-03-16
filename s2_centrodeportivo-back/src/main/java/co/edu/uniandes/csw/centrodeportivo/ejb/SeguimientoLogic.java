/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.MaquinaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.SeguimientoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.SeguimientoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Lina Cardozo
 */
@Stateless
public class SeguimientoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(SeguimientoLogic.class.getName());

    @Inject
    private SeguimientoPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un seguimiento en la persistencia.
     *
     * @param seguimientoEntity La entidad que representa el seguimiento a
     * persistir.
     * @return La entiddad del seguimiento luego de persistirla.
     * @throws BusinessLogicException Si el seguimiento a persistir ya existe.
     */
    public SeguimientoEntity createSeguimiento(SeguimientoEntity seguimientoEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del seguimiento");
        // Invoca la persistencia para crear el seguimiento
        persistence.create(seguimientoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del seguimiento");
        return seguimientoEntity;
    }

    /**
     *
     * Obtener todos los seguimientos existentes en la base de datos.
     *
     * @return una lista de seguimientos.
     */
    /*public List<SeguimientoEntity> getSeguimientos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los seguimientos");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<SeguimientoEntity> seguimientos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las seguimientoes");
        return seguimientos;
    }*/

    /**
     *
     * Obtener un seguimiento por medio de su id.
     *
     * @param seguimientosId: id del seguimiento para ser buscado.
     * @return el seguimiento solicitado por medio de su id.
     */
    public SeguimientoEntity getSeguimiento(Long seguimientosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el seguimiento con id = {0}", seguimientosId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        SeguimientoEntity seguimientoEntity = persistence.find(seguimientosId);
        if (seguimientoEntity == null) {
            LOGGER.log(Level.SEVERE, "El seguimiento con el id = {0} no existe", seguimientosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el seguimiento con id = {0}", seguimientosId);
        return seguimientoEntity;
    }

    /**
     *
     * Actualizar un seguimiento.
     *
     * @param seguimientosId: id del seguimiento para buscarlo en la base de
     * datos.
     * @param seguimientoEntity: seguimiento con los cambios para ser actualizado,
     * por ejemplo el tiempo.
     * @return el seguimiento con los cambios actualizados en la base de datos.
     */
    public SeguimientoEntity updateSeguimiento(Long seguimientosId, SeguimientoEntity seguimientoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el seguimiento con id = {0}", seguimientosId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        SeguimientoEntity newEntity = persistence.update(seguimientoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el seguimiento con id = {0}", seguimientoEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un seguimiento
     *
     * @param seguimientosId: id del seguimiento a borrar
     * @throws BusinessLogicException Si el seguimiento a eliminar tiene libros.
     */
    public void deleteSeguimiento(Long seguimientosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el seguimiento con id = {0}", seguimientosId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        List<MaquinaEntity> maquinas = getSeguimiento(seguimientosId).getMaquinas();
        if (maquinas != null && !maquinas.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el autor con id = " + seguimientosId + " porque tiene máquinas asociadas");
        }
        if (persistence.find(seguimientosId).getDeportista() != null) {
            throw new BusinessLogicException("No se puede borrar el premio con id = " + seguimientosId + " porque tiene un deportista asociado");
        }
        persistence.delete(seguimientosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el seguimiento con id = {0}", seguimientosId);
    }
    
}
