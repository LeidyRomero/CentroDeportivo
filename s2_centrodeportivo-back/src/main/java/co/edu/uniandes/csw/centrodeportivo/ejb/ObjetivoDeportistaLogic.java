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
public class ObjetivoDeportistaLogic {
   private static final Logger LOGGER = Logger.getLogger(ObjetivoDeportistaLogic.class.getName());

    @Inject
    private DeportistaPersistence deportistaPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    @Inject
    private ObjetivoPersistence objetivoPersistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    /**
     * Agregar un deportista a un objetivo
     *
     * @param objetivosId El id objetivo a guardar
     * @param deportistasId El id del deportista al cual se le va a guardar el objetivo.
     * @return El objetivo que fue agregado al deportista.
     */
    public DeportistaEntity addDeportista(Long deportistasId, Long objetivosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de asociar el deportista con id = {0} al objetivo con id = " + objetivosId, deportistasId);
        DeportistaEntity deportistaEntity = deportistaPersistence.find(deportistasId);
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivosId);
        objetivoEntity.setDeportista(deportistaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el deportista con id = {0} al objetivo con id = " + objetivosId, deportistasId);
        return deportistaPersistence.find(deportistasId);
    }

    /**
     *
     * Obtener un objetivo por medio de su id y el de su deportista.
     *
     * @param objetivosId id del objetivo a ser buscado.
     * @return el deportista solicitado por medio de su id.
     */
    public DeportistaEntity getDeportista(Long objetivosId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el deportista del objetivo con id = {0}", objetivosId);
        DeportistaEntity deportistaEntity = objetivoPersistence.find(objetivosId).getDeportista();
        LOGGER.log(Level.INFO, "Termina proceso de consultar el deportista del objetivo con id = {0}", objetivosId);
        return deportistaEntity;
    }

    /**
     * Reemplazar deportista de un objetivo
     *
     * @param objetivosId el id del objetivo que se quiere actualizar.
     * @param deportistasId El id del nuevo deportista asociado al objetivo.
     * @return el nuevo deportista asociado.
     */
    public DeportistaEntity replaceDeportista(Long objetivosId, Long deportistasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar el deportista del objetivo objetivo con id = {0}", objetivosId);
        DeportistaEntity deportistaEntity = deportistaPersistence.find(deportistasId);
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivosId);
        objetivoEntity.setDeportista(deportistaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de asociar el deportista con id = {0} al objetivo con id = " + objetivosId, deportistasId);
        return deportistaPersistence.find(deportistasId);
    }

    /**
     * Borrar el deportista de un objetivo
     *
     * @param objetivosId El objetivo que se desea borrar del deportista.
     * @throws BusinessLogicException si el objetivo no tiene deportista
     */
    public void removeDeportista(Long objetivosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el deportista del objetivo con id = {0}", objetivosId);
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivosId);
        if (objetivoEntity.getDeportista() == null) {
            throw new BusinessLogicException("El objetivo no tiene deportista");
        }
        DeportistaEntity deportistaEntity = deportistaPersistence.find(objetivoEntity.getDeportista().getId());
        objetivoEntity.setDeportista(null);
        deportistaEntity.getObjetivos().remove(objetivoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el deportista con id = {0} del objetivo con id = " + objetivosId, deportistaEntity.getId());
    }
}