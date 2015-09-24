package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.claim;
import static org.activiti.engine.test.assertions.ProcessEngineTests.runtimeService;
import static org.activiti.engine.test.assertions.ProcessEngineTests.taskQuery;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class TaskAssertIsNotAssignedTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "TaskAssert-isNotAssigned.bpmn"
  })
  public void testIsNotAssigned_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-isNotAssigned"
    );
    // Then
    assertThat(processInstance).task().isNotAssigned();
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-isNotAssigned.bpmn"
  })
  public void testIsNotAssigned_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-isNotAssigned"
    );
    // When
    claim(taskQuery().singleResult(), "fozzie");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().isNotAssigned();
      }
    });
  }
  
}
