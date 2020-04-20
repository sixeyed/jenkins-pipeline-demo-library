package vars

import com.lesfurets.jenkins.unit.BaseRegressionTest
import org.junit.Before
import org.junit.Test

class AuditToolsTest extends BaseRegressionTest {

  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();
    callStackPath = "test/vars/callstacks/"
    binding.setVariable('scm', [:])
  }

  @Test
  public void call() throws Exception {
    def script = loadScript('vars/auditTools.groovy')
    script.call()
    printCallStack()
    testNonRegression("default")
  }
}
