/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.RutinaEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.EjercicioPersistence;

import co.edu.uniandes.csw.centrodeportivo.persistence.RutinaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Francisco Jose MacAllister
 */
@Stateless
public class RutinaEjerciciosLogic {

    private static final Logger LOGGER = Logger.getLogger(RutinaEjerciciosLogic.class.getName());

    @Inject
    private EjercicioPersistence ejercicioPersistence;

    @Inject
    private RutinaPersistence rutinaPersistence;

    /**
     * Agregar un ejercicio a la rutina
     *
     * @param ejerciciosId El id ejercicio a guardar
     * @param rutinasId El id de la rutina en la cual se va a guardar el
     * ejercicio.
     * @return El ejercicio creado.
     */
    public EjercicioEntity addEjercicio(Long ejerciciosId, Long rutinasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un ejercicio a la rutina con id = {0}", rutinasId);
        RutinaEntity rutinaEntity = rutinaPersistence.find(rutinasId);
        EjercicioEntity ejercicioEntity = ejercicioPersistence.find(ejerciciosId);
        ejercicioEntity.setRutina(rutinaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un ejercicio a la rutina con id = {0}", rutinasId);
        return ejercicioEntity;
    }

    /**
     * Retorna todos los ejercicios asociados a una rutina
     *
     * @param rutinasId El ID de la rutina buscada
     * @return La lista de ejercicios de la rutina
     */
    public List<EjercicioEntity> getEjercicios(Long rutinasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los ejercicios asociados a la rutina con id = {0}", rutinasId);
        return rutinaPersistence.find(rutinasId).getEjercicios();
    }

    /**
     * Retorna un ejercicio asociado a una rutina
     *
     * @param rutinasId El id de la rutina a buscar.
     * @param ejerciciosId El id del ejercicio a buscar
     * @return El ejercicio encontrado dentro de la rutina.
     * @throws BusinessLogicException Si el ejercicio no se encuentra en la
     * rutina
     */
    public EjercicioEntity getEjercicio(Long rutinasId, Long ejerciciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el ejercicio con id = {0} de la rutina con id = " + rutinasId, ejerciciosId);
        List<EjercicioEntity> ejercicios = rutinaPersistence.find(rutinasId).getEjercicios();
        EjercicioEntity ejercicioEntity = ejercicioPersistence.find(ejerciciosId);
        int index = ejercicios.indexOf(ejercicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el ejercicio con id = {0} de la rutina con id = " + rutinasId, ejerciciosId);
        if (index >= 0) {
            return ejercicios.get(index);
        }
        throw new BusinessLogicException("El ejercicio no está asociado a la rutina");
    }

    /**
     * Remplazar ejercicios de una rutina
     *
     * @param ejercicios Lista de ejercicios que serán los de la rutina.
     * @param rutinasId El id de la rutina que se quiere actualizar.
     * @return La lista de ejercicios actualizada.
     */
    public List<EjercicioEntity> replaceEjercicios(Long rutinasId, List<EjercicioEntity> ejercicios) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la rutina con id = {0}", rutinasId);
        RutinaEntity rutinaEntity = rutinaPersistence.find(rutinasId);
        List<EjercicioEntity> ejercicioList = ejercicioPersistence.findAll();
        for (EjercicioEntity ejercicio : ejercicioList) {
            if (ejercicios.contains(ejercicio)) {
                ejercicio.setRutina(rutinaEntity);
            } else if (ejercicio.getRutina() != null && ejercicio.getRutina().equals(rutinaEntity)) {
                ejercicio.setRutina(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la rutina con id = {0}", rutinasId);
        return ejercicios;
    }
    
}
