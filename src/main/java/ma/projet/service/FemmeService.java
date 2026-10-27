package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme> {

    @Override
    public boolean create(Femme o) {
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
    public boolean update(Femme o) {
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
    public boolean delete(Femme o) {
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
    public Femme findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Femme.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Femme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from Femme", Femme.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * 1. Requete Native Nommee retournant le nombre d'enfants d'une femme entre deux dates
     */
    public int countEnfantsEntreDates(Femme f, Date d1, Date d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<?> query = session.createNamedQuery("Femme.countEnfantsBetweenDates");
            query.setParameter("femmeId", f.getId());
            query.setParameter("d1", d1);
            query.setParameter("d2", d2);
            Object result = query.getSingleResult();
            if (result instanceof Number) {
                return ((Number) result).intValue();
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }

    /**
     * 2. Requete Nommee retournant les femmes mariees au moins deux fois
     */
    public List<Femme> findFemmesMarieesAuMoinsDeuxFois() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createNamedQuery("Femme.findFemmesMarieesAuMoinsDeuxFois", Femme.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    /**
     * 3. Trouver la femme la plus agee
     */
    public Femme findFemmePlusAgee() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createQuery("FROM Femme f ORDER BY f.dateNaissance ASC", Femme.class);
            query.setMaxResults(1);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }

    /**
     * Affichage formatee de la liste des femmes
     */
    public void afficherToutesLesFemmes() {
        List<Femme> femmes = findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("----------------------------------------------------------------");
        System.out.println("Liste des femmes enregistrees dans l'etat civil :");
        System.out.println("----------------------------------------------------------------");
        System.out.printf("%-6s %-25s %-15s %-15s %-25s%n", "ID", "Nom & Prenom", "Telephone", "Date Naiss.", "Adresse");
        for (Femme f : femmes) {
            String dn = f.getDateNaissance() != null ? sdf.format(f.getDateNaissance()) : "-";
            System.out.printf("%-6d %-25s %-15s %-15s %-25s%n",
                    f.getId(), f.getNom() + " " + f.getPrenom(), f.getTelephone(), dn, f.getAdresse());
        }
        System.out.println();
    }
}
