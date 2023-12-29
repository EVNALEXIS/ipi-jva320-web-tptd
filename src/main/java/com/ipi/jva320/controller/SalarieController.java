package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/salaries")
public class SalarieController {

    @Autowired
    SalarieAideADomicileService salarieAideADomicileService;
    @Autowired
    SalarieAideADomicileService salarie;

    @GetMapping(value = "/{id}")
    public String getSalarie(final ModelMap mp, @PathVariable Long id) {
        SalarieAideADomicile salarie = salarieAideADomicileService.getSalarie(id);
        mp.put("salarie", salarie);
        return "detail_Salarie";
    }

    @GetMapping(value = "/aide/new")
    public String afficherCreerSalarie() {
        return "creer_salarie";
    }

    @PostMapping(value = "/save")
    public String creerSalarie(SalarieAideADomicile sad) throws SalarieException {
        salarieAideADomicileService.creerSalarieAideADomicile(sad);
        return "redirect:/salaries/" + sad.getId();
    }

    @PostMapping(value = "/modif/{id}")
    public String modifSalarie(@PathVariable Long id, @ModelAttribute SalarieAideADomicile salarieAideADomicile) throws SalarieException {

        SalarieAideADomicile newSalarie = salarieAideADomicileService.getSalarie(id);

        newSalarie.setNom(salarieAideADomicile.getNom());
        newSalarie.setId(salarieAideADomicile.getId());
        newSalarie.setCongesPayesAcquisAnneeN(salarieAideADomicile.getCongesPayesAcquisAnneeN());
        newSalarie.setCongesPayesPris(salarieAideADomicile.getCongesPayesPris());
        newSalarie.setCongesPayesPrisAnneeNMoins1(salarieAideADomicile.getCongesPayesPrisAnneeNMoins1());
        newSalarie.setJoursTravaillesAnneeN(salarieAideADomicile.getJoursTravaillesAnneeN());
        newSalarie.setMoisDebutContrat(salarieAideADomicile.getMoisDebutContrat());
        newSalarie.setMoisEnCours(salarieAideADomicile.getMoisEnCours());
        newSalarie.setJoursTravaillesAnneeNMoins1(salarieAideADomicile.getJoursTravaillesAnneeNMoins1());
        newSalarie.setCongesPayesAcquisAnneeNMoins1(salarieAideADomicile.getCongesPayesAcquisAnneeNMoins1());

        salarieAideADomicileService.updateSalarieAideADomicile(newSalarie);

        // Rediriger vers la liste des salariés après la modification
        return "redirect:/salaries";
    }

    @GetMapping(value = "/{id}/delete")
    public String deleteSalarie(@PathVariable Long id) throws SalarieException {
        salarieAideADomicileService.deleteSalarieAideADomicile(id);

        return "redirect:/salaries";
    }

    @GetMapping(params = "nom")
    public String searchSalarie(@RequestParam(name = "nom", required = false) String nom, ModelMap mp) throws SalarieException {
        List<SalarieAideADomicile> sad = salarieAideADomicileService.getSalaries(nom);
        if (!sad.isEmpty()) {
            mp.put("listSalarie", sad);
            return "list";

        } else {
            throw new SalarieException("Le salarié recherché n'existe pas");
        }

    }

    @GetMapping
    public String list(ModelMap mp) {
        mp.put("listSalarie", salarieAideADomicileService.getSalaries());
        return "list";
    }


    @ModelAttribute("nbSalarie")
    public Long nbSalarie() {
        return salarieAideADomicileService.countSalaries();
    }


}
