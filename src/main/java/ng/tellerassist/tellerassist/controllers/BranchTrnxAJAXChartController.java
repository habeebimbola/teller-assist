package ng.tellerassist.tellerassist.controllers;

import ng.tellerassist.tellerassist.entity.BranchDetail;
import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;
import java.time.LocalDateTime;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class BranchTrnxAJAXChartController {

    @Inject
    private TransactionServiceDefinition serviceDefinition;

    @RequestMapping(value = "/branchTrnx.action", method = RequestMethod.POST, headers = "Accept=*/*", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody()
    Integer getBranchTrnxCount() {
        BranchDetail branchDetail = (BranchDetail) this.serviceDefinition.loadEntity(BranchDetail.class);
        Integer trnxCount = this.serviceDefinition.completedBranchTrnxCountPerTimePeriod(LocalDateTime.now(), branchDetail.getId());
        return trnxCount;
    }

    @RequestMapping(value = "/requestQueue.action", method = RequestMethod.POST, produces = "application/json", headers = "Accept=*/*")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Integer getIncompleteBranchRequestsCount() {
        BranchDetail branchDetail = (BranchDetail) this.serviceDefinition.loadEntity(BranchDetail.class);
        Integer trnxCount = this.serviceDefinition.incompleteBranchTrnxCountPerDay(LocalDateTime.now(), branchDetail.getId());
        return trnxCount;
    }

    @RequestMapping(value = "/aboveBenchmark.action", method = RequestMethod.POST, produces = "application/json", headers = "Accept=*/*")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Integer getTellersAboveBenchmarkCount() {
        BranchDetail branchDetail = (BranchDetail) this.serviceDefinition.loadEntity(BranchDetail.class);
        Integer tellersCount = this.serviceDefinition.tellersAboveBenchmarkCount(branchDetail.getId());
        return tellersCount;
    }
}
