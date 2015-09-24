package org.activiti.engine.test.assertions;

import org.activiti.engine.test.assertions.helpers.Failure;
import org.activiti.engine.test.assertions.helpers.ProcessAssertTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.Deployment;
import org.activiti.engine.test.ProcessEngineRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class ProcessInstanceAssertHasVariablesTest extends ProcessAssertTestCase {

  @Rule
  public ProcessEngineRule processEngineRule = new ProcessEngineRule();

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasVariables.bpmn"
  })
  public void testHasVariables_One_Success() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasVariables", ProcessEngineTests.withVariables("aVariable", "aValue")
    );
    // Then
    ProcessEngineAssertions.assertThat(processInstance).hasVariables();
    // And
    ProcessEngineAssertions.assertThat(processInstance).hasVariables("aVariable");
    // When
    ProcessEngineTests.complete(ProcessEngineTests.task(processInstance));
    // Then
    ProcessEngineAssertions.assertThat(processInstance).hasVariables();
    // And
    ProcessEngineAssertions.assertThat(processInstance).hasVariables("aVariable");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasVariables.bpmn"
  })
  public void testHasVariables_One_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasVariables", ProcessEngineTests.withVariables("aVariable", "aValue")
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("anotherVariable");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("aVariable", "anotherVariable");
      }
    });
    // When
    ProcessEngineTests.complete(ProcessEngineTests.task(processInstance));
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("anotherVariable");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("aVariable", "anotherVariable");
      }
    });
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasVariables.bpmn"
  })
  public void testHasVariables_Two_Success() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasVariables", ProcessEngineTests.withVariables("firstVariable", "firstValue", "secondVariable", "secondValue")
    );
    // Then
    ProcessEngineAssertions.assertThat(processInstance).hasVariables();
    // And
    ProcessEngineAssertions.assertThat(processInstance).hasVariables("firstVariable");
    // And
    ProcessEngineAssertions.assertThat(processInstance).hasVariables("secondVariable");
    // And
    ProcessEngineAssertions.assertThat(processInstance).hasVariables("firstVariable", "secondVariable");
    // And
    ProcessEngineAssertions.assertThat(processInstance).hasVariables("secondVariable", "firstVariable");
    // When
    ProcessEngineTests.complete(ProcessEngineTests.task(processInstance));
    // Then
    ProcessEngineAssertions.assertThat(processInstance).hasVariables();
    // And
    ProcessEngineAssertions.assertThat(processInstance).hasVariables("firstVariable");
    // And
    ProcessEngineAssertions.assertThat(processInstance).hasVariables("secondVariable");
    // And
    ProcessEngineAssertions.assertThat(processInstance).hasVariables("firstVariable", "secondVariable");
    // And
    ProcessEngineAssertions.assertThat(processInstance).hasVariables("secondVariable", "firstVariable");
  }

  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasVariables.bpmn"
  })
  public void testHasVariables_Two_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasVariables", ProcessEngineTests.withVariables("firstVariable", "firstValue", "secondVariable", "secondValue")
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("anotherVariable");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("firstVariable", "anotherVariable");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("secondVariable", "anotherVariable");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("firstVariable", "secondVariable", "anotherVariable");
      }
    });
    // When
    ProcessEngineTests.complete(ProcessEngineTests.task(processInstance));
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("anotherVariable");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("firstVariable", "anotherVariable");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("secondVariable", "anotherVariable");
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("firstVariable", "secondVariable", "anotherVariable");
      }
    });
  }
  
  @Test
  @Deployment(resources = {
    "ProcessInstanceAssert-hasVariables.bpmn"
  })
  public void testHasVariables_None_Failure() {
    // When
    final ProcessInstance processInstance = ProcessEngineTests.runtimeService().startProcessInstanceByKey(
      "ProcessInstanceAssert-hasVariables"
    );
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables();
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("aVariable");
      }
    });
    // When
    ProcessEngineTests.complete(ProcessEngineTests.task(processInstance));
    // Then
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables();
      }
    });
    // And
    expect(new Failure() {
      @Override
      public void when() {
        ProcessEngineAssertions.assertThat(processInstance).hasVariables("aVariable");
      }
    });
  }

}
