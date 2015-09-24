package org.activiti.engine.test.assertions;

import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import java.util.Date;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.jobQuery;
import static org.activiti.engine.test.assertions.ProcessEngineTests.runtimeService;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAssertHasDueDateTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "JobAssert-hasDueDate.bpmn"
  })
  public void testHasDueDate_Success() {
    // When
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasDueDate"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // Then
    assertThat(jobQuery().singleResult()).hasDueDate(jobQuery().singleResult().getDuedate());
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasDueDate.bpmn"
  })
  public void testHasDueDate_Failure() {
    // When
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasDueDate"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasDueDate(new Date(1000));
      }
    });
  }

  @Test
  @Deployment(resources = {
    "JobAssert-hasDueDate.bpmn"
  })
  public void testHasDueDate_Error_Null() {
    // When
    runtimeService().startProcessInstanceByKey(
      "JobAssert-hasDueDate"
    );
    // Then
    assertThat(jobQuery().singleResult()).isNotNull();
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(jobQuery().singleResult()).hasDueDate(null);
      }
    });
  }

}
