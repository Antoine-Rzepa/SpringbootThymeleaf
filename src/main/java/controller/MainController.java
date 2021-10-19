package controller;

import java.util.ArrayList;
import java.util.List;

import form.PersonnageForm;
import model.Personnage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    private static List<Personnage> personnages = new ArrayList<Personnage>();

    static {
        personnages.add(new Personnage(1, "Bill", "Gates"));
        personnages.add(new Personnage(2, "Steve", "Jobs"));
    }

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

        model.addAttribute("personnages", personnages);

        return "personnagesList";
    }

    @GetMapping(value = { "/addPersonnage" })
    public String showAddPersonPage(Model model) {

        PersonnageForm personnageForm = new PersonnageForm();
        model.addAttribute("personnageForm", personnageForm);

        return "addPersonnage";
    }

    @PostMapping(value = { "/addPersonnage" })
    public String savePerson(Model model, //
                             @ModelAttribute("personnageForm") PersonnageForm personnageForm) {

        String name = personnageForm.getName();
        String type = personnageForm.getType();

        if (name != null && name.length() > 0 //
                && type != null && type.length() > 0) {
            Personnage e = personnages.get(personnages.size()-1);
            Personnage newPersonnage = new Personnage(e.getId()+1, name, type);
            personnages.add(newPersonnage);

            return "redirect:/personnages";
        }

        model.addAttribute("errorMessage", errorMessage);
        return "addPersonnage";
    }

}