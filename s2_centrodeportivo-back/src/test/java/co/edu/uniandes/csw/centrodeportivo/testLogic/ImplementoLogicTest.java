/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.testLogic;

import co.edu.uniandes.csw.centrodeportivo.ejb.ImplementoLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.ImplementoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.ImplementoPersistence;
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
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de Implemento
 *
 * @author Lina Cardozo
 */
@RunWith(Arquillian.class)
public class ImplementoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ImplementoLogic implementoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ImplementoEntity> data = new ArrayList<ImplementoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ImplementoEntity.class.getPackage())
                .addPackage(ImplementoLogic.class.getPackage())
                .addPackage(ImplementoPersistence.class.getPackage())
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
        em.createQuery("delete from ImplementoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ImplementoEntity entity = factory.manufacturePojo(ImplementoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Implemento.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test
    public void createImplementoTest() throws BusinessLogicException {
        ImplementoEntity newEntity = factory.manufacturePojo(ImplementoEntity.class);
        ImplementoEntity result = implementoLogic.createImplemento(newEntity);
        Assert.assertNotNull(result);
        ImplementoEntity entity = em.find(ImplementoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getCantidadExistencias(), entity.getCantidadExistencias());
    }

    /**
     * Prueba para crear un Implemento con el mismo nombre de un Implemento que ya
     * existe.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createImplementoConMismoNombreTest() throws BusinessLogicException {
        ImplementoEntity newEntity = factory.manufacturePojo(ImplementoEntity.class);
        newEntity.setNombre(data.get(0).getNombre());
        implementoLogic.createImplemento(newEntity);
    }

    /**
     * Prueba para consultar la lista de Implementos.
     */
    @Test
    public void getImplementosTest() {
        List<ImplementoEntity> list = implementoLogic.getImplementos();
        Assert.assertEquals(data.size(), list.size());
        for (ImplementoEntity entity : list) {
            boolean found = false;
            for (ImplementoEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    /**
     * Prueba para consultar un Implemento.
     */
    @Test
    public void getImplementoTest() {
        ImplementoEntity entity = data.get(0);
        ImplementoEntity resultEntity = implementoLogic.getImplemento(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getCantidadExistencias(), resultEntity.getCantidadExistencias());
    }

    /**
     * Prueba para actualizar un Implemento.
     */
    @Test
    public void updateImplementoTest() {
        ImplementoEntity entity = data.get(0);
        ImplementoEntity pojoEntity = factory.manufacturePojo(ImplementoEntity.class);
        pojoEntity.setId(entity.getId());
        implementoLogic.updateImplemento(pojoEntity.getId(), pojoEntity);
        ImplementoEntity resp = em.find(ImplementoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(pojoEntity.getCantidadExistencias(), resp.getCantidadExistencias());
    }

    /**
     * Prueba para eliminar un Implemento.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test
    public void deleteImplementoTest() throws BusinessLogicException {
        ImplementoEntity entity = data.get(1);
        implementoLogic.deleteImplemento(entity.getId());
        ImplementoEntity deleted = em.find(ImplementoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}