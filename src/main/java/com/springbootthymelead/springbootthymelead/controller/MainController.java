package com.springbootthymelead.springbootthymelead.controller;

import java.util.ArrayList;
import java.util.List;

import com.springbootthymelead.springbootthymelead.form.PersonnageForm;
import com.springbootthymelead.springbootthymelead.model.Personnage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class MainController {

    // Injectez (inject) via application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

    @GetMapping(value = { "/", "/index" })
    public String index(Model model) {

        model.addAttribute("message", message);

        return "index";
    }

    @GetMapping(value = { "/personnages" })
    public String personnagesList(Model model) {

        RestTemplate restTemplate = new RestTemplate();
        Personnage[] personnages = restTemplate.getForObject("http://localhost:8081/personnages", Personnage[].class);

        model.addAttribute("personnages", personnages);

        return "personnagesList";
    }

    @GetMapping(value = { "/addPersonnage" })
    public String AddPersonPage(Model model) {

        PersonnageForm personnageForm = new PersonnageForm();
        model.addAttribute("personnageForm", personnageForm);
        model.addAttribute("edit", false);

        return "addPersonnage";
    }

    @GetMapping(value = { "/editPersonnage/{id}" })
    public String EditPersonPage(Model model, @PathVariable int id) {

        PersonnageForm personnageForm = new PersonnageForm();
        RestTemplate restTemplate = new RestTemplate();
        Personnage personnage = restTemplate.getForObject("http://localhost:8081/personnages/" + id, Personnage.class);

        personnageForm.setName(personnage.getName());
        personnageForm.setType(personnage.getType());

        model.addAttribute("personnageForm", personnageForm);
        model.addAttribute("edit", true);
        model.addAttribute("personnage", personnage);

        return "addPersonnage";
    }

    @PostMapping(value = { "/setPersonnage/{id}" })
    public String setPerson(Model model, @ModelAttribute("personnageForm") PersonnageForm personnageForm, @ModelAttribute("personnage") Personnage personnage, @PathVariable int id) {

        personnage.setId(id);
        personnage.setName(personnageForm.getName());
        personnage.setType(personnageForm.getType());

        if (personnage.getName() != null && personnage.getName().length() > 0 //
                && personnage.getType() != null && personnage.getType().length() > 0) {

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.put("http://localhost:8081/personnages/" + personnage.getId(),personnage);

            return "redirect:/personnages";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "redirect:/editPersonnage/" + personnage.getId();
    }


    @PostMapping(value = { "/addPersonnage" })
    public String savePerson(Model model,@ModelAttribute("personnageForm") PersonnageForm personnageForm) {

        RestTemplate restTemplate = new RestTemplate();
        Personnage[] personnages = restTemplate.getForObject("http://localhost:8081/personnages", Personnage[].class);

        String name = personnageForm.getName();
        String type = personnageForm.getType();

        if (name != null && name.length() > 0 //
                && type != null && type.length() > 0) {
            Personnage e = personnages[(personnages.length-1)];
            Personnage newPersonnage = new Personnage(e.getId()+1, name, type);

            restTemplate.postForObject("http://localhost:8081/personnages",newPersonnage, Personnage.class);

            return "redirect:/personnages";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addPersonnage";
    }

    @GetMapping(value = {"/delete/{id}"})
    public String deletePerson(@PathVariable int id){

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8081/personnages/" + id);

        return "redirect:/personnages";
    }

}