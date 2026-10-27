package ma.projet.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "taches")
@NamedQueries({
    @NamedQuery(
        name = "findTachesPrixSuperieur1000",
        query = "FROM Tache t WHERE t.prix > 1000"
    )
})
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut")
    private Date dateDebut;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin")
    private Date dateFin;

    @Column(nullable = false)
    private double prix;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;

    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EmployeTache> employeTaches = new ArrayList<>();

    public Tache() {}

    public Tache(String nom, Date dateDebut, Date dateFin, double prix, Projet projet) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.prix = prix;
        this.projet = projet;
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public List<EmployeTache> getEmployeTaches() {
        return employeTaches;
    }

    public void setEmployeTaches(List<EmployeTache> employeTaches) {
        this.employeTaches = employeTaches;
    }

    @Override
    public String toString() {
        return "Tache{id=" + id + ", nom='" + nom + "', prix=" + prix + " DH}";
    }
}
