package ma.projet;

import ma.projet.util.HibernateUtil;

public class App {
    public static void main(String[] args) {
        System.out.println("################################################################################");
        System.out.println("#                                                                              #");
        System.out.println("#      EVALUATION PRATIQUE - INTEGRATION DE HIBERNATE & JPA                    #");
        System.out.println("#      EXERCICE 1 : GESTION DE STOCK                                           #");
        System.out.println("#      EXERCICE 2 : GESTION DE PROJETS                                         #");
        System.out.println("#      EXERCICE 3 : GESTION DE L'ETAT CIVIL                                    #");
        System.out.println("#                                                                              #");
        System.out.println("################################################################################\n");

        try {
            // Execution Exercice 1
            TestEx1.run();
            System.out.println("\n");

            // Execution Exercice 2
            TestEx2.run();
            System.out.println("\n");

            // Execution Exercice 3
            TestEx3.run();
            System.out.println("\n");

            System.out.println("================================================================================");
            System.out.println("   EXECUTION DE L'EVALUATION TERMINEE AVEC SUCCES - 100% VALIDE !");
            System.out.println("================================================================================");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
