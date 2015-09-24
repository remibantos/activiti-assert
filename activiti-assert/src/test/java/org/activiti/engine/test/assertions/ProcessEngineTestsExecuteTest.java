package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineTestsExecuteTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-execute.bpmn"
  })
  public void testExecute_Success() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-execute"
    );
    // Then
    ProcessEngineAssertions.assertThat(processInstance).isNotEnded();
    // And
    ProcessEngineAssertions.assertThat(ProcessEngineTests.job()).isNotNull();
    // When
    ProcessEngineTests.execute(ProcessEngineTests.job());
    // Then
    ProcessEngineAssertions.assertThat(ProcessEngineTests.job()).isNull();
    // And
    ProcessEngineAssertions.assertThat(processInstance).isEnded();
  }

  @Test
  @Deployment(resources = {
    "ProcessEngineTests-execute.bpmn"
  })
  public void testExecute_Failure() {
    // Given
    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessEngineTests-execute"
    );
    // And
    ProcessEngineAssertions.assertThat(processInstance).isNotEnded();
    // And
    final Job job = ProcessEngineTests.job();
    ProcessEngineTests.execute(job);
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineTests.execute(job);
      }
    }, IllegalStateException.class);
  }

}
