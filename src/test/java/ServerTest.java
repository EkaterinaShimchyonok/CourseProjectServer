import org.example.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ServerTest {

    private Server server;
    private ServerSocket serverSocket;

    @BeforeEach
    public void setUp() {
        server = new Server();
        serverSocket = mock(ServerSocket.class);
    }

    @Test
    public void testStartThrowsSocketException() throws IOException {
        // Подготовка mock объекта ServerSocket, который выбрасывает SocketException
        doThrow(new SocketException("Test SocketException")).when(serverSocket).accept();

        assertThrows(SocketException.class, () -> {
            // Вызов приватного метода startServer для тестирования
            server.start();
        });
    }

    @Test
    public void testStartThrowsIOException() throws IOException {
        // Подготовка mock объекта ServerSocket, который выбрасывает IOException
        doThrow(new IOException("Test IOException")).when(serverSocket).accept();

        assertThrows(IOException.class, () -> {
            // Вызов приватного метода startServer для тестирования
            server.start();
        });
    }
}
