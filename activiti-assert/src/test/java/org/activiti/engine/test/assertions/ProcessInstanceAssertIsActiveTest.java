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
public class ProcessInstanceAssertIsActiveTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isActive.bpmn"
  })
  public void testIsActive_Success() {
    // When
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isActive"
    );
    // Then
    ProcessEngineAssertions.assertThat(processInstance).isActive();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isActive.bpmn"
  })
  public void testIsActive_AfterActivation_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isActive"
    );
    // And
    ProcessEngineTests.runtimeService().suspendProcessInstanceById(processInstance.getId());
    // When
    ProcessEngineTests.runtimeService().activateProcessInstanceById(processInstance.getId());
    // Then
    ProcessEngineAssertions.assertThat(processInstance).isActive();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isActive.bpmn"
  })
  public void testIsActive_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isActive"
    );
    // When
    ProcessEngineTests.runtimeService().suspendProcessInstanceById(processInstance.getId());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).isActive();
      }
    });
  }

}
