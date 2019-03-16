/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.testLogic;

import co.edu.uniandes.csw.centrodeportivo.ejb.EjercicioLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.EjercicioPersistence;
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
public class EjercicioLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private EjercicioLogic ejercicioLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EjercicioEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EjercicioEntity.class.getPackage())
                .addPackage(EjercicioLogic.class.getPackage())
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
        em.createQuery("delete from EjercicioEntity").executeUpdate();
        em.createQuery("delete from ObjetivoEntity").executeUpdate();
    }
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()
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
     * Prueba para crear un ejercicio.
     */
    @Test
    public void CreateEjercicioTest() throws BusinessLogicException
    {
        EjercicioEntity nuevoEjercicioEntity = factory.manufacturePojo(EjercicioEntity.class);
        EjercicioEntity result = ejercicioLogic.createEjercicio(nuevoEjercicioEntity);
        Assert.assertNotNull(result);
        EjercicioEntity entity = em.find(EjercicioEntity.class, result.getId());
        Assert.assertEquals(nuevoEjercicioEntity.getId(), entity.getId());
    
    }
    
    /**
     * Prueba para consultar la lista de ejercicios.
     */
    @Test
    public void getEjerciciosTest() {
        List<EjercicioEntity> list = ejercicioLogic.getEjercicios();
        Assert.assertEquals(data.size(), list.size());
        for (EjercicioEntity entity : list) {
            boolean found = false;
            for (EjercicioEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    /**
     * Prueba para consultar un ejercicio.
     */
    @Test
    public void getEjercicioTest() {
        EjercicioEntity entity = data.get(0);
        EjercicioEntity resultEntity = ejercicioLogic.getEjercicio(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        
    }
    /**
     * Prueba para actualizar un ejercicio.
     */
    @Test
    public void updateEjercicioTest() {
        EjercicioEntity entity = data.get(0);
        EjercicioEntity pojoEntity = factory.manufacturePojo(EjercicioEntity.class);

        pojoEntity.setId(entity.getId());

        ejercicioLogic.updateEjercicio(pojoEntity.getId(), pojoEntity);

        EjercicioEntity resp = em.find(EjercicioEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        
    }

    /**
     * Prueba para eliminar un Ejercicio
     *
     * @throws co.edu.uniandes.csw.bookstore.exceptions.BusinessLogicException
     */
    @Test
    public void deleteEjercicioTest() throws BusinessLogicException {
        EjercicioEntity entity = data.get(0);
        ejercicioLogic.deleteEjercicio(entity.getId());
        EjercicioEntity deleted = em.find(EjercicioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
