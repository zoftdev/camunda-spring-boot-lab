package com.hlex.camundacleantest.email;

import org.apache.logging.log4j.util.Strings;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * com.hlex.camundacleantest.email.FindRequestorDelegate
 */
@Component
public class FindRequestorDelegate implements JavaDelegate {

    Logger logger=LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    ProcessEngine processEngine;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("run {}",getClass());
        String userId= (String) execution.getVariable("requestor");
        User reqUser=processEngine.getIdentityService().createUserQuery().userId(userId).singleResult();
        if(Strings.isBlank(reqUser.getEmail())){
            throw new RuntimeException("requestor has no email");
        }
        execution.setVariable("email_to",reqUser.getEmail());
        execution.setVariable("email_subject","order has been process");
        execution.setVariable("email_text","we are process your order");

        
    }

    
}