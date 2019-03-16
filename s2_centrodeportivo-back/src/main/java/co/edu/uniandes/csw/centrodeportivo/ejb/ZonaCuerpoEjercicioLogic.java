/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ZonaCuerpoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.EjercicioPersistence;
import co.edu.uniandes.csw.centrodeportivo.persistence.ZonaCuerpoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Pardo
 */
@Stateless
public class ZonaCuerpoEjercicioLogic {
    private static final Logger LOGGER = Logger.getLogger(ZonaCuerpoEjercicioLogic.class.getName());

    @Inject
    private EjercicioPersistence ejercicioPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private ZonaCuerpoPersistence zonaCuerpoPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar un ejercicio a un zonaCuerpo
     *
     * @param zonasCuerpoId El id zonaCuerpo a guardar
     * @param ejerciciosId El id del ejercicio al cual se le va a guardar el zonaCuerpo.
     * @return El zonaCuerpo que fue agregado al ejercicio.
     */
    public EjercicioEntity addEjercicio(Long ejerciciosId, Long zonasCuerpoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el ejercicio con id = {0} al zonaCuerpo con id = " + zonasCuerpoId, ejerciciosId);
        EjercicioEntity ejercicioEntity = ejercicioPersistence.find(ejerciciosId);
        ZonaCuerpoEntity zonaCuerpoEntity = zonaCuerpoPersistence.find(zonasCuerpoId);
        zonaCuerpoEntity.setEjercicio(ejercicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el ejercicio con id = {0} al zonaCuerpo con id = " + zonasCuerpoId, ejerciciosId);
        return ejercicioPersistence.find(ejerciciosId);
    }

    /**
     *
     * Obtener un zonaCuerpo por medio de su id y el de su ejercicio.
     *
     * @param zonasCuerpoId id del zonaCuerpo a ser buscado.
     * @return el ejercicio solicitada por medio de su id.
     */
    public EjercicioEntity getEjercicio(Long zonasCuerpoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el ejercicio del zonaCuerpo con id = {0}", zonasCuerpoId);
        EjercicioEntity ejercicioEntity = zonaCuerpoPersistence.find(zonasCuerpoId).getEjercicio();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el ejercicio del zonaCuerpo con id = {0}", zonasCuerpoId);
        return ejercicioEntity;
    }

    /**
     * Remplazar ejercicio de un zonaCuerpo
     *
     * @param zonasCuerpoId el id del zonaCuerpo que se quiere actualizar.
     * @param ejerciciosId El id del nuebo ejercicio asociado al zonaCuerpo.
     * @return el nuevo ejercicio asociado.
     */
    public EjercicioEntity replaceEjercicio(Long zonasCuerpoId, Long ejerciciosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el ejercicio del zonaCuerpo zonaCuerpo con id = {0}", zonasCuerpoId);
        EjercicioEntity ejercicioEntity = ejercicioPersistence.find(ejerciciosId);
        ZonaCuerpoEntity zonaCuerpoEntity = zonaCuerpoPersistence.find(zonasCuerpoId);
        zonaCuerpoEntity.setEjercicio(ejercicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el ejercicio con id = {0} al zonaCuerpo con id = " + zonasCuerpoId, ejerciciosId);
        return ejercicioPersistence.find(ejerciciosId);
    }

    /**
     * Borrar el ejercicio de un zonaCuerpo
     *
     * @param zonasCuerpoId El zonaCuerpo que se desea borrar del ejercicio.
     * @throws BusinessLogicException si el zonaCuerpo no tiene ejercicio
     */
    public void removeEjercicio(Long zonasCuerpoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el ejercicio del zonaCuerpo con id = {0}", zonasCuerpoId);
        ZonaCuerpoEntity zonaCuerpoEntity = zonaCuerpoPersistence.find(zonasCuerpoId);
        if (zonaCuerpoEntity.getEjercicio() == null) {
            throw new BusinessLogicException("El zonaCuerpo no tiene ejercicio");
        }
        EjercicioEntity ejercicioEntity = ejercicioPersistence.find(zonaCuerpoEntity.getEjercicio().getId());
        zonaCuerpoEntity.setEjercicio(null);
        ejercicioEntity.getZonasCuerpo().remove(zonaCuerpoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el ejercicio con id = {0} del zonaCuerpo con id = " + zonasCuerpoId, ejercicioEntity.getId());
    }
}
