
package ng.tellerassist.tellerassist.auth.dao;

import ng.tellerassist.tellerassist.entity.TellerAssistUserDetail;

public interface TellerAssistAuthDAO {
  
    public abstract TellerAssistUserDetail findUser(String username );
}
