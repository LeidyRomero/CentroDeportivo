/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.testLogic;

import co.edu.uniandes.csw.centrodeportivo.ejb.DeportistaObjetivoLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.DeportistaEntity;
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
public class DeportistaObjetivoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private DeportistaObjetivoLogic deportistaObjetivoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ObjetivoEntity> data = new ArrayList<ObjetivoEntity>();

    private List<DeportistaEntity> deportistasData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ObjetivoEntity.class.getPackage())
                .addPackage(DeportistaObjetivoLogic.class.getPackage())
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
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from DeportistaEntity").executeUpdate();
        em.createQuery("delete from ObjetivoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            DeportistaEntity deportistas = factory.manufacturePojo(DeportistaEntity.class);
            em.persist(deportistas);
            deportistasData.add(deportistas);
        }
        for (int i = 0; i < 3; i++) {
            ObjetivoEntity entity = factory.manufacturePojo(ObjetivoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                deportistasData.get(i).setObjetivo(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Deportistas existente a un Objetivo.
     */
    @Test
    public void addObjetivoTest() {
        ObjetivoEntity entity = data.get(0);
        DeportistaEntity deportistaEntity = deportistasData.get(1);
        ObjetivoEntity response = deportistaObjetivoLogic.addObjetivo(entity.getId(), deportistaEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entity.getId(), response.getId());
    }

    /**
     * Prueba para consultar un Objetivo.
     */
    @Test
    public void getObjetivoTest() {
        DeportistaEntity entity = deportistasData.get(0);
        ObjetivoEntity resultEntity = deportistaObjetivoLogic.getObjetivo(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getObjetivo().getId(), resultEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Deportistas asociadas a una instancia
     * de Objetivo.
     */
    @Test
    public void replaceObjetivoTest() {
        ObjetivoEntity entity = data.get(0);
        deportistaObjetivoLogic.replaceObjetivo(deportistasData.get(1).getId(), entity.getId());
        entity = deportistaObjetivoLogic.getObjetivo(deportistasData.get(1).getId());
        Assert.assertTrue(entity.getCasosExitosos().contains(deportistasData.get(1)));
    }

    /**
     * Prueba para desasociar un Deportista existente de un Objetivo existente.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test
    public void removeDeportistaTest() throws BusinessLogicException {
        deportistaObjetivoLogic.removeObjetivo(deportistasData.get(0).getId());
        Assert.assertNull(deportistaObjetivoLogic.getObjetivo(deportistasData.get(0).getId()));
    }

    /**
     * Prueba para desasociar un Deportista existente de un Objetivo existente.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void removeDeportistaSinObjetivoTest() throws BusinessLogicException {
        deportistaObjetivoLogic.removeObjetivo(deportistasData.get(1).getId());
    }
}
