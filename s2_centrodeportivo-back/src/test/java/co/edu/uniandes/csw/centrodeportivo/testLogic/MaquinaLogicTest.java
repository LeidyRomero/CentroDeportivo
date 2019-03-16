/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.testLogic;

import co.edu.uniandes.csw.centrodeportivo.ejb.MaquinaLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.MaquinaEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.MaquinaPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
public class MaquinaLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private MaquinaLogic maquinaLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private List<MaquinaEntity> data = new ArrayList<MaquinaEntity>();
    
    private List<EjercicioEntity> ejerciciosData = new ArrayList<EjercicioEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MaquinaEntity.class.getPackage())
                .addPackage(MaquinaLogic.class.getPackage())
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
        em.createQuery("delete from EjercicioEntity").executeUpdate();
        em.createQuery("delete from MaquinaEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            EjercicioEntity ejercicios = factory.manufacturePojo(EjercicioEntity.class);
            em.persist(ejercicios);
            ejerciciosData.add(ejercicios);
        }
        for (int i = 0; i < 3; i++) {
            MaquinaEntity entity = factory.manufacturePojo(MaquinaEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                ejerciciosData.get(i).setMaquina(entity);
            }
        }
    }
    
    /**
     * Prueba para crear una maquina.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test
    public void createMaquinaTest() throws BusinessLogicException {
        MaquinaEntity newEntity = factory.manufacturePojo(MaquinaEntity.class);
        MaquinaEntity result = maquinaLogic.createMaquina(newEntity);
        Assert.assertNotNull(result);
        MaquinaEntity entity = em.find(MaquinaEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    /**
     * Prueba para consultar la lista de maquinas.
     */
    @Test
    public void getMaquinasTest() {
        List<MaquinaEntity> list = maquinaLogic.getMaquinas();
        Assert.assertEquals(data.size(), list.size());
        for (MaquinaEntity entity : list) {
            boolean found = false;
            for (MaquinaEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
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
        MaquinaEntity resultEntity = maquinaLogic.getMaquina(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
    }

    /**
     * Prueba para actualizar un Editorial.
     */
    @Test
    public void updateMaquinaTest() {
        MaquinaEntity entity = data.get(0);
        MaquinaEntity pojoEntity = factory.manufacturePojo(MaquinaEntity.class);
        pojoEntity.setId(entity.getId());
        maquinaLogic.updateMaquina(pojoEntity.getId(), pojoEntity);
        MaquinaEntity resp = em.find(MaquinaEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
    }

    /**
     * Prueba para eliminar un Editorial.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test
    public void deleteMaquinaTest() throws BusinessLogicException {
        MaquinaEntity entity = data.get(1);
        maquinaLogic.deleteMaquina(entity.getId());
        MaquinaEntity deleted = em.find(MaquinaEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    
}
