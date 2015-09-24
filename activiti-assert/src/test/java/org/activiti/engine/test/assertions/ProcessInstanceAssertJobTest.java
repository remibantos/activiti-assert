package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.mock.Mocks;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertJobTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_Single_Success() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // Then
    ProcessEngineAssertions.assertThat(processInstance).job().isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_SingleWithQuery_Success() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // Then
    ProcessEngineAssertions.assertThat(processInstance).job(ProcessEngineTests.jobQuery().executable()).isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_MultipleWithQuery_Success() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // Then
    ProcessEngineAssertions.assertThat(processInstance).job("ServiceTask_1").isNotNull();
    // And
    Mocks.register("serviceTask_1", "someService");
    // And
    ProcessEngineTests.execute(ProcessEngineTests.jobQuery().singleResult());
    // Then
    ProcessEngineAssertions.assertThat(processInstance).job("ServiceTask_2").isNotNull();
    // And
    ProcessEngineAssertions.assertThat(processInstance).job("ServiceTask_3").isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_NotYet_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).job("ServiceTask_2").isNotNull();
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_Passed_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // Then
    Mocks.register("serviceTask_1", "someService");
    // When
    ProcessEngineTests.execute(ProcessEngineTests.jobQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task("ServiceTask_1").isNotNull();
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_MultipleWithQuery_Failure() {
    // Given
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // And
    Mocks.register("serviceTask_1", "someService");
    ProcessEngineTests.execute(ProcessEngineTests.jobQuery().singleResult());
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).job(ProcessEngineTests.jobQuery().executable()).isNotNull();
      }
    }, ActivitiException.class);
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_MultipleWithTaskDefinitionKey_Success() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // And
    Mocks.register("serviceTask_1", "someService");
    ProcessEngineTests.execute(ProcessEngineTests.jobQuery().singleResult());
    // Then
    ProcessEngineAssertions.assertThat(processInstance).job("ServiceTask_2").isNotNull();
    // And
    ProcessEngineAssertions.assertThat(processInstance).job("ServiceTask_3").isNotNull();
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-job.bpmn"
  })
  public void testJob_MultipleWithTaskDefinitionKey_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-job"
    );
    // And
    Mocks.register("serviceTask_1", "someService");
    ProcessEngineTests.execute(ProcessEngineTests.jobQuery().list().get(0));
    // And
    Mocks.register("serviceTask_2", "someService");
    ProcessEngineTests.execute(ProcessEngineTests.jobQuery().list().get(0));
    // And
    Mocks.register("serviceTask_3", "someService");
    ProcessEngineTests.execute(ProcessEngineTests.jobQuery().list().get(0));
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).job("ServiceTask_4").isNotNull();
      }
    }, ActivitiException.class);
  }

}
