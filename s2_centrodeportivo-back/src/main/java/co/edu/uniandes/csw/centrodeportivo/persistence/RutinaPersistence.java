/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.persistence;

import co.edu.uniandes.csw.centrodeportivo.entities.RutinaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Francisco Jose MacAllister
 */
@Stateless
public class RutinaPersistence {
    private static final Logger LOGGER = Logger.getLogger(RutinaPersistence.class.getName());

    @PersistenceContext(unitName = "LudisPU")
    protected EntityManager em;
    
    /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param rutinaEntity
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public RutinaEntity create(RutinaEntity rutinaEntity) {
        LOGGER.log(Level.INFO, "Creando una nueva rutina");
        /* Note que hacemos uso de un método propio de EntityManager para persistir al rutina en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(rutinaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear una nueva rutina");
        return rutinaEntity;
    }
    
    /**
     * Devuelve todos los rutinas de la base de datos.
     *
     * @return una lista con todos los rutinas que encuentre en la base de
     * datos, "select u from RutinaEntity u" es como un "select * from
     * RutinaEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<RutinaEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todas las rutinas");
        // Se crea un query para buscar todos los rutinas en la base de datos.
        TypedQuery query = em.createQuery("select u from RutinaEntity u", RutinaEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de rutinas.
        return query.getResultList();
    }
    
     /**
     * Busca si hay algun rutina con el id que se envía de argumento
     *
     * @param rutinaId
     * @return un rutina.
     */
    public RutinaEntity find(Long rutinaId) {
        LOGGER.log(Level.INFO, "Consultando el rutina con id={0}", rutinaId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from RutinaEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(RutinaEntity.class, rutinaId);
    }
     /**
     * Actualiza la informacion de un rutina.
     *
     * @param rutinaEntity
     * @return un rutina con los cambios aplicados.
     */
    public RutinaEntity update(RutinaEntity rutinaEntity) {
        LOGGER.log(Level.INFO, "Actualizando el rutina con id = {0}", rutinaEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        el rutina con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar el rutina con id = {0}", rutinaEntity.getId());
        return em.merge(rutinaEntity);
    }

public void delete(Long rutinaId) {
        LOGGER.log(Level.INFO, "Borrando la rutina con id = {0}", rutinaId);
        // Se hace uso de mismo método que esta explicado en public EditorialEntity find(Long id) para obtener la editorial a borrar.
        RutinaEntity entity = em.find(RutinaEntity.class, rutinaId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar la rutina con id = {0}", rutinaId);
    }
public RutinaEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando rutina por nombre ", name);
        // Se crea un query para buscar rutinas con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From RutinaEntity e where e.name = :name", RutinaEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("name", name);
        // Se invoca el query se obtiene la lista resultado
        List<RutinaEntity> sameName = query.getResultList();
        RutinaEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar rutina por nombre ", name);
        return result;
}
}
