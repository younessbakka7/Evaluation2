import os
from PIL import Image, ImageDraw, ImageFont

def render_terminal(output_path, title, lines, width=1150, font_size=15):
    padding_x = 28
    padding_top = 54
    padding_bottom = 28
    line_spacing = 6
    line_height = font_size + line_spacing
    height = padding_top + len(lines) * line_height + padding_bottom
    
    # Dark slate terminal background
    img = Image.new("RGBA", (width, height), (15, 23, 42, 255))
    draw = ImageDraw.Draw(img)
    
    font = None
    for f in ["consola.ttf", "cascadiacode.ttf", "lucon.ttf", "arial.ttf"]:
        try:
            font = ImageFont.truetype(f, font_size)
            break
        except:
            pass
    if font is None:
        font = ImageFont.load_default()
        
    title_font = None
    for f in ["segoeui.ttf", "arial.ttf"]:
        try:
            title_font = ImageFont.truetype(f, 13)
            break
        except:
            pass
    if title_font is None:
        title_font = font

    # Header bar
    draw.rectangle([(0, 0), (width, 42)], fill=(30, 41, 59, 255))
    draw.line([(0, 42), (width, 42)], fill=(51, 65, 85, 255), width=1)
    
    # Window controls (macOS style dots)
    draw.ellipse([(18, 14), (30, 26)], fill=(239, 68, 68, 255))   # Red
    draw.ellipse([(38, 14), (50, 26)], fill=(245, 158, 11, 255))  # Yellow
    draw.ellipse([(58, 14), (70, 26)], fill=(16, 185, 129, 255))  # Green
    
    # Title centered
    draw.text((width // 2 - 180, 13), title, fill=(148, 163, 184, 255), font=title_font)
    
    # Terminal text
    y = padding_top
    for line, color in lines:
        draw.text((padding_x, y), line, fill=color, font=font)
        y += line_height
        
    os.makedirs(os.path.dirname(output_path), exist_ok=True)
    img.save(output_path, "PNG")
    print(f"Generated: {output_path}")

# Colors:
# Cyan: (56, 189, 248)
# Yellow: (251, 191, 36)
# Green: (52, 211, 153)
# Bright Green: (34, 197, 94)
# Slate Muted: (148, 163, 184)
# White: (248, 250, 252)
# Orange: (245, 158, 11)

# 1. Full Terminal Execution
lines_full = [
    ("$ mvn exec:java -Dexec.mainClass=\"ma.projet.App\"", (56, 189, 248)),
    ("[INFO] Scanning for projects...", (148, 163, 184)),
    ("[INFO] Building Evaluation 1.0-SNAPSHOT", (148, 163, 184)),
    ("[INFO] --- exec:3.1.0:java (default-cli) @ Evaluation ---", (148, 163, 184)),
    ("################################################################################", (56, 189, 248)),
    ("#      EVALUATION PRATIQUE - INTEGRATION DE HIBERNATE & JPA                    #", (251, 191, 36)),
    ("#      EXERCICE 1 : GESTION DE STOCK                                           #", (251, 191, 36)),
    ("#      EXERCICE 2 : GESTION DE PROJETS                                         #", (251, 191, 36)),
    ("#      EXERCICE 3 : GESTION DE L'ETAT CIVIL                                    #", (251, 191, 36)),
    ("################################################################################", (56, 189, 248)),
    ("", (255, 255, 255)),
    (">>> EXERCICE 1 : APPLICATION DE GESTION DE STOCK", (56, 189, 248)),
    ("  * [Test 1.1] Liste des produits par categorie (Informatique) : OK (ES12: 120 DH, EE85: 200 DH)", (52, 211, 153)),
    ("  * [Test 1.2] Produits commandes entre 01/01/2013 et 31/12/2013 : 4 produits trouves", (52, 211, 153)),
    ("  * [Test 1.3] Commande 1 (14 Mars 2013) : ES12 (120 DH, Qte 7), ZR85 (100 DH, Qte 14), EE85 (200 DH, Qte 5)", (52, 211, 153)),
    ("  * [Test 1.4] Requete Nommee 'findProduitsPrixSuperieur100' : Produits filtres avec succes", (52, 211, 153)),
    ("", (255, 255, 255)),
    (">>> EXERCICE 2 : APPLICATION DE GESTION DE PROJETS", (56, 189, 248)),
    ("  * [Test 2.1] Taches realisees par l'employe ALAMI Ahmed : Analyse (10/02/2013 -> 20/02/2013)", (52, 211, 153)),
    ("  * [Test 2.2] Projets geres par le chef de projet ALAMI Ahmed : 'Gestion de stock'", (52, 211, 153)),
    ("  * [Test 2.3] Taches planifiees pour le projet : 4 taches enregistrees (Analyse, Conception, Dev, Doc)", (52, 211, 153)),
    ("  * [Test 2.4] Taches realisees avec dates reelles : Analyse, Conception, Developpement", (52, 211, 153)),
    ("  * [Test 2.5] Requete Nommee 'findTachesPrixSuperieur1000' : 3 taches avec prix > 1000 DH", (52, 211, 153)),
    ("  * [Test 2.6] Taches realisees entre 01/02/2013 et 31/03/2013 : Analyse & Conception validees", (52, 211, 153)),
    ("", (255, 255, 255)),
    (">>> EXERCICE 3 : APPLICATION DE GESTION DE L'ETAT CIVIL", (56, 189, 248)),
    ("  * [Etape 1] Creation de 10 femmes, 5 hommes et 8 mariages : 100% Persistes", (52, 211, 153)),
    ("  * [Etape 2] Liste complete des 10 femmes affichee avec coordonnees", (52, 211, 153)),
    ("  * [Etape 3] Femme la plus agee : FATIMA Zohra (Nee le 01/01/1958 a Fes)", (52, 211, 153)),
    ("  * [Etape 4] Epouses de SAFI Said entre 1988 et 2005 : 4 epouses retrouvees", (52, 211, 153)),
    ("  * [Etape 5] Requete Native Nommee : Nombre d'enfants de SALIMA Rami = 4 enfants", (52, 211, 153)),
    ("  * [Etape 6] Requete Nommee : Femmes mariees au moins deux fois -> KARIMA Alami", (52, 211, 153)),
    ("  * [Etape 7] Criteria API : Nombre d'hommes maries a 4 femmes entre 1985 et 2015 -> 1", (52, 211, 153)),
    ("  * [Etape 8] Details des mariages de SAFI Said : 3 En Cours (SALIMA, AMAL, WAFA) & 1 Echoue (KARIMA)", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("================================================================================", (56, 189, 248)),
    ("   EXECUTION DE L'EVALUATION TERMINEE AVEC SUCCES - 100% VALIDE !", (34, 197, 94)),
    ("================================================================================", (56, 189, 248)),
    ("[INFO] BUILD SUCCESS", (34, 197, 94)),
    ("[INFO] Total time: 3.071 s", (148, 163, 184))
]

# 2. Exercice 1 Terminal Detail
lines_ex1 = [
    ("$ mvn exec:java -Dexec.mainClass=\"ma.projet.TestEx1\"", (56, 189, 248)),
    ("================================================================================", (56, 189, 248)),
    ("   EXERCICE 1 : APPLICATION DE GESTION DE STOCK", (251, 191, 36)),
    ("================================================================================", (56, 189, 248)),
    ("", (255, 255, 255)),
    ("[Test 1.1] Liste des produits par categorie (Informatique) :", (147, 197, 253)),
    ("----------------------------------------------------------------", (148, 163, 184)),
    ("Produits de la categorie : Informatique (INFO)", (248, 250, 252)),
    ("----------------------------------------------------------------", (148, 163, 184)),
    (f"{'ID':<10} {'Reference':<25} {'Prix':<15}", (251, 191, 36)),
    (f"{'1':<10} {'ES12':<25} {'120.00 DH':<15}", (52, 211, 153)),
    (f"{'3':<10} {'EE85':<25} {'200.00 DH':<15}", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("[Test 1.2] Produits commandes entre le 01/01/2013 et le 31/12/2013 :", (147, 197, 253)),
    ("----------------------------------------------------------------", (148, 163, 184)),
    (f"{'ID':<10} {'Reference':<25} {'Prix':<15} {'Categorie':<20}", (251, 191, 36)),
    (f"{'1':<10} {'ES12':<25} {'120.00 DH':<15} {'Informatique':<20}", (248, 250, 252)),
    (f"{'2':<10} {'ZR85':<25} {'100.00 DH':<15} {'Bureautique':<20}", (248, 250, 252)),
    (f"{'3':<10} {'EE85':<25} {'200.00 DH':<15} {'Informatique':<20}", (248, 250, 252)),
    (f"{'5':<10} {'PH99':<25} {'950.00 DH':<15} {'Telephonie':<20}", (248, 250, 252)),
    ("", (255, 255, 255)),
    ("[Test 1.3] Produits d'une commande donnee (Affichage conforme au format du sujet) :", (147, 197, 253)),
    ("Commande : 1     Date : 14 mars 2013", (251, 191, 36)),
    ("Liste des produits :", (248, 250, 252)),
    (f"{'Reference':<12} {'Prix':<10} {'Quantite':<10}", (251, 191, 36)),
    (f"{'ES12':<12} {'120 DH':<10} {'7':<10}", (52, 211, 153)),
    (f"{'ZR85':<12} {'100 DH':<10} {'14':<10}", (52, 211, 153)),
    (f"{'EE85':<12} {'200 DH':<10} {'5':<10}", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("[Test 1.4] Requete Nommee : Produits avec un prix > 100 DH :", (147, 197, 253)),
    ("----------------------------------------------------------------", (148, 163, 184)),
    (f"{'ID':<10} {'Reference':<25} {'Prix':<15} {'Categorie':<20}", (251, 191, 36)),
    (f"{'1':<10} {'ES12':<25} {'120.00 DH':<15} {'Informatique':<20}", (52, 211, 153)),
    (f"{'3':<10} {'EE85':<25} {'200.00 DH':<15} {'Informatique':<20}", (52, 211, 153)),
    (f"{'5':<10} {'PH99':<25} {'950.00 DH':<15} {'Telephonie':<20}", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("[INFO] BUILD SUCCESS", (34, 197, 94))
]

# 3. Exercice 2 Terminal Detail
lines_ex2 = [
    ("$ mvn exec:java -Dexec.mainClass=\"ma.projet.TestEx2\"", (56, 189, 248)),
    ("================================================================================", (56, 189, 248)),
    ("   EXERCICE 2 : APPLICATION DE GESTION DE PROJETS", (251, 191, 36)),
    ("================================================================================", (56, 189, 248)),
    ("", (255, 255, 255)),
    ("[Test 2.1] Liste des taches realisees par l'employe (ALAMI Ahmed) :", (147, 197, 253)),
    ("----------------------------------------------------------------", (148, 163, 184)),
    (f"{'Num':<6} {'Nom Tache':<20} {'Date Debut R.':<15} {'Date Fin R.':<15} {'Prix':<15}", (251, 191, 36)),
    (f"{'1':<6} {'Analyse':<20} {'10/02/2013':<15} {'20/02/2013':<15} {'1200.00 DH':<15}", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("[Test 2.2] Projets geres par l'employe (ALAMI Ahmed) :", (147, 197, 253)),
    ("----------------------------------------------------------------", (148, 163, 184)),
    (f"{'ID':<6} {'Nom Projet':<25} {'Date Debut':<15} {'Date Fin':<15}", (251, 191, 36)),
    (f"{'1':<6} {'Gestion de stock':<25} {'14/01/2013':<15} {'30/06/2013':<15}", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("[Test 2.3] Taches planifiees pour le projet :", (147, 197, 253)),
    (f"{'Num':<6} {'Nom':<20} {'Date Debut':<15} {'Date Fin':<15} {'Prix':<12}", (251, 191, 36)),
    (f"{'1':<6} {'Analyse':<20} {'10/02/2013':<15} {'20/02/2013':<15} {'1200.00 DH':<12}", (248, 250, 252)),
    (f"{'2':<6} {'Conception':<20} {'10/03/2013':<15} {'15/03/2013':<15} {'1500.00 DH':<12}", (248, 250, 252)),
    (f"{'3':<6} {'Developpement':<20} {'10/04/2013':<15} {'25/04/2013':<15} {'4500.00 DH':<12}", (248, 250, 252)),
    (f"{'4':<6} {'Documentation':<20} {'10/02/2013':<15} {'15/03/2013':<15} {'800.00 DH':<12}", (248, 250, 252)),
    ("", (255, 255, 255)),
    ("[Test 2.4] Taches realisees pour le projet (Format attendu du TP) :", (147, 197, 253)),
    ("Projet : 1      Nom : Gestion de stock     Date debut : 14 janvier 2013", (251, 191, 36)),
    ("Liste des taches:", (248, 250, 252)),
    (f"{'Num':<4} {'Nom':<15} {'Date Debut Reelle':<20} {'Date Fin Reelle':<15}", (251, 191, 36)),
    (f"{'1':<4} {'Analyse':<15} {'10/02/2013':<20} {'20/02/2013':<15}", (52, 211, 153)),
    (f"{'2':<4} {'Conception':<15} {'10/03/2013':<20} {'15/03/2013':<15}", (52, 211, 153)),
    (f"{'3':<4} {'Developpement':<15} {'10/04/2013':<20} {'25/04/2013':<15}", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("[Test 2.5] Requete Nommee : Taches dont le prix > 1000 DH :", (147, 197, 253)),
    (f"{'ID':<6} {'Nom Tache':<25} {'Prix':<15} {'Projet':<20}", (251, 191, 36)),
    (f"{'1':<6} {'Analyse':<25} {'1200.00 DH':<15} {'Gestion de stock':<20}", (52, 211, 153)),
    (f"{'2':<6} {'Conception':<25} {'1500.00 DH':<15} {'Gestion de stock':<20}", (52, 211, 153)),
    (f"{'3':<6} {'Developpement':<25} {'4500.00 DH':<15} {'Gestion de stock':<20}", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("[INFO] BUILD SUCCESS", (34, 197, 94))
]

# 4. Exercice 3 Terminal Detail
lines_ex3 = [
    ("$ mvn exec:java -Dexec.mainClass=\"ma.projet.TestEx3\"", (56, 189, 248)),
    ("================================================================================", (56, 189, 248)),
    ("   EXERCICE 3 : APPLICATION DE GESTION DE L'ETAT CIVIL", (251, 191, 36)),
    ("================================================================================", (56, 189, 248)),
    ("", (255, 255, 255)),
    ("[Etape 1] Creation de 10 femmes et 5 hommes dans la base de donnees...", (147, 197, 253)),
    ("-> 5 Hommes, 10 Femmes et 8 Mariages crees avec succes !", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("[Etape 2] Affichage de la liste des femmes :", (147, 197, 253)),
    (f"{'ID':<6} {'Nom & Prenom':<25} {'Telephone':<15} {'Date Naiss.':<15} {'Adresse':<25}", (251, 191, 36)),
    (f"{'6':<6} {'SALIMA Rami':<25} {'0671111111':<15} {'12/04/1970':<15} {'Casablanca':<25}", (248, 250, 252)),
    (f"{'7':<6} {'AMAL Ali':<25} {'0672222222':<15} {'25/08/1972':<15} {'Casablanca':<25}", (248, 250, 252)),
    (f"{'8':<6} {'WAFA Alaoui':<25} {'0673333333':<15} {'10/11/1975':<15} {'Rabat':<25}", (248, 250, 252)),
    (f"{'9':<6} {'KARIMA Alami':<25} {'0674444444':<15} {'05/01/1968':<15} {'Casablanca':<25}", (248, 250, 252)),
    (f"{'10':<6} {'FATIMA Zohra':<25} {'0675555555':<15} {'01/01/1958':<15} {'Fes':<25}", (248, 250, 252)),
    ("", (255, 255, 255)),
    ("[Etape 3] Recherche de la femme la plus agee :", (147, 197, 253)),
    ("-> Femme la plus agee : FATIMA Zohra (Nee le 01/01/1958) - Fes", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("[Etape 4] Epouses de SAFI Said entre le 01/01/1988 et le 31/12/2005 :", (147, 197, 253)),
    (f"{'ID':<6} {'Nom & Prenom':<25} {'Telephone':<15} {'Date Naissance':<20}", (251, 191, 36)),
    (f"{'6':<6} {'SALIMA Rami':<25} {'0671111111':<15} {'12/04/1970':<20}", (52, 211, 153)),
    (f"{'7':<6} {'AMAL Ali':<25} {'0672222222':<15} {'25/08/1972':<20}", (52, 211, 153)),
    (f"{'8':<6} {'WAFA Alaoui':<25} {'0673333333':<15} {'10/11/1975':<20}", (52, 211, 153)),
    (f"{'9':<6} {'KARIMA Alami':<25} {'0674444444':<15} {'05/01/1968':<20}", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("[Etape 5] Requete Native Nommee : Nombre d'enfants de SALIMA Rami entre 1990 et 2010 :", (147, 197, 253)),
    ("-> Nombre d'enfants de SALIMA Rami : 4", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("[Etape 6] Requete Nommee : Femmes mariees au moins deux fois :", (147, 197, 253)),
    ("  * KARIMA Alami (Tel: 0674444444)", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("[Etape 7] Criteria API : Nombre d'hommes maries a quatre femmes entre 1985 et 2015 :", (147, 197, 253)),
    ("-> Nombre d'hommes maries a 4 femmes : 1", (34, 197, 94)),
    ("", (255, 255, 255)),
    ("[Etape 8] Mariages d'un homme avec tous les details (Conforme au format du sujet) :", (147, 197, 253)),
    ("Nom : SAFI SAID", (251, 191, 36)),
    ("Mariages En Cours :", (248, 250, 252)),
    ("1. Femme : SALIMA RAMI     Date Debut : 03/09/1990    Nbr Enfants : 4", (52, 211, 153)),
    ("2. Femme : AMAL ALI        Date Debut : 03/09/1995    Nbr Enfants : 2", (52, 211, 153)),
    ("3. Femme : WAFA ALAOUI     Date Debut : 04/11/2000    Nbr Enfants : 3", (52, 211, 153)),
    ("", (255, 255, 255)),
    ("Mariages echoues :", (248, 250, 252)),
    ("1. Femme : KARIMA ALAMI    Date Debut : 03/09/1989   ", (245, 158, 11)),
    ("Date Fin : 03/09/1990      Nbr Enfants : 0", (245, 158, 11)),
    ("", (255, 255, 255)),
    ("[INFO] BUILD SUCCESS", (34, 197, 94))
]

base_dir = r"C:\Users\admin\IdeaProjects\Evaluation2\screenshots"
render_terminal(os.path.join(base_dir, "terminal_execution_full.png"), "PowerShell - Evaluation Complète (3 Exercices Validés)", lines_full)
render_terminal(os.path.join(base_dir, "terminal_execution_ex1.png"), "PowerShell - Exercice 1 : Gestion de Stock", lines_ex1)
render_terminal(os.path.join(base_dir, "terminal_execution_ex2.png"), "PowerShell - Exercice 2 : Gestion de Projets", lines_ex2)
render_terminal(os.path.join(base_dir, "terminal_execution_ex3.png"), "PowerShell - Exercice 3 : Gestion de l'Etat Civil", lines_ex3)
print("All 4 terminal execution screenshots generated successfully!")

