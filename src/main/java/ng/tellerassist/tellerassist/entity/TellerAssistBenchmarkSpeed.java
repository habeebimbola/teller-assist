package ng.tellerassist.tellerassist.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TRANS_SPEED_CONFIG")
public class TellerAssistBenchmarkSpeed implements Serializable {

    private Integer Id;
    private Double configValue;
    private String description;
    private Date lastModifiedDate;

    @Id
    @Column(name = "SPEED_CONFIG_ID")
    @GeneratedValue(generator = "increment", strategy = GenerationType.AUTO)
    @SequenceGenerator(name = "increment", sequenceName = "TRANS_SPEED_CONFIG_SEQ")
//    @GenericGenerator(name="increment",strategy="increment")
    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    @Column(name = "CONFIG_VALUE")
    public Double getConfigValue() {
        return configValue;
    }

    public void setConfigValue(Double configValue) {
        this.configValue = configValue;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_DATE")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
