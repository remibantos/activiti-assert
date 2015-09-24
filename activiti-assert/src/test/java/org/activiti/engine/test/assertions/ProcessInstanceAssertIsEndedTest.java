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
public class ProcessInstanceAssertIsEndedTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isEnded.bpmn"
  })
  public void testIsEnded_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isEnded"
    );
    // When
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // Then
    ProcessEngineAssertions.assertThat(processInstance).isEnded();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isEnded.bpmn"
  })
  public void testIsEnded_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isEnded"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).isEnded();
      }
    });
  }

}
