/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.persistence;

import co.edu.uniandes.csw.centrodeportivo.entities.ImplementoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Implemento. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author Lina Cardozo
 */
@Stateless
public class ImplementoPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(ImplementoPersistence.class.getName());
    
    @PersistenceContext(unitName = "LudisPU")
    protected EntityManager em;
    
    /**
     * Método para persistir la entidad en la base de datos.
     *
     * @param implementoEntity objeto implemento que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public ImplementoEntity create(ImplementoEntity implementoEntity) {
        LOGGER.log(Level.INFO, "Creando un implemento nuevo");
        /* Note que hacemos uso de un método propio de EntityManager para persistir el implemento en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(implementoEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un implemento nuevo");
        return implementoEntity;
    }

    /**
     * Devuelve todos los implementos de la base de datos.
     *
     * @return una lista con todos los implementos que encuentre en la base de
     * datos, "select u from ImplementoEntity u" es como un "select * from
     * ImplementoEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<ImplementoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los implementos");
        // Se crea un query para buscar todos los implementos en la base de datos.
        TypedQuery query = em.createQuery("select u from ImplementoEntity u", ImplementoEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de implementos.
        return query.getResultList();
    }
	
    /**
     * Busca si hay algún implemento con el id que se envía de argumento
     *
     * @param implementosId: id correspondiente al implemento buscado.
     * @return un implemento.
     */
    public ImplementoEntity find(Long implementosId) {
        LOGGER.log(Level.INFO, "Consultando implemento con id={0}", implementosId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from ImplementoEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(ImplementoEntity.class, implementosId);
    }

    /**
     * Actualiza un implemento.
     *
     * @param implementoEntity: el implemento que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un implemento con los cambios aplicados.
     */
    public ImplementoEntity update(ImplementoEntity implementoEntity) {
        LOGGER.log(Level.INFO, "Actualizando implemento con id = {0}", implementoEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        el implemento con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar el implemento con id = {0}", implementoEntity.getId());
        return em.merge(implementoEntity);
    }
	
    /**
     *
     * Borra un implemento de la base de datos recibiendo como argumento el id
     * del implemento
     *
     * @param implementosId: id correspondiente al implemento a borrar.
     */
    public void delete(Long implementosId) {
        LOGGER.log(Level.INFO, "Borrando implemento con id = {0}", implementosId);
        // Se hace uso de mismo método que esta explicado en public ImplementoEntity find(Long id) para obtener el implemento a borrar.
        ImplementoEntity entity = em.find(ImplementoEntity.class, implementosId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from ImplementoEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el implemento con id = {0}", implementosId);
    }
	
    /**
     * Busca si hay algún implemento con el nombre que se envía de argumento
     *
     * @param nombre: Nombre del implemento que se está buscando
     * @return null si no existe ningún implemento con el nombre del argumento.
     * Si existe alguno devuelve el primero.
     */
    public ImplementoEntity findByName(String nombre) {
        LOGGER.log(Level.INFO, "Consultando implemento por nombre ", nombre);
        // Se crea un query para buscar implementos con el nombre que recibe el método como argumento. ":nombre" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From ImplementoEntity e where e.nombre = :nombre", ImplementoEntity.class);
        // Se remplaza el placeholder ":nombre" con el valor del argumento 
        query = query.setParameter("nombre", nombre);
        // Se invoca el query se obtiene la lista resultado
        List<ImplementoEntity> sameName = query.getResultList();
        ImplementoEntity result;
        if (sameName == null) {
            result = null;
        } else if (sameName.isEmpty()) {
            result = null;
        } else {
            result = sameName.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar implemento por nombre ", nombre);
        return result;
    }
}
