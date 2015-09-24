package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.runtimeService;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertIsNotWaitingForTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Ignore
  @Deployment(resources = {
    "ProcessInstanceAssert-isNotWaitingFor.bpmn"
  })
  public void testIsNotWaitingFor_One_Message_Success() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isNotWaitingFor"
    );
    // When
    //runtimeService().correlateMessage("myMessage"); TODO
    // Then
    assertThat(processInstance).isNotWaitingFor("myMessage");
  }

  @Test
  @Ignore
  @Deployment(resources = {
    "ProcessInstanceAssert-isNotWaitingFor-2.bpmn"
  })
  public void testIsNotWaitingFor_Two_Messages_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isNotWaitingFor-2"
    );
    // When
    //runtimeService().correlateMessage("myMessage"); TODO
    // And
    // runtimeService().correlateMessage("yourMessage"); TODO
    // Then
    assertThat(processInstance).isNotWaitingFor("myMessage", "yourMessage");
  }

  @Test
  @Ignore
  @Deployment(resources = {
    "ProcessInstanceAssert-isNotWaitingFor-2.bpmn"
  })
  public void testIsNotWaitingFor_One_Of_Two_Messages_Success() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isNotWaitingFor-2"
    );
    // When
    //runtimeService().correlateMessage("myMessage"); TODO
    // Then
    assertThat(processInstance).isNotWaitingFor("myMessage");
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isNotWaitingFor("yourMessage");
      }
    });
  }
  
  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isNotWaitingFor.bpmn"
  })
  public void testIsNotWaitingFor_One_Message_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isNotWaitingFor"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isNotWaitingFor("myMessage");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isNotWaitingFor.bpmn"
  })
  public void testIsNotWaitingFor_Not_Waiting_For_One_Of_One_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isNotWaitingFor"
    );
    // Then
    assertThat(processInstance).isNotWaitingFor("yourMessage");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isNotWaitingFor.bpmn"
  })
  public void testIsNotWaitingFor_Not_Waiting_For_One_Of_Two_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isNotWaitingFor"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isNotWaitingFor("myMessage", "yourMessage");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isNotWaitingFor.bpmn"
  })
  public void testIsNotWaitingFor_Null_Error() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isNotWaitingFor"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isNotWaitingFor();
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isNotWaitingFor(null);
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isNotWaitingFor("myMessage", null);
      }
    });
  }

}
