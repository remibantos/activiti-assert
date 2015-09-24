package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.ProcessEngineException;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertTaskTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_Single_Success() {
    // When
    final ProcessInstance processInstance = startProcess();
    // Then
    ProcessEngineAssertions.assertThat(processInstance).task().isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_SingleWithQuery_Success() {
    // When
    final ProcessInstance processInstance = startProcess();
    // Then
    ProcessEngineAssertions.assertThat(processInstance).task(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_1")).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_MultipleWithQuery_Success() {
    // When
    final ProcessInstance processInstance = startProcess();
    // And
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // Then
    ProcessEngineAssertions.assertThat(processInstance).task(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_2")).isNotNull();
    // And
    ProcessEngineAssertions.assertThat(processInstance).task(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_3")).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_NotYet_Failure() {
    // When
    final ProcessInstance processInstance = startProcess();
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_2")).isNotNull();
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_Passed_Failure() {
    // Given
    final ProcessInstance processInstance = startProcess();
    // When
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_1")).isNotNull();
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_MultipleWithQuery_Failure() {
    // When
    final ProcessInstance processInstance = startProcess();
    // And
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_1").singleResult());
    // And
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // And
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_3").singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_4")).isNotNull();
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_MultipleWithTaskDefinitionKey_Success() {
    // When
    final ProcessInstance processInstance = startProcess();
    // And
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // Then
    ProcessEngineAssertions.assertThat(processInstance).task("UserTask_2").isNotNull();
    // And
    ProcessEngineAssertions.assertThat(processInstance).task("UserTask_3").isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_MultipleWithTaskDefinitionKey_Failure() {
    // When
    final ProcessInstance processInstance = startProcess();
    // And
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_1").singleResult());
    // And
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_2").singleResult());
    // And
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_3").singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task("UserTask_4").isNotNull();
      }
    }, ProcessEngineException.class);
  }


  @Test
  @Deployment(resources = {
      "ProcessInstanceAssert-task.bpmn"
  })
  public void testTask_notWaitingAtTaskDefinitionKey() {
    final ProcessInstance processInstance = startProcess();
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task("UserTask_2").isNotNull();
      }
    });
  }

  private ProcessInstance startProcess() {
    return ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-task"
    );
  }
  
}
