
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.EjercicioPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class EjercicioLogic {
    private static final Logger LOGGER = Logger.getLogger(EjercicioLogic.class.getName());
    
    @Inject
    private EjercicioPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un ejercicio en la persistencia.
     *
     * @param ejercicioEntity La entidad que representa el ejercicio a
     * persistir.
     * @return La entiddad del ejercicio luego de persistirla.
     * @throws BusinessLogicException Si el ejercicio a persistir ya existe.
     */
    public EjercicioEntity createEjercicio(EjercicioEntity ejercicioEntity) throws BusinessLogicException 
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del ejercicio");
        // Invoca la persistencia para crear el ejercicio
        persistence.create(ejercicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del ejercicio");
        return ejercicioEntity;
    }

    /**
     *
     * Obtener todos los ejercicios existentes en la base de datos.
     *
     * @return una lista de ejercicios.
     */
    public List<EjercicioEntity> getEjercicios() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todos los ejercicios");
        // Note que, por medio de la inyección de dependencias se llama al método "findAll()" que se encuentra en la persistencia.
        List<EjercicioEntity> ejercicios = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todos los ejercicioes");
        return ejercicios;
    }

    /**
     *
     * Obtener un ejercicio por medio de su id.
     *
     * @param ejerciciosId: id del ejercicio para ser buscado.
     * @return el ejercicio solicitado por medio de su id.
     */
    public EjercicioEntity getEjercicio(Long ejerciciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la ejercicio con id = {0}", ejerciciosId);
        // Note que, por medio de la inyección de dependencias se llama al método "find(id)" que se encuentra en la persistencia.
        EjercicioEntity ejercicioEntity = persistence.find(ejerciciosId);
        if (ejercicioEntity == null) {
            LOGGER.log(Level.SEVERE, "El ejercicio con el id = {0} no existe", ejerciciosId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el ejercicio con id = {0}", ejerciciosId);
        return ejercicioEntity;
    }

    /**
     *
     * Actualizar un ejercicio.
     *
     * @param ejerciciosId: id del ejercicio para buscarlo en la base de
     * datos.
     * @param ejercicioEntity: ejercicio con los cambios para ser actualizado,
     * por ejemplo el .
     * @return el ejercicio con los cambios actualizados en la base de datos.
     */
    public EjercicioEntity updateEjercicio(Long ejerciciosId, EjercicioEntity ejercicioEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el ejercicio con id = {0}", ejerciciosId);
        // Note que, por medio de la inyección de dependencias se llama al método "update(entity)" que se encuentra en la persistencia.
        EjercicioEntity newEntity = persistence.update(ejercicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el ejercicio con id = {0}", ejercicioEntity.getId());
        return newEntity;
    }

    /**
     * Borrar un ejercicio
     *
     * @param ejerciciosId: id del ejercicio a borrar
     * @throws BusinessLogicException Si el ejercicio a eliminar tiene un ejercicio asociado.
     */
    public void deleteEjercicio(Long ejerciciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el ejercicio con id = {0}", ejerciciosId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        EjercicioEntity ejercicioEntity = persistence.find(ejerciciosId);     
        persistence.delete(ejerciciosId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el ejercicio con id = {0}", ejerciciosId);
    }
}
