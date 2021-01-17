package ng.tellerassist.tellerassist.beans;

import ng.tellerassist.tellerassist.entity.BranchDetail;

public class TrnxBean {

    private BranchDetail branchDetail;
    private Double branchSpeed;
    private Integer noOfTellers;
    private Integer completedTrnxCount;
    private Integer trnxOnQueueCount;

    public Integer getCompletedTrnxCount() {
        return completedTrnxCount;
    }

    public void setCompletedTrnxCount(Integer completedTrnxCount) {
        this.completedTrnxCount = completedTrnxCount;
    }

    public Integer getTrnxOnQueueCount() {
        return trnxOnQueueCount;
    }

    public void setTrnxOnQueueCount(Integer trnxOnQueueCount) {
        this.trnxOnQueueCount = trnxOnQueueCount;
    }

    public BranchDetail getBranchDetail() {
        return branchDetail;
    }

    public void setBranchDetail(BranchDetail branchDetail) {
        this.branchDetail = branchDetail;
    }

    public Double getBranchSpeed() {
        return branchSpeed;
    }

    public void setBranchSpeed(Double branchSpeed) {
        this.branchSpeed = branchSpeed;
    }

    public Integer getNoOfTellers() {
        return noOfTellers;
    }

    public void setNoOfTellers(Integer noOfTellers) {
        this.noOfTellers = noOfTellers;
    }

}
