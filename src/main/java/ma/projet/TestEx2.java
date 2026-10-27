package ma.projet;

import ma.projet.classes.Employe;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestEx2 {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("================================================================================");
        System.out.println("   EXERCICE 2 : APPLICATION DE GESTION DE PROJETS");
        System.out.println("================================================================================");

        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService employeTacheService = new EmployeTacheService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // 1. Creation des employes
        Employe emp1 = new Employe("ALAMI", "Ahmed", "0661234567");
        Employe emp2 = new Employe("BENJELLOUN", "Karim", "0662345678");
        Employe emp3 = new Employe("CHRAIBI", "Sara", "0663456789");

        employeService.create(emp1);
        employeService.create(emp2);
        employeService.create(emp3);

        // 2. Creation d'un projet gere par emp1 (Chef de projet)
        Date debProjet = null;
        Date finProjet = null;
        Date d1Deb = null, d1Fin = null;
        Date d2Deb = null, d2Fin = null;
        Date d3Deb = null, d3Fin = null;
        try {
            debProjet = sdf.parse("14/01/2013");
            finProjet = sdf.parse("30/06/2013");

            d1Deb = sdf.parse("10/02/2013");
            d1Fin = sdf.parse("20/02/2013");

            d2Deb = sdf.parse("10/03/2013");
            d2Fin = sdf.parse("15/03/2013");

            d3Deb = sdf.parse("10/04/2013");
            d3Fin = sdf.parse("25/04/2013");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Projet projet = new Projet("Gestion de stock", debProjet, finProjet, emp1);
        projetService.create(projet);

        // 3. Creation des taches conformes a l'enonce
        Tache t1 = new Tache("Analyse", d1Deb, d1Fin, 1200.0, projet);
        Tache t2 = new Tache("Conception", d2Deb, d2Fin, 1500.0, projet);
        Tache t3 = new Tache("Developpement", d3Deb, d3Fin, 4500.0, projet);
        Tache t4 = new Tache("Documentation", d1Deb, d2Fin, 800.0, projet); // <= 1000 DH

        tacheService.create(t1);
        tacheService.create(t2);
        tacheService.create(t3);
        tacheService.create(t4);

        // 4. Affectation des taches aux employes avec dates reelles
        EmployeTache et1 = new EmployeTache(emp1, t1, d1Deb, d1Fin);
        EmployeTache et2 = new EmployeTache(emp2, t2, d2Deb, d2Fin);
        EmployeTache et3 = new EmployeTache(emp3, t3, d3Deb, d3Fin);

        employeTacheService.create(et1);
        employeTacheService.create(et2);
        employeTacheService.create(et3);

        // Test 2.1 : Taches realisees par un employe
        System.out.println("\n[Test 2.1] Liste des taches realisees par l'employe (ALAMI Ahmed) :");
        employeService.afficherTachesRealisees(emp1);

        // Test 2.2 : Projets geres par un employe
        System.out.println("[Test 2.2] Projets geres par l'employe (ALAMI Ahmed) :");
        employeService.afficherProjetsGeres(emp1);

        // Test 2.3 : Taches planifiees pour un projet
        System.out.println("[Test 2.3] Taches planifiees pour le projet :");
        projetService.afficherTachesPlanifiees(projet);

        // Test 2.4 : Taches realisees pour un projet avec dates reelles (Format attendu)
        System.out.println("[Test 2.4] Taches realisees pour le projet (Format attendu du TP) :");
        projetService.afficherTachesRealisees(projet);

        // Test 2.5 : Taches avec prix > 1000 DH (Requete Nommee)
        System.out.println("[Test 2.5] Requete Nommee : Taches dont le prix > 1000 DH :");
        tacheService.afficherTachesPrixSuperieur1000();

        // Test 2.6 : Taches realisees entre deux dates
        try {
            Date start = sdf.parse("01/02/2013");
            Date end = sdf.parse("31/03/2013");
            System.out.println("[Test 2.6] Taches realisees entre le 01/02/2013 et le 31/03/2013 :");
            tacheService.afficherTachesRealiseesEntre(start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
