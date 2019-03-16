/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.persistence;

import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
/**
 *
 * @author Daniel Pardo
 */
@Stateless
public class EjercicioPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(EjercicioPersistence.class.getName());

    @PersistenceContext(unitName = "LudisPU")
    protected EntityManager em;
    
    
    public EjercicioEntity create(EjercicioEntity ejercicioEntity) {
        LOGGER.log(Level.INFO, "Creando un ejercicio nuevo");
        em.persist(ejercicioEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un ejercicio nuevo");
        return ejercicioEntity;
    }    

    public List<EjercicioEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los ejercicios");
        TypedQuery query = em.createQuery("select u from EjercicioEntity u", EjercicioEntity.class);        
        return query.getResultList();
    }    

    public EjercicioEntity find(Long ejercicioEntity) {
        LOGGER.log(Level.INFO, "Consultando el ejercicio con id={0}", ejercicioEntity);
        return em.find(EjercicioEntity.class, ejercicioEntity);
    }

    public EjercicioEntity update(EjercicioEntity ejercicioEntity) {
        LOGGER.log(Level.INFO, "Actualizando el ejercicio con id = {0}", ejercicioEntity.getId());
        LOGGER.log(Level.INFO, "Saliendo de actualizar el ejercicio con id = {0}", ejercicioEntity.getId());
        return em.merge(ejercicioEntity);
    }
    
    public void delete(Long ejerciciosId) {
        LOGGER.log(Level.INFO, "Borrando el ejercicio con id = {0}", ejerciciosId);
        EjercicioEntity entity = em.find(EjercicioEntity.class, ejerciciosId);
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el ejercicio con id = {0}", ejerciciosId);
    }
   
}
