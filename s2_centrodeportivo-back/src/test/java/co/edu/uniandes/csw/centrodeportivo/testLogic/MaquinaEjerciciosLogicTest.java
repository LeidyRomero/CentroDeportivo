/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.testLogic;

import co.edu.uniandes.csw.centrodeportivo.ejb.MaquinaEjerciciosLogic;
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
 * @author Diany Quintero
 */
@RunWith(Arquillian.class)
public class MaquinaEjerciciosLogicTest {
    
   private PodamFactory factory = new PodamFactoryImpl();
    
   @Inject 
   private MaquinaLogic maquinaLogic;
   @Inject
   private MaquinaEjerciciosLogic maquinaEjerciciosLogic;
   
   @PersistenceContext
   private EntityManager em;
   
   @Inject
   private UserTransaction utx;
      
   private List<MaquinaEntity> data = new ArrayList<MaquinaEntity>();
   
   private List<EjercicioEntity> ejerciciosData = new ArrayList<>();
   
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
     * Prueba para asociar un Ejercicio existente a una Maquina.
     */
    @Test
    public void addEjercicioTest() {
        MaquinaEntity entity = data.get(0);
        EjercicioEntity ejercicioEntity = ejerciciosData.get(1);
        EjercicioEntity response = maquinaEjerciciosLogic.addEjercicio(ejercicioEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(ejercicioEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Ejercicios asociadas a una
     * instancia Maquina.
     */
    @Test
    public void getEjerciciosTest() {
        List<EjercicioEntity> list = maquinaEjerciciosLogic.getEjercicios(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para obtener una instancia de Ejercicio asociada a una instancia
     * Maquina.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test
    public void getEjercicioTest() throws BusinessLogicException {
        MaquinaEntity entity = data.get(0);
        EjercicioEntity ejercicioEntity = ejerciciosData.get(0);
        EjercicioEntity response = maquinaEjerciciosLogic.getEjercicio(entity.getId(), ejercicioEntity.getId());

        Assert.assertEquals(ejercicioEntity.getId(), response.getId());
        Assert.assertEquals(ejercicioEntity.getCategoria(), response.getCategoria());
        Assert.assertEquals(ejercicioEntity.getDescripcion(), response.getDescripcion());
        Assert.assertEquals(ejercicioEntity.getDuracion(), response.getDuracion());
        Assert.assertEquals(ejercicioEntity.getExplicacion(), response.getExplicacion());
        Assert.assertEquals(ejercicioEntity.getImplementos(), response.getImplementos());
        Assert.assertEquals(ejercicioEntity.getMaquina(), response.getMaquina());
        Assert.assertEquals(ejercicioEntity.getNombre(), response.getNombre());
        Assert.assertEquals(ejercicioEntity.getNumeroDeSeries(), response.getNumeroDeSeries());
        Assert.assertEquals(ejercicioEntity.getRutina(), response.getRutina());
        Assert.assertEquals(ejercicioEntity.getZonasCuerpo(), response.getZonasCuerpo());
    }

    /**
     * Prueba para obtener una instancia de Ejercicio asociada a una instancia
     * Maquina que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getEjercicioNoAsociadoTest() throws BusinessLogicException {
        MaquinaEntity entity = data.get(0);
        EjercicioEntity ejercicioEntity = ejerciciosData.get(1);
        maquinaEjerciciosLogic.getEjercicio(entity.getId(), ejercicioEntity.getId());    
    }
    
    /**
     * Prueba para remplazar las instancias de Books asociadas a una instancia
     * de Editorial.
     */
    @Test
    public void replaceEjerciciosTest() {
        MaquinaEntity entity = data.get(0);
        List<EjercicioEntity> list = ejerciciosData.subList(1, 3);
        maquinaEjerciciosLogic.replaceEjercicios(entity.getId(), list);

        entity = maquinaLogic.getMaquina(entity.getId());
        Assert.assertFalse(entity.getEjercicios().contains(ejerciciosData.get(0)));
        Assert.assertTrue(entity.getEjercicios().contains(ejerciciosData.get(1)));
        Assert.assertTrue(entity.getEjercicios().contains(ejerciciosData.get(2)));
    }


   
   
   
   
   
}
