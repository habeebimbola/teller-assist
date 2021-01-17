package ng.tellerassist.tellerassist.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "BANKS")
public class TellerAssistBank implements Serializable {

    private Integer Id;
    private String bankName;
    private String bankDescription;

    @Id
    @Column(name = "BANK_ID")
    @GeneratedValue(generator = "increment", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "increment", sequenceName = "BANKS_SEQ")
//    @GenericGenerator(name="increment",strategy="increment")
    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    @Column(name = "BANK_DESCRIPTION")
    public String getBankDescription() {
        return bankDescription;
    }

    public void setBankDescription(String bankDescription) {
        this.bankDescription = bankDescription;
    }

    @Column(name = "BANK_NAME")
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

}
