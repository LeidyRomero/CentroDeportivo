/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.persistence;

import co.edu.uniandes.csw.centrodeportivo.entities.EspecialistaEntity;
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
public class EspecialistaPersistence {
    private static final Logger LOGGER = Logger.getLogger(EspecialistaPersistence.class.getName());
    
    @PersistenceContext(unitName = "LudisPU")
    protected EntityManager em;
    
     public EspecialistaEntity create(EspecialistaEntity especialistaEntity) {
        LOGGER.log(Level.INFO, "Creando un nuevo especialista");
        /* Note que hacemos uso de un método propio de EntityManager para persistir al especialista en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(especialistaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un nuevo especialista");
        return especialistaEntity;
    }
     
      public List<EspecialistaEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los especialistas");
        // Se crea un query para buscar todos los especialistas en la base de datos.
        TypedQuery query = em.createQuery("select u from EspecialistaEntity u", EspecialistaEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de especialistas.
        return query.getResultList();
    }
       public EspecialistaEntity find(Long especialistaId) {
        LOGGER.log(Level.INFO, "Consultando el especialista con id={0}", especialistaId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from EspecialistaEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(EspecialistaEntity.class, especialistaId);
    }
       public EspecialistaEntity update(EspecialistaEntity especialistaEntity) {
        LOGGER.log(Level.INFO, "Actualizando el especialista con id = {0}", especialistaEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        el especialista con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar el especialista con id = {0}", especialistaEntity.getId());
        return em.merge(especialistaEntity);
    }

     public void delete(Long especialistaId) {
        LOGGER.log(Level.INFO, "Borrando especialista con id = {0}", especialistaId);
        // Se hace uso de mismo método que esta explicado en public EditorialEntity find(Long id) para obtener la especialista a borrar.
        EspecialistaEntity entity = em.find(EspecialistaEntity.class, especialistaId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from EditorialEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el especialista con id = {0}", especialistaId);
    }

   public EspecialistaEntity findByName(String name) {
        LOGGER.log(Level.INFO, "Consultando especialista por nombre ", name);
        // Se crea un query para buscar especialistaes con el nombre que recibe el método como argumento. ":name" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From EspecialistaEntity e where e.name = :name", EspecialistaEntity.class);
        // Se remplaza el placeholder ":name" con el valor del argumento 
        query = query.setParameter("name", name);
        // Se invoca el query se obtiene la lista resultado
        List<EspecialistaEntity> sameName = query.getResultList();
        EspecialistaEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar especialista por nombre ", name);
        return result;
    }
}
