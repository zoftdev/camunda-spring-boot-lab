package com.hlex.camundacleantest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;
import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

/**
 * HelloTest
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HelloTest {

	Logger logger=LoggerFactory.getLogger(this.getClass());



    @Autowired
	ProcessEngine processEngine;

    @Before
	public void setUp() {
		// init(processEngine);
    }

    @Test
	public void testHelloFlow(){
        logger.info("pe: {}",processEngine.getName());
        ProcessInstance processInstance= processEngine.getRuntimeService().startProcessInstanceByKey("hello");
        assertThat(processInstance).isStarted();
        assertThat(processInstance).hasVariables("a").variables().containsEntry("a",5);
	}

}