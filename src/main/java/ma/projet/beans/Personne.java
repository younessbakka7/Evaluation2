package ma.projet.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "personnes")
@Inheritance(strategy = InheritanceType.JOINED)
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(nullable = false, length = 50)
    protected String nom;

    @Column(nullable = false, length = 50)
    protected String prenom;

    @Column(length = 20)
    protected String telephone;

    @Column(length = 150)
    protected String adresse;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_naissance")
    protected Date dateNaissance;

    public Personne() {}

    public Personne(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    @Override
    public String toString() {
        return "Personne{id=" + id + ", nom='" + nom + "', prenom='" + prenom + "'}";
    }
}
