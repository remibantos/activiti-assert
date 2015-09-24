package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.jobQuery;
import static org.activiti.engine.test.assertions.ProcessEngineTests.runtimeService;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAssertHasExecutionIdTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasExecutionId.bpmn"
  })
  public void testHasExecutionId_Success() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasExecutionId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    assertThat(jobQuery().singleResult()).hasExecutionId(jobQuery().singleResult().getExecutionId());
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasExecutionId.bpmn"
  })
  public void testHasExecutionId_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasExecutionId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasExecutionId("otherExecutionId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasExecutionId.bpmn"
  })
  public void testHasExecutionId_Error_Null() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasExecutionId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasExecutionId(null);
      }
    });
  }

}
