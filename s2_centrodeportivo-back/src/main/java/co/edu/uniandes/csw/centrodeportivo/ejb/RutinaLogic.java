/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.RutinaEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
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
public class RutinaLogic {
    private static final Logger LOGGER = Logger.getLogger(RutinaLogic.class.getName());
    
    @Inject 
    private RutinaPersistence persistencia;
    
     /**
     * Metodo que se encarga de crear un rutina en la base de datos.
     * 
     * @param rutinaEntity Objeto de RutinaEntity con los datos nuevos
     * @return Objeto de RutinaEntity con los datos nuevos y su ID.
     */
    public RutinaEntity createRutina(RutinaEntity rutinaEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del rutina");
        // Invoca la persistencia para crear el rutina
        if (persistencia.findByName(rutinaEntity.getNombre()) != null)
        {
            throw new BusinessLogicException("Ya existe una Rutina con el nombre \"" + rutinaEntity.getNombre() + "\"");
        }
        persistencia.create(rutinaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del rutina");
        return rutinaEntity;
    }
     /**
     * Obtiene la lista de los registros de los rutinas.
     * @return Coleccion de objetos tipo RutinaEntity
     */
    public List<RutinaEntity> getRutinas()
    {
        LOGGER.log(Level.INFO,"Inicia proceso de consultar todos las rutinas almacenadas en la base de datos");
        List<RutinaEntity> lista = persistencia.findAll();
        LOGGER.log(Level.INFO,"Termina proceso de consultar todos las rutinas");
        return lista;
    }
    
        /**
     * Obtiene los datos de una instancia de Rutina a partir de su ID.
     * @param rutinasId Identificador de la instancia a consultar.
     * @return Instancia de RutinaEntity con los datos del rutina encontrado.
     */
    public RutinaEntity getRutina(Long rutinasId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un rutina identificado con el id = {0}",rutinasId);
        RutinaEntity rutinaEntity = persistencia.find(rutinasId);
        if(rutinaEntity == null)
            LOGGER.log(Level.SEVERE, "No se encontró el rutina identificado con el id = {0}");
        
        LOGGER.log(Level.INFO, "Termina proceso de consultar un rutina identificado con el id = {0}");
        return rutinaEntity;
    }
    
    /**
     * Actualiza la informacion de una instancia de rutina de la base de datos.
     * 
     * @param rutinasId identificador de la instancia a actualizar.
     * @param rutinaEntity instancia de Entity con los nuevos datos.
     * @return Instancia de RutinaEntity con los datos actualizados.
     */
    public RutinaEntity updateRutina(Long rutinasId, RutinaEntity rutinaEntity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el rutina identificado con el id = {0}",rutinasId);
        RutinaEntity rutinaActualizado = persistencia.update(rutinaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el rutina identificado con el id = {0}",rutinasId);
        return rutinaActualizado;
    }

    /**
     * Borrar un rutina
     *
     * @param rutinasId: id de la rutina a borrar
     * @throws BusinessLogicException Si la rutina a eliminar tiene libros.
     */
    public void deleteRutina(Long rutinasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la rutina con id = {0}", rutinasId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        List<EjercicioEntity> ejercicios = getRutina(rutinasId).getEjercicios();
        if (ejercicios != null && !ejercicios.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la rutina con id = " + rutinasId + " porque tiene ejercicios asociados");
        }
        List<ObjetivoEntity> objetivos = getRutina(rutinasId).getObjetivos();
        if (objetivos != null && !objetivos.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar la rutina con id = " + rutinasId + " porque tiene objetivos asociados");
        }
        persistencia.delete(rutinasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la rutina con id = {0}", rutinasId);
    }
    
}
