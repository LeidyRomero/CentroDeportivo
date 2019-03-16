/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.ZonaCuerpoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.ZonaCuerpoPersistence;
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
public class ZonaCuerpoLogic {
    private static final Logger LOGGER = Logger.getLogger(ZonaCuerpoLogic.class.getName());
    
    @Inject
    private ZonaCuerpoPersistence persistence;
    
     /**
     * Se encarga de crear una zonaCuerpo en la base de datos.
     *
     * @param zonaCuerpoEntity Objeto de tipo ZonaCuerpoEntity con los datos nuevos
     * @return Objeto de ZonaCuerpoEntity con los datos nuevos y su ID.
     */
    public ZonaCuerpoEntity createZonaCuerpo(ZonaCuerpoEntity zonaCuerpoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la zonaCuerpo");
        // Invoca la persistencia para crear la editorial
        persistence.create(zonaCuerpoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la editorial");
        return zonaCuerpoEntity;
    }
    
    
     /**
     * Obtiene la lista de las zonasCuerpo.
     *
     * @return Colección de objetos de ZonaCuerpoentity.
     */
    public List<ZonaCuerpoEntity> getZonasCuerpo() {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar todas las zonasCuerpo");
        List<ZonaCuerpoEntity> lista = persistence.findAll();
        LOGGER.log(Level.INFO, "Termina proceso de consultar todas las zonasCuerpo");
        return lista;
    }
    
    /**
     * Obtiene los datos de una instancia de Mquina a partir de su ID.
     *
     * @param zonasCuerpoId Identificador de la instancia a consultar
     * @return Instancia de ZonaCuerpoEntity con los datos del Author consultado.
     */
    public ZonaCuerpoEntity getZonaCuerpo(Long zonasCuerpoId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la zonaCuerpo con id = {0}", zonasCuerpoId);
        ZonaCuerpoEntity zonaCuerpoEntity = persistence.find(zonasCuerpoId);
        if (zonaCuerpoEntity == null) {
            LOGGER.log(Level.SEVERE, "la zonaCuerpo con el id = {0} no existe", zonasCuerpoId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar la zonaCuerpo con id = {0}", zonasCuerpoId);
        return zonaCuerpoEntity;
    }
    
     /**
     * Actualiza la información de una instancia de ZonaCuerpo.
     *
     * @param zonasCuerpoId Identificador de la instancia a actualizar
     * @param zonaCuerpoEntity Instancia de ZonaCuerpoEntity con los nuevos datos.
     * @return Instancia de ZonaCuerpoEntity con los datos actualizados.
     */
    public ZonaCuerpoEntity updateZonaCuerpo(Long zonasCuerpoId, ZonaCuerpoEntity zonaCuerpoEntity) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la mquina con id = {0}", zonasCuerpoId);
        ZonaCuerpoEntity newZonaCuerpoEntity = persistence.update(zonaCuerpoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el autor con id = {0}", zonasCuerpoId);
        return newZonaCuerpoEntity;
    }
    
     /**
     * Elimina una instancia de ZonaCuerpo de la base de datos.
     *
     * @param zonasCuerpoId Identificador de la instancia a eliminar.
     * @throws BusinessLogicException si la zonaCuerpo a eliminar no existe.
     */
    public void deleteZonaCuerpo(Long zonasCuerpoId){
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la zonaCuerpo con id = {0}", zonasCuerpoId);
        
        persistence.delete(zonasCuerpoId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la zonaCuerpo con id = {0}", zonasCuerpoId);
    }
}