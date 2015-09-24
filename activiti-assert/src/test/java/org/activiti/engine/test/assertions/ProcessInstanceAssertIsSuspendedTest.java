package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.runtimeService;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertIsSuspendedTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isSuspended.bpmn"
  })
  public void testIsSuspended_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isSuspended"
    );
    // When
    runtimeService().suspendProcessInstanceById(processInstance.getId());
    // Then
    assertThat(processInstance).isSuspended();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isSuspended.bpmn"
  })
  public void testIsSuspended_AfterStart_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isSuspended"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isSuspended();
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isSuspended.bpmn"
  })
  public void testIsSuspended_AfterActivation_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isSuspended"
    );
    // When
    runtimeService().suspendProcessInstanceById(processInstance.getId());
    // And
    runtimeService().activateProcessInstanceById(processInstance.getId());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isSuspended();
      }
    });
  }

}
