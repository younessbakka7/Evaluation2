package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override
    public boolean create(Tache o) {
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
    public boolean update(Tache o) {
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
    public boolean delete(Tache o) {
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
    public Tache findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Tache.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Tache> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from Tache", Tache.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * 1. Afficher les taches dont le prix est superieur a 1000 DH (Requete Nommee)
     */
    public void afficherTachesPrixSuperieur1000() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Tache> query = session.createNamedQuery("findTachesPrixSuperieur1000", Tache.class);
            List<Tache> taches = query.list();

            System.out.println("----------------------------------------------------------------");
            System.out.println("Taches dont le prix est superieur a 1000 DH (Requete Nommee) :");
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-6s %-25s %-15s %-20s%n", "ID", "Nom Tache", "Prix", "Projet");
            for (Tache t : taches) {
                System.out.printf("%-6d %-25s %-15s %-20s%n",
                        t.getId(), t.getNom(), String.format("%.2f DH", t.getPrix()),
                        t.getProjet() != null ? t.getProjet().getNom() : "");
            }
            System.out.println();
        } finally {
            session.close();
        }
    }

    /**
     * 2. Afficher les taches realisees entre deux dates
     */
    public void afficherTachesRealiseesEntre(Date d1, Date d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String hql = "FROM EmployeTache et WHERE et.dateDebutReelle >= :d1 AND et.dateFinReelle <= :d2";
            Query<EmployeTache> query = session.createQuery(hql, EmployeTache.class);
            query.setParameter("d1", d1);
            query.setParameter("d2", d2);
            List<EmployeTache> list = query.list();

            System.out.println("----------------------------------------------------------------");
            System.out.println("Taches realisees entre le " + sdf.format(d1) + " et le " + sdf.format(d2) + " :");
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-6s %-20s %-15s %-15s %-15s%n", "ID", "Nom Tache", "Debut Reel", "Fin Reelle", "Employe");
            for (EmployeTache et : list) {
                String deb = et.getDateDebutReelle() != null ? sdf.format(et.getDateDebutReelle()) : "-";
                String fin = et.getDateFinReelle() != null ? sdf.format(et.getDateFinReelle()) : "-";
                String emp = et.getEmploye() != null ? et.getEmploye().getNom() + " " + et.getEmploye().getPrenom() : "-";
                System.out.printf("%-6d %-20s %-15s %-15s %-15s%n",
                        et.getTache().getId(), et.getTache().getNom(), deb, fin, emp);
            }
            System.out.println();
        } finally {
            session.close();
        }
    }
}
