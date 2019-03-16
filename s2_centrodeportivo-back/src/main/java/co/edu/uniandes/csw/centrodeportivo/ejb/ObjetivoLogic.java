/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.ObjetivoPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Leidy Romero
 */
@Stateless
public class ObjetivoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ObjetivoLogic.class.getName());
    
    @Inject 
    private ObjetivoPersistence persistencia;
    
     /**
     * Metodo que se encarga de crear un objetivo en la base de datos.
     * 
     * @param objetivoEntity Objeto de ObjetivoEntity con los datos nuevos
     * @return Objeto de ObjetivoEntity con los datos nuevos y su ID.
     * @throws BusinessLogicException si ya existe un objetivo con la descripcion
     */
    public ObjetivoEntity createObjetivo(ObjetivoEntity objetivoEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de un objetivo");
        if (persistencia.findByDescripcion(objetivoEntity.getDescripcion()) != null) {
            throw new BusinessLogicException("Ya existe un onjetivo con la descripción \"" + objetivoEntity.getDescripcion() + "\"");
        }
        ObjetivoEntity nuevoObjetivo = persistencia.create(objetivoEntity);
        LOGGER.log(Level.INFO,"Termina proceso de creación de un objetivo");
        return nuevoObjetivo;
    }
     /**
     * Obtiene la lista de los registros de los objetivos.
     * @return Coleccion de objetos tipo ObjetivoEntity
     */
    public List<ObjetivoEntity> getObjetivos()
    {
        LOGGER.log(Level.INFO,"Inicia proceso de consultar todos los objetivos almacenado en la base de datos");
        List<ObjetivoEntity> lista = persistencia.findAll();
        LOGGER.log(Level.INFO,"Termina proceso de consultar todos los objetivos");
        return lista;
    }
    
        /**
     * Obtiene los datos de una instancia de Objetivo a partir de su ID.
     * @param objetivosId Identificador de la instancia a consultar.
     * @return Instancia de ObjetivoEntity con los datos del objetivo encontrado.
     */
    public ObjetivoEntity getObjetivo(Long objetivosId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un objetivo identificado con el id = {0}",objetivosId);
        ObjetivoEntity objetivoEntity = persistencia.find(objetivosId);
        if(objetivoEntity == null)
            LOGGER.log(Level.SEVERE, "No se encontró el objetivo identificado con el id = {0}");
        
        LOGGER.log(Level.INFO, "Termina proceso de consultar un objetivo identificado con el id = {0}");
        return objetivoEntity;
    }
    
    /**
     * Actualiza la informacion de una instancia de objetivo de la base de datos.
     * 
     * @param objetivosId identificador de la instancia a actualizar.
     * @param objetivoEntity instancia de Entity con los nuevos datos.
     * @return Instancia de ObjetivoEntity con los datos actualizados.
     * REGLA DE NEGOCIO: La descripcion del nuevo objetivo no debe existir en la base de datos.
     */
    public ObjetivoEntity updateObjetivo(Long objetivosId, ObjetivoEntity objetivoEntity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el objetivo identificado con el id = {0}",objetivosId);
        ObjetivoEntity objetivoActualizado = persistencia.update(objetivoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el objetivo identificado con el id = {0}",objetivosId);
        return objetivoActualizado;
    }
}
