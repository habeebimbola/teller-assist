package ng.tellerassist.tellerassist.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "USER_DETAILS")
public class TellerAssistUserDetail extends ParentEntity implements Serializable {

    private Integer id;
    //private Integer accessLevel;
    @NotEmpty(message = "Username cannot be empty.")
    @Size(min = 6, max = 20, message = "Username must be at least 6 characters long, and maximum of 20 characters long.")
    private String username;
    @NotEmpty(message = "Firstname must not be empty.")
    private String firstName;
    @NotEmpty(message = "Lastname cannot be empty.")
    @Size(min = 3, max = 20)
    @Pattern(regexp = "^\\w{1,20}$", message = "Lastname length must not be greater 20 characters.")
    private String lastName;
    @NotEmpty(message = "Email cannot be empty.")
    @Pattern(regexp = "^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})$", message = "Entered Email Not Valid.")
    private String email;

    private BranchDetail branchAssigned;
    @NotEmpty(message = "Passwords cannot be empty.")
    private String password;
    private String confirmPassword;
    private Date lastModifiedDate;
    private boolean isFirstTimeLogin;
    private boolean enabled;
    private TellerAssistRole role;

//    private Set<TellerAssistUserRole> userRole = new HashSet<TellerAssistUserRole>(0);
//    public TellerAssistUserDetail() {
//    }
//
//    public TellerAssistUserDetail(String username, String password, boolean enabled) {
//        this.username = username;
//        this.password = password;
//        this.enabled = enabled;
//    }
//    public TellerAssistUserDetail(String username, String password, boolean enabled, TellerAssistRole userRole) {
//        this.username = username;
//        this.password = password;
//        this.enabled = enabled;
//        this.userRole = userRole;
//    }
    @NotNull(message = "NOT_NULL")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "ENABLED")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tellerAssistUser")
//    public Set<TellerAssistUserRole> getUserRole() {
//        return userRole;
//    }
//
//    public void setUserRole(Set<TellerAssistUserRole> userRole) {
//        this.userRole = userRole;
//    }
    @Id
    @Column(name = "USERID")
    @GeneratedValue(generator = "increment", strategy = GenerationType.SEQUENCE)
    @GenericGenerator(strategy="increment", name="increment")
    @SequenceGenerator(name = "increment", sequenceName = "USER_DETAILS_SEQ")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "FIRSTNAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "LASTNAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "BRANCH_ASSIGNED_ID")
    public BranchDetail getBranchAssigned() {
        return branchAssigned;
    }

    public void setBranchAssigned(BranchDetail branchAssigned) {
        this.branchAssigned = branchAssigned;
    }

    @Transient
    public boolean isIsFirstTimeLogin() {
        return isFirstTimeLogin;
    }

    public void setIsFirstTimeLogin(boolean isFirstTimeLogin) {
        this.isFirstTimeLogin = isFirstTimeLogin;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_DATE", insertable= false)
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    //@Id
    @Column(name = "USERNAME", nullable = false, unique = true)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

//    @Column(name = "USER_ROLE")
//    public Integer getAccessLevel() {
//        return accessLevel;
//    }
//
//    public void setAccessLevel(Integer accessLevel) {
//        this.accessLevel = accessLevel;
//    }
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ROLE")
    public TellerAssistRole getRole() {
        return role;
    }

    public void setRole(TellerAssistRole role) {
        this.role = role;
    }
}
