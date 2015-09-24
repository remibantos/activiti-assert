package org.activiti.engine.test.assertions;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.jobQuery;
import static org.activiti.engine.test.assertions.ProcessEngineTests.managementService;
import static org.activiti.engine.test.assertions.ProcessEngineTests.runtimeService;
import static org.assertj.core.api.Fail.fail;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAssertHasExceptionMessageTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasExceptionMessage.bpmn"
  })
  public void testHasExceptionMessage_Success() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasExceptionMessage"
    );
    // When
    try {
      managementService().executeJob(jobQuery().singleResult().getId());
      fail ("expected ActivitiException to be thrown, but did not find any.");
    } catch (ActivitiException t) {}
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    assertThat(jobQuery().singleResult()).hasExceptionMessage();
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasExceptionMessage.bpmn"
  })
  public void testHasExceptionMessage_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasExceptionMessage"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasExceptionMessage();
      }
    });
  }

}
