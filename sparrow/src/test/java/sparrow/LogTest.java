package sparrow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.inphase.sparrow.service.system.LogService;

/**      
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: sunchao
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations={"/springconfig/applicationContext.xml","/springconfig/applicationContext-web.xml"})
public class LogTest {

	@Autowired
	private LogService logService;
	
	@Test
	public void testGetTextLog(){
		System.out.println(logService.getTextLog(3, ""));
	}
}
