package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineTests.assertThat;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class TaskAssertHasDescriptionTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "TaskAssert-hasDescription.bpmn"
  })
  public void testHasDescription_Success() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasDescription"
    );
    // Then
    ProcessEngineAssertions.assertThat(processInstance).task().hasDescription("description");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasDescription.bpmn"
  })
  public void testHasDescription_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasDescription"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasDescription("otherDescription");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasDescription.bpmn"
  })
  public void testHasDescription_Null_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasDescription"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).task().hasDescription(null);
      }
    });
  }

}
