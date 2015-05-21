package cn.codergege.training.dao;

import cn.codergege.training.domain.User;

public interface UserDao {
	User getUser(String loginName, String password);
}
