/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.test.persistence;

import co.edu.uniandes.csw.centrodeportivo.entities.DeportistaEntity;
import javax.inject.Inject;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import co.edu.uniandes.csw.centrodeportivo.persistence.;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Leidy Romero
 */
@RunWith(Arquillian.class)
public class DeportistaPersistenceTest {
    
    @Inject
    private DeportistaPersistence deportistaPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<DeportistaEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el 
     * archivo beans.xml para resolver la inyeccion de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(DeportistaEntity.class.getPackage())
                .addPackage(DeportistaPersistence.class.getPackage())
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
            inserData();
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
        em.createQuery("delete from DeportistaEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas
     */
    private void inserData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i<10 ; i++)
        {
            DeportistaEntity entidad = factory.manufacturePojo(DeportistaEntity.class);
            
            em.persist (entidad);
            data.add (entidad);
        }
    }
    
    /**
     * Prueba para crear un deportista
     */
    @Test
    public void createDeportistaEntity()
    {
        PodamFactory factory =  new PodamFactoryImpl();
        DeportistaEntity nuevoDeportista = factory.manufacturePojo(DeportistaEntity.class);
        DeportistaEntity resultado = deportistaPersistence.create(nuevoDeportista);
        
        Assert.assertNotNull(resultado);
        DeportistaEntity entidad = em.find(DeportistaEntity.class, resultado.getId());
        
        //Assert.assertEquals(nuevoDeportista.getCedula(), entidad.getCedula());
    }
    
    /**
     * Prueba para consultar la lista de deportistas
     */
    @Test
    public void getDeportistasTest()
    {
        List<DeportistaEntity> lista = deportistaPersistence.findAll();
        Assert.assertEquals(data.size(), lista.size());
        for (DeportistaEntity entidad : lista) {
            boolean found = false;
            for (DeportistaEntity entity : data) {
                if (entidad.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
     /**
     * Prueba para consultar un Deportista
     */
    @Test
    public void getDeportistaTest() {
        DeportistaEntity entity = data.get(0);
        DeportistaEntity nuevaEntity = deportistaPersistence.find(entity.getId());
        Assert.assertNotNull(nuevaEntity);
        Assert.assertEquals(entity.getNombre(), nuevaEntity.getNombre());
        Assert.assertEquals(entity.getCedula(), nuevaEntity.getCedula());
    }
    
     /**
     * Prueba para actualizar un Deportista
     */
    @Test
    public void updateDeportistaTest() {
        DeportistaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        DeportistaEntity newEntity = factory.manufacturePojo(DeportistaEntity.class);

        newEntity.setId(entity.getId());

        deportistaPersistence.update(newEntity);

        DeportistaEntity resp = em.find(DeportistaEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para eliminar un Author.
     */
    @Test
    public void deleteDeportistaTest() {
        DeportistaEntity entity = data.get(0);
        deportistaPersistence.delete(entity.getId());
        DeportistaEntity deleted = em.find(DeportistaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
