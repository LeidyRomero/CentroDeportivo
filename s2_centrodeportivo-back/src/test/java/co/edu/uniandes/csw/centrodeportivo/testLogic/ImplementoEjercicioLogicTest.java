/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.testLogic;

import co.edu.uniandes.csw.centrodeportivo.ejb.ImplementoEjercicioLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.EjercicioEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.ImplementoEntity;
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
public class ImplementoEjercicioLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ImplementoEjercicioLogic implementoEjercicioLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<EjercicioEntity> data = new ArrayList<EjercicioEntity>();

    private List<ImplementoEntity> implementosData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EjercicioEntity.class.getPackage())
                .addPackage(ImplementoEjercicioLogic.class.getPackage())
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
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from ImplementoEntity").executeUpdate();
        em.createQuery("delete from EjercicioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ImplementoEntity implementos = factory.manufacturePojo(ImplementoEntity.class);
            em.persist(implementos);
            implementosData.add(implementos);
        }
        for (int i = 0; i < 3; i++) {
            EjercicioEntity entity = factory.manufacturePojo(EjercicioEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                implementosData.get(i).setEjercicio(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Implementos existente a un Ejercicio.
     */
    @Test
    public void addEjercicioTest() {
        EjercicioEntity entity = data.get(0);
        ImplementoEntity implementoEntity = implementosData.get(1);
        EjercicioEntity response = implementoEjercicioLogic.addEjercicio(entity.getId(), implementoEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entity.getId(), response.getId());
    }

    /**
     * Prueba para consultar un Ejercicio.
     */
    @Test
    public void getEjercicioTest() {
        ImplementoEntity entity = implementosData.get(0);
        EjercicioEntity resultEntity = implementoEjercicioLogic.getEjercicio(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getEjercicio().getId(), resultEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Implementos asociadas a una instancia
     * de Ejercicio.
     */
    @Test
    public void replaceEjercicioTest() {
        EjercicioEntity entity = data.get(0);
        implementoEjercicioLogic.replaceEjercicio(implementosData.get(1).getId(), entity.getId());
        entity = implementoEjercicioLogic.getEjercicio(implementosData.get(1).getId());
        Assert.assertTrue(entity.getImplementos().contains(implementosData.get(1)));
    }

    /**
     * Prueba para desasociar un Implemento existente de un Ejercicio existente.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test
    public void removeImplementoTest() throws BusinessLogicException {
        implementoEjercicioLogic.removeEjercicio(implementosData.get(0).getId());
        Assert.assertNull(implementoEjercicioLogic.getEjercicio(implementosData.get(0).getId()));
    }

    /**
     * Prueba para desasociar un Implemento existente de un Ejercicio existente.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void removeImplementoSinEjercicioTest() throws BusinessLogicException {
        implementoEjercicioLogic.removeEjercicio(implementosData.get(1).getId());
    }
}
