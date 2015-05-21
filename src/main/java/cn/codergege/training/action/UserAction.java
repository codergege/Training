package cn.codergege.training.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.codergege.training.domain.User;
import cn.codergege.training.service.UserService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Controller @Scope("prototype")
public class UserAction extends ActionSupport implements ModelDriven<User>, Preparable{
	@Resource
	private UserService userService;
	private User user;
	private Map<String, Object> dataMap;
	//login
	public void prepareLogin(){
		System.out.println(1);
		user = new User();
	}
	public String login(){
		System.out.println(user);
        dataMap = new HashMap<String, Object>();
        String success = "true";
		String message = "登录成功！";
		
		if(userService.login(user)){
			
		}else{
			success = "false";
			message = "用户名或密码错误";
		}
		dataMap.put("success", success);
		dataMap.put("message", message);
		//dataMap.put("user", user);
		
		return "login";
	}
	
	
	
	@Override
	public void prepare() throws Exception {
	}
	@Override
	public User getModel() {
		System.out.println(2);
		return user;
	}
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
}
