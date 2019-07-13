package com.hlex.camundacleantest.init;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.Permissions;
import org.camunda.bpm.engine.authorization.Resources;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * ConfigUser
 */
@Configuration
@ConfigurationProperties(prefix = "config-user")
public class ConfigUser {

    
    boolean addBasicUser;
    boolean runSampleFlow;

    

    @Autowired
    ProcessEngine ProcessEngine;

    @EventListener
    public void createUser(PostDeployEvent postDeployEvent) {
        
        Group officer = ProcessEngine.getIdentityService().newGroup("officer");
        officer.setName("Common Officer");
        ProcessEngine.getIdentityService().saveGroup(officer);


        Authorization global = ProcessEngine.getAuthorizationService()
                .createNewAuthorization(Authorization.AUTH_TYPE_GLOBAL);

        //application.task to all
        global.setResourceType(0);
        global.setResourceId("tasklist");
        global.addPermission(Permissions.ALL);
        ProcessEngine.getAuthorizationService().saveAuthorization(global);

        //filter.read to all
        global = ProcessEngine.getAuthorizationService()
                .createNewAuthorization(Authorization.AUTH_TYPE_GLOBAL);
        global.setResourceType(5); // .setPermissions(new Permission[]{Permission.ALL});
        global.setResourceId("*");
        global.addPermission(Permissions.READ);
        ProcessEngine.getAuthorizationService().saveAuthorization(global);


        if (addBasicUser) {
            // config user1 to user group
            User user1 = ProcessEngine.getIdentityService().newUser("user1");
            user1.setPassword("user1");
            user1.setFirstName("user1");
            user1.setLastName("user1");
            user1.setEmail("user1@localhost");
            ProcessEngine.getIdentityService().saveUser(user1);
            ProcessEngine.getIdentityService().createMembership("user1", "officer");

            grantCreateProcessInstance(ProcessEngine.getAuthorizationService(), "officer", "hello");
            grantCreateProcessInstance(ProcessEngine.getAuthorizationService(), "officer", "approveProcess");

            // grant create to PROCESS_INSTANCE .*
            Authorization grantLaunchProcess = ProcessEngine.getAuthorizationService()
                    .createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
            grantLaunchProcess.setResource(Resources.PROCESS_INSTANCE);
            grantLaunchProcess.setResourceId("*");
            grantLaunchProcess.setGroupId("officer");
            grantLaunchProcess.setPermissions(new Permissions[] { Permissions.CREATE });
            ProcessEngine.getAuthorizationService().saveAuthorization(grantLaunchProcess);

            // ==== config opertation1 user in group operation,officer
            Group operation = ProcessEngine.getIdentityService().newGroup("operation");
            operation.setName("Operation Team");
            ProcessEngine.getIdentityService().saveGroup(operation);

            User operation1 = ProcessEngine.getIdentityService().newUser("operation1");
            operation1.setPassword("operation1");
            operation1.setFirstName("operation1");
            operation1.setLastName("operation1");
            operation1.setEmail("operation1@localhost");
            ProcessEngine.getIdentityService().saveUser(operation1);
            ProcessEngine.getIdentityService().createMembership("operation1", "officer");
            ProcessEngine.getIdentityService().createMembership("operation1", "operation");

            if(runSampleFlow){
                ProcessEngine.getRuntimeService().startProcessInstanceByKey("approveProcess","myusername");
            }
        }

    }

    // grant CREATE_INSTANCE,READ to PROCESS_DEFINITION('hello')
    private void grantCreateProcessInstance(AuthorizationService authorizationService, String group,
            String processKey) {
        Authorization grantLaunchProcess = authorizationService.createNewAuthorization(Authorization.AUTH_TYPE_GRANT);
        grantLaunchProcess.setResource(Resources.PROCESS_DEFINITION);
        grantLaunchProcess.setResourceId(processKey);
        grantLaunchProcess.setGroupId(group);
        grantLaunchProcess.setPermissions(new Permissions[] { Permissions.CREATE_INSTANCE, Permissions.READ });
        authorizationService.saveAuthorization(grantLaunchProcess);
    }


    public ConfigUser() {
    }

    public ConfigUser(boolean addBasicUser, boolean runSampleFlow, ProcessEngine ProcessEngine) {
        this.addBasicUser = addBasicUser;
        this.runSampleFlow = runSampleFlow;
        this.ProcessEngine = ProcessEngine;
    }

    public boolean isAddBasicUser() {
        return this.addBasicUser;
    }

    public boolean getAddBasicUser() {
        return this.addBasicUser;
    }

    public void setAddBasicUser(boolean addBasicUser) {
        this.addBasicUser = addBasicUser;
    }

    public boolean isRunSampleFlow() {
        return this.runSampleFlow;
    }

    public boolean getRunSampleFlow() {
        return this.runSampleFlow;
    }

    public void setRunSampleFlow(boolean runSampleFlow) {
        this.runSampleFlow = runSampleFlow;
    }

    

    public void setProcessEngine(ProcessEngine ProcessEngine) {
        this.ProcessEngine = ProcessEngine;
    }

    public ConfigUser addBasicUser(boolean addBasicUser) {
        this.addBasicUser = addBasicUser;
        return this;
    }

    public ConfigUser runSampleFlow(boolean runSampleFlow) {
        this.runSampleFlow = runSampleFlow;
        return this;
    }
 

    @Override
    public String toString() {
        return "{" +
            " addBasicUser='" + isAddBasicUser() + "'" +
            ", runSampleFlow='" + isRunSampleFlow() + "'" +
          
            "}";
    }

}