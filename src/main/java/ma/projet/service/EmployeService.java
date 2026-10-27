package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe o) {
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
    public boolean update(Employe o) {
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
    public boolean delete(Employe o) {
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
    public Employe findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Employe.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Employe> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from Employe", Employe.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * 1. Afficher la liste des taches realisees par un employe
     */
    public void afficherTachesRealisees(Employe e) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String hql = "FROM EmployeTache et WHERE et.employe = :emp";
            Query<EmployeTache> query = session.createQuery(hql, EmployeTache.class);
            query.setParameter("emp", e);
            List<EmployeTache> list = query.list();

            System.out.println("----------------------------------------------------------------");
            System.out.println("Taches realisees par l'employe : " + e.getNom() + " " + e.getPrenom());
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-6s %-20s %-15s %-15s %-15s%n", "Num", "Nom Tache", "Date Debut R.", "Date Fin R.", "Prix");
            for (EmployeTache et : list) {
                String dDebut = et.getDateDebutReelle() != null ? sdf.format(et.getDateDebutReelle()) : "En cours";
                String dFin = et.getDateFinReelle() != null ? sdf.format(et.getDateFinReelle()) : "En cours";
                System.out.printf("%-6d %-20s %-15s %-15s %-15s%n",
                        et.getTache().getId(),
                        et.getTache().getNom(),
                        dDebut,
                        dFin,
                        String.format("%.2f DH", et.getTache().getPrix()));
            }
            System.out.println();
        } finally {
            session.close();
        }
    }

    /**
     * 2. Afficher la liste des projets geres par un employe
     */
    public void afficherProjetsGeres(Employe e) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String hql = "FROM Projet p WHERE p.chefDeProjet = :emp";
            Query<Projet> query = session.createQuery(hql, Projet.class);
            query.setParameter("emp", e);
            List<Projet> projets = query.list();

            System.out.println("----------------------------------------------------------------");
            System.out.println("Projets geres par le chef de projet : " + e.getNom() + " " + e.getPrenom());
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-6s %-25s %-15s %-15s%n", "ID", "Nom Projet", "Date Debut", "Date Fin");
            for (Projet p : projets) {
                String dDeb = p.getDateDebut() != null ? sdf.format(p.getDateDebut()) : "-";
                String dFin = p.getDateFin() != null ? sdf.format(p.getDateFin()) : "-";
                System.out.printf("%-6d %-25s %-15s %-15s%n", p.getId(), p.getNom(), dDeb, dFin);
            }
            System.out.println();
        } finally {
            session.close();
        }
    }
}
