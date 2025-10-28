package ma.projet.classes.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class TestGestionStock {

    public static void main(String[] args) {
        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cms = new CommandeService();
        LigneCommandeService lcs = new LigneCommandeService();

        // === 1. Ajouter des catégories ===
        if (cs.getAll().isEmpty()) {
            cs.create(new Categorie("PC", "Ordinateurs portables"));
            cs.create(new Categorie("ECR", "Écrans"));
        }

        // === 2. Ajouter des produits ===
        if (ps.getAll().isEmpty()) {
            Categorie pc = cs.getAll().get(0);
            Categorie ecr = cs.getAll().get(1);

            ps.create(new Produit("ES12", 120, pc));
            ps.create(new Produit("ZR85", 100, pc));
            ps.create(new Produit("EE85", 200, ecr));
        }

        // === 3. Créer une commande ===
        if (cms.getAll().isEmpty()) {
            cms.create(new Commande(LocalDate.of(2013, 3, 14)));
        }

        // === 4. Lier les produits à la commande ===
        Commande commande = cms.getAll().get(0);
        List<Produit> produits = ps.getAll();

        if (lcs.findByCommande(commande.getId()).isEmpty()) {
            lcs.create(new LigneCommandeProduit(commande, produits.get(0), 7));
            lcs.create(new LigneCommandeProduit(commande, produits.get(1), 14));
            lcs.create(new LigneCommandeProduit(commande, produits.get(2), 5));
        }

        // === 5. Affichage formaté ===
        afficherCommandeDetail(commande, lcs.findByCommande(commande.getId()));
    }

    private static void afficherCommandeDetail(Commande c, List<LigneCommandeProduit> lignes) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.FRENCH);
        String dateStr = c.getDate().format(fmt);

        // Capitaliser la première lettre du mois
        if (!dateStr.isEmpty()) {
            dateStr = Character.toUpperCase(dateStr.charAt(0)) + dateStr.substring(1);
        }

        System.out.println();
        System.out.println("Commande : " + c.getId() + "     Date : " + dateStr);
        System.out.println("Liste des produits :");
        System.out.println("Référence   Prix    Quantité");

        for (LigneCommandeProduit l : lignes) {
            System.out.printf("%-10s %-7.0fDH %-10d%n",
                    l.getProduit().getReference(),
                    l.getProduit().getPrix(),
                    l.getQuantite());
        }
    }
}

