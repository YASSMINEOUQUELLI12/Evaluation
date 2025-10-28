package ma.projet;


import ma.projet.classes.*;
import ma.projet.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class GestionprojetApplication {
    public static void main(String[] args) {
        SpringApplication.run(GestionprojetApplication.class, args);
    }
    }

