package org.activiti.engine.test.assertions.examples;

import org.activiti.engine.test.assertions.ProcessEngineAssertions;
import org.activiti.engine.test.assertions.ProcessEngineTests;
import org.activiti.engine.test.assertions.examples.jobannouncement.JobAnnouncement;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ProcessEngineRule;
import org.activiti.engine.test.mock.Mocks;
import org.activiti.engine.test.assertions.examples.jobannouncement.JobAnnouncementService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class JobAnnouncementPublicationProcessTest {

  @Rule public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Mock public JobAnnouncementService jobAnnouncementService;
  @Mock public JobAnnouncement jobAnnouncement;

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
  @Deployment(resources = { "camunda-testing-job-announcement-publication.bpmn" })
  public void testPublishOnlyOnCompanyWebsite() {

    when(jobAnnouncement.getId()).thenReturn(1L);

    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "camunda-testing-job-announcement-publication",
      ProcessEngineTests.withVariables(
              "jobAnnouncementId", jobAnnouncement.getId(),
              "twitter", false,
              "facebook", false
      )
    );

    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
    verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));

    ProcessEngineAssertions.assertThat(processInstance).hasPassed(
      "Anzeige_auf_Firmenwebsite_publizieren"
    );

    ProcessEngineAssertions.assertThat(processInstance).isEnded();

    verifyNoMoreInteractions(jobAnnouncementService);

  }

  @Test
  @Deployment(resources = { "camunda-testing-job-announcement-publication.bpmn" })
  public void testPublishAlsoOnTwitter() {

    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "camunda-testing-job-announcement-publication",
      ProcessEngineTests.withVariables(
              "jobAnnouncementId", jobAnnouncement.getId(),
              "twitter", true,
              "facebook", false
      )
    );

    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
    verify(jobAnnouncementService, never()).postToFacebook(any(Long.class));

    ProcessEngineAssertions.assertThat(processInstance).hasPassed(
      "Anzeige_auf_Firmenwebsite_publizieren",
      "Anzeige_auf_Twitter_publizieren"
    );

    ProcessEngineAssertions.assertThat(processInstance).isEnded();

    verifyNoMoreInteractions(jobAnnouncementService);

  }

  @Test
  @Deployment(resources = { "camunda-testing-job-announcement-publication.bpmn" })
  public void testPublishAlsoOnFacebook() {

    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "camunda-testing-job-announcement-publication",
      ProcessEngineTests.withVariables(
              "jobAnnouncementId", jobAnnouncement.getId(),
              "twitter", false,
              "facebook", true
      )
    );

    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService, never()).postToTwitter(any(Long.class));
    verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());

    ProcessEngineAssertions.assertThat(processInstance).hasPassed(
      "Anzeige_auf_Firmenwebsite_publizieren",
      "Anzeige_auf_Facebook_Page_publizieren"
    );

    ProcessEngineAssertions.assertThat(processInstance).isEnded();

    verifyNoMoreInteractions(jobAnnouncementService);

  }

  @Test
  @Deployment(resources = { "camunda-testing-job-announcement-publication.bpmn" })
  public void testPublishAlsoOnFacebookAndTwitter() {

    ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "camunda-testing-job-announcement-publication",
      ProcessEngineTests.withVariables(
              "jobAnnouncementId", jobAnnouncement.getId(),
              "twitter", true,
              "facebook", true
      )
    );

    verify(jobAnnouncementService).postToWebsite(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToTwitter(jobAnnouncement.getId());
    verify(jobAnnouncementService).postToFacebook(jobAnnouncement.getId());

    ProcessEngineAssertions.assertThat(processInstance).hasPassed(
      "Anzeige_auf_Firmenwebsite_publizieren",
      "Anzeige_auf_Twitter_publizieren",
      "Anzeige_auf_Facebook_Page_publizieren"
    );
    
    ProcessEngineAssertions.assertThat(processInstance).isEnded();

    verifyNoMoreInteractions(jobAnnouncementService);

  }

}
