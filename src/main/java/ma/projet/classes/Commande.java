package ma.projet.classes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_commande", nullable = false)
    private Date date;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LigneCommandeProduit> ligneCommandes = new ArrayList<>();

    public Commande() {}

    public Commande(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<LigneCommandeProduit> getLigneCommandes() {
        return ligneCommandes;
    }

    public void setLigneCommandes(List<LigneCommandeProduit> ligneCommandes) {
        this.ligneCommandes = ligneCommandes;
    }

    @Override
    public String toString() {
        return "Commande{id=" + id + ", date=" + date + "}";
    }
}
