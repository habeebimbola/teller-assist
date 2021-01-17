
package ng.tellerassist.tellerassist.servicesImplementation;

import ng.tellerassist.tellerassist.auth.service.PasswordEncodingService;
import ng.tellerassist.tellerassist.entity.TellerAssistUserDetail;
import ng.tellerassist.tellerassist.serviceDefinition.UserServiceDefinition;
import javax.inject.Inject;
import javax.inject.Named;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=false,propagation=Propagation.REQUIRES_NEW)
public class UserServiceImpl implements UserServiceDefinition{
    @Inject
    private SessionFactory sessionFactory;
    @Inject
    @Named(value="passwordService")
    private PasswordEncodingService passwordService;
    
    @Override
    public void saveUser(TellerAssistUserDetail tellerUser)
    {
        tellerUser.setEnabled(true);
        tellerUser.setPassword(this.passwordService.encodeUserPassword(tellerUser.getPassword()));
        this.sessionFactory.getCurrentSession().save(tellerUser);
    }
}
