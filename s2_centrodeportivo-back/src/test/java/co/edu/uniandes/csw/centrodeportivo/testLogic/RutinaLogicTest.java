/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.testLogic;


import co.edu.uniandes.csw.centrodeportivo.ejb.RutinaLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.RutinaEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
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
 * @author Leidy Romero
 */
@RunWith(Arquillian.class)
public class RutinaLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private RutinaLogic rutinaLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<RutinaEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RutinaEntity.class.getPackage())
                .addPackage(RutinaLogic.class.getPackage())
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
        em.createQuery("delete from RutinaEntity").executeUpdate();
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
            RutinaEntity entidad = factory.manufacturePojo(RutinaEntity.class);
            
            em.persist (entidad);
            data.add (entidad);
        }
    }
    /**
     * Prueba para crear un rutina
     */
    public void createRutinaEntity() throws BusinessLogicException
    {
        PodamFactory factory =  new PodamFactoryImpl();
        RutinaEntity nuevoRutina = factory.manufacturePojo(RutinaEntity.class);
        RutinaEntity resultado = rutinaLogic.createRutina(nuevoRutina);
        
        Assert.assertNotNull(resultado);
        RutinaEntity entidad = em.find(RutinaEntity.class, resultado.getId());
        
        Assert.assertEquals(nuevoRutina.getId(), entidad.getId());
    }
    
     /**
     * Prueba para consultar la lista de Rutinas.
     */
    @Test
    public void getRutinasTest() {
        List<RutinaEntity> list = rutinaLogic.getRutinas();
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

    /**
     * Prueba para consultar un Rutina.
     */
    @Test
    public void getRutinaTest() {
        RutinaEntity entity = data.get(0);
        RutinaEntity newEntity = rutinaLogic.getRutina(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNombre(), newEntity.getNombre());
    }

    /**
     * Prueba para actualizar un Rutina.
     */
    @Test
    public void updateRutinaTest() {
        RutinaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        RutinaEntity nuevaEntity = factory.manufacturePojo(RutinaEntity.class);

        nuevaEntity.setId(entity.getId());

        rutinaLogic.updateRutina(nuevaEntity.getId(), nuevaEntity);

        RutinaEntity resp = em.find(RutinaEntity.class, entity.getId());

        Assert.assertEquals(nuevaEntity.getNombre(), resp.getNombre());
       
    }
}
