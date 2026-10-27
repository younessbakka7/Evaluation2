package ma.projet;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestEx1 {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("================================================================================");
        System.out.println("   EXERCICE 1 : APPLICATION DE GESTION DE STOCK");
        System.out.println("================================================================================");

        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneCommandeService = new LigneCommandeService();

        // 1. Creation des categories
        Categorie catInfo = new Categorie("INFO", "Informatique");
        Categorie catBur = new Categorie("BUR", "Bureautique");
        Categorie catTel = new Categorie("TEL", "Telephonie");

        categorieService.create(catInfo);
        categorieService.create(catBur);
        categorieService.create(catTel);

        // 2. Creation des produits (avec les references de l'exemple du lab : ES12, ZR85, EE85...)
        Produit p1 = new Produit("ES12", 120.0f, catInfo);
        Produit p2 = new Produit("ZR85", 100.0f, catBur);
        Produit p3 = new Produit("EE85", 200.0f, catInfo);
        Produit p4 = new Produit("CL01", 80.0f, catBur);
        Produit p5 = new Produit("PH99", 950.0f, catTel);

        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);
        produitService.create(p4);
        produitService.create(p5);

        // 3. Creation des commandes avec dates precises
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse("14/03/2013");
            date2 = sdf.parse("20/05/2013");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Commande c1 = new Commande(date1 != null ? date1 : new Date());
        Commande c2 = new Commande(date2 != null ? date2 : new Date());
        commandeService.create(c1);
        commandeService.create(c2);

        // 4. Lignes de commandes (Exactement comme dans l'exemple du TP)
        LigneCommandeProduit l1 = new LigneCommandeProduit(p1, c1, 7);
        LigneCommandeProduit l2 = new LigneCommandeProduit(p2, c1, 14);
        LigneCommandeProduit l3 = new LigneCommandeProduit(p3, c1, 5);
        LigneCommandeProduit l4 = new LigneCommandeProduit(p5, c2, 2);

        ligneCommandeService.create(l1);
        ligneCommandeService.create(l2);
        ligneCommandeService.create(l3);
        ligneCommandeService.create(l4);

        // Test 1 : Afficher les produits par categorie
        System.out.println("\n[Test 1.1] Liste des produits par categorie (Informatique) :");
        produitService.afficherProduitsParCategorie(catInfo);

        // Test 2 : Afficher les produits commandes entre deux dates
        try {
            Date start = sdf.parse("01/01/2013");
            Date end = sdf.parse("31/12/2013");
            System.out.println("[Test 1.2] Produits commandes entre le 01/01/2013 et le 31/12/2013 :");
            produitService.afficherProduitsEntreDeuxDates(start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Test 3 : Afficher les produits commandes dans une commande donnee (Format attendu)
        System.out.println("[Test 1.3] Produits d'une commande donnee (Affichage conforme au format du sujet) :");
        produitService.afficherProduitsParCommande(c1);

        // Test 4 : Afficher les produits dont le prix est superieur a 100 DH (Requete Nommee)
        System.out.println("[Test 1.4] Requete Nommee : Produits avec un prix > 100 DH :");
        produitService.afficherProduitsPrixSuperieur100();
    }
}
