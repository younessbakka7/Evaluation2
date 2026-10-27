package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme> {

    @Override
    public boolean create(Homme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Homme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Homme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Homme findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Homme.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Homme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from Homme", Homme.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * 1. Afficher les epouses d'un homme entre deux dates
     */
    public void afficherEpousesEntreDates(Homme h, Date d1, Date d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String hql = "SELECT DISTINCT m.femme FROM Mariage m WHERE m.homme = :h AND m.dateDebut BETWEEN :d1 AND :d2";
            Query<Femme> query = session.createQuery(hql, Femme.class);
            query.setParameter("h", h);
            query.setParameter("d1", d1);
            query.setParameter("d2", d2);
            List<Femme> epouses = query.list();

            System.out.println("----------------------------------------------------------------");
            System.out.println("Epouses de " + h.getNom() + " " + h.getPrenom() + " entre le " + sdf.format(d1) + " et le " + sdf.format(d2) + " :");
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-6s %-25s %-15s %-20s%n", "ID", "Nom & Prenom", "Telephone", "Date Naissance");
            for (Femme f : epouses) {
                String dNais = f.getDateNaissance() != null ? sdf.format(f.getDateNaissance()) : "-";
                System.out.printf("%-6d %-25s %-15s %-20s%n",
                        f.getId(), f.getNom() + " " + f.getPrenom(), f.getTelephone(), dNais);
            }
            System.out.println();
        } finally {
            session.close();
        }
    }

    /**
     * 2. Afficher les mariages d'un homme donne, avec les details
     * Format attendu:
     * Nom : SAFI SAID
     * Mariages En Cours :
     * 1. Femme : SALIMA RAMI   Date Debut : 03/09/1990    Nbr Enfants : 4
     * 2. Femme : AMAL ALI      Date Debut : 03/09/1995    Nbr Enfants : 2
     * 3. Femme : WAFA ALAOUI   Date Debut : 04/11/2000    Nbr Enfants : 3
     *
     * Mariages echoues :
     * 1. Femme : KARIMA ALAMI  Date Debut : 03/09/1989    
     * Date Fin : 03/09/1990    Nbr Enfants : 0
     */
    public void afficherMariagesHomme(Homme h) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String hql = "FROM Mariage m WHERE m.homme = :h ORDER BY m.dateDebut ASC";
            Query<Mariage> query = session.createQuery(hql, Mariage.class);
            query.setParameter("h", h);
            List<Mariage> mariages = query.list();

            List<Mariage> enCours = new ArrayList<>();
            List<Mariage> echoues = new ArrayList<>();

            for (Mariage m : mariages) {
                if (m.getDateFin() == null) {
                    enCours.add(m);
                } else {
                    echoues.add(m);
                }
            }

            System.out.println("Nom : " + h.getNom().toUpperCase() + " " + h.getPrenom().toUpperCase());
            System.out.println("Mariages En Cours :");
            if (enCours.isEmpty()) {
                System.out.println("  Aucun mariage en cours.");
            } else {
                int i = 1;
                for (Mariage m : enCours) {
                    System.out.printf("%d. Femme : %-15s Date Debut : %-13s Nbr Enfants : %d%n",
                            i++,
                            m.getFemme().getNom().toUpperCase() + " " + m.getFemme().getPrenom().toUpperCase(),
                            sdf.format(m.getDateDebut()),
                            m.getNbrEnfant());
                }
            }

            System.out.println("\nMariages echoues :");
            if (echoues.isEmpty()) {
                System.out.println("  Aucun mariage echoue.");
            } else {
                int i = 1;
                for (Mariage m : echoues) {
                    System.out.printf("%d. Femme : %-15s Date Debut : %-13s%nDate Fin : %-15s Nbr Enfants : %d%n",
                            i++,
                            m.getFemme().getNom().toUpperCase() + " " + m.getFemme().getPrenom().toUpperCase(),
                            sdf.format(m.getDateDebut()),
                            sdf.format(m.getDateFin()),
                            m.getNbrEnfant());
                }
            }
            System.out.println();
        } finally {
            session.close();
        }
    }
}
