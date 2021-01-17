package ng.tellerassist.tellerassist.controllers;

import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;
import java.time.LocalDateTime;
import java.util.List;
import javax.inject.Inject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(value = "/**")
public class ChartAJAXController {

    @Inject
    private TransactionServiceDefinition serviceDefinition;

    @RequestMapping(method = RequestMethod.POST, value = {"/avgSpeedList.action"}, headers = "Accept=*/*", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Double> getAvgSpeedList() {

        List<Double> returnList = this.serviceDefinition.avgTrnxPerTimePeriodSpeedList(LocalDateTime.now());

        return returnList;
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/incompleteTrnx"}, headers = "Accept=*/*", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Integer getNonCompletedTrnxCount() {
        Integer trnxCount = this.serviceDefinition.incompleteBankTrnxCountPerTimePeriod(LocalDateTime.now());
        return trnxCount;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = {"/totalTrnxCount"}, headers = "Accept=*/*", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public Integer getTotalBankTrnxCount() {
        Integer trnxCount = this.serviceDefinition.totalBankTrnxPerTimePeriod(LocalDateTime.now());
        return trnxCount;
    }

    @RequestMapping(method = RequestMethod.POST, value = {"/belowBenchmark"}, headers = "Accept=*/*", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Integer getBranchBelowBenchmarkCount() {
        Integer branchCount = this.serviceDefinition.branchesBelowBenchmarkCountPerTimePeriod(LocalDateTime.now());
        return branchCount;
    }
}
