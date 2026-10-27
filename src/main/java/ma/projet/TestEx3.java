package ma.projet;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestEx3 {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("================================================================================");
        System.out.println("   EXERCICE 3 : APPLICATION DE GESTION DE L'ETAT CIVIL");
        System.out.println("================================================================================");

        HommeService hommeService = new HommeService();
        FemmeService femmeService = new FemmeService();
        MariageService mariageService = new MariageService();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // -------------------------------------------------------------------------
        // 1. Creation de 10 femmes et 5 hommes
        // -------------------------------------------------------------------------
        System.out.println("\n[Etape 1] Creation de 10 femmes et 5 hommes dans la base de donnees...");
        try {
            // Hommes (avec SAFI Said comme dans le sujet)
            Homme h1 = new Homme("SAFI", "Said", "0661112233", "Casablanca, Maarif", sdf.parse("15/05/1965"));
            Homme h2 = new Homme("BENALI", "Omar", "0662223344", "Rabat, Agdal", sdf.parse("20/08/1970"));
            Homme h3 = new Homme("TAZI", "Khalid", "0663334455", "Fes, Ville Nouvelle", sdf.parse("10/12/1975"));
            Homme h4 = new Homme("IDRISSI", "Hassan", "0664445566", "Marrakech, Gueliz", sdf.parse("05/03/1980"));
            Homme h5 = new Homme("EL AMRANI", "Youssef", "0665556677", "Tanger, Malabata", sdf.parse("25/09/1982"));

            hommeService.create(h1);
            hommeService.create(h2);
            hommeService.create(h3);
            hommeService.create(h4);
            hommeService.create(h5);

            // 10 Femmes (avec SALIMA Rami, AMAL Ali, WAFA Alaoui, KARIMA Alami comme dans le sujet)
            Femme f1 = new Femme("SALIMA", "Rami", "0671111111", "Casablanca", sdf.parse("12/04/1970"));
            Femme f2 = new Femme("AMAL", "Ali", "0672222222", "Casablanca", sdf.parse("25/08/1972"));
            Femme f3 = new Femme("WAFA", "Alaoui", "0673333333", "Rabat", sdf.parse("10/11/1975"));
            Femme f4 = new Femme("KARIMA", "Alami", "0674444444", "Casablanca", sdf.parse("05/01/1968"));
            Femme f5 = new Femme("FATIMA", "Zohra", "0675555555", "Fes", sdf.parse("01/01/1958")); // La plus agee
            Femme f6 = new Femme("HOUDA", "Mansouri", "0676666666", "Marrakech", sdf.parse("14/02/1980"));
            Femme f7 = new Femme("MERIEM", "Tazi", "0677777777", "Tanger", sdf.parse("20/09/1982"));
            Femme f8 = new Femme("SAMIRA", "Idrissi", "0678888888", "Meknes", sdf.parse("18/06/1985"));
            Femme f9 = new Femme("NAOUAL", "Bennani", "0679999999", "Agadir", sdf.parse("30/12/1988"));
            Femme f10 = new Femme("LATIFA", "Berrada", "0670000000", "Kenitra", sdf.parse("04/07/1992"));

            femmeService.create(f1);
            femmeService.create(f2);
            femmeService.create(f3);
            femmeService.create(f4);
            femmeService.create(f5);
            femmeService.create(f6);
            femmeService.create(f7);
            femmeService.create(f8);
            femmeService.create(f9);
            femmeService.create(f10);

            // Mariages pour SAFI Said (Conforme a l'exemple du sujet) :
            // 1. Mariage echoue : KARIMA ALAMI (03/09/1989 -> 03/09/1990, 0 enfant)
            Mariage m1 = new Mariage(h1, f4, sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0);
            // 2. Mariage en cours : SALIMA RAMI (03/09/1990 -> null, 4 enfants)
            Mariage m2 = new Mariage(h1, f1, sdf.parse("03/09/1990"), null, 4);
            // 3. Mariage en cours : AMAL ALI (03/09/1995 -> null, 2 enfants)
            Mariage m3 = new Mariage(h1, f2, sdf.parse("03/09/1995"), null, 2);
            // 4. Mariage en cours : WAFA ALAOUI (04/11/2000 -> null, 3 enfants)
            Mariage m4 = new Mariage(h1, f3, sdf.parse("04/11/2000"), null, 3);

            // Mariage supplementaire pour KARIMA ALAMI pour tester les femmes mariees au moins deux fois
            Mariage m5 = new Mariage(h2, f4, sdf.parse("15/06/1993"), null, 2);

            // Mariage pour BENALI Omar et FATIMA Zohra
            Mariage m6 = new Mariage(h2, f5, sdf.parse("01/01/1985"), sdf.parse("01/01/1992"), 1);

            // Mariages pour d'autres hommes
            Mariage m7 = new Mariage(h3, f6, sdf.parse("10/05/2005"), null, 2);
            Mariage m8 = new Mariage(h4, f7, sdf.parse("20/07/2010"), null, 1);

            mariageService.create(m1);
            mariageService.create(m2);
            mariageService.create(m3);
            mariageService.create(m4);
            mariageService.create(m5);
            mariageService.create(m6);
            mariageService.create(m7);
            mariageService.create(m8);

            System.out.println("-> 5 Hommes, 10 Femmes et 8 Mariages crees avec succes !");

            // -------------------------------------------------------------------------
            // 2. Afficher la liste des femmes
            // -------------------------------------------------------------------------
            System.out.println("\n[Etape 2] Affichage de la liste des femmes :");
            femmeService.afficherToutesLesFemmes();

            // -------------------------------------------------------------------------
            // 3. Afficher la femme la plus agee
            // -------------------------------------------------------------------------
            System.out.println("[Etape 3] Recherche de la femme la plus agee :");
            Femme plusAgee = femmeService.findFemmePlusAgee();
            if (plusAgee != null) {
                System.out.println("-> Femme la plus agee : " + plusAgee.getNom() + " " + plusAgee.getPrenom()
                        + " (Nee le " + sdf.format(plusAgee.getDateNaissance()) + ") - " + plusAgee.getAdresse());
            }
            System.out.println();

            // -------------------------------------------------------------------------
            // 4. Afficher les epouses d'un homme donne
            // -------------------------------------------------------------------------
            System.out.println("[Etape 4] Epouses de SAFI Said entre le 01/01/1988 et le 31/12/2005 :");
            hommeService.afficherEpousesEntreDates(h1, sdf.parse("01/01/1988"), sdf.parse("31/12/2005"));

            // -------------------------------------------------------------------------
            // 5. Afficher le nombre d'enfants d'une femme entre deux dates (Requete Native Nommee)
            // -------------------------------------------------------------------------
            System.out.println("[Etape 5] Requete Native Nommee : Nombre d'enfants de SALIMA Rami entre 1990 et 2010 :");
            int nbrEnfants = femmeService.countEnfantsEntreDates(f1, sdf.parse("01/01/1990"), sdf.parse("31/12/2010"));
            System.out.println("-> Nombre d'enfants de SALIMA Rami : " + nbrEnfants);
            System.out.println();

            // -------------------------------------------------------------------------
            // 6. Afficher les femmes mariees deux fois ou plus (Requete Nommee)
            // -------------------------------------------------------------------------
            System.out.println("[Etape 6] Requete Nommee : Femmes mariees au moins deux fois :");
            List<Femme> multMaries = femmeService.findFemmesMarieesAuMoinsDeuxFois();
            for (Femme f : multMaries) {
                System.out.println("  * " + f.getNom() + " " + f.getPrenom() + " (Tel: " + f.getTelephone() + ")");
            }
            System.out.println();

            // -------------------------------------------------------------------------
            // 7. Afficher les hommes maries a quatre femmes entre deux dates (Criteria API)
            // -------------------------------------------------------------------------
            System.out.println("[Etape 7] Criteria API : Nombre d'hommes maries a quatre femmes entre 1985 et 2015 :");
            long count4 = mariageService.countHommesMariesQuatreFemmesEntreDates(sdf.parse("01/01/1985"), sdf.parse("31/12/2015"));
            System.out.println("-> Nombre d'hommes maries a 4 femmes : " + count4);
            System.out.println();

            // -------------------------------------------------------------------------
            // 8. Afficher les mariages d'un homme avec tous les details (Format attendu du TP)
            // -------------------------------------------------------------------------
            System.out.println("[Etape 8] Mariages d'un homme avec tous les details (Conforme au format du sujet) :");
            hommeService.afficherMariagesHomme(h1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
