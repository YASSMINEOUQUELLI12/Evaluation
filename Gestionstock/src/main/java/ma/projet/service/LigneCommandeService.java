package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.LigneCommandeId;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class LigneCommandeService extends AbstractService<LigneCommandeProduit, LigneCommandeId> {

    public LigneCommandeService() {
        super(LigneCommandeProduit.class);
    }

    /**
     * Récupère toutes les lignes d'une commande donnée (par son id)
     */
    public List<LigneCommandeProduit> findByCommande(int id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                            "from LigneCommandeProduit l where l.commande.id = :id",
                            LigneCommandeProduit.class
                    )
                    .setParameter("id", id)
                    .getResultList();
        }
    }
}


