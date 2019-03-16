/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.testLogic;

import co.edu.uniandes.csw.centrodeportivo.ejb.SeguimientoLogic;
import co.edu.uniandes.csw.centrodeportivo.entities.DeportistaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.MaquinaEntity;
import co.edu.uniandes.csw.centrodeportivo.entities.SeguimientoEntity;
import co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.centrodeportivo.persistence.SeguimientoPersistence;
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
 * Pruebas de logica de Seguimiento
 *
 * @author Lina Cardozo
 */
@RunWith(Arquillian.class)
public class SeguimientoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private SeguimientoLogic seguimientoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<SeguimientoEntity> data = new ArrayList<>();
    
    private List<MaquinaEntity> maquinasData = new ArrayList<>();
    
    private List<DeportistaEntity> deportistaData = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(SeguimientoEntity.class.getPackage())
                .addPackage(SeguimientoLogic.class.getPackage())
                .addPackage(SeguimientoPersistence.class.getPackage())
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
        em.createQuery("delete from MaquinaEntity").executeUpdate();
        em.createQuery("delete from SeguimientoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        /*for(int i = 0 ; i < 3; i++)
        {
            MaquinaEntity maquinas = factory.manufacturePojo(MaquinaEntity.class);
            em.persist(maquinas);
            maquinasData.add(maquinas);
        }*/
        for (int i = 0; i < 3; i++) {
            SeguimientoEntity entity = factory.manufacturePojo(SeguimientoEntity.class);
            em.persist(entity);
            data.add(entity);
        }        
        /*MaquinaEntity maquina = factory.manufacturePojo(MaquinaEntity.class);
        maquina.setSeguimiento(data.get(1));
        em.persist(maquina);
        data.get(1).getMaquinas().add(maquina);*/
        
        
        /*em.persist(deportistaEntity);
        deportistaEntity.setSeguimiento(data.get(0));
        data.get(0).setDeportista(deportistaEntity);*/
    }

    /**
     * Prueba para crear un Seguimiento.
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test
    public void createSeguimientoTest()throws BusinessLogicException {
        SeguimientoEntity newEntity = factory.manufacturePojo(SeguimientoEntity.class);
        SeguimientoEntity result = seguimientoLogic.createSeguimiento(newEntity);
        Assert.assertNotNull(result);
        SeguimientoEntity entity = em.find(SeguimientoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getTiempo(), entity.getTiempo());
        Assert.assertEquals(newEntity.getCalorias(), entity.getCalorias());
        Assert.assertEquals(newEntity.getVelocidadPromedio(), entity.getVelocidadPromedio());
        Assert.assertEquals(newEntity.getRitmoCardiacoPromedio(), entity.getRitmoCardiacoPromedio());
    }

    /**
     * Prueba para consultar un Seguimiento.
     */
    @Test
    public void getSeguimientoTest() {
        SeguimientoEntity entity = data.get(0);
        SeguimientoEntity resultEntity = seguimientoLogic.getSeguimiento(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getTiempo(), resultEntity.getTiempo());
        Assert.assertEquals(entity.getCalorias(), resultEntity.getCalorias());
        Assert.assertEquals(entity.getVelocidadPromedio(), resultEntity.getVelocidadPromedio());
        Assert.assertEquals(entity.getRitmoCardiacoPromedio(), resultEntity.getRitmoCardiacoPromedio());
    }

    /**
     * Prueba para actualizar un Seguimiento.
     */
    @Test
    public void updateSeguimientoTest() {
        SeguimientoEntity entity = data.get(0);
        SeguimientoEntity pojoEntity = factory.manufacturePojo(SeguimientoEntity.class);

        pojoEntity.setId(entity.getId());

        seguimientoLogic.updateSeguimiento(pojoEntity.getId(), pojoEntity);

        SeguimientoEntity resp = em.find(SeguimientoEntity.class, entity.getId());

        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getTiempo(), resp.getTiempo());
        Assert.assertEquals(pojoEntity.getCalorias(), resp.getCalorias());
        Assert.assertEquals(pojoEntity.getVelocidadPromedio(), resp.getVelocidadPromedio());
        Assert.assertEquals(pojoEntity.getRitmoCardiacoPromedio(), resp.getRitmoCardiacoPromedio());
    }

    /**
     * Prueba para eliminar un Seguimiento
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test
    public void deleteSeguimientoTest() throws BusinessLogicException {
        SeguimientoEntity entity = data.get(0);
        seguimientoLogic.deleteSeguimiento(entity.getId());
        SeguimientoEntity deleted = em.find(SeguimientoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }

    /**
     * Prueba para eliminar un Seguimiento asociado a una máquina
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    /*@Test(expected = BusinessLogicException.class)
    public void deleteSeguimientoConMaquinaTest() throws BusinessLogicException {
        seguimientoLogic.deleteSeguimiento(data.get(2).getId());
    }*/
   
}