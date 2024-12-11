import static org.junit.jupiter.api.Assertions.*;

import org.example.DatabaseManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManagerTest {

    @BeforeEach
    public void setUp() {
        DatabaseManager.getInstance(); // Инициализация соединения перед каждым тестом
    }

    @AfterEach
    public void tearDown() {
        DatabaseManager.close(); // Закрытие соединения после каждого теста
    }

    @Test
    public void testGetInstance() {
        Connection connection = DatabaseManager.getInstance();
        assertNotNull(connection, "Соединение не должно быть null");
        try {
            assertFalse(connection.isClosed(), "Соединение должно быть открыто");
        } catch (SQLException e) {
            fail("Ошибка при проверке состояния соединения: " + e.getMessage());
        }
    }

    @Test
    public void testClose() {
        DatabaseManager.close();
        try {
            Connection connection = getConnectionViaReflection();
            assertTrue(connection.isClosed(), "Соединение должно быть закрыто");
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            fail("Ошибка при проверке закрытия соединения: " + e.getMessage());
        }
    }

    private Connection getConnectionViaReflection() throws NoSuchFieldException, IllegalAccessException {
        Field connectionField = DatabaseManager.class.getDeclaredField("connection");
        connectionField.setAccessible(true);
        return (Connection) connectionField.get(null);
    }
}
