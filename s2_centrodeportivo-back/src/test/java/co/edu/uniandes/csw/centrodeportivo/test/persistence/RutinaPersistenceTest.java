/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.test.persistence;


import co.edu.uniandes.csw.centrodeportivo.entities.RutinaEntity;
import co.edu.uniandes.csw.centrodeportivo.persistence.RutinaPersistence;
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Francisco Jose MacAllister
 */
@RunWith(Arquillian.class)
public class RutinaPersistenceTest {
    @Inject
    private RutinaPersistence rutinaPersistencia;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<RutinaEntity> data = new ArrayList<RutinaEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RutinaEntity.class.getPackage())
                .addPackage(RutinaPersistence.class.getPackage())
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
     */
    private void clearData() {
        em.createQuery("delete from RutinaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            RutinaEntity entity = factory.manufacturePojo(RutinaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     * Prueba para crear una rutina
     */
    @Test
    public void createRutinaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        RutinaEntity newEntity = factory.manufacturePojo(RutinaEntity.class);
        RutinaEntity result = rutinaPersistencia.create(newEntity);

        Assert.assertNotNull(result);

        RutinaEntity entity = em.find(RutinaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
     /**
     * Prueba para consultar la lista de rutinas.
     */
    @Test
    public void getRutinasTest() {
        List<RutinaEntity> list = rutinaPersistencia.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (RutinaEntity ent : list) {
            boolean found = false;
            for (RutinaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
     @Test
    public void deleteEspecialistaTest() {
        RutinaEntity entity = data.get(0);
        rutinaPersistencia.delete(entity.getId());
        RutinaEntity deleted = em.find(RutinaEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }
}
