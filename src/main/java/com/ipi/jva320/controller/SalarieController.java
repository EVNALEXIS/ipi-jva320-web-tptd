package com.ipi.jva320.controller;

import com.ipi.jva320.exception.SalarieException;
import com.ipi.jva320.model.SalarieAideADomicile;
import com.ipi.jva320.service.SalarieAideADomicileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public String createSalarie(SalarieAideADomicile sad) throws SalarieException {
        salarieAideADomicileService.creerSalarieAideADomicile(sad);
        return "redirect:/salaries/" + sad.getId();
    }

    @PostMapping(value = "/modif/{id}")
    public String updateSalarie(@PathVariable Long id, @ModelAttribute SalarieAideADomicile salarieAideADomicile) throws SalarieException {

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


    //La limitation par page fonctionne, 10 salariés par page

    //Le tri fonctionne uniquement lors du premier click (ASC -> DESC) mais lors du second (DESC -> ASC)

    // Pagination, presque OK, la page par défaut (avec les salarié en dur) est accessible en appuyant sur suivant

    @GetMapping
    public String listSalaries(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
            @RequestParam(name = "sortProperty", defaultValue = "nom") String sortProperty,
            ModelMap mp
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        Page<SalarieAideADomicile> salariesPage = salarieAideADomicileService.getSalaries(pageRequest);

        mp.put("listSalarie", salariesPage.getContent());
        mp.put("currentPage", salariesPage.getNumber());
        mp.put("totalPages", salariesPage.getTotalPages());
        mp.put("totalItems", salariesPage.getTotalElements());

        return "list";
    }


    @ModelAttribute("nbSalarie")
    public Long nbSalarie() {
        return salarieAideADomicileService.countSalaries();
    }


}
