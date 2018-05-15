package sparrow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.inphase.sparrow.entity.system.Role;
import com.inphase.sparrow.service.system.FunctionItemService;
import com.inphase.sparrow.service.system.RoleService;

/**      
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: sunchao
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(locations={"/springconfig/applicationContext.xml","/springconfig/applicationContext-web.xml"})
public class RoleTest {	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private FunctionItemService functionItemService;	

	@Test
	public void testGetRole(){
		roleService.getRole(2);
	}
	
	@Test
	public void testListRole(){
		//roleService.listRole(0, 1);
	}
	
	@Test
	public void testSaveRole(){
		Role role = new Role();
		role.setRoleName("testRole");
		role.setRoleDescription("这是一个测试角色。");
		role.setRoleIsprivate(0);
		roleService.saveRoleTran(role);
	}
	
	@Test
	public void testUpdateRole(){
		Role role = new Role();
		role.setRoleName("测试角色2");
		role.setRoleDescription("这是一个测试角色。");
		role.setRoleIsprivate(0);
		role.setRoleId(2);
		roleService.updateRole(role);
	}
	
	@Test
	public void testRemoveRole(){
		roleService.removeRoleTran(2);
	}
	
	@Test
	public void testSaveRoleFunctionMap(){
		roleService.saveRoleFunctionItemMap(1, "1,2,3,4");
	}
	
	@Test
	public void testGetFunctionItem() throws Exception{
		functionItemService.getFunctionItem(-1,1,-1,false);
	}
}
