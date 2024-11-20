package co.edu.unbosque.view.util.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceSingleton {
    private static DataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/rosita"); // Cambiar a PostgreSQL
        config.setUsername("postgres"); // Cambiar al usuario correcto
        config.setPassword("contrasena"); // Cambiar a la contraseña correcta
        config.setMaximumPoolSize(10); // Ajusta según tus necesidades
        config.setDriverClassName("org.postgresql.Driver"); // Asegúrate de que el driver esté configurado

        dataSource = new HikariDataSource(config);
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
}