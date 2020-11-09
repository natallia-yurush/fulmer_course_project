package by.ny.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class AbstractDao {
    protected Connection connection;

    public AbstractDao() {
        try {
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/fulmer","root","root");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
