package ma.projet.service;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ProjetService implements IDao<Projet> {

    @Override
    public boolean create(Projet o) {
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
    public boolean update(Projet o) {
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
    public boolean delete(Projet o) {
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
    public Projet findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Projet.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Projet> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from Projet", Projet.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * 1. Afficher la liste des taches planifiees pour un projet
     */
    public void afficherTachesPlanifiees(Projet p) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String hql = "FROM Tache t WHERE t.projet = :p";
            Query<Tache> query = session.createQuery(hql, Tache.class);
            query.setParameter("p", p);
            List<Tache> taches = query.list();

            System.out.println("----------------------------------------------------------------");
            System.out.println("Taches planifiees pour le projet : " + p.getNom() + " (ID: " + p.getId() + ")");
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-6s %-20s %-15s %-15s %-12s%n", "Num", "Nom", "Date Debut", "Date Fin", "Prix");
            for (Tache t : taches) {
                String dDeb = t.getDateDebut() != null ? sdf.format(t.getDateDebut()) : "-";
                String dFin = t.getDateFin() != null ? sdf.format(t.getDateFin()) : "-";
                System.out.printf("%-6d %-20s %-15s %-15s %-12s%n",
                        t.getId(), t.getNom(), dDeb, dFin, String.format("%.2f DH", t.getPrix()));
            }
            System.out.println();
        } finally {
            session.close();
        }
    }

    /**
     * 2. Afficher la liste des taches realisees avec les dates reelles
     * Format attendu:
     * Projet : 4      Nom : Gestion de stock     Date debut : 14 Janvier 2013
     * Liste des taches:
     * Num Nom            Date Debut Reelle   Date Fin Reelle
     * 12  Analyse        10/02/2013          20/02/2013
     * 13  Conception     10/03/2013          15/03/2013
     * 14  Developpement  10/04/2013          25/04/2013
     */
    public void afficherTachesRealisees(Projet p) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat sdfFrench = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String hql = "FROM EmployeTache et WHERE et.tache.projet = :p ORDER BY et.tache.id ASC";
            Query<EmployeTache> query = session.createQuery(hql, EmployeTache.class);
            query.setParameter("p", p);
            List<EmployeTache> list = query.list();

            String dateDebutStr = p.getDateDebut() != null ? sdfFrench.format(p.getDateDebut()) : "-";
            System.out.println("Projet : " + p.getId() + "      Nom : " + p.getNom() + "     Date debut : " + dateDebutStr);
            System.out.println("Liste des taches:");
            System.out.printf("%-4s %-15s %-20s %-15s%n", "Num", "Nom", "Date Debut Reelle", "Date Fin Reelle");
            for (EmployeTache et : list) {
                String dDebR = et.getDateDebutReelle() != null ? sdfDate.format(et.getDateDebutReelle()) : "N/A";
                String dFinR = et.getDateFinReelle() != null ? sdfDate.format(et.getDateFinReelle()) : "En cours";
                System.out.printf("%-4d %-15s %-20s %-15s%n",
                        et.getTache().getId(),
                        et.getTache().getNom(),
                        dDebR,
                        dFinR);
            }
            System.out.println();
        } finally {
            session.close();
        }
    }
}
