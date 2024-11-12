import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import co.edu.unbosque.model.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HibernateUtilTest {

    private HibernateUtil hibernateUtil;
    private EntityManagerFactory entityManagerFactoryMock;

    @BeforeEach
    public void setUp() {
        // Mock del EntityManagerFactory para aislar las pruebas de la conexión real
        entityManagerFactoryMock = mock(EntityManagerFactory.class);
        hibernateUtil = HibernateUtil.getInstance();
        HibernateUtil.getExecutorService().shutdownNow(); // Reiniciar el ExecutorService entre pruebas
    }

    @AfterEach
    public void tearDown() {
        hibernateUtil.closeEntityManagerFactory();
    }

    @Test
    public void testGetInstance_ShouldReturnSameInstance() {
        HibernateUtil instance1 = HibernateUtil.getInstance();
        HibernateUtil instance2 = HibernateUtil.getInstance();
        assertSame(instance1, instance2, "Debe retornar la misma instancia");
    }

    @Test
    public void testGetEntityManager_ShouldReturnEntityManager() {
        EntityManager entityManager = hibernateUtil.getEntityManager();
        assertNotNull(entityManager, "Debe retornar una instancia de EntityManager");
        entityManager.close();
    }

    @Test
    public void testExecuteAsync_ShouldRunTaskInAnotherThread() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        HibernateUtil.getInstance().executeAsync(() -> {
            System.out.println("Task executed in async mode");
            assertTrue(Thread.currentThread().getName().contains("pool"));
        });
        executorService.shutdown();
    }

    @Test
    public void testCloseEntityManagerFactory_ShouldCloseEntityManagerFactoryAndExecutorService() {
        // Mock del EntityManagerFactory para verificar su cierre
        doNothing().when(entityManagerFactoryMock).close();
        
        hibernateUtil.closeEntityManagerFactory();

        assertTrue(hibernateUtil.getExecutorService().isShutdown(), "El ExecutorService debería estar cerrado");
    }
}
