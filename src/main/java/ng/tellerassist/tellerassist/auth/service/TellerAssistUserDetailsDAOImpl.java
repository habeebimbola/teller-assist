package ng.tellerassist.tellerassist.auth.service;

import ng.tellerassist.tellerassist.auth.dao.TellerAssistAuthDAO;
import ng.tellerassist.tellerassist.entity.TellerAssistUserDetail;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class TellerAssistUserDetailsDAOImpl implements TellerAssistAuthDAO {

    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public TellerAssistUserDetail findUser(String username) throws UsernameNotFoundException {
        TellerAssistUserDetail tellerAssistUser = null;
        String userExistStr = "from TellerAssistUserDetail u where upper(u.username)=upper(:uname)";

//        Query userExistQuery = this.sessionFactory.openSession().createQuery(userExistStr);
//        userExistQuery.setParameter("uname", username);
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(TellerAssistUserDetail.class);

        crit.add(Restrictions.ilike("username", username, MatchMode.EXACT));

        if (crit.uniqueResult() != null) {
            tellerAssistUser = (TellerAssistUserDetail) crit.uniqueResult();
            return tellerAssistUser;
        }
        throw new UsernameNotFoundException("User does not exist");

    }

}
