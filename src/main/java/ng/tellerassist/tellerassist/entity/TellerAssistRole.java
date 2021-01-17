
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
@Table(name="ROLES")
public class TellerAssistRole implements Serializable{
    
    private Integer id;
    private String roleName;
    private String description;
    private Date lastModifiedDate;
    private Integer roleKey;

  
    @Column(name="DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(value= TemporalType.TIMESTAMP)
    @Column(name="LAST_MODIFIED_DATE")
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
    @Id
    @Column(name="ROLE_ID", nullable = false)
    @GeneratedValue(generator = "increment",strategy=GenerationType.SEQUENCE)
    @SequenceGenerator(name = "increment", sequenceName="ROLES_SEQ")
    public Integer getId() {
        return id;
    }

    public void setId(Integer Id) {
        this.id = Id;
    }

    @Column(name="ROLE_NAME", nullable=false)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    @Column(name="ROLE_KEY", nullable=false)
      public Integer getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(Integer roleKey) {
        this.roleKey = roleKey;
    }
}
