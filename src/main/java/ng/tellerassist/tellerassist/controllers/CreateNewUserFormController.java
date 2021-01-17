package ng.tellerassist.tellerassist.controllers;

import ng.tellerassist.tellerassist.beans.validators.NewUserValidator;
import ng.tellerassist.tellerassist.entity.BranchDetail;
import ng.tellerassist.tellerassist.entity.TellerAssistRole;
import ng.tellerassist.tellerassist.entity.TellerAssistUserDetail;
import ng.tellerassist.tellerassist.enums.PersistenceStatusEnum;
import ng.tellerassist.tellerassist.serviceDefinition.TransactionServiceDefinition;
import ng.tellerassist.tellerassist.serviceDefinition.UserServiceDefinition;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping(value = "newUserCreate.action")
@SessionAttributes(types = TellerAssistUserDetail.class)
public class CreateNewUserFormController {
    
    @Inject
    private NewUserValidator userValidator;
    
    @Inject
    private TransactionServiceDefinition serviceDefinition;

    @Inject
    private UserServiceDefinition userService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String setupForm(@RequestParam(value="svd", required = false) Integer save, HttpServletRequest request, HttpServletResponse response, Model model) {
        TellerAssistUserDetail userDetail = new TellerAssistUserDetail();
        
        if((save != null) && (save == PersistenceStatusEnum.SAVED.getValue() ))
        {
            userDetail.setSuccessMessage("New user has been successfully created.");
            model.addAttribute("saved", true );
        }
        model.addAttribute("tellerAssistUser", userDetail);
        return "createNewUserForm";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processSubmission(@Valid() @ModelAttribute("tellerAssistUser") TellerAssistUserDetail userDetail, BindingResult result) {
        
        this.userValidator.userExists(this.serviceDefinition, userDetail,result);
        
        if(result.hasErrors())
        {
            return "createNewUserForm";
        }
        this.userService.saveUser(userDetail);
        return "redirect:newUserCreate.action";
    }
    
    @ModelAttribute(value = "roles")
    public List<TellerAssistRole> getRoles()
    {
        return (ArrayList<TellerAssistRole>)this.serviceDefinition.findAll(TellerAssistRole.class);
    }
    
    @ModelAttribute(value="branches")
    public List<BranchDetail> getBranches()
    {
        List<BranchDetail> returnList = (ArrayList<BranchDetail>)this.serviceDefinition.findAll(BranchDetail.class);
        return returnList;
    }
}
