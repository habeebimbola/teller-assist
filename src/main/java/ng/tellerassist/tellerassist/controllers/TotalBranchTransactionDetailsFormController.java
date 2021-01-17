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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "totalTrnx.action")
public class TotalBranchTransactionDetailsFormController {

    @Inject
    private TransactionServiceDefinition serviceDefinition;

    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(HttpServletRequest request, HttpServletResponse response, Model model) {
        BranchDetail branchDetail = (BranchDetail) this.serviceDefinition.loadEntity(BranchDetail.class);
        TrnxBean trnxBean = new TrnxBean();
        trnxBean.setCompletedTrnxCount(this.serviceDefinition.totalBranchTrnxPerDayCount(LocalDateTime.now(), branchDetail.getId()));
        trnxBean.setTrnxOnQueueCount(this.serviceDefinition.incompleteBranchTrnxCountPerDay(LocalDateTime.now(), branchDetail.getId()));
        trnxBean.setBranchDetail(branchDetail);

        model.addAttribute("trnxBean", trnxBean);

        return "totalTrnxDetailsForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmit(HttpServletResponse response, HttpServletRequest request) {
        return "redirect:totalTrnx.action";
    }
}
