package by.ny.client;

import java.io.IOException;
import java.net.Socket;

public class ConnectionUtil {
    public static Socket getSocket() throws IOException {
        return new Socket("127.0.0.1", 3333);
    }
}
