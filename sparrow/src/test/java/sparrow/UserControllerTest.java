package sparrow;

import javax.servlet.http.Cookie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.inphase.sparrow.entity.system.User;
import com.inphase.sparrow.service.system.UserService;


@RunWith(SpringRunner.class)
@ContextConfiguration(locations={"/springconfig/applicationContext.xml","/springconfig/applicationContext-web.xml"})
@WebAppConfiguration
@Transactional
public class UserControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Autowired
	private UserService userService;
	
	@Before
	public void setup(){
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	@Rollback(false)
	public void testAddUser() throws Exception{
		mockMvc.perform((MockMvcRequestBuilders.post("/user/saveuser").param("operLogin", "xuwei111").
				param("operPassword", "123456").cookie(getCookie()))).
		andExpect(MockMvcResultMatchers.status().isOk()).
        andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	@Rollback(false)
	public void testLogin() throws Exception{
		mockMvc.perform((MockMvcRequestBuilders.post("/login").param("userName", "ss007cc").
				param("password", "654321"))).
		andExpect(MockMvcResultMatchers.status().isOk());
        //andDo(MockMvcResultHandlers.print());
		//andReturn().getResponse().getContentAsString();   
	}
	
	/**
	 * @Description 模拟需要cookie的请求测试
	 * @throws Exception
	 */
	@Test
	@Rollback(false)
	public void testLog() throws Exception{
		mockMvc.perform((MockMvcRequestBuilders.post("/log/gettextlog").param("type", "1").cookie(getCookie()))).
		andExpect(MockMvcResultMatchers.status().isOk());
	
	}
	
	private Cookie getCookie() throws Exception{
		MvcResult result = mockMvc.perform((MockMvcRequestBuilders.post("/login").param("userName", "xuwei").
				param("password", "123456")))
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())
				.andReturn();
		return result.getResponse().getCookie("loginuser");
	}
	
	@Test
	public void testSaveUserRoleMap(){
		userService.saveUserRoleMap(1, "1");
	}
	
	@Test
	public void testSaveUser() throws Exception{
		User user = new User();
		user.setOperLogin("xuwei");
		user.setOperPassword("123456");
		user.setOperSex(1);
		user.setOperStatus(1);
		userService.saveUserTran(user);
	}
	
	@Test
	public void testGetUser() throws Exception{
		mockMvc.perform((MockMvcRequestBuilders.post("/user/get").param("type", "1").cookie(getCookie()))).
		andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void testListAreaByParentId() throws Exception{
		mockMvc.perform((MockMvcRequestBuilders.post("/area/list").param("parentId", "0").cookie(getCookie()))).
		andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
	}
}
