package ma.projet.beans;

import jakarta.persistence.*;

import java.time.LocalDate;


import jakarta.persistence.*;
import java.time.LocalDate;

@MappedSuperclass // ou @Entity + @Inheritance, voir Cas B
public abstract class Personne {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        protected Integer id;

        @Column(nullable=false, length=60) private String nom;
        @Column(nullable=false, length=60) private String prenom;
        private String telephone;
        private String adresse;
        @Column(nullable=false) private LocalDate dateNaissance;

        // getters/setters
        public Integer getId() { return id; }
        public String getNom() { return nom; }
        public void setNom(String nom) { this.nom = nom; }
        public String getPrenom() { return prenom; }
        public void setPrenom(String prenom) { this.prenom = prenom; }
        public String getTelephone() { return telephone; }
        public void setTelephone(String telephone) { this.telephone = telephone; }
        public String getAdresse() { return adresse; }
        public void setAdresse(String adresse) { this.adresse = adresse; }
        public LocalDate getDateNaissance() { return dateNaissance; }
        public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
}
