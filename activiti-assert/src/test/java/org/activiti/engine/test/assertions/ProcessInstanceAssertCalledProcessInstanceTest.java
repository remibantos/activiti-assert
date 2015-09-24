package org.activiti.engine.test.assertions;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.junit.Rule;
import org.junit.Test;

import static org.activiti.engine.test.assertions.ProcessEngineAssertions.assertThat;
import static org.activiti.engine.test.assertions.ProcessEngineTests.*;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertCalledProcessInstanceTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {"ProcessInstanceAssert-calledProcessInstance-superProcess1.bpmn",
    "ProcessInstanceAssert-calledProcessInstance-subProcess1.bpmn",
    "ProcessInstanceAssert-calledProcessInstance-subProcess2.bpmn"
  })
  public void testCalledProcessInstance_FirstOfTwoSequential_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-calledProcessInstance-superProcess1"
    );
    // Then
    assertThat(processInstance)
      .calledProcessInstance()
      .hasProcessDefinitionKey("ProcessInstanceAssert-calledProcessInstance-subProcess1");
    // And
    assertThat(processInstance)
      .calledProcessInstance("ProcessInstanceAssert-calledProcessInstance-subProcess1")
      .hasProcessDefinitionKey("ProcessInstanceAssert-calledProcessInstance-subProcess1");
    // And
    assertThat(processInstance)
      .calledProcessInstance("ProcessInstanceAssert-calledProcessInstance-subProcess2")
      .isNull();
    // And
    assertThat(processInstance)
      .calledProcessInstance(processInstanceQuery())
      .hasProcessDefinitionKey("ProcessInstanceAssert-calledProcessInstance-subProcess1");
  }

  @Test
  @Deployment(resources = {"ProcessInstanceAssert-calledProcessInstance-superProcess1.bpmn",
    "ProcessInstanceAssert-calledProcessInstance-subProcess1.bpmn",
    "ProcessInstanceAssert-calledProcessInstance-subProcess2.bpmn"
  })
  public void testCalledProcessInstance_SecondOfTwoSequential_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-calledProcessInstance-superProcess1"
    );
    // Then
    assertThat(processInstance)
      .calledProcessInstance("ProcessInstanceAssert-calledProcessInstance-subProcess2")
      .isNull();
    // And
    assertThat(processInstance)
      .calledProcessInstance()
      .hasProcessDefinitionKey("ProcessInstanceAssert-calledProcessInstance-subProcess1");
    // When
    complete(task("UserTask_1", calledProcessInstance()));
    // Then
    assertThat(processInstance)
      .calledProcessInstance()
      .hasProcessDefinitionKey("ProcessInstanceAssert-calledProcessInstance-subProcess2");
    // And
    assertThat(processInstance)
      .calledProcessInstance("ProcessInstanceAssert-calledProcessInstance-subProcess1")
      .isNull();
  }

  @Test
  @Deployment(resources = {"ProcessInstanceAssert-calledProcessInstance-superProcess1.bpmn",
    "ProcessInstanceAssert-calledProcessInstance-subProcess1.bpmn",
    "ProcessInstanceAssert-calledProcessInstance-subProcess2.bpmn"
  })
  public void testCalledProcessInstance_SecondOfTwoSequential_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-calledProcessInstance-superProcess1"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance)
          .calledProcessInstance("ProcessInstanceAssert-calledProcessInstance-subProcess2")
          .isNotNull();
      }
    });
    // And
    assertThat(processInstance)
      .calledProcessInstance()
      .hasProcessDefinitionKey("ProcessInstanceAssert-calledProcessInstance-subProcess1");
    // When
    complete(task("UserTask_1", calledProcessInstance()));
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance).calledProcessInstance("ProcessInstanceAssert-calledProcessInstance-subProcess1")
          .isNotNull();
      }
    });
  }

  @Test
  @Deployment(resources = {"ProcessInstanceAssert-calledProcessInstance-superProcess2.bpmn",
    "ProcessInstanceAssert-calledProcessInstance-subProcess1.bpmn",
    "ProcessInstanceAssert-calledProcessInstance-subProcess2.bpmn"
  })
  public void testCalledProcessInstance_TwoOfTwoParallel_Success() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-calledProcessInstance-superProcess2"
    );
    // Then
    assertThat(processInstance)
      .calledProcessInstance("ProcessInstanceAssert-calledProcessInstance-subProcess1")
      .hasProcessDefinitionKey("ProcessInstanceAssert-calledProcessInstance-subProcess1");
    // And
    assertThat(processInstance)
      .calledProcessInstance(processInstanceQuery().processDefinitionKey("ProcessInstanceAssert-calledProcessInstance-subProcess1"))
      .hasProcessDefinitionKey("ProcessInstanceAssert-calledProcessInstance-subProcess1");
    // And
    assertThat(processInstance)
      .calledProcessInstance("ProcessInstanceAssert-calledProcessInstance-subProcess2")
      .hasProcessDefinitionKey("ProcessInstanceAssert-calledProcessInstance-subProcess2");
    // And
    assertThat(processInstance)
      .calledProcessInstance(processInstanceQuery().processDefinitionKey("ProcessInstanceAssert-calledProcessInstance-subProcess2"))
      .hasProcessDefinitionKey("ProcessInstanceAssert-calledProcessInstance-subProcess2");
  }

  @Test
  @Deployment(resources = {"ProcessInstanceAssert-calledProcessInstance-superProcess2.bpmn",
    "ProcessInstanceAssert-calledProcessInstance-subProcess1.bpmn",
    "ProcessInstanceAssert-calledProcessInstance-subProcess2.bpmn"
  })
  public void testCalledProcessInstance_TwoOfTwoParallel_Failure() {
    // When
    final ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-calledProcessInstance-superProcess2"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processInstance)
          .calledProcessInstance("ProcessInstanceAssert-calledProcessInstance-subProcess3")
          .isNotNull();
      }
    });
  }

}
