package application.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionSGBD {

    private static final String configurationFile = "BD.properties";
    
    public ConnexionSGBD() {
    }

    public Connection connexion() throws SQLException, ClassNotFoundException {

        String jdbcDriver, dbUrl, username, password;
        DatabaseAccessProperties dap = new DatabaseAccessProperties(configurationFile);
        jdbcDriver = dap.getJdbcDriver();
        dbUrl = dap.getDatabaseUrl();
        username = dap.getUsername();
        password = dap.getPassword();

        // Load the database driver
        Class.forName(jdbcDriver);// Get a connection to the database
        // Print information about connection warnings
        Connection conn = DriverManager.getConnection(dbUrl, username, password);

        return conn;
    }

}
