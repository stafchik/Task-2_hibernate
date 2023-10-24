package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Util {
    private static final SessionFactory sessionFactory;
    private static final String URL = "jdbc:postgresql://localhost:5432/firstdb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";
    private static final String DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
    private static final String DRIVER = "org.postgresql.Driver";

    static {
        Configuration configuration = new Configuration();
        configuration.setProperty("connection.driver_class", DRIVER);
        configuration.setProperty("hibernate.connection.url", URL);
        configuration.setProperty("hibernate.connection.username", USER);
        configuration.setProperty("hibernate.connection.password", PASSWORD);
        configuration.setProperty("dialect", DIALECT);
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("show_sql", "true");
        configuration.setProperty(" hibernate.connection.pool_size", "10");
        configuration.addAnnotatedClass(User.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}