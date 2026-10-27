package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class MariageService implements IDao<Mariage> {

    @Override
    public boolean create(Mariage o) {
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
    public boolean update(Mariage o) {
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
    public boolean delete(Mariage o) {
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
    public Mariage findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Mariage.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Mariage> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from Mariage", Mariage.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * Methode utilisant l'API Criteria pour afficher le nombre d'hommes maries a quatre femmes entre deux dates
     */
    public long countHommesMariesQuatreFemmesEntreDates(Date d1, Date d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Homme> cq = cb.createQuery(Homme.class);
            Root<Mariage> root = cq.from(Mariage.class);

            Predicate datePredicate = cb.between(root.get("dateDebut"), d1, d2);

            cq.select(root.get("homme"))
              .where(datePredicate)
              .groupBy(root.get("homme"))
              .having(cb.ge(cb.countDistinct(root.get("femme")), 4L));

            List<Homme> hommes = session.createQuery(cq).getResultList();
            return hommes.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
    }
}
