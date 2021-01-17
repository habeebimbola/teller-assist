
package ng.tellerassist.tellerassist.beans.validators;

import ng.tellerassist.tellerassist.entity.TellerAssistUserDetail;
import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;

import javax.inject.Inject;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class NewUserValidator implements Validator{
    
    private final TransactionServiceDefinition serviceDefinition;
    
    @Inject
    public NewUserValidator(TransactionServiceDefinition serviceDefinition)
    {
        this.serviceDefinition = serviceDefinition;
    }
    public void userExists(TransactionServiceDefinition serviceDefinition, TellerAssistUserDetail userDetail, BindingResult result )
    {
        TellerAssistUserDetail tellerUser = (TellerAssistUserDetail)serviceDefinition.findByPropertyNameValue(TellerAssistUserDetail.class,"username",userDetail.getUsername());
        if(tellerUser != null )
        {
            result.rejectValue("username", "username", "Username Already Exists. Please Choose A Different Username.");
            return;
        }
        if(tellerUser.getPassword().contentEquals(tellerUser.getConfirmPassword()))
        {
            result.rejectValue("passsword", "passsword", "Password Not Confirmed. Please Confirm Your Password.");
            return;
        }
        if(tellerUser.getBranchAssigned().getId() == -1 )
        {
            result.rejectValue("branchAssigned.id", "branchAssigned.id", "User Not Assigned To A Branch. Please Assign User To Branch.");
            return;
        }
        if(tellerUser.getRole().getId() == -1 )
        {
            result.rejectValue("role.id", "role.id", "User Not Assigned To Role. Please Assign User To A Role.");
        }
    }

    @Override
    public boolean supports(Class<?> type) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void validate(Object o, Errors errors) {
        TellerAssistUserDetail tellerUser =(TellerAssistUserDetail)o;
        tellerUser = (TellerAssistUserDetail)serviceDefinition.findByPropertyNameValue(TellerAssistUserDetail.class,"username",tellerUser.getUsername());
        if(tellerUser != null )
        {
            errors.rejectValue("username", "username", "Username Already Exists. Please Choose A Different Username.");
            return;
        }
        if(tellerUser.getPassword().contentEquals(tellerUser.getConfirmPassword()))
        {
            errors.rejectValue("passsword", "passsword", "Password Not Confirmed. Please Confirm Your Password.");
            return;
        }
        if(tellerUser.getBranchAssigned().getId() == -1 )
        {
            errors.rejectValue("branchAssigned.id", "branchAssigned.id", "User Not Assigned To A Branch. Please Assign User To Branch.");
            return;
        }
        if(tellerUser.getRole().getId() == -1 )
        {
            errors.rejectValue("role.id", "role.id", "User Not Assigned To Role. Please Assign User To A Role.");
        }
        //builder.
        //numbers.toString().trim().toString().t
    }
}
