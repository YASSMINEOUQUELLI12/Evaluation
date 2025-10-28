package ma.projet;

import ma.projet.classes.*;
import ma.projet.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Component
public class RapportProjetRunner implements CommandLineRunner {

    private final ProjetRepository projetRepo;
    private final EmployeRepository employeRepo;
    private final TacheRepository tacheRepo;
    private final EmployeTacheRepository etRepo;

    public RapportProjetRunner(ProjetRepository projetRepo, EmployeRepository employeRepo,
                               TacheRepository tacheRepo, EmployeTacheRepository etRepo) {
        this.projetRepo = projetRepo;
        this.employeRepo = employeRepo;
        this.tacheRepo = tacheRepo;
        this.etRepo = etRepo;
    }

    @Override
    public void run(String... args) {

        // 🧱 1️⃣ Insertion automatique si la base est vide
        if (projetRepo.count() == 0) {
            System.out.println("⚙️ Insertion des données de test...");

            Employe chef = new Employe("Hassan", "El Amrani", "0600000000");
            employeRepo.save(chef);

            Projet p = new Projet("Gestion de stock",
                    LocalDate.of(2013, 1, 14),
                    LocalDate.of(2013, 6, 30),
                    chef);
            projetRepo.save(p);

            Tache t1 = new Tache("Analyse", LocalDate.of(2013, 2, 10), LocalDate.of(2013, 2, 20), 1500, p);
            Tache t2 = new Tache("Conception", LocalDate.of(2013, 3, 10), LocalDate.of(2013, 3, 15), 1800, p);
            Tache t3 = new Tache("Développement", LocalDate.of(2013, 4, 10), LocalDate.of(2013, 4, 25), 5000, p);
            tacheRepo.saveAll(List.of(t1, t2, t3));

            Employe dev = new Employe("Sara", "B.", "0611111111");
            employeRepo.save(dev);

            EmployeTache a1 = new EmployeTache(dev, t1, LocalDate.of(2013, 2, 10), LocalDate.of(2013, 2, 20));
            EmployeTache a2 = new EmployeTache(dev, t2, LocalDate.of(2013, 3, 10), LocalDate.of(2013, 3, 15));
            EmployeTache a3 = new EmployeTache(dev, t3, LocalDate.of(2013, 4, 10), LocalDate.of(2013, 4, 25));
            etRepo.saveAll(List.of(a1, a2, a3));
        }

        // 🧱 2️⃣ Si le projet existe mais les affectations manquent
        if (etRepo.count() == 0) {
            System.out.println("⚙️ Insertion des affectations manquantes...");
            Employe dev = employeRepo.findAll().stream()
                    .filter(e -> e.getNom().equals("Sara"))
                    .findFirst().orElseThrow();

            Projet p = projetRepo.findByNom("Gestion de stock").orElseThrow();
            List<Tache> taches = tacheRepo.findAll();

            EmployeTache a1 = new EmployeTache(dev, taches.get(0), LocalDate.of(2013,2,10), LocalDate.of(2013,2,20));
            EmployeTache a2 = new EmployeTache(dev, taches.get(1), LocalDate.of(2013,3,10), LocalDate.of(2013,3,15));
            EmployeTache a3 = new EmployeTache(dev, taches.get(2), LocalDate.of(2013,4,10), LocalDate.of(2013,4,25));
            etRepo.saveAll(List.of(a1, a2, a3));
        }

        // 🧾 3️⃣ Affichage du rapport formaté
        Projet p = projetRepo.findByNom("Gestion de stock")
                .orElse(projetRepo.findAll().get(0));

        var headerFmt = DateTimeFormatter.ofPattern("d MMMM uuuu", Locale.FRENCH);
        String headerDate = capitalize(p.getDateDebut().format(headerFmt));

        System.out.printf("Projet : %d      Nom : %s     Date début : %s%n",
                p.getId(), p.getNom(), headerDate);
        System.out.println("Liste des tâches:");
        System.out.println("Num Nom            Date Début Réelle   Date Fin Réelle");

        var rowFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<EmployeTache> lignes = etRepo.findByTache_Projet_IdOrderByDateDebutReelleAsc(p.getId());

        for (EmployeTache et : lignes) {
            String dd = et.getDateDebutReelle() != null ? et.getDateDebutReelle().format(rowFmt) : "";
            String df = et.getDateFinReelle() != null ? et.getDateFinReelle().format(rowFmt) : "";
            System.out.printf("%-3d %-15s %-18s %s%n",
                    et.getTache().getId(), et.getTache().getNom(), dd, df);
        }
    }

    private static String capitalize(String s) {
        return (s == null || s.isEmpty()) ? s : s.substring(0,1).toUpperCase() + s.substring(1);
    }
}


