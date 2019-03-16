/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.persistence;

import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ZonaCuerpoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author estudiante
 */
@Stateless
public class ZonaCuerpoPersistence {

    private static final Logger LOGGER = Logger.getLogger(ZonaCuerpoPersistence.class.getName());

    @PersistenceContext(unitName = "LudisPU")
    protected EntityManager em;

    public ZonaCuerpoEntity create(ZonaCuerpoEntity zonaCuerpoEntity) {
        LOGGER.log(Level.INFO, "Creando una zona del cuerpo nueva");
        em.persist(zonaCuerpoEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una zona del cuerpo nueva");
        return zonaCuerpoEntity;
    }    

    public List<ZonaCuerpoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las zonas del cuerpo");
        TypedQuery query = em.createQuery("select u from ZonaCuerpoEntity u", ZonaCuerpoEntity.class);        
        return query.getResultList();
    }    

    public ZonaCuerpoEntity find(Long zonaCuerpoEntity) {
        LOGGER.log(Level.INFO, "Consultando la zona del cuerpo con id={0}", zonaCuerpoEntity);
        return em.find(ZonaCuerpoEntity.class, zonaCuerpoEntity);
    }
   
    public ZonaCuerpoEntity update(ZonaCuerpoEntity zonaCuerpoEntity) {
        LOGGER.log(Level.INFO, "Actualizando la zona del cuerpo con id = {0}", zonaCuerpoEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizarla zonaCuerpo con id = {0}", zonaCuerpoEntity.getId());
        return em.merge(zonaCuerpoEntity);
    }
     
    public void delete(Long zonaCuerpoId) {
        LOGGER.log(Level.INFO, "Borrando la zonaCuerpo con id = {0}", zonaCuerpoId);
        ZonaCuerpoEntity entity = em.find(ZonaCuerpoEntity.class, zonaCuerpoId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la zonaCuerpo con id = {0}", zonaCuerpoId);
    }
}
