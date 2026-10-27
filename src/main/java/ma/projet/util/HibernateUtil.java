package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Charger application.properties
                Properties properties = new Properties();
                InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties");
                if (input != null) {
                    properties.load(input);
                    configuration.setProperties(properties);
                } else {
                    configuration.configure("hibernate.cfg.xml");
                }

                // Enregistrement des entites - Exercice 1
                configuration.addAnnotatedClass(ma.projet.classes.Categorie.class);
                configuration.addAnnotatedClass(ma.projet.classes.Produit.class);
                configuration.addAnnotatedClass(ma.projet.classes.Commande.class);
                configuration.addAnnotatedClass(ma.projet.classes.LigneCommandeProduit.class);

                // Enregistrement des entites - Exercice 2
                configuration.addAnnotatedClass(ma.projet.classes.Employe.class);
                configuration.addAnnotatedClass(ma.projet.classes.Projet.class);
                configuration.addAnnotatedClass(ma.projet.classes.Tache.class);
                configuration.addAnnotatedClass(ma.projet.classes.EmployeTache.class);

                // Enregistrement des entites - Exercice 3
                configuration.addAnnotatedClass(ma.projet.beans.Personne.class);
                configuration.addAnnotatedClass(ma.projet.beans.Homme.class);
                configuration.addAnnotatedClass(ma.projet.beans.Femme.class);
                configuration.addAnnotatedClass(ma.projet.beans.Mariage.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.err.println("Erreur lors de l'initialisation de SessionFactory: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
