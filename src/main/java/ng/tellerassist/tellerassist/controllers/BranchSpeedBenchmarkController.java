package ng.tellerassist.tellerassist.controllers;

import ng.tellerassist.tellerassist.entity.TellerAssistBenchmarkSpeed;
import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping(value = "configSpeedBenchmark.action")
@SessionAttributes(types = {TellerAssistBenchmarkSpeed.class})
public class BranchSpeedBenchmarkController {

    @Inject
    private TransactionServiceDefinition serviceDefinition;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(Model model, HttpServletRequest request, HttpServletResponse response) {
        Object benchmarkSpeedConfig = (TellerAssistBenchmarkSpeed) this.serviceDefinition.loadEntity(TellerAssistBenchmarkSpeed.class);

        if (benchmarkSpeedConfig instanceof TellerAssistBenchmarkSpeed) {
            TellerAssistBenchmarkSpeed speedBenchmark = (TellerAssistBenchmarkSpeed) benchmarkSpeedConfig;
            model.addAttribute("speedConfig", speedBenchmark);
        }
//        else
//        {
//            model.addAttribute("speedConfig", new TellerAssistBenchmarkSpeed());
//        }
        return "branchSpeedConfigForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("speedConfig") TellerAssistBenchmarkSpeed speedBenchmark, BindingResult result) {
        return "redirect:configSpeedBenchmark.action";
    }

}
