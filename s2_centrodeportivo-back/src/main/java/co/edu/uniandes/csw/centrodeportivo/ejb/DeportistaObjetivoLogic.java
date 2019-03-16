/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.DeportistaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.DeportistaPersistence;
import co.edu.uniandes.csw.centrodeportivo.persistence.ObjetivoPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Leidy Romero
 */
@Stateless
public class DeportistaObjetivoLogic {

    private static final Logger LOGGER = Logger.getLogger(DeportistaObjetivoLogic.class.getName());

    @Inject
    private ObjetivoPersistence objetivoPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private DeportistaPersistence deportistaPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar un objetivo a un deportista
     *
     * @param deportistasId El id deportista a guardar
     * @param objetivosId El id del objetivo al cual se le va a guardar el deportista.
     * @return El deportista que fue agregado al objetivo.
     */
    public ObjetivoEntity addObjetivo(Long objetivosId, Long deportistasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el objetivo con id = {0} al deportista con id = " + deportistasId, objetivosId);
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivosId);
        DeportistaEntity deportistaEntity = deportistaPersistence.find(deportistasId);
        deportistaEntity.setObjetivo(objetivoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el objetivo con id = {0} al deportista con id = " + deportistasId, objetivosId);
        return objetivoPersistence.find(objetivosId);
    }

    /**
     *
     * Obtener un deportista por medio de su id y el de su objetivo.
     *
     * @param deportistasId id del deportista a ser buscado.
     * @return el objetivo solicitado por medio de su id.
     */
    public ObjetivoEntity getObjetivo(Long deportistasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el objetivo del deportista con id = {0}", deportistasId);
        ObjetivoEntity objetivoEntity = deportistaPersistence.find(deportistasId).getObjetivo();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el objetivo del deportista con id = {0}", deportistasId);
        return objetivoEntity;
    }

    /**
     * Remplazar objetivo de un deportista
     *
     * @param deportistasId el id del deportista que se quiere actualizar.
     * @param objetivosId El id del nuebo objetivo asociado al deportista.
     * @return el nuevo objetivo asociado.
     */
    public ObjetivoEntity replaceObjetivo(Long deportistasId, Long objetivosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el objetivo del deportista deportista con id = {0}", deportistasId);
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivosId);
        DeportistaEntity deportistaEntity = deportistaPersistence.find(deportistasId);
        deportistaEntity.setObjetivo(objetivoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el objetivo con id = {0} al deportista con id = " + deportistasId, objetivosId);
        return objetivoPersistence.find(objetivosId);
    }

    /**
     * Borrar el objetivo de un deportista
     *
     * @param deportistasId El deportista que se desea borrar del objetivo.
     * @throws BusinessLogicException si el deportista no tiene objetivo
     */
    public void removeObjetivo(Long deportistasId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el objetivo del deportista con id = {0}", deportistasId);
        DeportistaEntity deportistaEntity = deportistaPersistence.find(deportistasId);
        if (deportistaEntity.getObjetivo() == null) {
            throw new BusinessLogicException("El deportista no tiene objetivo");
        }
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(deportistaEntity.getObjetivo().getId());
        deportistaEntity.setObjetivo(null);
        objetivoEntity.getCasosExitosos().remove(deportistaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el objetivo con id = {0} del deportista con id = " + deportistasId, objetivoEntity.getId());
    }
}