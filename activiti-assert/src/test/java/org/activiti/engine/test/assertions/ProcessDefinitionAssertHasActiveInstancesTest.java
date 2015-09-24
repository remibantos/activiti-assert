package org.activiti.engine.test.assertions;

import org.activiti.engine.repository.ProcessDefinition;
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
public class ProcessDefinitionAssertHasActiveInstancesTest extends ProcessAssertTestCase {

  @Rule
  public ActivitiRule ActivitiRule = new ActivitiRule();

  @Test
  @Deployment(resources = {
    "ProcessDefinitionAssert-hasActiveInstances.bpmn"
  })
  public void testHasActiveInstances_One_Started_Success() {
    // Given
    final ProcessDefinition processDefinition = 
      processDefinitionQuery().processDefinitionKey("ProcessDefinitionAssert-hasActiveInstances").singleResult();
    // When
    runtimeService().startProcessInstanceByKey(
      "ProcessDefinitionAssert-hasActiveInstances"
    );
    // Then
    assertThat(processDefinition).hasActiveInstances(1);
  }

  @Test
  @Deployment(resources = {
    "ProcessDefinitionAssert-hasActiveInstances.bpmn"
  })
  public void testHasActiveInstances_One_Started_Failure() {
    // Given
    final ProcessDefinition processDefinition =
      processDefinitionQuery().processDefinitionKey("ProcessDefinitionAssert-hasActiveInstances").singleResult();
    // When
    runtimeService().startProcessInstanceByKey(
      "ProcessDefinitionAssert-hasActiveInstances"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processDefinition).hasActiveInstances(0);
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processDefinition).hasActiveInstances(2);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessDefinitionAssert-hasActiveInstances.bpmn"
  })
  public void testHasActiveInstances_Two_Started_Success() {
    // Given
    final ProcessDefinition processDefinition =
      processDefinitionQuery().processDefinitionKey("ProcessDefinitionAssert-hasActiveInstances").singleResult();
    // When
    runtimeService().startProcessInstanceByKey(
      "ProcessDefinitionAssert-hasActiveInstances"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessDefinitionAssert-hasActiveInstances"
    );
    // Then
    assertThat(processDefinition).hasActiveInstances(2);
  }

  @Test
  @Deployment(resources = {
    "ProcessDefinitionAssert-hasActiveInstances.bpmn"
  })
  public void testHasActiveInstances_Two_Started_Failure() {
    // Given
    final ProcessDefinition processDefinition =
      processDefinitionQuery().processDefinitionKey("ProcessDefinitionAssert-hasActiveInstances").singleResult();
    // When
    runtimeService().startProcessInstanceByKey(
      "ProcessDefinitionAssert-hasActiveInstances"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessDefinitionAssert-hasActiveInstances"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processDefinition).hasActiveInstances(0);
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processDefinition).hasActiveInstances(1);
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processDefinition).hasActiveInstances(3);
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessDefinitionAssert-hasActiveInstances.bpmn"
  })
  public void testHasActiveInstances_Two_Started_One_Ended_Success() {
    // Given
    final ProcessDefinition processDefinition =
      processDefinitionQuery().processDefinitionKey("ProcessDefinitionAssert-hasActiveInstances").singleResult();
    // When
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessDefinitionAssert-hasActiveInstances"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessDefinitionAssert-hasActiveInstances"
    );
    // And
    complete(task(processInstance));
    // Then
    assertThat(processDefinition).hasActiveInstances(1);
  }

  @Test
  @Deployment(resources = {
    "ProcessDefinitionAssert-hasActiveInstances.bpmn"
  })
  public void testHasActiveInstances_Two_Started_One_Ended_Failure() {
    // Given
    final ProcessDefinition processDefinition =
      processDefinitionQuery().processDefinitionKey("ProcessDefinitionAssert-hasActiveInstances").singleResult();
    // When
    ProcessInstance processInstance = runtimeService().startProcessInstanceByKey(
      "ProcessDefinitionAssert-hasActiveInstances"
    );
    // And
    runtimeService().startProcessInstanceByKey(
      "ProcessDefinitionAssert-hasActiveInstances"
    );
    // And
    complete(task(processInstance));
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processDefinition).hasActiveInstances(0);
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        assertThat(processDefinition).hasActiveInstances(2);
      }
    });
  }

}
