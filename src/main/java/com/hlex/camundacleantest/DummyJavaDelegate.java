package com.hlex.camundacleantest;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DummyJavaDelegate
 */
public class DummyJavaDelegate implements JavaDelegate {

    Logger logger=LoggerFactory.getLogger(this.getClass());
    
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("dummy task:"+execution.getCurrentActivityName());
	}

    
}