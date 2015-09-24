package org.activiti.engine.test.assertions;

import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAssertHasDeploymentIdTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasDeploymentId.bpmn"
  })
  public void testHasDeploymentId_Success() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasDeploymentId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    assertThat(jobQuery().singleResult()).hasProcessDefinitionId(processDefinitionQuery().processDefinitionId(processInstanceQuery().singleResult().getProcessDefinitionId()).singleResult().getDeploymentId());
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasDeploymentId.bpmn"
  })
  public void testHasDeploymentId_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasDeploymentId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(jobQuery().singleResult()).hasProcessDefinitionId("otherDeploymentId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasDeploymentId.bpmn"
  })
  public void testHasDeploymentId_Error_Null() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasDeploymentId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(jobQuery().singleResult()).hasProcessDefinitionId(null);
      }
    });
  }

}
