package by.ny.client;

import java.io.IOException;
import java.net.Socket;

public class ConnectionUtil {
    public static Socket getSocket() throws IOException {
        return new Socket("localhost", 3333);
    }
}
