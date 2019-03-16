/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.persistence;

import co.edu.uniandes.csw.centrodeportivo.entities.SeguimientoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Seguimiento. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 *
 * @author Lina Cardozo
 */
@Stateless
public class SeguimientoPersistence {
    
    private static final Logger LOGGER = Logger.getLogger(SeguimientoPersistence.class.getName());
    
    @PersistenceContext(unitName = "LudisPU")
    protected EntityManager em;
    
    /**
     * Método para persistir la entidad en la base de datos.
     *
     * @param seguimientoEntity objeto seguimiento que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public SeguimientoEntity create(SeguimientoEntity seguimientoEntity) {
        LOGGER.log(Level.INFO, "Creando un seguimiento nuevo");
        /* Note que hacemos uso de un método propio de EntityManager para persistir el seguimiento en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(seguimientoEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un seguimiento nuevo");
        return seguimientoEntity;
    }
	
    /**
     * Devuelve todos los seguimientos de la base de datos.
     *
     * @return una lista con todos los seguimientos que encuentre en la base de
     * datos, "select u from SeguimientoEntity u" es como un "select * from
     * SeguimientoEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<SeguimientoEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los seguimientos");
        // Se crea un query para buscar todos los seguimientos en la base de datos.
        TypedQuery query = em.createQuery("select u from SeguimientoEntity u", SeguimientoEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de seguimientos.
        return query.getResultList();
    }
	
    /**
     * Busca si hay algún seguimiento con el id que se envía de argumento
     *
     * @param seguimientosId: id correspondiente al seguimiento buscado.
     * @return un seguimiento.
     */
    public SeguimientoEntity find(Long seguimientosId) {
        LOGGER.log(Level.INFO, "Consultando seguimiento con id={0}", seguimientosId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from SeguimientoEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(SeguimientoEntity.class, seguimientosId);
    }

    /**
     * Actualiza un seguimiento.
     *
     * @param seguimientoEntity: el seguimiento que viene con los nuevos cambios.
     * Por ejemplo el nombre pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un seguimiento con los cambios aplicados.
     */
    public SeguimientoEntity update(SeguimientoEntity seguimientoEntity) {
        LOGGER.log(Level.INFO, "Actualizando seguimiento con id = {0}", seguimientoEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        el seguimiento con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar el seguimiento con id = {0}", seguimientoEntity.getId());
        return em.merge(seguimientoEntity);
    }
	
    /**
     *
     * Borra un seguimiento de la base de datos recibiendo como argumento el id
     * del seguimiento
     *
     * @param seguimientosId: id correspondiente al seguimiento a borrar.
     */
    public void delete(Long seguimientosId) {
        LOGGER.log(Level.INFO, "Borrando seguimiento con id = {0}", seguimientosId);
        // Se hace uso de mismo método que esta explicado en public SeguimientoEntity find(Long id) para obtener el seguimiento a borrar.
        SeguimientoEntity entity = em.find(SeguimientoEntity.class, seguimientosId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from SeguimientoEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar el seguimiento con id = {0}", seguimientosId);
    }
	
    /**
     * Busca si hay algún seguimiento con el tiempo que se envía de argumento
     *
     * @param tiempo: Tiempo del seguimiento que se está buscando
     * @return null si no existe ningún seguimiento con el tiempo del argumento.
     * Si existe alguno devuelve el primero.
     */
    public SeguimientoEntity findByTime(Integer tiempo) {
        LOGGER.log(Level.INFO, "Consultando seguimiento por tiempo ", tiempo);
        // Se crea un query para buscar seguimientos con el tiempo que recibe el método como argumento. ":tiempo" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select e From SeguimientoEntity e where e.tiempo = :tiempo", SeguimientoEntity.class);
        // Se remplaza el placeholder ":tiempo" con el valor del argumento 
        query = query.setParameter("tiempo", tiempo);
        // Se invoca el query se obtiene la lista resultado
        List<SeguimientoEntity> sameTime = query.getResultList();
        SeguimientoEntity result;
        if (sameTime == null) {
            result = null;
        } else if (sameTime.isEmpty()) {
            result = null;
        } else {
            result = sameTime.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar seguimiento por tiempo ", tiempo);
        return result;
    }
}