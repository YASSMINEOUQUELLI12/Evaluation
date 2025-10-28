package ma.projet.config;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.repo.FemmeRepository;
import ma.projet.repo.HommeRepository;
import ma.projet.repo.MariageRepository;
import ma.projet.service.MariageCriteriaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Configuration
public class TestProgrammeRunner {

    @Bean
    CommandLineRunner runTests(
            HommeRepository hommeRepo,
            FemmeRepository femmeRepo,
            MariageRepository mariageRepo,
            MariageCriteriaService criteriaService
    ) {
        return args -> {
            // 1) Créer 10 femmes et 5 hommes (seed si base vide)
            if (hommeRepo.count() == 0 && femmeRepo.count() == 0) {
                for (int i = 1; i <= 5; i++) hommeRepo.save(fabHomme("SAFI", "SAID" + i, 1965 + i));
                for (int i = 1; i <= 10; i++) femmeRepo.save(fabFemme("F" + i, "PRENOM" + i, 1970 + i));

                Homme h1 = hommeRepo.findAll().get(0);
                List<Femme> f = femmeRepo.findAll();

                // 4 mariages pour h1 (3 en cours, 1 terminé)
                mariageRepo.save(m(h1, f.get(0), d(1989, 9, 3),  d(1990, 9, 3), 0));
                mariageRepo.save(m(h1, f.get(1), d(1990, 9, 3),  null,          4));
                mariageRepo.save(m(h1, f.get(2), d(1995, 9, 3),  null,          2));
                mariageRepo.save(m(h1, f.get(3), d(2000,11, 4),  null,          3));

                // 4 mariages pour h2 (tout en cours)
                Homme h2 = hommeRepo.findAll().get(1);
                mariageRepo.save(m(h2, f.get(4), d(1992, 1, 1), null, 1));
                mariageRepo.save(m(h2, f.get(5), d(1996, 1, 1), null, 2));
                mariageRepo.save(m(h2, f.get(6), d(2000, 1, 1), null, 0));
                mariageRepo.save(m(h2, f.get(7), d(2005, 1, 1), null, 3));

                // Femme mariée au moins 2 fois (F9) : h3 puis h2
                Homme h3 = hommeRepo.findAll().get(2);
                mariageRepo.save(m(h3, f.get(8), d(1993, 5, 5), d(1998, 5, 5), 1));
                mariageRepo.save(m(h2, f.get(8), d(1999, 6, 6), null,          2));
            }

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // 2) Afficher la liste des femmes
            System.out.println("\nLISTE DES FEMMES :");
            femmeRepo.findAll().forEach(ff ->
                    System.out.println(" - " + nomComplet(ff) + " (" + ff.getDateNaissance().format(fmt) + ")")
            );

            // 3) Afficher la femme la plus âgée
            var plusAgee = femmeRepo.findAll().stream()
                    .min(Comparator.comparing(Femme::getDateNaissance))
                    .orElse(null);
            System.out.println("\nFEMME LA PLUS AGÉE : " + (plusAgee != null ? nomComplet(plusAgee) : "N/A"));

            // 4) Afficher les épouses d’un homme donné
            var hommeCible = hommeRepo.findAll().get(0); // SAID1
            var epouses = mariageRepo.findEpousesEntreDates(hommeCible.getId(), d(1989,1,1), d(2001,1,1));
            System.out.println("\nÉPOUSES DE " + nomComplet(hommeCible) + " ENTRE 1989 ET 2001 :");
            epouses.forEach(f -> System.out.println(" - " + nomComplet(f)));

            // 5) Nombre d’enfants d’une femme entre deux dates (native query)
            var f1 = femmeRepo.findAll().get(1); // mariée à h1 avec 4 enfants
            long enfants = femmeRepo.sumChildrenBetween(f1.getId(), d(1988,1,1), d(2025,1,1));
            System.out.println("\nNOMBRE D’ENFANTS DE " + nomComplet(f1) + " ENTRE 1988 ET 2025 : " + enfants);

            // 6) Femmes mariées deux fois ou plus (NamedQuery)
            System.out.println("\nFEMMES MARIÉES AU MOINS 2 FOIS :");
            femmeRepo.findMarieesAuMoinsDeuxFois()
                    .forEach(f -> System.out.println(" - " + nomComplet(f)));

            // 7) Hommes mariés à quatre femmes entre deux dates (Criteria API)
            long nbHommes4 = criteriaService.countHommesAvecQuatreEpousesEntre(d(1980,1,1), d(2030,1,1));
            System.out.println("\nNOMBRE D’HOMMES MARIÉS À 4 FEMMES (1980-2030) : " + nbHommes4);

            // 8) Afficher les mariages d’un homme avec tous les détails
            System.out.println("\n" + afficherMariagesDetail(mariageRepo, hommeCible, fmt));
        };
    }

    // ===== Helpers =====
    private static LocalDate d(int y, int m, int d) { return LocalDate.of(y, m, d); }

    private static Homme fabHomme(String nom, String prenom, int annee){
        Homme h = new Homme();
        h.setNom(nom); h.setPrenom(prenom);
        h.setAdresse("Adresse H"); h.setTelephone("0600");
        h.setDateNaissance(LocalDate.of(annee,1,1));
        return h;
    }
    private static Femme fabFemme(String nom, String prenom, int annee){
        Femme f = new Femme();
        f.setNom(nom); f.setPrenom(prenom);
        f.setAdresse("Adresse F"); f.setTelephone("0700");
        f.setDateNaissance(LocalDate.of(annee,1,1));
        return f;
    }
    private static Mariage m(Homme h, Femme f, LocalDate d1, LocalDate d2, int n){
        Mariage m = new Mariage();
        m.setHomme(h); m.setFemme(f); m.setDateDebut(d1); m.setDateFin(d2); m.setNbrEnfant(n);
        return m;
    }
    private static String nomComplet(Homme h){ return (h.getNom()+" "+h.getPrenom()).toUpperCase(); }
    private static String nomComplet(Femme f){ return (f.getNom()+" "+f.getPrenom()).toUpperCase(); }

    private static String afficherMariagesDetail(MariageRepository repo, Homme homme, DateTimeFormatter fmt){
        var mariages = repo.findAllByHommeIdWithFemme(homme.getId());
        StringBuilder sb = new StringBuilder();
        sb.append("Nom : ").append(nomComplet(homme)).append("\n");

        sb.append("Mariages En Cours :\n");
        int i = 1;
        for (var m : mariages) {
            if (m.getDateFin() == null) {
                sb.append(i++).append(". ")
                        .append("Femme : ").append(nomComplet(m.getFemme()))
                        .append("   Date Début : ").append(m.getDateDebut().format(fmt))
                        .append("    Nbr Enfants : ").append(m.getNbrEnfant()).append("\n");
            }
        }
        if (i==1) sb.append("(Aucun)\n");

        sb.append("\nMariages échoués :\n");
        i = 1;
        for (var m : mariages) {
            if (m.getDateFin() != null) {
                sb.append(i++).append(". ")
                        .append("Femme : ").append(nomComplet(m.getFemme()))
                        .append("  Date Début : ").append(m.getDateDebut().format(fmt))
                        .append("    \nDate Fin : ").append(m.getDateFin().format(fmt))
                        .append("    Nbr Enfants : ").append(m.getNbrEnfant()).append("\n");
            }
        }
        if (i==1) sb.append("(Aucun)\n");

        return sb.toString();
    }
}

