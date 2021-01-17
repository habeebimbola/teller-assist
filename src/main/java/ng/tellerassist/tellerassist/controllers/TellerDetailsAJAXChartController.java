package ng.tellerassist.tellerassist.controllers;

import ng.tellerassist.tellerassist.entity.BranchDetail;
import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/**")
public class TellerDetailsAJAXChartController {

    @Inject
    private TransactionServiceDefinition serviceDefinition;

    @RequestMapping(value = {"/tellerTrnxAmount.action"}, method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
    public @ResponseBody
    Map<String, Double> getTellerTrnxAmount() {
        BranchDetail branchDetail = (BranchDetail) this.serviceDefinition.loadEntity(BranchDetail.class);

        HashMap<String, Double> returnMap = (HashMap<String, Double>) this.serviceDefinition.trnxAmountByEachTellerPerTimePeriod(LocalDateTime.now(), branchDetail.getId());
        return returnMap;
    }

    @RequestMapping(value = {"/avgSpeed.action"}, method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
    public @ResponseBody
    Map<String, Double> getTellerAvgSpeed() {
        BranchDetail branchDetail = (BranchDetail) this.serviceDefinition.loadEntity(BranchDetail.class);
        HashMap<String, Double> returnMap = (HashMap<String, Double>) this.serviceDefinition.avgSpeedByEachTellerPerTimePeriod(LocalDateTime.now(), branchDetail.getId());
        return returnMap;
    }

    @RequestMapping(value = {"/trnxCount.action"}, method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
    public @ResponseBody
    Map<String, Integer> getTellerTrnxCount() {
        BranchDetail branchDetail = (BranchDetail) this.serviceDefinition.loadEntity(BranchDetail.class);
        HashMap<String, Integer> returnMap = (HashMap<String, Integer>) this.serviceDefinition.trnxCountByEachTellerPerTimePeriod(LocalDateTime.now(), branchDetail.getId());
        return returnMap;
    }
}
