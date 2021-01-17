package ng.tellerassist.tellerassist.controllers;

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
public class TotalTrnxAJAXChartController {

    @Inject
    private TransactionServiceDefinition serviceDefinition;

    @RequestMapping(value = {"/incompleteBranchTrnx.action"}, method = RequestMethod.POST, produces = "application/json", headers = "Accept=*/*")
    public @ResponseBody
    Map<String, Integer> getIncompleteBranchTrnxDetail() {
        HashMap<String, Integer> returnMap = (HashMap<String, Integer>) this.serviceDefinition.incompleteBranchTrnxDetail(LocalDateTime.now(), 1);

        return returnMap;
    }

    @RequestMapping(value = {"/completeBranchTrnx.action"}, method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
    public @ResponseBody
    Map<String, Integer> getCompletedBranchTrnxDetail() {
        HashMap<String, Integer> returnMap = (HashMap<String, Integer>) this.serviceDefinition.completedBranchTrnxDetail(LocalDateTime.now(), 1);

        return returnMap;
    }
}
