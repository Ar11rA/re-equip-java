package org.example.config;

import org.example.domain.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class DatabaseConfiguration {
    public static Session getSession() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url", String.format(
          "jdbc:postgresql://%s:%s/%s",
          System.getenv("DB_HOST"),
          System.getenv("DB_PORT"),
          System.getenv("DB_NAME")));
        properties.setProperty("dialect", "org.hibernate.dialect.PostgresSQL");
        properties.setProperty("hibernate.connection.username", System.getenv("DB_USER"));
        properties.setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"));
        properties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.setProperty("packagesToScan", "org.example.domain");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Employee.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
          .applySettings(configuration.getProperties()).build();

        SessionFactory sessionFactory =
          configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory.openSession();
    }
}
