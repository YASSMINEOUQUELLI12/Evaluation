package ma.projet.test;



import ma.projet.classes.*;
import ma.projet.service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class RapportProjetMain {
    public static void main(String[] args) {
        EmployeService empSrv = new EmployeService();
        ProjetService  prjSrv = new ProjetService();
        TacheService   tchSrv = new TacheService();
        EmployeTacheService etSrv = new EmployeTacheService();

        // ===== Seed minimal si base vide =====
        if (prjSrv.findAll().isEmpty()) {
            Employe chef = new Employe("Hassan","El Amrani","0600000000");
            Integer idChef = empSrv.create(chef);

            Projet p = new Projet("Gestion de stock",
                    LocalDate.of(2013,1,14),
                    LocalDate.of(2013,6,30),
                    empSrv.findById(idChef));
            prjSrv.create(p);

            Tache t1 = new Tache("Analyse",      LocalDate.of(2013,2,10), LocalDate.of(2013,2,20), 1500, p);
            Tache t2 = new Tache("Conception",   LocalDate.of(2013,3,10), LocalDate.of(2013,3,15), 1800, p);
            Tache t3 = new Tache("Développement",LocalDate.of(2013,4,10), LocalDate.of(2013,4,25), 5000, p);
            Integer idT1 = tchSrv.create(t1);
            Integer idT2 = tchSrv.create(t2);
            Integer idT3 = tchSrv.create(t3);

            Employe dev = new Employe("Sara","B.","0611111111");
            Integer idDev = empSrv.create(dev);

            EmployeTache a1 = new EmployeTache();
            a1.setEmploye(empSrv.findById(idDev));
            a1.setTache(tchSrv.findById(idT1));
            a1.setDateDebutReelle(LocalDate.of(2013,2,10));
            a1.setDateFinReelle(LocalDate.of(2013,2,20));
            etSrv.create(a1);

            EmployeTache a2 = new EmployeTache();
            a2.setEmploye(empSrv.findById(idDev));
            a2.setTache(tchSrv.findById(idT2));
            a2.setDateDebutReelle(LocalDate.of(2013,3,10));
            a2.setDateFinReelle(LocalDate.of(2013,3,15));
            etSrv.create(a2);

            EmployeTache a3 = new EmployeTache();
            a3.setEmploye(empSrv.findById(idDev));
            a3.setTache(tchSrv.findById(idT3));
            a3.setDateDebutReelle(LocalDate.of(2013,4,10));
            a3.setDateFinReelle(LocalDate.of(2013,4,25));
            etSrv.create(a3);
        }

        // ===== Récupérer un projet et imprimer le rapport =====
        Projet p = prjSrv.findAll().get(0);

        DateTimeFormatter headerFmt = DateTimeFormatter.ofPattern("d MMMM uuuu", Locale.FRENCH);
        String headerDate = capitalize(p.getDateDebut().format(headerFmt));

        System.out.printf("Projet : %d      Nom : %s     Date début : %s%n",
                p.getId(), p.getNom(), headerDate);

        System.out.println("Liste des tâches:");
        System.out.println("Num Nom            Date Début Réelle   Date Fin Réelle");

        DateTimeFormatter rowFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        List<EmployeTache> lignes = prjSrv.getTachesRealiseesAvecDates(p.getId());

        for (EmployeTache et : lignes) {
            String dd = et.getDateDebutReelle() != null ? et.getDateDebutReelle().format(rowFmt) : "";
            String df = et.getDateFinReelle()   != null ? et.getDateFinReelle().format(rowFmt)   : "";
            System.out.printf("%-3d %-15s %-18s %s%n",
                    et.getTache().getId(),
                    et.getTache().getNom(),
                    dd, df);
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }
}
