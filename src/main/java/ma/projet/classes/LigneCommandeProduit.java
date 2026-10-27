package ma.projet.classes;

import javax.persistence.*;

@Entity
@Table(name = "ligne_commande_produit")
public class LigneCommandeProduit {
    @EmbeddedId
    private LigneCommandeProduitPK pk;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("produit")
    @JoinColumn(name = "produit_id")
    private Produit produit;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("commande")
    @JoinColumn(name = "commande_id")
    private Commande commande;

    @Column(nullable = false)
    private int quantite;

    public LigneCommandeProduit() {}

    public LigneCommandeProduit(Produit produit, Commande commande, int quantite) {
        this.produit = produit;
        this.commande = commande;
        this.quantite = quantite;
        this.pk = new LigneCommandeProduitPK(
                produit != null ? produit.getId() : 0,
                commande != null ? commande.getId() : 0
        );
    }

    public LigneCommandeProduitPK getPk() {
        return pk;
    }

    public void setPk(LigneCommandeProduitPK pk) {
        this.pk = pk;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
        if (this.pk == null) {
            this.pk = new LigneCommandeProduitPK();
        }
        if (produit != null) {
            this.pk.setProduit(produit.getId());
        }
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
        if (this.pk == null) {
            this.pk = new LigneCommandeProduitPK();
        }
        if (commande != null) {
            this.pk.setCommande(commande.getId());
        }
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "LigneCommandeProduit{" +
                "produit=" + (produit != null ? produit.getReference() : "null") +
                ", commande=" + (commande != null ? commande.getId() : "null") +
                ", quantite=" + quantite +
                '}';
    }
}
