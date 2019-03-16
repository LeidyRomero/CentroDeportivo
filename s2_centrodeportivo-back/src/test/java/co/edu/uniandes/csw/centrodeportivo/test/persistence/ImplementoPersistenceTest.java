/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.test.persistence;

import co.edu.uniandes.csw.centrodeportivo.entities.ImplementoEntity;
import co.edu.uniandes.csw.centrodeportivo.persistence.ImplementoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de persistencia de Implemento
 *
 * @author Lina Cardozo
 */
@RunWith(Arquillian.class)
public class ImplementoPersistenceTest {
    
    /**
     * Inyección de la dependencia a la clase ImplementoPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private ImplementoPersistence implementoPersistence;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para marcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

    /**
     * Lista que tiene los datos de prueba.
     */
    private List<ImplementoEntity> data = new ArrayList<ImplementoEntity>();

    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Implemento, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ImplementoEntity.class.getPackage())
                .addPackage(ImplementoPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     *
     *
     */
    private void clearData() {
        em.createQuery("delete from ImplementoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     *
     *
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            ImplementoEntity entity = factory.manufacturePojo(ImplementoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Implemento.
     *
     *
     */
    @Test
    public void createImplementoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        ImplementoEntity newEntity = factory.manufacturePojo(ImplementoEntity.class);
        ImplementoEntity result = implementoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        ImplementoEntity entity = em.find(ImplementoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    /**
     * Prueba para eliminar un Implemento.
     *
     *
     */
    @Test
    public void deleteImplementoTest() {
        ImplementoEntity entity = data.get(0);
        implementoPersistence.delete(entity.getId());
        ImplementoEntity deleted = em.find(ImplementoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para consultar un Implemento por id.
     *
     *
     */
    @Test
    public void findImplementoTest() {
        ImplementoEntity entity = data.get(0);
        ImplementoEntity newEntity = implementoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        Assert.assertEquals(entity.getCantidadExistencias(), newEntity.getCantidadExistencias());
    }
    
    /**
     * Prueba para consultar un Implemento por nombre.
     *
     *
     */
    @Test
    public void FindImplementoByNameTest() {
        ImplementoEntity entity = data.get(0);
        ImplementoEntity newEntity = implementoPersistence.findByName(entity.getNombre());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
        
        newEntity = implementoPersistence.findByName(null);
        Assert.assertNull(newEntity);
    }
}