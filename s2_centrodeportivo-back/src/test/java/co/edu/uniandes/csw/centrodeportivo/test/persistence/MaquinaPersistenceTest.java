/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.test.persistence;

import co.edu.uniandes.csw.centrodeportivo.entities.MaquinaEntity;
import co.edu.uniandes.csw.centrodeportivo.persistence.MaquinaPersistence;
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
 * @author Diany Quintero
 */
@RunWith(Arquillian.class)
public class MaquinaPersistenceTest {
    
    @Inject
    private MaquinaPersistence maquinaPersistencia;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<MaquinaEntity> data = new ArrayList<MaquinaEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MaquinaEntity.class.getPackage())
                .addPackage(MaquinaPersistence.class.getPackage())
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
        em.createQuery("delete from MaquinaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            MaquinaEntity entity = factory.manufacturePojo(MaquinaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }

    /**
     * Prueba para crear una maquina.
     */
    @Test
    public void createMaquinaTest() {
        PodamFactory factory = new PodamFactoryImpl();
        MaquinaEntity newEntity = factory.manufacturePojo(MaquinaEntity.class);
        MaquinaEntity result = maquinaPersistencia.create(newEntity);

        Assert.assertNotNull(result);

        MaquinaEntity entity = em.find(MaquinaEntity.class, result.getId());

        Assert.assertEquals(newEntity.getId(), entity.getId());
    }
     /**
     * Prueba para consultar la lista de maquinas.
     */
    @Test
    public void getMaquinasTest() {
        List<MaquinaEntity> list = maquinaPersistencia.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (MaquinaEntity ent : list) {
            boolean found = false;
            for (MaquinaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar una maquina.
     */
    @Test
    public void getMaquinaTest() {
        MaquinaEntity entity = data.get(0);
        MaquinaEntity newEntity = maquinaPersistencia.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }

    /**
     * Prueba para eliminar una maquina.
     */
    @Test
    public void deleteMaquinaTest() {
        MaquinaEntity entity = data.get(0);
        maquinaPersistencia.delete(entity.getId());
        MaquinaEntity deleted = em.find(MaquinaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar una Reserva.
     */
    @Test
    public void updateMaquinaTest() {
        MaquinaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        MaquinaEntity newEntity = factory.manufacturePojo(MaquinaEntity.class);

        newEntity.setId(entity.getId());

        maquinaPersistencia.update(newEntity);

        MaquinaEntity resp = em.find(MaquinaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
    }
    
}
