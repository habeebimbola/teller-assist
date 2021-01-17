package ng.tellerassist.tellerassist.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {

    @RequestMapping(method = RequestMethod.POST, value = "/logoutForm")
    public String setupForm(HttpServletRequest request, HttpServletResponse response, Model model) {

        return "logoutForm";
    }
}
