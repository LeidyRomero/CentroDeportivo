/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.centrodeportivo.test.persistence;
import co.edu.uniandes.csw.centrodeportivo.entities.ZonaCuerpoEntity;
import co.edu.uniandes.csw.centrodeportivo.persistence.ZonaCuerpoPersistence;
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
 * @author Daniel Pardo
 */
@RunWith(Arquillian.class)
public class ZonaCuerpoPersistenceTest 
{
    @Inject
    private ZonaCuerpoPersistence zonaCuerpoPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<ZonaCuerpoEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el 
     * archivo beans.xml para resolver la inyeccion de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment(){
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ZonaCuerpoEntity.class.getPackage())
                .addPackage(ZonaCuerpoPersistence.class.getPackage())
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
            em.joinTransaction();
            clearData();
            inserData();
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
        em.createQuery("delete from ZonaCuerpoEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas
     */
    private void inserData()
    {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i<10 ; i++)
        {
            ZonaCuerpoEntity entidad = factory.manufacturePojo(ZonaCuerpoEntity.class);
            
            em.persist (entidad);
            data.add (entidad);
        }
    }
    
    /**
     * Prueba para crear un zonaCuerpo
     */
    @Test
    public void createZonaCuerpoEntity()
    {
        PodamFactory factory =  new PodamFactoryImpl();
        ZonaCuerpoEntity nuevoZonaCuerpo = factory.manufacturePojo(ZonaCuerpoEntity.class);
        ZonaCuerpoEntity resultado = zonaCuerpoPersistence.create(nuevoZonaCuerpo);
        
        Assert.assertNotNull(resultado);
        ZonaCuerpoEntity entidad = em.find(ZonaCuerpoEntity.class, resultado.getId());
        
        //Assert.assertEquals(nuevoZonaCuerpo.getCedula(), entidad.getCedula());
    }
    
    /**
     * Prueba para consultar la lista de zonasCuerpo
     */
    @Test
    public void getZonasCuerpoTest()
    {
        List<ZonaCuerpoEntity> lista = zonaCuerpoPersistence.findAll();
        Assert.assertEquals(data.size(), lista.size());
        for (ZonaCuerpoEntity entidad : lista) {
            boolean found = false;
            for (ZonaCuerpoEntity entity : data) {
                if (entidad.getId().equals(entity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
     /**
     * Prueba para consultar un ZonaCuerpo
     */
    @Test
    public void getZonaCuerpoTest() {
        ZonaCuerpoEntity entity = data.get(0);
        ZonaCuerpoEntity nuevaEntity = zonaCuerpoPersistence.find(entity.getId());
        Assert.assertNotNull(nuevaEntity);
        Assert.assertEquals(entity.getNombre(), nuevaEntity.getNombre());
        
    }
    
     /**
     * Prueba para actualizar un ZonaCuerpo
     */
    @Test
    public void updateZonaCuerpoTest() {
        ZonaCuerpoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ZonaCuerpoEntity newEntity = factory.manufacturePojo(ZonaCuerpoEntity.class);

        newEntity.setId(entity.getId());

        zonaCuerpoPersistence.update(newEntity);

        ZonaCuerpoEntity resp = em.find(ZonaCuerpoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
    }

    /**
     * Prueba para eliminar un Author.
     */
    @Test
    public void deleteZonaCuerpoTest() {
        ZonaCuerpoEntity entity = data.get(0);
        zonaCuerpoPersistence.delete(entity.getId());
        ZonaCuerpoEntity deleted = em.find(ZonaCuerpoEntity.class, entity.getId());
        Assert.assertNull(deleted);
        
       
        
    }
}
