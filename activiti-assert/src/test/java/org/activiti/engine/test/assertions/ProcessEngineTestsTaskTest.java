package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.task;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineTestsTaskTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @After
  public void tearDown() {
    ProcessEngineAssertions.reset();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    // Then
    ProcessEngineAssertions.assertThat(task()).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_OnlyActivity_Failure() {
    // Given
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        task();
      }
    }, IllegalStateException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    // And
    ProcessEngineTests.complete(task());
    // When
    expect(new Failure() {
      @Override
      public void when() {
        task();
      }
    }, ActivitiException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    // Then
    ProcessEngineAssertions.assertThat(task("UserTask_1")).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    ProcessEngineTests.complete(task());
    // Then
    ProcessEngineAssertions.assertThat(task("UserTask_2")).isNotNull().hasDefinitionKey("UserTask_2");
    // And
    ProcessEngineAssertions.assertThat(task("UserTask_3")).isNotNull().hasDefinitionKey("UserTask_3");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_OnlyActivity_Failure() {
    // Given
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        task("UserTask_1");
      }
    }, IllegalStateException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    // Then
    ProcessEngineAssertions.assertThat(task(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_1"))).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_OnlyActivity_Failure() {
    // Given
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        task(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_1"));
      }
    }, IllegalStateException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    ProcessEngineTests.complete(task());
    // Then
    ProcessEngineAssertions.assertThat(task(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_2"))).isNotNull().hasDefinitionKey("UserTask_2");
    // And
    ProcessEngineAssertions.assertThat(task(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_3"))).isNotNull().hasDefinitionKey("UserTask_3");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    // And
    ProcessEngineTests.complete(task());
    // When
    expect(new Failure() {
      @Override
      public void when() {
        task(ProcessEngineTests.taskQuery());
      }
    }, ActivitiException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    ProcessEngineAssertions.assertThat(task(processInstance)).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_TwoActivities_processInstance_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.complete(task(processInstance));
    // When
    expect(new Failure() {
      @Override
      public void when() {
        task(processInstance);
      }
    }, ActivitiException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    ProcessEngineAssertions.assertThat(task("UserTask_1", processInstance)).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskDefinitionKey_processInstance_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    ProcessEngineTests.complete(task(processInstance));
    // Then
    ProcessEngineAssertions.assertThat(task("UserTask_2", processInstance)).isNotNull().hasDefinitionKey("UserTask_2");
    // And
    ProcessEngineAssertions.assertThat(task("UserTask_3", processInstance)).isNotNull().hasDefinitionKey("UserTask_3");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // Then
    ProcessEngineAssertions.assertThat(task(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_1"), processInstance)).isNotNull().hasDefinitionKey("UserTask_1");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_processInstance_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // When
    ProcessEngineTests.complete(task(processInstance));
    // Then
    ProcessEngineAssertions.assertThat(task(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_2"), processInstance)).isNotNull().hasDefinitionKey("UserTask_2");
    // And
    ProcessEngineAssertions.assertThat(task(ProcessEngineTests.taskQuery().taskDefinitionKey("UserTask_3"), processInstance)).isNotNull().hasDefinitionKey("UserTask_3");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-task.bpmn"
  })
  public void testTask_taskQuery_processInstance_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-task"
    );
    // And
    ProcessEngineTests.complete(task(processInstance));
    // When
    expect(new Failure() {
      @Override
      public void when() {
        task(ProcessEngineTests.taskQuery(), processInstance);
      }
    }, ActivitiException.class);
  }

}
