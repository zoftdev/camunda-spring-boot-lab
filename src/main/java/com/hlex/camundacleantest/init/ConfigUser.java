package com.hlex.camundacleantest.init;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * ConfigUser
 */
@Component
@Profile("dev")
public class ConfigUser {

    @Value("${init.isAddBasicUser}")
    boolean isAddBasicUser;

    @Autowired
    ProcessEngine ProcessEngine;
    @EventListener
    public void createUser(PostDeployEvent postDeployEvent){
        Group officer=ProcessEngine.getIdentityService().newGroup("officer");
        officer.setName("Common Officer");
        ProcessEngine.getIdentityService().saveGroup(officer);
        Authorization global= ProcessEngine.getAuthorizationService().createNewAuthorization(Authorization.AUTH_TYPE_GLOBAL);
        global.setResourceType(0); //.setPermissions(new Permission[]{Permission.ALL});
        global.setResourceId("tasklist");
        global.addPermission(Permissions.ALL);
        ProcessEngine.getAuthorizationService().saveAuthorization(global);


        
        if(isAddBasicUser){
            User user1=ProcessEngine.getIdentityService().newUser("user1");
            user1.setPassword("user1");
            user1.setFirstName("user1");
            user1.setLastName("user1");
            ProcessEngine.getIdentityService().saveUser(user1);
        }




    }
}