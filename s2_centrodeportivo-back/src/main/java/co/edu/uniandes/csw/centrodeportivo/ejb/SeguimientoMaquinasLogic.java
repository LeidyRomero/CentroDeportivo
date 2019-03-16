/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.MaquinaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.SeguimientoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.MaquinaPersistence;
import co.edu.uniandes.csw.centrodeportivo.persistence.SeguimientoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
/**
 * Clase que implementa la conexion con la persistencia para la relación entre
 * la entidad de Seguimiento y Maquiina.
 * @author Diany Quintero
 */

@Stateless
public class SeguimientoMaquinasLogic{
    
 private static final Logger LOGGER = Logger.getLogger(SeguimientoMaquinasLogic.class.getName());


    @Inject
    private MaquinaPersistence maquinaPersistence;
    
    @Inject
    private SeguimientoPersistence seguimientoPersistence;
    
        /**
     * Agregar una maquina al seguimiento
     *
     * @param maquinasId El id de la maquina a guardar
     * @param seguimientosId El id del seguimiento en la cual se va a guardar el
     * ejercicio.
     * @return La maquina creada.
     */
    public MaquinaEntity addMaquina(Long maquinasId, Long seguimientosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle una maquina a un seguimiento con id = {0}", seguimientosId);
        SeguimientoEntity seguimientoEntity = seguimientoPersistence.find(seguimientosId);
        MaquinaEntity maquinaEntity = maquinaPersistence.find(maquinasId);
        maquinaEntity.setSeguimiento(seguimientoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle una maquina a un seguimiento con id = {0}", seguimientosId);
        return maquinaEntity;
    }
    
    /**
     * Retorna todos las maquinas asociadas a un seguimiento
     *
     * @param seguimientosId El ID de la maquina buscada
     * @return La lista de maquinas del seguimiento
     */
    public List<MaquinaEntity> getMaquinas(Long seguimientosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar las maquinas asociadas al seguimiento con id = {0}", seguimientosId);
        return seguimientoPersistence.find(seguimientosId).getMaquinas();
    }
    
    /**
     * Retorna una maquina asociada a un seguimiento
     *
     * @param maquinasId El id de la maquina a buscar.
     * @param seguimientosId El id del seguimiento a buscar
     * @return El ejercicio encontrado dentro de la maquina.
     * @throws BusinessLogicException Si la maquina no se encuentra en la
     * maquina
     */
    public MaquinaEntity getMaquina(Long seguimientosId, Long maquinasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la maquina con id = {0} del seguimiento con id = " + seguimientosId, maquinasId);
        List<MaquinaEntity> maquinas = seguimientoPersistence.find(seguimientosId).getMaquinas();
        MaquinaEntity maquinaEntity = maquinaPersistence.find(maquinasId);
        int index = maquinas.indexOf(maquinaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la maquina con id = {0} del seguimiento con id = " + seguimientosId, maquinasId);
        if (index >= 0) {
            return maquinas.get(index);
        }
        throw new BusinessLogicException("La maquina no está asociada al Seguimiento");
    }
    
    /**
     * Reemplazar maquinas de un Segumiento
     *
     * @param maquinas Lista de maquinas que serán los del Seguimiento.
     * @param seguimientosId El id del seguimiento que se quiere actualizar.
     * @return La lista de maquinas actualizada.
     */
    public List<MaquinaEntity> replaceMaquinas(Long seguimientosId, List<MaquinaEntity> maquinas) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el seguimiento con id = {0}", seguimientosId);
        SeguimientoEntity seguimientoEntity = seguimientoPersistence.find(seguimientosId);
        List<MaquinaEntity> maquinaList = maquinaPersistence.findAll();
        for (MaquinaEntity maquina : maquinaList) {
            if (maquinas.contains(maquina)) {
                maquina.setSeguimiento(seguimientoEntity);
            } else if (maquina.getSeguimiento()!= null && maquina.getSeguimiento().equals(seguimientoEntity)) {
                maquina.setSeguimiento(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el seguimiento con id = {0}", seguimientosId);
        return maquinas;
    }
}
