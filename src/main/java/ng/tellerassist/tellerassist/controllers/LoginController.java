package ng.tellerassist.tellerassist.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
//import org.hibernate.engine.transaction.spi.TransactionContext;

@Controller
@RequestMapping(value = "/login.action")
public class LoginController {

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(HttpServletResponse response, HttpServletRequest request) {
        return "loginForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:login.action";
    }
}
