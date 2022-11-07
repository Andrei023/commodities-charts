package eu.heisenbug.commoditiescharts.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {

    @RequestMapping("/")
    public ModelAndView index(ModelMap model) {
        return new ModelAndView("redirect:/charts");
    }
}
