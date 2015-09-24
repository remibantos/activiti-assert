package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineAssertions.reset;
import static org.activiti.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineTestsCompleteTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @After
  public void tearDown() {
    reset();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-complete.bpmn"
  })
  public void testComplete_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-complete"
    );
    // When
    complete(task(processInstance));
    // Then
    ProcessEngineAssertions.assertThat(processInstance).isEnded();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-complete.bpmn"
  })
  public void testComplete_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-complete"
    );
    // And
    final Task task = task(processInstance);
    // When
    complete(task);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        complete(task);
      }
    }, ActivitiException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-complete.bpmn"
  })
  public void testComplete_WithVariables_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-complete"
    );
    // When
    complete(task(processInstance), withVariables("a", "b"));
    // Then
    ProcessEngineAssertions.assertThat(processInstance).isEnded();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-complete.bpmn"
  })
  public void testComplete_WithVariables_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-complete"
    );
    // And
    final Task task = task(processInstance);
    // When
    complete(task);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        complete(task, withVariables("a", "b"));
      }
    }, ActivitiException.class);
  }

}
