package ma.projet.beans;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "femmes")
@PrimaryKeyJoinColumn(name = "id")
@NamedQueries({
    @NamedQuery(
        name = "Femme.findFemmesMarieesAuMoinsDeuxFois",
        query = "SELECT f FROM Femme f JOIN f.mariages m GROUP BY f HAVING COUNT(m) >= 2"
    )
})
@NamedNativeQueries({
    @NamedNativeQuery(
        name = "Femme.countEnfantsBetweenDates",
        query = "SELECT COALESCE(SUM(m.nbr_enfant), 0) FROM mariage m WHERE m.femme_id = :femmeId AND m.date_debut BETWEEN :d1 AND :d2"
    )
})
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mariage> mariages = new ArrayList<>();

    public Femme() {
        super();
    }

    public Femme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
        super(nom, prenom, telephone, adresse, dateNaissance);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }

    @Override
    public String toString() {
        return "Femme{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
