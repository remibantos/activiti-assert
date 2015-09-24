package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.runtimeService;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertIsWaitingForTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingFor.bpmn"
  })
  public void testIsWaitingFor_One_Message_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingFor"
    );
    // Then
    assertThat(processInstance).isWaitingFor("myMessage");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingFor-2.bpmn"
  })
  public void testIsWaitingFor_Two_Messages_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingFor-2"
    );
    // Then
    assertThat(processInstance).isWaitingFor("myMessage", "yourMessage");
  }

  @Test
  @Ignore
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingFor-2.bpmn"
  })
  public void testIsWaitingFor_One_Of_Two_Messages_Success() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingFor-2"
    );
    // When
    // runtimeService().correlateMessage("myMessage"); TODO
    // Then
    assertThat(processInstance).isWaitingFor("yourMessage");
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingFor("myMessage");
      }
    });
  }
  
  @Test
  @Ignore
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingFor.bpmn"
  })
  public void testIsWaitingFor_One_Message_Failure() {
    // Given
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingFor"
    );
    // When
    // runtimeService().correlateMessage("myMessage"); TODO
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingFor("myMessage");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingFor.bpmn"
  })
  public void testIsWaitingFor_Not_Waiting_For_One_Of_One_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingFor"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingFor("yourMessage");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingFor.bpmn"
  })
  public void testIsWaitingFor_Not_Waiting_For_One_Of_Two_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingFor"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingFor("myMessage", "yourMessage");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-isWaitingFor.bpmn"
  })
  public void testIsWaitingFor_Null_Error() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-isWaitingFor"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingFor();
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingFor(null);
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).isWaitingFor("myMessage", null);
      }
    });
  }

}
