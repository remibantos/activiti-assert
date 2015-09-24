package org.activiti.engine.test.assertions;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * @author Martin Schimak <martin.schimak@plexiti.com>
 */
public class CalledProcessInstanceAssert extends ProcessInstanceAssert {

  protected CalledProcessInstanceAssert(final ProcessEngine engine, final ProcessInstance actual) {
    super(engine, actual, CalledProcessInstanceAssert.class);
  }

  protected static CalledProcessInstanceAssert assertThat(final ProcessEngine engine, final ProcessInstance actual) {
    return new CalledProcessInstanceAssert(engine, actual);
  }

}
