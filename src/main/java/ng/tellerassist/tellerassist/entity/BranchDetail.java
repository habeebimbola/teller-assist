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
@Table(name = "BRANCH_DETAILS")
public class BranchDetail implements Serializable {

    private Integer id;
    private String name;
    private String address;

    @Id
    @Column(name = "BRANCH_ID")
    @GeneratedValue(generator = "increment", strategy = GenerationType.AUTO)
//    @GenericGenerator(name="increment",strategy="increment")
    @SequenceGenerator(name = "increment", sequenceName = "BRANCH_DETAIL_SEQ")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "BRANCH_NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "BRANCH_ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
