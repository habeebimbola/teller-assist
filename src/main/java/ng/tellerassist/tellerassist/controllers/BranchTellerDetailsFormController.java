package ng.tellerassist.tellerassist.controllers;

import ng.tellerassist.tellerassist.beans.TrnxBean;
import ng.tellerassist.tellerassist.entity.BranchDetail;
import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "tellerDetails.action")
public class BranchTellerDetailsFormController {

    @Inject
    private TransactionServiceDefinition serviceDefinition;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(@RequestParam(required = false, value = "bId") Integer branchId, HttpServletRequest request, HttpServletResponse response, Model model) {
        BranchDetail branchDetail = (BranchDetail) this.serviceDefinition.loadEntity(BranchDetail.class);

        TrnxBean trnxBean = new TrnxBean();
        trnxBean.setBranchDetail(branchDetail);
        trnxBean.setNoOfTellers(this.serviceDefinition.tellersAboveBenchmarkCount(branchDetail.getId()));

        model.addAttribute("trnxBean", trnxBean);
        return "tellerDetailsForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(HttpServletRequest request, HttpServletResponse response) {

        return "redirect:tellerDetails.action";
    }
}
