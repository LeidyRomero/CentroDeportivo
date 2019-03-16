/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.DeportistaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.EspecialistaEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.DeportistaPersistence;
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
public class EspecialistaDeportistasLogic {

     private static final Logger LOGGER = Logger.getLogger(EspecialistaDeportistasLogic.class.getName());

    @Inject
    private DeportistaPersistence deportistaPersistence;

    @Inject
    private EspecialistaPersistence especialistaPersistence;

    /**
     * Agregar un deportista a la especialista
     *
     * @param deportistasId El id deportista a guardar
     * @param especialistasId El id de la especialista en la cual se va a guardar el
     * deportista.
     * @return El deportista creado.
     */
    public DeportistaEntity addDeportista(Long deportistasId, Long especialistasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un deportista a la especialista con id = {0}", especialistasId);
        EspecialistaEntity especialistaEntity = especialistaPersistence.find(especialistasId);
        DeportistaEntity deportistaEntity = deportistaPersistence.find(deportistasId);
        deportistaEntity.setEspecialista(especialistaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un deportista a la especialista con id = {0}", especialistasId);
        return deportistaEntity;
    }

    /**
     * Retorna todos los deportistas asociados a una especialista
     *
     * @param especialistasId El ID de la especialista buscada
     * @return La lista de deportistas de la especialista
     */
    public List<DeportistaEntity> getDeportistas(Long especialistasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los deportistas asociados a la especialista con id = {0}", especialistasId);
        return especialistaPersistence.find(especialistasId).getDeportistas();
    }

    /**
     * Retorna un deportista asociado a una especialista
     *
     * @param especialistasId El id de la especialista a buscar.
     * @param deportistasId El id del deportista a buscar
     * @return El deportista encontrado dentro de la especialista.
     * @throws BusinessLogicException Si el deportista no se encuentra en la
     * especialista
     */
    public DeportistaEntity getDeportista(Long especialistasId, Long deportistasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el deportista con id = {0} de la especialista con id = " + especialistasId, deportistasId);
        List<DeportistaEntity> deportistas = especialistaPersistence.find(especialistasId).getDeportistas();
        DeportistaEntity deportistaEntity = deportistaPersistence.find(deportistasId);
        int index = deportistas.indexOf(deportistaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el deportista con id = {0} de la especialista con id = " + especialistasId, deportistasId);
        if (index >= 0) {
            return deportistas.get(index);
        }
        throw new BusinessLogicException("El deportista no está asociado a la especialista");
    }

    /**
     * Remplazar deportistas de una especialista
     *
     * @param deportistas Lista de deportistas que serán los de la especialista.
     * @param especialistasId El id de la especialista que se quiere actualizar.
     * @return La lista de deportistas actualizada.
     */
    public List<DeportistaEntity> replaceDeportistas(Long especialistasId, List<DeportistaEntity> deportistas) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la especialista con id = {0}", especialistasId);
        EspecialistaEntity especialistaEntity = especialistaPersistence.find(especialistasId);
        List<DeportistaEntity> deportistaList = deportistaPersistence.findAll();
        for (DeportistaEntity deportista : deportistaList) {
            if (deportistas.contains(deportista)) {
                deportista.setEspecialista(especialistaEntity);
            } else if (deportista.getEspecialista() != null && deportista.getEspecialista().equals(especialistaEntity)) {
                deportista.setEspecialista(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la especialista con id = {0}", especialistasId);
        return deportistas;
    }
}
