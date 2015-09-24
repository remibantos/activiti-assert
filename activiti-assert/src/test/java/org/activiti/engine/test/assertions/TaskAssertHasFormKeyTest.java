package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineTests.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.runtimeService;

/**
 * @author Simon Zambrovski (Holisticon AG)
 */
public class TaskAssertHasFormKeyTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "TaskAssert-hasFormKey.bpmn"
  })
  public void testHasFormKey_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasFormKey"
    );
    // Then
    assertThat(processInstance).task().hasFormKey("formKey");
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasFormKey.bpmn"
  })
  public void testHasFormKey_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasFormKey"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasFormKey("otherFormKey");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "TaskAssert-hasFormKey.bpmn"
  })
  public void testHasFormKey_Null_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "TaskAssert-hasFormKey"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).task().hasFormKey(null);
      }
    });
  }

}
