/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.MaquinaEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.MaquinaPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Diany Quintero
 */
@Stateless
public class MaquinaLogic 
{
    private static final Logger LOGGER = Logger.getLogger(MaquinaLogic.class.getName());
    
    @Inject
    private MaquinaPersistence persistence;
    
     /**
     * Se encarga de crear una maquina en la base de datos.
     *
     * @param maquinaEntity Objeto de tipo MaquinaEntity con los datos nuevos
     * @return Objeto de MaquinaEntity con los datos nuevos y su ID.
     */
    public MaquinaEntity createMaquina(MaquinaEntity maquinaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la maquina");
        // Invoca la persistencia para crear la editorial
        persistence.create(maquinaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la editorial");
        return maquinaEntity;
    }
    
    
     /**
     * Obtiene la lista de las maquinas.
     *
     * @return Colección de objetos de Maquinaentity.
     */
    public List<MaquinaEntity> getMaquinas() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las maquinas");
        List<MaquinaEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las maquinas");
        return lista;
    }
    
    /**
     * Obtiene los datos de una instancia de Mquina a partir de su ID.
     *
     * @param maquinasId Identificador de la instancia a consultar
     * @return Instancia de MaquinaEntity con los datos del Author consultado.
     */
    public MaquinaEntity getMaquina(Long maquinasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la maquina con id = {0}", maquinasId);
        MaquinaEntity maquinaEntity = persistence.find(maquinasId);
        if (maquinaEntity == null) {
            LOGGER.log(Level.SEVERE, "la maquina con el id = {0} no existe", maquinasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la maquina con id = {0}", maquinasId);
        return maquinaEntity;
    }
    
     /**
     * Actualiza la información de una instancia de Maquina.
     *
     * @param maquinasId Identificador de la instancia a actualizar
     * @param maquinaEntity Instancia de MaquinaEntity con los nuevos datos.
     * @return Instancia de MaquinaEntity con los datos actualizados.
     */
    public MaquinaEntity updateMaquina(Long maquinasId, MaquinaEntity maquinaEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la mquina con id = {0}", maquinasId);
        MaquinaEntity newMaquinaEntity = persistence.update(maquinaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el autor con id = {0}", maquinasId);
        return newMaquinaEntity;
    }
    
     /**
     * Elimina una instancia de Maquina de la base de datos.
     *
     * @param maquinasId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si la maquina a eliminar no existe.
     */
    public void deleteMaquina(Long maquinasId){
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la maquina con id = {0}", maquinasId);
        
        persistence.delete(maquinasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la maquina con id = {0}", maquinasId);
    }
    
}
