package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employe_tache")
public class EmployeTache {
    @EmbeddedId
    private EmployeTachePK pk;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("employe")
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("tache")
    @JoinColumn(name = "tache_id")
    private Tache tache;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_debut_reelle")
    private Date dateDebutReelle;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_fin_reelle")
    private Date dateFinReelle;

    public EmployeTache() {}

    public EmployeTache(Employe employe, Tache tache, Date dateDebutReelle, Date dateFinReelle) {
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
        this.pk = new EmployeTachePK(
                employe != null ? employe.getId() : 0,
                tache != null ? tache.getId() : 0
        );
    }

    public EmployeTachePK getPk() {
        return pk;
    }

    public void setPk(EmployeTachePK pk) {
        this.pk = pk;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
        if (this.pk == null) {
            this.pk = new EmployeTachePK();
        }
        if (employe != null) {
            this.pk.setEmploye(employe.getId());
        }
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
        if (this.pk == null) {
            this.pk = new EmployeTachePK();
        }
        if (tache != null) {
            this.pk.setTache(tache.getId());
        }
    }

    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }

    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }

    public Date getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }

    @Override
    public String toString() {
        return "EmployeTache{" +
                "employe=" + (employe != null ? employe.getNom() : "null") +
                ", tache=" + (tache != null ? tache.getNom() : "null") +
                ", dateDebutReelle=" + dateDebutReelle +
                ", dateFinReelle=" + dateFinReelle +
                '}';
    }
}
