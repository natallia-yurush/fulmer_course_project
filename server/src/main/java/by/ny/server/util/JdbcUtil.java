package by.ny.server.util;

import by.ny.server.exception.ProjectException;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtil {
    private String url;
    private String user;
    private String password;
    private Connection connection;
    private final String propertiesFile = "database.properties";
    protected static final Logger logger = LogManager.getLogger(JdbcUtil.class);

    public JdbcUtil() {
        getPropertyValues();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
        return null;
    }

    private void getConnection(String url) {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            logger.warn(ex.getMessage());
        }
    }

    private void getPropertyValues() {
        Properties properties = getProperties();
        if (properties != null) {
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
            url = properties.getProperty("db.url");
        }
    }

    private Properties getProperties() {
        InputStream inputStream;
        Properties prop = null;
        try {
            prop = new Properties();
            inputStream = getClass().getClassLoader().getResourceAsStream(propertiesFile);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Property file '" + propertiesFile + "' not found in the classpath");
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return prop;
    }

    private void executeSqlScript(String path) {
        try {
            ScriptRunner sr = new ScriptRunner(connection);
            Reader reader = new BufferedReader(new FileReader(path));
            sr.runScript(reader);
        } catch (Exception e) {
            logger.warn("Failed to Execute" + path + " The error is " + e.getMessage());
        }
    }

    public void createDatabaseWithTables() throws ProjectException {
        Properties properties = getProperties();
        if (properties == null) {
            throw new ProjectException("Failed to mount database");
        }
        try {
            connection = DriverManager.getConnection(properties.getProperty("db.origin_url"), user, password);
        } catch (SQLException ex) {
            getConnection(url);
            executeSqlScript(properties.getProperty("db.script_database_file_path"));
        }
        executeSqlScript(properties.getProperty("db.script_tables_file_path"));
    }
}

