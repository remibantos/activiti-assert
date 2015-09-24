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
public class TaskAssertHasCandidateGroupTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_PreDefined_Success() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // Then
    ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateGroup("candidateGroup");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_PreDefined_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateGroup("candidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_Predefined_Removed_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    ProcessEngineTests.taskService().deleteCandidateGroup(ProcessEngineTests.taskQuery().singleResult().getId(), "candidateGroup");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateGroup("candidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_PreDefined_Other_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    ProcessEngineTests.taskService().deleteCandidateGroup(ProcessEngineTests.taskQuery().singleResult().getId(), "candidateGroup");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateGroup("otherCandidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_ExplicitelySet_Success() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // And
    ProcessEngineTests.taskService().addCandidateGroup(ProcessEngineTests.taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // Then
    ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateGroup("explicitCandidateGroupId");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_ExplicitelySet_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateGroup("candidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_ExplicitelySet_Removed_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // And
    ProcessEngineTests.taskService().addCandidateGroup(ProcessEngineTests.taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // When
    ProcessEngineTests.taskService().deleteCandidateGroup(ProcessEngineTests.taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateGroup("explicitCandidateGroupId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_ExplicitelySet_Other_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    ProcessEngineTests.complete(ProcessEngineTests.taskQuery().singleResult());
    // And
    ProcessEngineTests.taskService().addCandidateGroup(ProcessEngineTests.taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // When
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateGroup("otherCandidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_MoreThanOne_Success() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    ProcessEngineTests.taskService().addCandidateGroup(ProcessEngineTests.taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // Then
    ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateGroup("candidateGroup");
    // And
    ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateGroup("explicitCandidateGroupId");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_MoreThanOne_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    ProcessEngineTests.taskService().addCandidateGroup(ProcessEngineTests.taskQuery().singleResult().getId(), "explicitCandidateGroupId");
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateGroup("otherCandidateGroup");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_Null_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasCandidateGroup(null);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasCandidateGroup.bpmn"
  })
  public void testHasCandidateGroup_NonExistingTask_Failure() {
    // Given
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasCandidateGroup"
    );
    // When
    final Task task = ProcessEngineTests.taskQuery().singleResult();
    ProcessEngineTests.complete(task);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(task).hasCandidateGroup("candidateGroup");
      }
    });
  }

}
