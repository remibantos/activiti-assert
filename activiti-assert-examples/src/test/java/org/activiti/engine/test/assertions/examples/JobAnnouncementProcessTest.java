package org.activiti.engine.test.assertions.examples;

import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.assertions.ProcessEngineAssertions;
import org.activiti.engine.test.assertions.ProcessEngineTests;
import org.activiti.engine.test.assertions.examples.jobannouncement.JobAnnouncement;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.mock.Mocks;
import org.activiti.engine.test.assertions.examples.jobannouncement.JobAnnouncementService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAnnouncementProcessTest {

  @Rule
  public final ActivitiRule ActivitiRule = new ActivitiRule();

  @Mock
  public JobAnnouncementService jobAnnouncementService;
  @Mock
  public JobAnnouncement jobAnnouncement;

          // Some boilerplate - we can easily get rid of again by 
  @Before // deciding where to ultimately put the jUnit integration
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    Mocks.register("jobAnnouncementService", jobAnnouncementService);
    Mocks.register("jobAnnouncement", jobAnnouncement);
  }

  @After
  public void tearDown() {
    Mocks.reset();
  }
  
  @Test
  @Deployment(resources = {
    "camunda-testing-job-announcement.bpmn",
    "camunda-testing-job-announcement-publication.bpmn"
  })
  public void testHappyPath() {

    when(jobAnnouncement.getId()).thenReturn(1L);
    when(jobAnnouncementService.findRequester(1L)).thenReturn("gonzo");
    when(jobAnnouncementService.findEditor(1L)).thenReturn("fozzie");

    final ProcessInstance processInstance = startProcess();

    ProcessEngineAssertions.assertThat(processInstance).isStarted().isNotEnded()
      .task().hasDefinitionKey("edit").hasCandidateGroup("engineering").isNotAssigned();

    ProcessEngineTests.claim(ProcessEngineTests.task(), "fozzie");

    ProcessEngineAssertions.assertThat(ProcessEngineTests.task()).isAssignedTo("fozzie");
    // and just to show off more possibilities...
    ProcessEngineAssertions.assertThat(processInstance).task(ProcessEngineTests.taskQuery().taskAssignee("fozzie")).hasDefinitionKey("edit");
    ProcessEngineAssertions.assertThat(processInstance).task("edit").isAssignedTo("fozzie");

    ProcessEngineTests.complete(ProcessEngineTests.task());

    ProcessEngineAssertions.assertThat(processInstance).task().hasDefinitionKey("review").isAssignedTo("gonzo");

    ProcessEngineTests.complete(ProcessEngineTests.task(), ProcessEngineTests.withVariables("approved", true));

    ProcessEngineAssertions.assertThat(processInstance).task().hasDefinitionKey("publish").hasCandidateGroup("engineering").isNotAssigned();
    
    ProcessEngineAssertions.assertThat(processInstance).hasVariables();
    ProcessEngineAssertions.assertThat(processInstance).hasVariables("jobAnnouncementId", "approved");
    ProcessEngineAssertions.assertThat(processInstance).variables()
      .hasSize(2)
      .containsEntry("jobAnnouncementId", jobAnnouncement.getId())
      .containsEntry("approved", true);

    // claim and complete could be combined, too
    ProcessEngineTests.complete(ProcessEngineTests.claim(ProcessEngineTests.task(), "fozzie"), ProcessEngineTests.withVariables("twitter", true, "facebook", true));

    verify(jobAnnouncementService).findRequester(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());
    verify(jobAnnouncementService).notifyAboutPostings(jobAnnouncement.getId());

    ProcessEngineAssertions.assertThat(processInstance).hasPassedInOrder("edit", "review", "publish", "publication", "mail");

    ProcessEngineAssertions.assertThat(processInstance).isEnded();

    verifyNoMoreInteractions(jobAnnouncementService);

    ProcessEngineAssertions.assertThat(ProcessEngineTests.processDefinition()).hasActiveInstances(0);

  }

  @Test
  @Deployment(resources = { "camunda-testing-job-announcement.bpmn", "camunda-testing-job-announcement-publication.bpmn" })
  public void should_fail_if_processInstance_is_not_waiting_at_expected_task() {
    final ProcessInstance processInstance = startProcess();
    try {
      ProcessEngineAssertions.assertThat(processInstance).task("review");
    } catch (AssertionError e) {
      return;
    }
    throw new AssertionError("Expected AssertionError to be thrown, but did not see any such exception.");
  }

  @Test
  @Deployment(resources = { "camunda-testing-job-announcement.bpmn", "camunda-testing-job-announcement-publication.bpmn" })
  public void should_not_fail_if_processInstance_is_waiting_at_expected_task() {
    final ProcessInstance processInstance = startProcess();
    ProcessEngineAssertions.assertThat(processInstance).task("edit").isNotAssigned();
  }

  private ProcessInstance startProcess() {
    return ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "camunda-testing-job-announcement",
      ProcessEngineTests.withVariables("jobAnnouncementId", jobAnnouncement.getId())
    );
  }

}
