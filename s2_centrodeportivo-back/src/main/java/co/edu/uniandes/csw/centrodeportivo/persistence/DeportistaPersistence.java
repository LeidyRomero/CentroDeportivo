/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.persistence;

import co.edu.uniandes.csw.centrodeportivo.entities.DeportistaEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase que maneja la persistencia para Deportista. Se conecta a través Entity
 * Manager de javax.persistance con la base de datos SQL.
 * @author Leidy Romero
 */
@Stateless
public class DeportistaPersistence {
    private static final Logger LOGGER = Logger.getLogger(DeportistaPersistence.class.getName());

    @PersistenceContext(unitName = "LudisPU")
    protected EntityManager em;
    
    
     /**
     * Método para persisitir la entidad en la base de datos.
     *
     * @param deportistaEntity objeto deportista que se creará en la base de datos
     * @return devuelve la entidad creada con un id dado por la base de datos.
     */
    public DeportistaEntity create(DeportistaEntity deportistaEntity) {
        LOGGER.log(Level.INFO, "Creando un deportista nuevo");
        /* Note que hacemos uso de un método propio de EntityManager para persistir al deportista en la base de datos.
        Es similar a "INSERT INTO table_name (column1, column2, column3, ...) VALUES (value1, value2, value3, ...);" en SQL.
         */
        em.persist(deportistaEntity);
        LOGGER.log(Level.INFO, "Saliendo de crear un deportista nuevo");
        return deportistaEntity;
    }
    
    /**
     * Devuelve todos los deportistas de la base de datos.
     *
     * @return una lista con todos los deportistas que encuentre en la base de
     * datos, "select u from DeportistaEntity u" es como un "select * from
     * DeportistaEntity;" - "SELECT * FROM table_name" en SQL.
     */
    public List<DeportistaEntity> findAll() {
        LOGGER.log(Level.INFO, "Consultando todos los deportistas");
        // Se crea un query para buscar todos los deportistas en la base de datos.
        TypedQuery query = em.createQuery("select u from DeportistaEntity u", DeportistaEntity.class);
        // Note que en el query se hace uso del método getResultList() que obtiene una lista de deportistas.
        return query.getResultList();
    }
    
     /**
     * Busca si hay algun deportista con el id que se envía de argumento
     *
     * @param deportistasId: id correspondiente al deportista buscado.
     * @return un deportista.
     */
    public DeportistaEntity find(Long deportistasId) {
        LOGGER.log(Level.INFO, "Consultando al deportista con id={0}", deportistasId);
        /* Note que se hace uso del metodo "find" propio del EntityManager, el cual recibe como argumento 
        el tipo de la clase y el objeto que nos hara el filtro en la base de datos en este caso el "id"
        Suponga que es algo similar a "select * from DeportistaEntity where id=id;" - "SELECT * FROM table_name WHERE condition;" en SQL.
         */
        return em.find(DeportistaEntity.class, deportistasId);
    }
     /**
     * Actualiza la informacion de un deportista.
     *
     * @param deportistaEntity: el deportista que viene con los nuevos cambios.
     * Por ejemplo la presion sanguinea pudo cambiar. En ese caso, se haria uso del método
     * update.
     * @return un deportista con los cambios aplicados.
     */
    public DeportistaEntity update(DeportistaEntity deportistaEntity) {
        LOGGER.log(Level.INFO, "Actualizando al deportista con id = {0}", deportistaEntity.getId());
        /* Note que hacemos uso de un método propio del EntityManager llamado merge() que recibe como argumento
        el deportista con los cambios, esto es similar a 
        "UPDATE table_name SET column1 = value1, column2 = value2, ... WHERE condition;" en SQL.
         */
        LOGGER.log(Level.INFO, "Saliendo de actualizar al deportista con id = {0}", deportistaEntity.getId());
        return em.merge(deportistaEntity);
    }
	
    /**
     *
     * Borra al deportista de la base de datos recibiendo como argumento su id
     *
     * @param deportistasId: id correspondiente al deportista a borrar.
     */
    public void delete(Long deportistasId) {
        LOGGER.log(Level.INFO, "Borrando al deportista con id = {0}", deportistasId);
        // Se hace uso de mismo método que esta explicado en public DeportistaEntity find(Long id) para obtener al deportista a borrar.
        DeportistaEntity entity = em.find(DeportistaEntity.class, deportistasId);
        /* Note que una vez obtenido el objeto desde la base de datos llamado "entity", volvemos hacer uso de un método propio del
         EntityManager para eliminar de la base de datos el objeto que encontramos y queremos borrar.
         Es similar a "delete from DeportistaEntity where id=id;" - "DELETE FROM table_name WHERE condition;" en SQL.*/
        em.remove(entity);
        LOGGER.log(Level.INFO, "Saliendo de borrar al deportista con id = {0}", deportistasId);
    }
    /**
     * Busca si hay algún deportista con el cedula que se envía de argumento
     *
     * @param cedula: Cedula del deportista que se está buscando
     * @return null si no existe ningún deportista conla cedula del argumento.
     * Si existe alguno devuelve el primero.
     */
    public DeportistaEntity findByCedula(Integer cedula) {
        LOGGER.log(Level.INFO, "Consultando deportista por cedula ", cedula);
        // Se crea un query para buscar deportistas con la cedula que recibe el método como argumento. ":cedula" es un placeholder que debe ser remplazado
        TypedQuery query = em.createQuery("Select dep From DeportistaEntity dep where dep.cedula = :cedula", DeportistaEntity.class);
        // Se remplaza el placeholder ":cedula" con el valor del argumento 
        query = query.setParameter("cedula", cedula);
        // Se invoca el query se obtiene la lista resultado
        List<DeportistaEntity> sameCedula = query.getResultList();
        DeportistaEntity result;
        if (sameCedula == null) {
            result = null;
        } else if (sameCedula.isEmpty()) {
            result = null;
        } else {
            result = sameCedula.get(0);
        }
        LOGGER.log(Level.INFO, "Saliendo de consultar deportista por cédula ", cedula);
        return result;
    }
}