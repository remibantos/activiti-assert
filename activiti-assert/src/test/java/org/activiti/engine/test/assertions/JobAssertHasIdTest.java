package org.activiti.engine.test.assertions;

import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.jobQuery;
import static org.activiti.engine.test.assertions.ProcessEngineTests.runtimeService;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAssertHasIdTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasId.bpmn"
  })
  public void testHasId_Success() {
    // When
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    assertThat(jobQuery().singleResult()).hasId(jobQuery().singleResult().getId());
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasId.bpmn"
  })
  public void testHasId_Failure() {
    // When
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasId("otherId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasId.bpmn"
  })
  public void testHasId_Error_Null() {
    // When
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasId(null);
      }
    });
  }

}
