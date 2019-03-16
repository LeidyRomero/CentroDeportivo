/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.testLogic;

import co.edu.uniandes.csw.centrodeportivo.ejb.SeguimientoLogic;
import co.edu.uniandes.csw.centrodeportivo.ejb.SeguimientoMaquinasLogic;
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
public class SeguimientoMaquinasLogicTest {
    
   private PodamFactory factory = new PodamFactoryImpl();
    
   @Inject 
   private SeguimientoLogic seguimientoLogic;
   @Inject
   private SeguimientoMaquinasLogic seguimientoMaquinasLogic;
   
   @PersistenceContext
   private EntityManager em;
   
   @Inject
   private UserTransaction utx;
      
   private List<SeguimientoEntity> data = new ArrayList<SeguimientoEntity>();
   
   private List<MaquinaEntity> maquinasData = new ArrayList<>();
   
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
        em.createQuery("delete from MaquinaEntity").executeUpdate();
        em.createQuery("delete from SeguimientoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MaquinaEntity maquinas = factory.manufacturePojo(MaquinaEntity.class);
            em.persist(maquinas);
            maquinasData.add(maquinas);
        }
        for (int i = 0; i < 3; i++) {
            SeguimientoEntity entity = factory.manufacturePojo(SeguimientoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                maquinasData.get(i).setSeguimiento(entity);
            }
        }
    }
    
    /**
     * Prueba para asociar un Ejercicio existente a una Maquina.
     */
    @Test
    public void addMaquinaTest() {
        SeguimientoEntity entity = data.get(0);
        MaquinaEntity maquinaEntity = maquinasData.get(1);
        MaquinaEntity response = seguimientoMaquinasLogic.addMaquina(maquinaEntity.getId(), entity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(maquinaEntity.getId(), response.getId());
    }
    
    /**
     * Prueba para obtener una colección de instancias de Maquina asociadas a una
     * instancia Seguimiento.
     */
    @Test
    public void getMaquinasTest() {
        List<MaquinaEntity> list = seguimientoMaquinasLogic.getMaquinas(data.get(0).getId());
        Assert.assertEquals(1, list.size());
    }
    
    /**
     * Prueba para obtener una instancia de Ejercicio asociada a una instancia
     * Maquina.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test
    public void getMaquinaTest() throws BusinessLogicException {
        SeguimientoEntity entity = data.get(0);
        MaquinaEntity maquinaEntity = maquinasData.get(0);
        MaquinaEntity response = seguimientoMaquinasLogic.getMaquina(entity.getId(), maquinaEntity.getId());
        Assert.assertEquals(maquinaEntity.getId(), response.getId());
        
    }

    /**
     * Prueba para obtener una instancia de Ejercicio asociada a una instancia
     * Maquina que no le pertenece.
     *
     * @throws co.edu.uniandes.csw.centrodeportivo.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void getEjercicioNoAsociadoTest() throws BusinessLogicException {
        SeguimientoEntity entity = data.get(0);
        MaquinaEntity maquinaEntity = maquinasData.get(1);
        seguimientoMaquinasLogic.getMaquina(entity.getId(), maquinaEntity.getId());    
    }
    
    /**
     * Prueba para remplazar las instancias de Maquinas asociadas a una instancia
     * de Seguimiento.
     */
    @Test
    public void replaceMaquinasTest() {
        SeguimientoEntity entity = data.get(0);
        List<MaquinaEntity> list = maquinasData.subList(1, 3);
        seguimientoMaquinasLogic.replaceMaquinas(entity.getId(), list);

        entity = seguimientoLogic.getSeguimiento(entity.getId());
        Assert.assertFalse(entity.getMaquinas().contains(maquinasData.get(0)));
        Assert.assertTrue(entity.getMaquinas().contains(maquinasData.get(1)));
        Assert.assertTrue(entity.getMaquinas().contains(maquinasData.get(2)));
    }


}
