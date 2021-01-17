package ng.tellerassist.tellerassist.controllers;

import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "manageuser.action")
public class ManageUserFormController {

    @Inject
    private TransactionServiceDefinition serviceDefinition;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(HttpServletRequest request, HttpServletResponse response) {
        return "manageUserForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit() {
        return "redirect:manageuser.action";
    }
}
