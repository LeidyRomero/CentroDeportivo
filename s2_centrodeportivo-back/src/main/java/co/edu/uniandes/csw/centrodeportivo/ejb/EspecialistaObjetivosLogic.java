/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.ejb;

import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.EspecialistaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;

import co.edu.uniandes.csw.centrodeportivo.persistence.EspecialistaPersistence;
import co.edu.uniandes.csw.centrodeportivo.persistence.ObjetivoPersistence;
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
public class EspecialistaObjetivosLogic {

    
     private static final Logger LOGGER = Logger.getLogger(EspecialistaObjetivosLogic.class.getName());

    @Inject
    private ObjetivoPersistence objetivoPersistence;

    @Inject
    private EspecialistaPersistence especialistaPersistence;

    /**
     * Agregar un objetivo a la especialista
     *
     * @param objetivosId El id objetivo a guardar
     * @param especialistasId El id de la especialista en la cual se va a guardar el
     * objetivo.
     * @return El objetivo creado.
     */
    public ObjetivoEntity addObjetivo(Long objetivosId, Long especialistasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de agregarle un objetivo a la especialista con id = {0}", especialistasId);
        EspecialistaEntity especialistaEntity = especialistaPersistence.find(especialistasId);
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivosId);
        objetivoEntity.setEspecialista(especialistaEntity);
        LOGGER.log(Level.INFO, "Termina proceso de agregarle un objetivo a la especialista con id = {0}", especialistasId);
        return objetivoEntity;
    }

    /**
     * Retorna todos los objetivos asociados a una especialista
     *
     * @param especialistasId El ID de la especialista buscada
     * @return La lista de objetivos de la especialista
     */
    public List<ObjetivoEntity> getObjetivos(Long especialistasId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar los objetivos asociados a la especialista con id = {0}", especialistasId);
        return especialistaPersistence.find(especialistasId).getObjetivos();
    }

    /**
     * Retorna un objetivo asociado a una especialista
     *
     * @param especialistasId El id de la especialista a buscar.
     * @param objetivosId El id del objetivo a buscar
     * @return El objetivo encontrado dentro de la especialista.
     * @throws BusinessLogicException Si el objetivo no se encuentra en la
     * especialista
     */
    public ObjetivoEntity getObjetivo(Long especialistasId, Long objetivosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el objetivo con id = {0} de la especialista con id = " + especialistasId, objetivosId);
        List<ObjetivoEntity> objetivos = especialistaPersistence.find(especialistasId).getObjetivos();
        ObjetivoEntity objetivoEntity = objetivoPersistence.find(objetivosId);
        int index = objetivos.indexOf(objetivoEntity);
        LOGGER.log(Level.INFO, "Termina proceso de consultar el objetivo con id = {0} de la especialista con id = " + especialistasId, objetivosId);
        if (index >= 0) {
            return objetivos.get(index);
        }
        throw new BusinessLogicException("El objetivo no está asociado a la especialista");
    }

    /**
     * Remplazar objetivos de una especialista
     *
     * @param objetivos Lista de objetivos que serán los de la especialista.
     * @param especialistasId El id de la especialista que se quiere actualizar.
     * @return La lista de objetivos actualizada.
     */
    public List<ObjetivoEntity> replaceObjetivos(Long especialistasId, List<ObjetivoEntity> objetivos) {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la especialista con id = {0}", especialistasId);
        EspecialistaEntity especialistaEntity = especialistaPersistence.find(especialistasId);
        List<ObjetivoEntity> objetivoList = objetivoPersistence.findAll();
        for (ObjetivoEntity objetivo : objetivoList) {
            if (objetivos.contains(objetivo)) {
                objetivo.setEspecialista(especialistaEntity);
            } else if (objetivo.getEspecialista() != null && objetivo.getEspecialista().equals(especialistaEntity)) {
                objetivo.setEspecialista(null);
            }
        }
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la especialista con id = {0}", especialistasId);
        return objetivos;
    }
    
}
