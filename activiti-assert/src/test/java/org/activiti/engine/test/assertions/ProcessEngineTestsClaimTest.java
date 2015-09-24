package org.activiti.engine.test.assertions;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineAssertions.reset;
import static org.activiti.engine.test.assertions.ProcessEngineTests.claim;
import static org.activiti.engine.test.assertions.ProcessEngineTests.runtimeService;
import static org.activiti.engine.test.assertions.ProcessEngineTests.task;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineTestsClaimTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @After
  public void tearDown() {
    reset();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-claim.bpmn"
  })
  public void testClaim_Success() {
    // Given
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-claim"
    );
    // When
    claim(task(processInstance), "fozzie");
    // Then
    assertThat(task(processInstance)).isNotNull().hasDefinitionKey("UserTask_1").isAssignedTo("fozzie");
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-claim.bpmn"
  })
  public void testClaim_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-claim"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        claim(task("UserTask_2", processInstance), "fozzie");
      }
    }, IllegalArgumentException.class);
  }

}
