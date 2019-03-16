/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.MaquinaEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.EjercicioPersistence;
import co.edu.uniandes.csw.centrodeportivo.persistence.MaquinaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Maquina y Ejercicio.
 * @author Diany Quintero
 */

@Stateless
public class MaquinaEjerciciosLogic {
    
 private static final Logger LOGGER = Logger.getLogger(MaquinaEjerciciosLogic.class.getName());

    @Inject
    private EjercicioPersistence ejercicioPersistence;

    @Inject
    private MaquinaPersistence maquinaPersistence;
    
        /**
     * Agregar un ejercicio a la maquina
     *
     * @param ejerciciosId El id del ejercicio a guardar
     * @param maquinasId El id de la maquina en la cual se va a guardar el
     * ejercicio.
     * @return El ejercicio creado.
     */
    public EjercicioEntity addEjercicio(Long ejerciciosId, Long maquinasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un ejercicio a una maquina con id = {0}", maquinasId);
        MaquinaEntity maquinaEntity = maquinaPersistence.find(maquinasId);
        EjercicioEntity ejercicioEntity = ejercicioPersistence.find(ejerciciosId);
        ejercicioEntity.setMaquina(maquinaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un ejercicio a una maquina con id = {0}", maquinasId);
        return ejercicioEntity;
    }
    
    /**
     * Retorna todos los ejercicios asociados a una maquina
     *
     * @param maquinasId El ID de la maquina buscada
     * @return La lista de ejercicios de la maquina
     */
    public List<EjercicioEntity> getEjercicios(Long maquinasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los ejercicios asociados a la maquina con id = {0}", maquinasId);
        return maquinaPersistence.find(maquinasId).getEjercicios();
    }
    
    /**
     * Retorna un ejercicio asociado a una maquina
     *
     * @param maquinasId El id de la maquina a buscar.
     * @param ejerciciosId El id del ejercicio a buscar
     * @return El ejercicio encontrado dentro de la maquina.
     * @throws BusinessLogicException Si la maquina no se encuentra en la
     * maquina
     */
    public EjercicioEntity getEjercicio(Long maquinasId, Long ejerciciosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el ejercicio con id = {0} de la maquina con id = " + maquinasId, ejerciciosId);
        List<EjercicioEntity> ejercicios = maquinaPersistence.find(maquinasId).getEjercicios();
        EjercicioEntity ejercicioEntity = ejercicioPersistence.find(ejerciciosId);
        int index = ejercicios.indexOf(ejercicioEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el ejercicio con id = {0} de la maquina con id = " + maquinasId, ejerciciosId);
        if (index >= 0) {
            return ejercicios.get(index);
           
        }
        throw new BusinessLogicException("El ejercicio no está asociado a la maquina");
    }
    
    /**
     * Remplazar ejercicios de una maquina
     *
     * @param ejercicios Lista de libros que serán los de la maquina.
     * @param maquinasId El id de la maquina que se quiere actualizar.
     * @return La lista de ejercicios actualizada.
     */
    public List<EjercicioEntity> replaceEjercicios(Long maquinasId, List<EjercicioEntity> ejercicios) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la maquina con id = {0}", maquinasId);
        MaquinaEntity maquinaEntity = maquinaPersistence.find(maquinasId);
        List<EjercicioEntity> ejercicioList = ejercicioPersistence.findAll();
        for (EjercicioEntity ejercicio : ejercicioList) {
            if (ejercicios.contains(ejercicio)) {
                ejercicio.setMaquina(maquinaEntity);
            } else if (ejercicio.getMaquina()!= null && ejercicio.getMaquina().equals(maquinaEntity)) {
                ejercicio.setMaquina(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la maquina con id = {0}", maquinasId);
        return ejercicios;
    }
}
