package com.hlex.camundacleantest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

import java.util.HashMap;
import java.util.Map;

/**
 * HelloTest
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EmailTest {

	Logger logger=LoggerFactory.getLogger(this.getClass());



    @Autowired
	ProcessEngine processEngine;


    @Test
	public void testHelloFlow(){
        logger.info("pe: {}",processEngine.getName());
        Map<String,Object>vals=new HashMap<>();
        vals.put("requestor","user1");
        ProcessInstance processInstance= processEngine.getRuntimeService().startProcessInstanceByKey("emailProcess",vals);
        assertThat(processInstance).isStarted();
        assertThat(processInstance)
        .hasVariables("email_to")
        .hasPassed("ServiceTask_mailReq");
	}

}