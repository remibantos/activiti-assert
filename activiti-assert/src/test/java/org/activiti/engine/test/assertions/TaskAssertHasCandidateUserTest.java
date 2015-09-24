package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class TaskAssertHasCandidateUserTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUserPreDefined_Success() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // Then
    ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateUser("candidateUser");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_PreDefined_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateUser("candidateUser");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_Predefined_Removed_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    ProcessEngineTests.taskService().deleteCandidateUser(ProcessEngineTests.taskQuery().singleResult().getId(), "candidateUser");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateUser("candidateUser");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_PreDefined_Other_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    ProcessEngineTests.taskService().deleteCandidateUser(ProcessEngineTests.taskQuery().singleResult().getId(), "candidateUser");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateUser("otherCandidateUser");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_ExplicitelySet_Success() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // And
    ProcessEngineTests.taskService().addCandidateUser(ProcessEngineTests.taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // Then
    ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateUser("explicitCandidateUserId");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_ExplicitelySet_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateUser("candidateUser");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_ExplicitelySet_Removed_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // And
    ProcessEngineTests.taskService().addCandidateUser(ProcessEngineTests.taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // When
    ProcessEngineTests.taskService().deleteCandidateUser(ProcessEngineTests.taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateUser("explicitCandidateUserId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_ExplicitelySet_Other_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // And
    ProcessEngineTests.taskService().addCandidateUser(ProcessEngineTests.taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // When
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateUser("otherCandidateUser");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_MoreThanOne_Success() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    ProcessEngineTests.taskService().addCandidateUser(ProcessEngineTests.taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // Then
    ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateUser("candidateUser");
    // And
    ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateUser("explicitCandidateUserId");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_MoreThanOne_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    ProcessEngineTests.taskService().addCandidateUser(ProcessEngineTests.taskQuery().singleResult().getId(), "explicitCandidateUserId");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateUser("otherCandidateUser");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_Null_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateUser(null);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateUser.bpmn"
  })
  public void testHasCandidateUser_NonExistingTask_Failure() {
    // Given
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateUser"
    );
    // When
    final Task task = ProcessEngineTests.taskQuery().singleResult();
    ProcessEngineTests.complete(task);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(task).hasCandidateUser("candidateUser");
      }
    });
  }

}
