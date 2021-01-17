package ng.tellerassist.tellerassist.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="USER_ROLE")
public class TellerAssistUserRole implements Serializable {

    private Integer userRoleId;
    private TellerAssistUserDetail tellerAssistUser;
    private String role;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USERNAME",nullable = false)
    public TellerAssistUserDetail getTellerAssistUser() {
        return tellerAssistUser;
    }

    public void setTellerAssistUser(TellerAssistUserDetail tellerAssistUser) {
        this.tellerAssistUser = tellerAssistUser;
    }
    

    public TellerAssistUserRole() {
    }

    public TellerAssistUserRole(TellerAssistUserDetail user, String role) {
        this.tellerAssistUser = user;
        this.role = role;
    }

    @Id
    @Column(name="USER_ROLE_ID")
    @GeneratedValue(generator = "increment", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "increment", sequenceName = "USER_ROLE_SEQ")
    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    @Column(name="ROLENAME")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
