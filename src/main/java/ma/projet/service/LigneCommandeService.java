package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {
    @Override
    public boolean create(LigneCommandeProduit o) {
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
    public boolean update(LigneCommandeProduit o) {
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
    public boolean delete(LigneCommandeProduit o) {
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
    public LigneCommandeProduit findById(int id) {
        // Pour une cle composite, on utilise la PK, mais l'interface impose un id int
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(LigneCommandeProduit.class, id);
        } catch (Exception e) {
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from LigneCommandeProduit", LigneCommandeProduit.class).list();
        } finally {
            session.close();
        }
    }
}
