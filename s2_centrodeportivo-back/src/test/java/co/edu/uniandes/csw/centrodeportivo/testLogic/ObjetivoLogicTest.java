/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.testLogic;

import co.edu.uniandes.csw.centrodeportivo.ejb.ObjetivoLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.ObjetivoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
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
 *
 * @author Leidy Romero
 */
@RunWith(Arquillian.class)
public class ObjetivoLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ObjetivoLogic objetivoLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ObjetivoEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ObjetivoEntity.class.getPackage())
                .addPackage(ObjetivoLogic.class.getPackage())
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
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i<10 ; i++)
        {
            ObjetivoEntity entidad = factory.manufacturePojo(ObjetivoEntity.class);
            
            em.persist (entidad);
            data.add (entidad);
        }
    }
    /**
     * Prueba para crear un objetivo
     * @throws BusinessLogicException Si el objetivo con esra descripcion ya existe
     */
    @Test
    public void createObjetivoEntity() throws BusinessLogicException
    {
        PodamFactory factory =  new PodamFactoryImpl();
        ObjetivoEntity nuevoObjetivo = factory.manufacturePojo(ObjetivoEntity.class);
        ObjetivoEntity resultado = objetivoLogic.createObjetivo(nuevoObjetivo);
        
        Assert.assertNotNull(resultado);
        ObjetivoEntity entidad = em.find(ObjetivoEntity.class, resultado.getId());
        
        Assert.assertEquals(nuevoObjetivo.getId(), entidad.getId());
    }
    
     /**
     * Prueba para consultar la lista de Objetivos.
     */
    @Test
    public void getObjetivosTest() {
        List<ObjetivoEntity> list = objetivoLogic.getObjetivos();
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
        ObjetivoEntity newEntity = objetivoLogic.getObjetivo(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getDescripcion(), newEntity.getDescripcion());
    }

    /**
     * Prueba para actualizar un Objetivo.
     */
    @Test
    public void updateObjetivoTest() {
        ObjetivoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ObjetivoEntity nuevaEntity = factory.manufacturePojo(ObjetivoEntity.class);

        nuevaEntity.setId(entity.getId());

        objetivoLogic.updateObjetivo(nuevaEntity.getId(), nuevaEntity);

        ObjetivoEntity resp = em.find(ObjetivoEntity.class, entity.getId());

        Assert.assertEquals(nuevaEntity.getDescripcion(), resp.getDescripcion());
        Assert.assertEquals(nuevaEntity.getCumplio(), resp.getCumplio());
    }
}
