package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class TaskAssertIsAssignedToTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "TaskAssert-isAssignedTo.bpmn"
  })
  public void testIsAssignedTo_Success() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-isAssignedTo"
    );
    // When
    ProcessEngineTests.claim(ProcessEngineTests.taskQuery().singleResult(), "fozzie");
    // Then
    ProcessEngineAssertions.assertThat(processInstance).task().isAssignedTo("fozzie");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-isAssignedTo.bpmn"
  })
  public void testIsAssignedTo_NotAssigned_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-isAssignedTo"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().isAssignedTo("fozzie");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-isAssignedTo.bpmn"
  })
  public void testIsAssignedTo_OtherAssignee_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-isAssignedTo"
    );
    // When
    ProcessEngineTests.claim(ProcessEngineTests.taskQuery().singleResult(), "fozzie");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().isAssignedTo("gonzo");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-isAssignedTo.bpmn"
  })
  public void testIsAssignedTo_Null_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-isAssignedTo"
    );
    // When
    ProcessEngineTests.claim(ProcessEngineTests.taskQuery().singleResult(), "fozzie");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().isAssignedTo(null);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-isAssignedTo.bpmn"
  })
  public void testIsAssignedTo_NonExistingTask_Failure() {
    // Given
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-isAssignedTo"
    );
    // When
    final Task task = ProcessEngineTests.taskQuery().singleResult();
    ProcessEngineTests.complete(task);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(task).isAssignedTo("fozzie");
      }
    });
  }

}
