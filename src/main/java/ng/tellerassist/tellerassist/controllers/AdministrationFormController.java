package ng.tellerassist.tellerassist.controllers;

import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "admin.action")
public class AdministrationFormController {

    @Inject
    private TransactionServiceDefinition serviceDefinition;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(HttpServletResponse response, HttpServletRequest request, Model model) {
        return "administrationForm";
    }

    public String processSubmit(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:admin.action";
    }
}
