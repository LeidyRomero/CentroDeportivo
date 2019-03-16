/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.test.persistence;

import co.edu.uniandes.csw.centrodeportivo.entities.SeguimientoEntity;
import co.edu.uniandes.csw.centrodeportivo.persistence.SeguimientoPersistence;
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
 * Pruebas de persistencia de Seguimiento
 *
 * @author Lina Cardozo
 */
@RunWith(Arquillian.class)
public class SeguimientoPersistenceTest {
    
    /**
     * Inyección de la dependencia a la clase SeguimientoPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private SeguimientoPersistence seguimientoPersistence;

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
    private List<SeguimientoEntity> data = new ArrayList<SeguimientoEntity>();

    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Seguimiento, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SeguimientoEntity.class.getPackage())
                .addPackage(SeguimientoPersistence.class.getPackage())
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
        em.createQuery("delete from SeguimientoEntity").executeUpdate();
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

            SeguimientoEntity entity = factory.manufacturePojo(SeguimientoEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Seguimiento.
     *
     *
     */
    @Test
    public void createSeguimientoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        SeguimientoEntity newEntity = factory.manufacturePojo(SeguimientoEntity.class);
        SeguimientoEntity result = seguimientoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        SeguimientoEntity entity = em.find(SeguimientoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getTiempo(), entity.getTiempo());
    }

    /**
     * Prueba para eliminar una Seguimiento.
     *
     *
     */
    @Test
    public void deleteSeguimientoTest() {
        SeguimientoEntity entity = data.get(0);
        seguimientoPersistence.delete(entity.getId());
        SeguimientoEntity deleted = em.find(SeguimientoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para consultar un Seguimiento por id.
     *
     *
     */
    @Test
    public void findSeguimientoTest() {
        SeguimientoEntity entity = data.get(0);
        SeguimientoEntity newEntity = seguimientoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getTiempo(), newEntity.getTiempo());
    }
    
    /**
     * Prueba para consultar un Seguimiento por nombre.
     *
     *
     */
    @Test
    public void findSeguimientoByTimeTest() {
        SeguimientoEntity entity = data.get(0);
        SeguimientoEntity newEntity = seguimientoPersistence.findByTime(entity.getTiempo());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getTiempo(), newEntity.getTiempo());
        
        newEntity = seguimientoPersistence.findByTime(null);
        Assert.assertNull(newEntity);
    }
}