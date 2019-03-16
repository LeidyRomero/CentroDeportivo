/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.test.persistence;


import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import co.edu.uniandes.csw.centrodeportivo.persistence.ObjetivoPersistence;
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
 * @author Leidy Romero
 */
@RunWith(Arquillian.class)
public class ObjetivoPersistenceTest {
    
    @Inject
    private ObjetivoPersistence objetivoPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<ObjetivoEntity> data = new ArrayList<ObjetivoEntity>();
    
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el 
     * archivo beans.xml para resolver la inyeccion de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ObjetivoEntity.class.getPackage())
                .addPackage(ObjetivoPersistence.class.getPackage())
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
     * Limpia las tablas que estan implicada en la prueba.
     */
    private void clearData()
    {
        em.createQuery("delete from ObjetivoEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas
     */
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i<3 ; i++)
        {
            ObjetivoEntity entidad = factory.manufacturePojo(ObjetivoEntity.class);
            
            em.persist (entidad);
            data.add (entidad);
        }
    }
    
     /**
     * Prueba para crear un objetivo
     */
    @Test
    public void createObjetivoEntity()
    {
        PodamFactory factory =  new PodamFactoryImpl();
        ObjetivoEntity nuevoObjetivo = factory.manufacturePojo(ObjetivoEntity.class);
        ObjetivoEntity resultado = objetivoPersistence.create(nuevoObjetivo);
        
        Assert.assertNotNull(resultado);
        ObjetivoEntity entidad = em.find(ObjetivoEntity.class, resultado.getId());
        
        Assert.assertEquals(nuevoObjetivo.getId(), entidad.getId());
    }
    
     /**
     * Prueba para consultar la lista de Objetivos.
     */
    @Test
    public void getObjetivosTest() {
        List<ObjetivoEntity> list = objetivoPersistence.findAll();
        Assert.assertEquals(data.size(), list.size());
        for (ObjetivoEntity ent : list) {
            boolean found = false;
            for (ObjetivoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Objetivo.
     */
    @Test
    public void getObjetivoTest() {
        ObjetivoEntity entity = data.get(0);
        ObjetivoEntity newEntity = objetivoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getId(), newEntity.getId());
    }

    /**
     * Prueba para actualizar un Objetivo.
     */
    @Test
    public void updateObjetivoTest() {
        ObjetivoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ObjetivoEntity newEntity = factory.manufacturePojo(ObjetivoEntity.class);

        newEntity.setId(entity.getId());

        objetivoPersistence.update(newEntity);

        ObjetivoEntity resp = em.find(ObjetivoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(newEntity.getCumplio(), resp.getCumplio());
    }
}
