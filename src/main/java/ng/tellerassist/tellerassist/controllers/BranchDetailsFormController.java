package ng.tellerassist.tellerassist.controllers;

import ng.tellerassist.tellerassist.beans.TrnxBean;
import ng.tellerassist.tellerassist.entity.BranchDetail;
import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;
import java.time.LocalDateTime;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "branchDetails.action")
public class BranchDetailsFormController {

    @Inject
    private TransactionServiceDefinition serviceDefintion;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(@RequestParam(value = "bId", required = false) String branchId, HttpServletRequest request, HttpServletResponse response, Model model) {
        BranchDetail branchDetail = (BranchDetail) serviceDefintion.loadEntity(BranchDetail.class);
        TrnxBean branchTrnxBean = new TrnxBean();

        Integer branchTellerCount = this.serviceDefintion.allTellersCountByBranch(branchDetail.getId());
        Double branchSpeed = this.serviceDefintion.getBranchSpeed(LocalDateTime.now(), branchDetail.getId());

        branchTrnxBean.setBranchDetail(branchDetail);
        branchTrnxBean.setBranchSpeed(branchSpeed);
        branchTrnxBean.setNoOfTellers(branchTellerCount);

        model.addAttribute("trnxBean", branchTrnxBean);
        return "branchDetailsForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(HttpServletRequest request, HttpServletResponse response, BindingResult result) {
        return "redirect:branchDetails.htm";
    }
}
