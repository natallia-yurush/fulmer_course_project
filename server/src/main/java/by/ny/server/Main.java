package by.ny.server;

import by.ny.server.exception.ProjectException;
import by.ny.server.thread.MultiThreadServer;
import by.ny.server.util.JdbcUtil;

public class Main {
    public static void main(String[] args) {
        try {
            new JdbcUtil().createDatabaseWithTables();
        } catch (ProjectException e) {
            e.printStackTrace();
            return;
        }
        int port = 3333;
        MultiThreadServer server = new MultiThreadServer(port);
        new Thread(server).start();
    }
}
