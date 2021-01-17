package ng.tellerassist.tellerassist.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TRANDETAILS")
public class TransactionDetail implements Serializable {

    private Integer Id;
    private String requestType;
    private String requestAccount;
    private String beneficiaryAccount;
    private String initiatorType;
    private String initiatorName;
    private String Initiator_CIF_ID;
    private Double requestAmount;
    private Date requestTrnxDate;
    private Date requestInitiatorDate;
    private Date requestCreationDate;
    private Date requestModifiedDate;
    private Date requestProcessedDate;
    private Date requestAuthorizationDate;
    private String requestProcessor;
    private String requestAuthorizer;
    private Integer requestStatus;
    private Integer deleteFlag;
    private String initiatorPhoneNumber;
    private String initiatorAddress;
    private String initiatorIdType;
    private String initiatorIdNo;
    private Integer ticketNumber;
    private BranchDetail branchDetail;
    private TellerAssistBank bank;

    @ManyToOne(cascade = CascadeType.ALL)
    public TellerAssistBank getBank() {
        return bank;
    }

    public void setBank(TellerAssistBank bank) {
        this.bank = bank;
    }

    @Column(name = "REQUEST_ACCOUNT")
    public String getRequestAccount() {
        return requestAccount;
    }

    public void setRequestAccount(String requestAccount) {
        this.requestAccount = requestAccount;
    }

    @Column(name = "BENEFICIARY_ACCOUNT")
    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    @Column(name = "INITIATOR_TYPE")
    public String getInitiatorType() {
        return initiatorType;
    }

    public void setInitiatorType(String initiatorType) {
        this.initiatorType = initiatorType;
    }

    @Column(name = "INITIATOR_NAME")
    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    @Column(name = "INITIATOR_CIF_ID")
    public String getInitiator_CIF_ID() {
        return Initiator_CIF_ID;
    }

    public void setInitiator_CIF_ID(String Initiator_CIF_ID) {
        this.Initiator_CIF_ID = Initiator_CIF_ID;
    }

    @Column(name = "REQUEST_AMOUNT")
    public Double getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(Double requestAmount) {
        this.requestAmount = requestAmount;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REQUEST_TRAN_DATE")
    public Date getRequestTrnxDate() {
        return requestTrnxDate;
    }

    public void setRequestTrnxDate(Date requestTrnxDate) {
        this.requestTrnxDate = requestTrnxDate;
    }

    @Column(name = "REQUEST_INITIATOR_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getRequestInitiatorDate() {
        return requestInitiatorDate;
    }

    public void setRequestInitiatorDate(Date requestInitiatorDate) {
        this.requestInitiatorDate = requestInitiatorDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REQUEST_CREATION_DATE")
    public Date getRequestCreationDate() {
        return requestCreationDate;
    }

    public void setRequestCreationDate(Date requestCreationDate) {
        this.requestCreationDate = requestCreationDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REQUEST_MODIFIED_DATE")
    public Date getRequestModifiedDate() {
        return requestModifiedDate;
    }

    public void setRequestModifiedDate(Date requestModifiedDate) {
        this.requestModifiedDate = requestModifiedDate;
    }

    @Column(name = "REQUEST_PROCESSED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getRequestProcessedDate() {
        return requestProcessedDate;
    }

    public void setRequestProcessedDate(Date requestProcessedDate) {
        this.requestProcessedDate = requestProcessedDate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REQUEST_AUTHORIZATION_DATE")
    public Date getRequestAuthorizationDate() {
        return requestAuthorizationDate;
    }

    public void setRequestAuthorizationDate(Date requestAuthorizationDate) {
        this.requestAuthorizationDate = requestAuthorizationDate;
    }

    @Column(name = "REQUEST_PROCESSOR")
    public String getRequestProcessor() {
        return requestProcessor;
    }

    public void setRequestProcessor(String requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @Column(name = "REQUEST_AUTHORIZER")
    public String getRequestAuthorizer() {
        return requestAuthorizer;
    }

    public void setRequestAuthorizer(String requestAuthorizer) {
        this.requestAuthorizer = requestAuthorizer;
    }

    @Column(name = "REQUEST_STATUS")
    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Column(name = "DELETE_FLAG")
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Column(name = "INITIATOR_PHONE_NUMBER")
    public String getInitiatorPhoneNumber() {
        return initiatorPhoneNumber;
    }

    public void setInitiatorPhoneNumber(String initiatorPhoneNumber) {
        this.initiatorPhoneNumber = initiatorPhoneNumber;
    }

    @Column(name = "INITIATOR_ADDRESS")
    public String getInitiatorAddress() {
        return initiatorAddress;
    }

    public void setInitiatorAddress(String initiatorAddress) {
        this.initiatorAddress = initiatorAddress;
    }

    @Column(name = "INITIATOR_ID_TYPE")
    public String getInitiatorIdType() {
        return initiatorIdType;
    }

    public void setInitiatorIdType(String initiatorIdType) {
        this.initiatorIdType = initiatorIdType;
    }

    @Column(name = "INITIATOR_ID_NUMBER")
    public String getInitiatorIdNo() {
        return initiatorIdNo;
    }

    public void setInitiatorIdNo(String initiatorIdNo) {
        this.initiatorIdNo = initiatorIdNo;
    }

    @Column(name = "TICKET_NUMBER")
    public Integer getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BRANCH_ID")
    public BranchDetail getBranchDetail() {
        return branchDetail;
    }

    public void setBranchDetail(BranchDetail branchDetail) {
        this.branchDetail = branchDetail;
    }

    @Column(name = "REQUEST_TYPE")
    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Id
    @Column(name = "REQUEST_ID")
    @GeneratedValue(generator = "increment", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "increment", sequenceName = "TRAN_DETAILS_SEQ")
//    @GenericGenerator(name="increment",strategy = "increment")
    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

}
