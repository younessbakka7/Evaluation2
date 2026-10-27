package ma.projet.beans;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "hommes")
@PrimaryKeyJoinColumn(name = "id")
public class Homme extends Personne {

    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mariage> mariages = new ArrayList<>();

    public Homme() {
        super();
    }

    public Homme(String nom, String prenom, String telephone, String adresse, Date dateNaissance) {
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
        return "Homme{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
