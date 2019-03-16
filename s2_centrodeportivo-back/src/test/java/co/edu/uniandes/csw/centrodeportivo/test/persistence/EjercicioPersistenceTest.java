/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.test.persistence;

import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import javax.inject.Inject;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import co.edu.uniandes.csw.centrodeportivo.persistence.EjercicioPersistence;
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
 * @author estudiante
 */
@RunWith(Arquillian.class)
public class EjercicioPersistenceTest {
      
    @Inject
    private EjercicioPersistence ejercicioPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<EjercicioEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el 
     * archivo beans.xml para resolver la inyeccion de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EjercicioEntity.class.getPackage())
                .addPackage(EjercicioPersistence.class.getPackage())
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
        em.createQuery("delete from EjercicioEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas
     */
    private void inserData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i<10 ; i++)
        {
            EjercicioEntity entidad = factory.manufacturePojo(EjercicioEntity.class);
            
            em.persist (entidad);
            data.add (entidad);
        }
    }
    
    /**
     * Prueba para crear un ejercicio
     */
    @Test
    public void createEjercicioEntity()
    {
        PodamFactory factory =  new PodamFactoryImpl();
        EjercicioEntity nuevoEjercicio = factory.manufacturePojo(EjercicioEntity.class);
        EjercicioEntity resultado = ejercicioPersistence.create(nuevoEjercicio);
        
        Assert.assertNotNull(resultado);
        EjercicioEntity entidad = em.find(EjercicioEntity.class, resultado.getId());
        
        //Assert.assertEquals(nuevoEjercicio.getCedula(), entidad.getCedula());
    }
    
    /**
     * Prueba para consultar la lista de ejercicios
     */
    @Test
    public void getEjerciciosTest()
    {
        List<EjercicioEntity> lista = ejercicioPersistence.findAll();
        Assert.assertEquals(data.size(), lista.size());
        for (EjercicioEntity entidad : lista) {
            boolean found = false;
            for (EjercicioEntity entity : data) {
                if (entidad.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
     /**
     * Prueba para consultar un Ejercicio
     */
    @Test
    public void getEjercicioTest() {
        EjercicioEntity entity = data.get(0);
        EjercicioEntity nuevaEntity = ejercicioPersistence.find(entity.getId());
        Assert.assertNotNull(nuevaEntity);
        Assert.assertEquals(entity.getNombre(), nuevaEntity.getNombre());
        
    }
    
     /**
     * Prueba para actualizar un Ejercicio
     */
    @Test
    public void updateEjercicioTest() {
        EjercicioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EjercicioEntity newEntity = factory.manufacturePojo(EjercicioEntity.class);

        newEntity.setId(entity.getId());

        ejercicioPersistence.update(newEntity);

        EjercicioEntity resp = em.find(EjercicioEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para eliminar un Author.
     */
    @Test
    public void deleteEjercicioTest() {
        EjercicioEntity entity = data.get(0);
        ejercicioPersistence.delete(entity.getId());
        EjercicioEntity deleted = em.find(EjercicioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
