/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.testLogic;


import co.edu.uniandes.csw.centrodeportivo.ejb.EspecialistaLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.DeportistaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.EspecialistaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.EspecialistaPersistence;
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
public class EspecialistaLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private EspecialistaLogic especialistaLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EspecialistaEntity> data = new ArrayList<>();
    
    private List<ObjetivoEntity> objetivosData = new ArrayList<>();
    
    private List<DeportistaEntity> deportistasData = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EspecialistaEntity.class.getPackage())
                .addPackage(EspecialistaLogic.class.getPackage())
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
     * Limpia las tablas que estan implicada en la prueba.
     */
    private void clearData()
    {
        em.createQuery("delete from EspecialistaEntity").executeUpdate();
       // em.createQuery("delete from ObjetivoEntity").executeUpdate();
        //em.createQuery("delete from DeportistaEntity").executeUpdate();
    }
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()
    {
        for (int i = 0; i < 3; i++) {
            ObjetivoEntity objetivos = factory.manufacturePojo(ObjetivoEntity.class);
            em.persist(objetivos);
            objetivosData.add(objetivos);
        }
        for (int i = 0; i < 3; i++) {
            DeportistaEntity deportistas = factory.manufacturePojo(DeportistaEntity.class);
            em.persist(deportistas);
            deportistasData.add(deportistas);
        }
        for (int i = 0; i < 3; i++) {
            EspecialistaEntity entity = factory.manufacturePojo(EspecialistaEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                
               // deportistasData.get(i).setEspecialista(entity);
                    
            }
        }
    }
    /**
     * Prueba para crear un especialista
     */
    public void createEspecialistaEntity() throws BusinessLogicException
    {
        EspecialistaEntity newEntity = factory.manufacturePojo(EspecialistaEntity.class);
        EspecialistaEntity result = especialistaLogic.createEspecialista(newEntity);
        Assert.assertNotNull(result);
        EspecialistaEntity entity = em.find(EspecialistaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
    }
    
     
    /**
     * Prueba para eliminar un Especialista.
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteEspecialistaTest() throws BusinessLogicException {
        EspecialistaEntity entity = data.get(1);
        especialistaLogic.deleteEspecialista(entity.getId());
        EspecialistaEntity deleted = em.find(EspecialistaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
     /**
     * Prueba para consultar la lista de Especialistas.
     */
    @Test
    public void getEspecialistasTest() {
        List<EspecialistaEntity> list = especialistaLogic.getEspecialistas();
        Assert.assertEquals(3, list.size());
        for (EspecialistaEntity ent : list) {
            boolean found = false;
            for (EspecialistaEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Especialista.
     */
    @Test
    public void getEspecialistaTest() {
        EspecialistaEntity entity = data.get(0);
        EspecialistaEntity newEntity = especialistaLogic.getEspecialista(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        //Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    /**
     * Prueba para actualizar un Especialista.
     */
    @Test
    public void updateEspecialistaTest() {
        EspecialistaEntity entity = data.get(0);
        
        EspecialistaEntity nuevaEntity = factory.manufacturePojo(EspecialistaEntity.class);

        nuevaEntity.setId(entity.getId());

        especialistaLogic.updateEspecialista(nuevaEntity.getId(), nuevaEntity);

        EspecialistaEntity resp = em.find(EspecialistaEntity.class, entity.getId());
        Assert.assertEquals(nuevaEntity.getId(), resp.getId());
        //Assert.assertEquals(nuevaEntity.getNombre(), resp.getNombre());
       
    }
    //Otras implementaciones de pruebas
    /**
     * Prueba para consultar la lista de Especialistas.
     */
    /*@Test
    public void getEspecialistasTest() {
        List<EspecialistaEntity> list = especialistaLogic.getEspecialistas();
        
        //Assert.assertEquals(data.size(), list.size());
         Assert.assertEquals(data.size(), list.size());
        for (EspecialistaEntity entity : list) {
            boolean found = false;
            for (EspecialistaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Especialista.
     */
    /*@Test
    public void getEspecialistaTest() {
        EspecialistaEntity entity = data.get(0);
        EspecialistaEntity newEntity = especialistaLogic.getEspecialista(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    /**
     * Prueba para actualizar un Especialista.
     */
    /*@Test
    public void updateEspecialistaTest() {
        EspecialistaEntity entity = data.get(0);
        EspecialistaEntity pojoEntity = factory.manufacturePojo(EspecialistaEntity.class);
        pojoEntity.setId(entity.getId());
        especialistaLogic.updateEspecialista(pojoEntity.getId(), pojoEntity);
        EspecialistaEntity resp = em.find(EspecialistaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
    }*/
}
