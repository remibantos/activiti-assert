package org.activiti.engine.test.assertions;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessEngineAssertionsTest {

  ProcessEngine processEngine;

  @Before
  public void setUp() {
    processEngine = Mockito.mock(ProcessEngine.class);
    ProcessEngineAssertions.init(processEngine);
  }

  @After
  public void tearDown() {
    ProcessEngineAssertions.reset();
  }

  @Test
  public void testProcessEngine() throws Exception {
    // When
    ProcessEngine returnedEngine = ProcessEngineAssertions.processEngine();
    // Then
    assertThat(returnedEngine).isNotNull().isSameAs(processEngine);
  }

  @Test
  public void testInit() throws Exception {
    // Given
    ProcessEngineAssertions.reset();
    // When
    ProcessEngineAssertions.init(processEngine);
    // Then
    assertThat(ProcessEngineAssertions.processEngine()).isNotNull().isSameAs(processEngine);
  }

  @Test
  public void testReset() throws Exception {
    // When
    ProcessEngineAssertions.reset();
    // Then
    assertThat(ProcessEngineTests.processEngine.get()).isNull();
  }

  @Test
  public void testAssertThat_ProcessDefinition() throws Exception {
    // Given
    ProcessDefinition processDefinition = Mockito.mock(ProcessDefinition.class);
    // When
    ProcessDefinitionAssert returnedAssert = ProcessEngineAssertions.assertThat(processDefinition);
    // Then
    assertThat(returnedAssert).isNotNull().isInstanceOf(ProcessDefinitionAssert.class);
    ProcessDefinitionAssert processDefinitionAssert = ProcessEngineAssertions.assertThat(processDefinition);
    assertThat(processDefinitionAssert.getActual()).isSameAs(processDefinition);
  }

  @Test
  public void testAssertThat_ProcessInstance() throws Exception {
    // Given
    ProcessInstance processInstance = Mockito.mock(ProcessInstance.class);
    // When
    ProcessInstanceAssert returnedAssert = ProcessEngineAssertions.assertThat(processInstance);
    // Then
    assertThat(returnedAssert).isNotNull().isInstanceOf(ProcessInstanceAssert.class);
    ProcessInstanceAssert processInstanceAssert = ProcessEngineAssertions.assertThat(processInstance);
    assertThat(processInstanceAssert.getActual()).isSameAs(processInstance);
  }

  @Test
  public void testAssertThat_Task() throws Exception {
    // Given
    Task task = Mockito.mock(Task.class);
    // When
    TaskAssert returnedAssert = ProcessEngineAssertions.assertThat(task);
    // Then
    assertThat(returnedAssert).isNotNull().isInstanceOf(TaskAssert.class);
    TaskAssert taskAssert = ProcessEngineAssertions.assertThat(task);
    assertThat(taskAssert.getActual()).isSameAs(task);
  }

  @Test
  public void testAssertThat_Job() throws Exception {
    // Given
    Job job = Mockito.mock(Job.class);
    // When
    JobAssert returnedAssert = ProcessEngineAssertions.assertThat(job);
    // Then
    assertThat(returnedAssert).isNotNull().isInstanceOf(JobAssert.class);
    JobAssert jobAssert = ProcessEngineAssertions.assertThat(job);
    assertThat(jobAssert.getActual()).isSameAs(job);
  }

}
