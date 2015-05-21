package cn.codergege.training.service.impl;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.codergege.training.dao.UserDao;
import cn.codergege.training.domain.User;
import cn.codergege.training.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;
	@Override
	public boolean login(User user) {
		boolean retVal;
		User dbUser = userDao.getUser(user.getLoginName(), user.getPassword());
		if(dbUser == null ){
			retVal = false;
		} else {
			retVal = true;
		}
		return retVal;
	}

}
