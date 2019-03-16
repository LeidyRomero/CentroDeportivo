/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.DeportistaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.EspecialistaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.EspecialistaPersistence;
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
public class EspecialistaLogic {

 private static final Logger LOGGER = Logger.getLogger(EspecialistaLogic.class.getName());
    
    @Inject 
    private EspecialistaPersistence persistencia;
    
     /**
     * Metodo que se encarga de crear un especialista en la base de datos.
     * 
     * @param especialistaEntity Objeto de EspecialistaEntity con los datos nuevos
     * @return Objeto de EspecialistaEntity con los datos nuevos y su ID.
     */
    public EspecialistaEntity createEspecialista(EspecialistaEntity especialistaEntity) throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "Inicia proceso de creación del especialista");
        // Invoca la persistencia para crear el especialista
        if (persistencia.findByName(especialistaEntity.getNombre()) != null) {
            throw new BusinessLogicException("Ya existe un Especialista con el nombre \"" + especialistaEntity.getNombre() + "\"");
        }
        
        persistencia.create(especialistaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del especialista");
        return especialistaEntity;
    }
     /**
     * Obtiene la lista de los especialistas.
     * @return Coleccion de objetos tipo EspecialistaEntity
     */
    public List<EspecialistaEntity> getEspecialistas()
    {
        LOGGER.log(Level.INFO,"Inicia proceso de consultar todos los especialistas almacenado en la base de datos");
        List<EspecialistaEntity> lista = persistencia.findAll();
        LOGGER.log(Level.INFO,"Termina proceso de consultar todos los especialistas");
        return lista;
    }
    
        /**
     * Obtiene los datos de un Especialista solicitado a partir de su identificador.
     * @param especialistasId Identificador de la instancia a consultar.
     * @return Instancia de EspecialistaEntity con los datos del especialista encontrado.
     */
    public EspecialistaEntity getEspecialista(Long especialistasId)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar un especialista identificado con el id = {0}",especialistasId);
        EspecialistaEntity especialistaEntity = persistencia.find(especialistasId);
        if(especialistaEntity == null)
        {
            LOGGER.log(Level.SEVERE, "No se encontró el especialista identificado con el id = {0}", especialistasId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar un especialista identificado con el id = {0}", especialistasId);
        return especialistaEntity;
    }
    
    /**
     * Actualiza la informacion de una especialista  determinado.
     * 
     * @param especialistasId identificador de la instancia a actualizar.
     * @param especialistaEntity instancia de Entity con los nuevos datos.
     * @return Instancia de EspecialistaEntity con los datos actualizados.
     */
    public EspecialistaEntity updateEspecialista(Long especialistasId, EspecialistaEntity especialistaEntity)
    {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el especialista identificado con el id = {0}",especialistasId);
        EspecialistaEntity especialistaActualizado = persistencia.update(especialistaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar el especialista identificado con el id = {0}",especialistasId);
        return especialistaActualizado;
    }

   /**
     * Borrar un especialista
     *
     * @param especialistasId: id de la especialista a borrar
     * @throws BusinessLogicException Si la especialista a eliminar tiene libros.
     */
    public void deleteEspecialista(Long especialistasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el especialista con id = {0}", especialistasId);
        // Note que, por medio de la inyección de dependencias se llama al método "delete(id)" que se encuentra en la persistencia.
        List<ObjetivoEntity> objetivos = getEspecialista(especialistasId).getObjetivos();
        if (objetivos != null && !objetivos.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el especialista con id = " + especialistasId + " porque tiene objetivos asociados");
        }
        List<DeportistaEntity> deportistas = getEspecialista(especialistasId).getDeportistas();
        if (deportistas != null && !deportistas.isEmpty()) {
            throw new BusinessLogicException("No se puede borrar el especialista con id = " + especialistasId + " porque tiene deportistas asociados");
        }
        persistencia.delete(especialistasId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el especialista con id = {0}", especialistasId);
    }

    
}
