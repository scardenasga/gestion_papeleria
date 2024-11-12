import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import co.edu.unbosque.model.EstablecerSecion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EstablecerSecionTest {

    @InjectMocks
    private EstablecerSecion establecerSecion;

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(entityManager.getTransaction()).thenReturn(transaction);
    }

    @Test
    public void testSetUsuarioActual_Success() {
        String usuarioActual = "testUser";

        doNothing().when(transaction).begin();
        doNothing().when(entityManager).createNativeQuery("SET myapp.usuario_actual = '" + usuarioActual + "'")
                                          .executeUpdate();
        doNothing().when(transaction).commit();

        establecerSecion.setUsuarioActual(usuarioActual);

        verify(transaction).begin();
        verify(entityManager).createNativeQuery("SET myapp.usuario_actual = '" + usuarioActual + "'");
        verify(transaction).commit();
        verify(entityManager).close();
    }

    @Test
    public void testSetUsuarioActual_RollbackOnException() {
        String usuarioActual = "testUser";

        doNothing().when(transaction).begin();
        doThrow(new RuntimeException("DB error")).when(entityManager)
                .createNativeQuery("SET myapp.usuario_actual = '" + usuarioActual + "'")
                .executeUpdate();
        doNothing().when(transaction).rollback();

        assertThrows(RuntimeException.class, () -> establecerSecion.setUsuarioActual(usuarioActual));

        verify(transaction).begin();
        verify(transaction).rollback();
        verify(entityManager).close();
    }
}
