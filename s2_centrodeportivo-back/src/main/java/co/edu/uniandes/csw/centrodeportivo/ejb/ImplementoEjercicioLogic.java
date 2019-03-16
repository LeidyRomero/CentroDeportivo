/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ImplementoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.EjercicioPersistence;
import co.edu.uniandes.csw.centrodeportivo.persistence.ImplementoPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author estudiante
 */
@Stateless
public class ImplementoEjercicioLogic {
    private static final Logger LOGGER = Logger.getLogger(ImplementoEjercicioLogic.class.getName());

    @Inject
    private EjercicioPersistence ejercicioPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private ImplementoPersistence implementoPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar un ejercicio a un implemento
     *
     * @param implementosId El id implemento a guardar
     * @param ejerciciosId El id del ejercicio al cual se le va a guardar el implemento.
     * @return El implemento que fue agregado al ejercicio.
     */
    public EjercicioEntity addEjercicio(Long ejerciciosId, Long implementosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el ejercicio con id = {0} al implemento con id = " + implementosId, ejerciciosId);
        EjercicioEntity ejercicioEntity = ejercicioPersistence.find(ejerciciosId);
        ImplementoEntity implementoEntity = implementoPersistence.find(implementosId);
        implementoEntity.setEjercicio(ejercicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el ejercicio con id = {0} al implemento con id = " + implementosId, ejerciciosId);
        return ejercicioPersistence.find(ejerciciosId);
    }

    /**
     *
     * Obtener un implemento por medio de su id y el de su ejercicio.
     *
     * @param implementosId id del implemento a ser buscado.
     * @return el ejercicio solicitada por medio de su id.
     */
    public EjercicioEntity getEjercicio(Long implementosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el ejercicio del implemento con id = {0}", implementosId);
        EjercicioEntity ejercicioEntity = implementoPersistence.find(implementosId).getEjercicio();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el ejercicio del implemento con id = {0}", implementosId);
        return ejercicioEntity;
    }

    /**
     * Remplazar ejercicio de un implemento
     *
     * @param implementosId el id del implemento que se quiere actualizar.
     * @param ejerciciosId El id del nuebo ejercicio asociado al implemento.
     * @return el nuevo ejercicio asociado.
     */
    public EjercicioEntity replaceEjercicio(Long implementosId, Long ejerciciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el ejercicio del implemento implemento con id = {0}", implementosId);
        EjercicioEntity ejercicioEntity = ejercicioPersistence.find(ejerciciosId);
        ImplementoEntity implementoEntity = implementoPersistence.find(implementosId);
        implementoEntity.setEjercicio(ejercicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el ejercicio con id = {0} al implemento con id = " + implementosId, ejerciciosId);
        return ejercicioPersistence.find(ejerciciosId);
    }

    /**
     * Borrar el ejercicio de un implemento
     *
     * @param implementosId El implemento que se desea borrar del ejercicio.
     * @throws BusinessLogicException si el implemento no tiene ejercicio
     */
    public void removeEjercicio(Long implementosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el ejercicio del implemento con id = {0}", implementosId);
        ImplementoEntity implementoEntity = implementoPersistence.find(implementosId);
        if (implementoEntity.getEjercicio() == null) {
            throw new BusinessLogicException("El implemento no tiene ejercicio");
        }
        EjercicioEntity ejercicioEntity = ejercicioPersistence.find(implementoEntity.getEjercicio().getId());
        implementoEntity.setEjercicio(null);
        ejercicioEntity.getImplementos().remove(implementoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el ejercicio con id = {0} del implemento con id = " + implementosId, ejercicioEntity.getId());
    }
}
