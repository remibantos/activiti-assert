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
public class JobAssertHasRetriesTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasRetries.bpmn"
  })
  public void testHasRetries_Success() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasRetries"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    assertThat(jobQuery().singleResult()).hasRetries(3);
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasRetries.bpmn"
  })
  public void testHasRetries_Failure() {
    // Given
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasRetries"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasRetries(2);
      }
    });
  }

}
