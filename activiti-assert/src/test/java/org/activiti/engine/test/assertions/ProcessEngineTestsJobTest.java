package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.ProcessEngineException;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ProcessEngineRule;
import org.activiti.engine.test.mock.Mocks;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineTestsJobTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @After
  public void tearDown() {
    ProcessEngineAssertions.reset();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // When
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    // Then
    ProcessEngineAssertions.assertThat(ProcessEngineTests.job()).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_OnlyActivity_Failure() {
    // Given
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineTests.job();
      }
    }, IllegalStateException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    // And
    Mocks.register("serviceTask_1", "someService");
    // And
    ProcessEngineTests.execute(ProcessEngineTests.job());
    // When
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineTests.job();
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_activityId_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // When
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    // Then
    ProcessEngineAssertions.assertThat(ProcessEngineTests.job("ServiceTask_1")).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_activityId_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // When
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    // And
    Mocks.register("serviceTask_1", "someService");
    // And
    ProcessEngineTests.execute(ProcessEngineTests.job());
    // Then
    ProcessEngineAssertions.assertThat(ProcessEngineTests.job("ServiceTask_2")).isNotNull();
    // And
    ProcessEngineAssertions.assertThat(ProcessEngineTests.job("ServiceTask_3")).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_activityId_OnlyActivity_Failure() {
    // Given
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineTests.job("ServiceTask_1");
      }
    }, IllegalStateException.class);
  }
  
  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobQuery_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // When
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    // Then
    ProcessEngineAssertions.assertThat(ProcessEngineTests.job(ProcessEngineTests.jobQuery())).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobQuery_OnlyActivity_Failure() {
    // Given
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineTests.job(ProcessEngineTests.jobQuery());
      }
    }, IllegalStateException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobQuery_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    // And
    Mocks.register("serviceTask_1", "someService");
    // And
    ProcessEngineTests.execute(ProcessEngineTests.job());
    // When
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineTests.job(ProcessEngineTests.jobQuery());
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // Then
    ProcessEngineAssertions.assertThat(ProcessEngineTests.job(processInstance)).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_TwoActivities_processInstance_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    Mocks.register("serviceTask_1", "someService");
    // And
    ProcessEngineTests.execute(ProcessEngineTests.job(processInstance));
    // When
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineTests.job(processInstance);
      }
    }, ProcessEngineException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobDefinitionKey_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // Then
    ProcessEngineAssertions.assertThat(ProcessEngineTests.job("ServiceTask_1", processInstance)).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobDefinitionKey_processInstance_TwoActivities_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    Mocks.register("serviceTask_1", "someService");
    // When
    ProcessEngineTests.execute(ProcessEngineTests.job(processInstance));
    // Then
    ProcessEngineAssertions.assertThat(ProcessEngineTests.job("ServiceTask_2", processInstance)).isNotNull();
    // And
    ProcessEngineAssertions.assertThat(ProcessEngineTests.job("ServiceTask_3", processInstance)).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobQuery_processInstance_OnlyActivity_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // Then
    ProcessEngineAssertions.assertThat(ProcessEngineTests.job(ProcessEngineTests.jobQuery(), processInstance)).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-job.bpmn"
  })
  public void testJob_jobQuery_processInstance_TwoActivities_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-job"
    );
    // And
    Mocks.register("serviceTask_1", "someService");
    // And
    ProcessEngineTests.execute(ProcessEngineTests.job(processInstance));
    // When
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineTests.job(ProcessEngineTests.jobQuery(), processInstance);
      }
    }, ProcessEngineException.class);
  }

}
