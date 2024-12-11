import org.example.ClientHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClientHandlerTest {

    private Socket clientSocket;
    private ClientHandler clientHandler;

    @BeforeEach
    public void setUp() throws IOException {
        clientSocket = mock(Socket.class);
        when(clientSocket.getInputStream()).thenThrow(new IOException("Test IOException"));
        when(clientSocket.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        clientHandler = new ClientHandler(clientSocket);
    }

    @Test
    public void testRunThrowsIOException() {
        assertDoesNotThrow(() -> {
            clientHandler.run();
        }, "Метод run() не должен выбрасывать исключение");
    }

    @Test
    public void testRunHandlesIOException() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        clientHandler.run();

        String expectedMessage = "Клиент отключился: null:0";
        assertTrue(outContent.toString().contains(expectedMessage));

        System.setOut(originalOut);
    }
}
