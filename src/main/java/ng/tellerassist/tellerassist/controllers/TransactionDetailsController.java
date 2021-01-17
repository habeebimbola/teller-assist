package ng.tellerassist.tellerassist.controllers;

import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/trnxDetails.action")
public class TransactionDetailsController {

    @Inject
    private TransactionServiceDefinition serviceDefinition;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(HttpServletRequest request, HttpServletResponse response, Model model) {
        this.serviceDefinition.avgTrnxPerTimePeriodSpeedList(LocalDateTime.now());
        this.serviceDefinition.branchesBelowBenchmarkCountPerTimePeriod(LocalDateTime.now());
        this.serviceDefinition.totalBankTrnxPerTimePeriod(LocalDateTime.now());
        return "transactionDetailsForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(HttpServletRequest request, HttpServletResponse response) {
        return "redirect:branchDetails.action";
    }

}
