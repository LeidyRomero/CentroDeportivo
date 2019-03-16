/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.ImplementoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.ImplementoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Clase que implementa la conexión con la persistencia para la entidad de
 * Implemento.
 * 
 * @author Lina Cardozo
 */
@Stateless
public class ImplementoLogic {

    private static final Logger LOGGER = Logger.getLogger(ImplementoLogic.class.getName());
    
    @Inject
    private ImplementoPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un implemento en la persistencia.
     *
     * @param implementoEntity La entidad que representa el implemento a
     * persistir.
     * @return La entiddad del implemento luego de persistirla.
     * @throws BusinessLogicException Si el implemento a persistir ya existe.
     */
    public ImplementoEntity createImplemento(ImplementoEntity implementoEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del implemento");
        // Invoca la persistencia para crear el implemento
        if (persistence.findByName(implementoEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un implemento con el nombre \"" + implementoEntity.getNombre() + "\"");
        }
        persistence.create(implementoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del implemento");
        return implementoEntity;
    }

    /**
     *
     * Obtener todos los implementos existentes en la base de datos.
     *
     * @return una lista de implementos.
     */
    public List<ImplementoEntity> getImplementos() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los implementos");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<ImplementoEntity> implementos = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los implementoes");
        return implementos;
    }

    /**
     *
     * Obtener un implemento por medio de su id.
     *
     * @param implementosId: id del implemento para ser buscado.
     * @return el implemento solicitado por medio de su id.
     */
    public ImplementoEntity getImplemento(Long implementosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la implemento con id = {0}", implementosId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        ImplementoEntity implementoEntity = persistence.find(implementosId);
        if (implementoEntity == null) {
            LOGGER.log(Level.SEVERE, "El implemento con el id = {0} no existe", implementosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el implemento con id = {0}", implementosId);
        return implementoEntity;
    }

    /**
     *
     * Actualizar un implemento.
     *
     * @param implementosId: id del implemento para buscarlo en la base de
     * datos.
     * @param implementoEntity: implemento con los cambios para ser actualizado,
     * por ejemplo el .
     * @return el implemento con los cambios actualizados en la base de datos.
     */
    public ImplementoEntity updateImplemento(Long implementosId, ImplementoEntity implementoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el implemento con id = {0}", implementosId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        ImplementoEntity newEntity = persistence.update(implementoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el implemento con id = {0}", implementoEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un implemento
     *
     * @param implementosId: id del implemento a borrar
     * @throws BusinessLogicException Si el implemento a eliminar tiene un ejercicio asociado.
     */
    public void deleteImplemento(Long implementosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el implemento con id = {0}", implementosId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        ImplementoEntity implementoEntity = persistence.find(implementosId);
        if (implementoEntity.getEjercicio() != null) {
            throw new BusinessLogicException("No se puede borrar el implemento con id = " + implementosId + " porque tiene un ejercicio asociado");
        }
        persistence.delete(implementosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el implemento con id = {0}", implementosId);
    }
    
}