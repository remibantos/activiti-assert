package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineTests.assertThat;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertHasProcessDefinitionKeyTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasProcessDefinitionKey-1.bpmn",
    "ProcessInstanceAssert-hasProcessDefinitionKey-2.bpmn"
  })
  public void testHasProcessDefinitionKey_Success() {
    // When
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasProcessDefinitionKey-1"
    );
    // Then
    ProcessEngineAssertions.assertThat(processInstance).hasProcessDefinitionKey("ProcessInstanceAssert-hasProcessDefinitionKey-1");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasProcessDefinitionKey-1.bpmn",
    "ProcessInstanceAssert-hasProcessDefinitionKey-2.bpmn"
  })
  public void testHasProcessDefinitionKey_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasProcessDefinitionKey-2"
    );
    // When
    ProcessEngineTests.runtimeService().suspendProcessInstanceById(processInstance.getId());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasProcessDefinitionKey("ProcessInstanceAssert-hasProcessDefinitionKey-1");
      }
    });
  }

}
