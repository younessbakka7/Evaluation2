package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
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
    public boolean update(Produit o) {
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
    public boolean delete(Produit o) {
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
    public Produit findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Produit.class, id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Produit> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from Produit", Produit.class).list();
        } finally {
            session.close();
        }
    }

    /**
     * 1. Afficher la liste des produits par categorie
     */
    public void afficherProduitsParCategorie(Categorie c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Produit> query = session.createQuery("FROM Produit p WHERE p.categorie = :c", Produit.class);
            query.setParameter("c", c);
            List<Produit> produits = query.list();

            System.out.println("----------------------------------------------------------------");
            System.out.println("Produits de la categorie : " + c.getLibelle() + " (" + c.getCode() + ")");
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-10s %-25s %-15s%n", "ID", "Reference", "Prix");
            for (Produit p : produits) {
                System.out.printf("%-10d %-25s %-15s%n", p.getId(), p.getReference(), String.format("%.2f DH", p.getPrix()));
            }
            System.out.println();
        } finally {
            session.close();
        }
    }

    /**
     * 2. Afficher les produits commandes entre deux dates
     */
    public void afficherProduitsEntreDeuxDates(Date d1, Date d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            String hql = "SELECT DISTINCT p FROM LigneCommandeProduit lcp " +
                         "JOIN lcp.produit p " +
                         "JOIN lcp.commande c " +
                         "WHERE c.date BETWEEN :d1 AND :d2";
            Query<Produit> query = session.createQuery(hql, Produit.class);
            query.setParameter("d1", d1);
            query.setParameter("d2", d2);
            List<Produit> produits = query.list();

            System.out.println("----------------------------------------------------------------");
            System.out.println("Produits commandes entre le " + sdf.format(d1) + " et le " + sdf.format(d2) + " :");
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-10s %-25s %-15s %-20s%n", "ID", "Reference", "Prix", "Categorie");
            for (Produit p : produits) {
                System.out.printf("%-10d %-25s %-15s %-20s%n",
                        p.getId(), p.getReference(), String.format("%.2f DH", p.getPrix()),
                        p.getCategorie() != null ? p.getCategorie().getLibelle() : "");
            }
            System.out.println();
        } finally {
            session.close();
        }
    }

    /**
     * 3. Afficher les produits commandes dans une commande donnee
     * Format attendu:
     * Commande : 4     Date : 14 Mars 2013
     * Liste des produits :
     * Reference   Prix    Quantite
     * ES12        120 DH  7
     * ZR85        100 DH  14
     * EE85        200 DH  5
     */
    public void afficherProduitsParCommande(Commande commande) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
        try {
            String hql = "FROM LigneCommandeProduit lcp WHERE lcp.commande = :cmd";
            Query<LigneCommandeProduit> query = session.createQuery(hql, LigneCommandeProduit.class);
            query.setParameter("cmd", commande);
            List<LigneCommandeProduit> lignes = query.list();

            System.out.println("Commande : " + commande.getId() + "     Date : " + sdf.format(commande.getDate()));
            System.out.println("Liste des produits :");
            System.out.printf("%-12s %-10s %-10s%n", "Reference", "Prix", "Quantite");
            for (LigneCommandeProduit l : lignes) {
                System.out.printf("%-12s %-10s %-10d%n",
                        l.getProduit().getReference(),
                        String.format("%.0f DH", l.getProduit().getPrix()),
                        l.getQuantite());
            }
            System.out.println();
        } finally {
            session.close();
        }
    }

    /**
     * 4. Afficher la liste des produits dont le prix est superieur a 100 DH (Requete Nommee)
     */
    public void afficherProduitsPrixSuperieur100() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Produit> query = session.createNamedQuery("findProduitsPrixSuperieur100", Produit.class);
            List<Produit> produits = query.list();

            System.out.println("----------------------------------------------------------------");
            System.out.println("Produits dont le prix est superieur a 100 DH (Requete Nommee) :");
            System.out.println("----------------------------------------------------------------");
            System.out.printf("%-10s %-25s %-15s %-20s%n", "ID", "Reference", "Prix", "Categorie");
            for (Produit p : produits) {
                System.out.printf("%-10d %-25s %-15s %-20s%n",
                        p.getId(), p.getReference(), String.format("%.2f DH", p.getPrix()),
                        p.getCategorie() != null ? p.getCategorie().getLibelle() : "");
            }
            System.out.println();
        } finally {
            session.close();
        }
    }
}
