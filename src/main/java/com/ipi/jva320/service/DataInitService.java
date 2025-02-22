package com.ipi.jva320.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Ajoute des données de test si vide au démarrage
 */
@Component
public class DataInitService implements CommandLineRunner {

    @Autowired
    private SalarieAideADomicileService salarieAideADomicileService;

    @Override
    public void run(String... argv) throws Exception {
        if (this.salarieAideADomicileService.countSalaries() != 0) {
        }
        /*
        SalarieAideADomicile s1 = this.salarieAideADomicileService.creerSalarieAideADomicile(
                new SalarieAideADomicile("Jean", LocalDate.parse("2022-12-05"), LocalDate.parse("2022-12-05"),
                        20, 0,
                        80, 10, 1));
                        */

    }
}
