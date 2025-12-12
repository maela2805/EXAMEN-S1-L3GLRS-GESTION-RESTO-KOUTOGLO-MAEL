package gestion_restaurant.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class DataSourceProvider {

    private static final HikariDataSource ds;

    static {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:postgresql://ep-solitary-shadow-ad05zza6.c-2.us-east-1.aws.neon.tech/neondb?sslmode=require");

        config.setUsername("neondb_owner");
        config.setPassword("npg_6OlUPeZkY5Lx");
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);
        config.setPoolName("gestion-restaurant-pool");

        ds = new HikariDataSource(config);
    }

    private DataSourceProvider() {}

    public static DataSource getDataSource() {
        return ds;
    }

    public static void close() {
        if (ds != null) {
            ds.close();
        }
    }
}
