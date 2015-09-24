package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertHasNoVariablesTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasNoVariables.bpmn"
  })
  public void testHasNoVariables_None_Success() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasNoVariables"
    );
    // Then
    ProcessEngineAssertions.assertThat(processInstance).hasNoVariables();
    // When
    ProcessEngineTests.complete(ProcessEngineTests.task(processInstance));
    // Then
    ProcessEngineAssertions.assertThat(processInstance).hasNoVariables();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasNoVariables.bpmn"
  })
  public void testHasNoVariables_One_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasNoVariables", ProcessEngineTests.withVariables("aVariable", "aValue")
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasNoVariables();
      }
    });
    // When
    ProcessEngineTests.complete(ProcessEngineTests.task(processInstance));
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasNoVariables();
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasNoVariables.bpmn"
  })
  public void testHasNoVariables_Two_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasNoVariables", ProcessEngineTests.withVariables("firstVariable", "firstValue", "secondVariable", "secondValue")
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasNoVariables();
      }
    });
    // When
    ProcessEngineTests.complete(ProcessEngineTests.task(processInstance));
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasNoVariables();
      }
    });
  }

}
