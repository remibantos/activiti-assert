package org.activiti.engine.test.assertions;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.job;
import static org.activiti.engine.test.assertions.ProcessEngineTests.runtimeService;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAssertHasActivityIdTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasActivityId.bpmn"
  })
  public void testHasActivityId_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "JobAssert-hasActivityId"
    );
    // And
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();    
    // Then
    assertThat(job()).isNotNull();
    // And
    assertThat(job()).hasActivityId("ServiceTask_1");
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasActivityId.bpmn"
  })
  public void testHasActivityId_Failure() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "JobAssert-hasActivityId"
    );
    // And
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    // Then
    assertThat(job()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(job()).hasActivityId("otherDeploymentId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasActivityId.bpmn"
  })
  public void testHasActivityId_Error_Null() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "JobAssert-hasActivityId"
    );
    // And
    ProcessEngineAssertions.assertThat(processInstance).isNotNull();
    // Then
    assertThat(job()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(job()).hasActivityId(null);
      }
    });
  }

}
