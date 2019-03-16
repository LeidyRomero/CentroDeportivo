/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.test.persistence;
import co.edu.uniandes.csw.centrodeportivo.entities.EspecialistaEntity;
import co.edu.uniandes.csw.centrodeportivo.persistence.EspecialistaPersistence;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import junit.framework.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.ClassInfoStrategy;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Francisco Jose MacAllister
 */
@RunWith(Arquillian.class)
public class EspecialistaPersistenceTest {
    
 @Inject
 private EspecialistaPersistence especialistaPersistence;
 @PersistenceContext
 private EntityManager em;
   /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;
     /**
     * lista que tiene los datos de prueba.
     */
    private List<EspecialistaEntity> data = new ArrayList<EspecialistaEntity>();
 
  @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EspecialistaEntity.class.getPackage())
                .addPackage(EspecialistaPersistence.class.getPackage())
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
        em.createQuery("delete from EspecialistaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {

            EspecialistaEntity entity = factory.manufacturePojo(EspecialistaEntity.class);

            em.persist(entity);

            data.add(entity);
        }
    }
    
    @Test
 public void createEspecialistaTest()
 {
     PodamFactory factory =new PodamFactoryImpl();
     EspecialistaEntity newEntity =factory.manufacturePojo(EspecialistaEntity.class);
     EspecialistaEntity result= especialistaPersistence.create(newEntity);
     
     Assert.assertNotNull(result);
     EspecialistaEntity entity=em.find(EspecialistaEntity.class, result.getId());
     Assert.assertEquals(newEntity.getId(), entity.getId());
 }
    
    /**
     * Prueba para consultar la lista de Especialistas.
     */
    @Test
    public void getEspecialistasTest() {
        List<EspecialistaEntity> list = especialistaPersistence.findAll();
        org.junit.Assert.assertEquals(data.size(), list.size());
        for (EspecialistaEntity ent : list) {
            boolean found = false;
            for (EspecialistaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            org.junit.Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar a un especialista.
     */
    @Test
    public void getEspecialistaTest() {
        EspecialistaEntity entity = data.get(0);
        EspecialistaEntity newEntity = especialistaPersistence.find(entity.getId());
        org.junit.Assert.assertNotNull(newEntity);
        org.junit.Assert.assertEquals(entity.getId(), newEntity.getId());
    }

    /**
     * Prueba para eliminar un especialista.
     */
    @Test
    public void deleteEspecialistaTest() {
        EspecialistaEntity entity = data.get(0);
        especialistaPersistence.delete(entity.getId());
        EspecialistaEntity deleted = em.find(EspecialistaEntity.class, entity.getId());
        org.junit.Assert.assertNull(deleted);
    }

    /**
     * Prueba para actualizar un Especialista.
     */
    @Test
    public void updateEspecialistaTest() {
        EspecialistaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EspecialistaEntity newEntity = factory.manufacturePojo(EspecialistaEntity.class);

        newEntity.setId(entity.getId());

        especialistaPersistence.update(newEntity);

        EspecialistaEntity resp = em.find(EspecialistaEntity.class, entity.getId());

        org.junit.Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }
}
