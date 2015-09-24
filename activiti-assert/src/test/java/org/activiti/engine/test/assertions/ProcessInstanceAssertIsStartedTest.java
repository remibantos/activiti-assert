package org.activiti.engine.test.assertions;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ProcessEngineRule;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertIsStartedTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isStarted.bpmn"
  })
  public void testIsStarted_AndActive_Success() {
    // When
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isStarted"
    );
    // Then
    assertThat(processInstance).isStarted();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isStarted.bpmn"
  })
  public void testIsStarted_AndEnded_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isStarted"
    );
    // When
    complete(taskQuery().singleResult());
    // Then
    assertThat(processInstance).isStarted();
  }
  
  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isStarted.bpmn"
  })
  public void testIsStarted_Failure() {
    // When
    final ProcessInstance processInstance = mock(ProcessInstance.class);
    // And
    when(processInstance.getId()).thenReturn("someNonExistingId");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isStarted();
      }
    });
  }

}
