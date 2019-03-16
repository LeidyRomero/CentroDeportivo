/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.testLogic;


import co.edu.uniandes.csw.centrodeportivo.ejb.ZonaCuerpoLogic;

import co.edu.uniandes.csw.centrodeportivo.entities.ZonaCuerpoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;

import co.edu.uniandes.csw.centrodeportivo.persistence.ZonaCuerpoPersistence;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class ZonaCuerpoLogicTest {
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ZonaCuerpoLogic zonaCuerpoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ZonaCuerpoEntity> data = new ArrayList<ZonaCuerpoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ZonaCuerpoEntity.class.getPackage())
                .addPackage(ZonaCuerpoLogic.class.getPackage())
                .addPackage(ZonaCuerpoPersistence.class.getPackage())
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
        em.createQuery("delete from ZonaCuerpoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ZonaCuerpoEntity entity = factory.manufacturePojo(ZonaCuerpoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un ZonaCuerpo.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test
    public void createZonaCuerpoTest() throws BusinessLogicException {
        ZonaCuerpoEntity newEntity = factory.manufacturePojo(ZonaCuerpoEntity.class);
        ZonaCuerpoEntity result = zonaCuerpoLogic.createZonaCuerpo(newEntity);
        Assert.assertNotNull(result);
        ZonaCuerpoEntity entity = em.find(ZonaCuerpoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }

  
    /**
     * Prueba para consultar la lista de ZonasCuerpo.
     */
    @Test
    public void getZonasCuerpoTest() {
        List<ZonaCuerpoEntity> list = zonaCuerpoLogic.getZonasCuerpo();
        Assert.assertEquals(data.size(), list.size());
        for (ZonaCuerpoEntity entity : list) {
            boolean found = false;
            for (ZonaCuerpoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un ZonaCuerpo.
     */
    @Test
    public void getZonaCuerpoTest() {
        ZonaCuerpoEntity entity = data.get(0);
        ZonaCuerpoEntity resultEntity = zonaCuerpoLogic.getZonaCuerpo(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
    }

    /**
     * Prueba para actualizar un ZonaCuerpo.
     */
    @Test
    public void updateZonaCuerpoTest() {
        ZonaCuerpoEntity entity = data.get(0);
        ZonaCuerpoEntity pojoEntity = factory.manufacturePojo(ZonaCuerpoEntity.class);
        pojoEntity.setId(entity.getId());
        zonaCuerpoLogic.updateZonaCuerpo(pojoEntity.getId(), pojoEntity);
        ZonaCuerpoEntity resp = em.find(ZonaCuerpoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para eliminar un ZonaCuerpo.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test
    public void deleteZonaCuerpoTest() throws BusinessLogicException {
        ZonaCuerpoEntity entity = data.get(1);
        zonaCuerpoLogic.deleteZonaCuerpo(entity.getId());
        ZonaCuerpoEntity deleted = em.find(ZonaCuerpoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
  
}
