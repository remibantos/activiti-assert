package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.jobQuery;
import static org.activiti.engine.test.assertions.ProcessEngineTests.runtimeService;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAssertHasProcessInstanceIdTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasProcessInstanceId.bpmn"
  })
  public void testHasProcessInstanceId_Success() {
    // When
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "JobAssert-hasProcessInstanceId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    assertThat(jobQuery().singleResult()).hasProcessInstanceId(processInstance.getProcessInstanceId());
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasProcessInstanceId.bpmn"
  })
  public void testHasProcessInstanceId_Failure() {
    // When
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasProcessInstanceId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasProcessInstanceId("someOtherId");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasProcessInstanceId.bpmn"
  })
  public void testHasProcessInstanceId_Error_Null() {
    // When
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasProcessInstanceId"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasProcessInstanceId(null);
      }
    });
  }

}
