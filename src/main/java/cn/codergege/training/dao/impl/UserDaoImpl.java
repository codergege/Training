package cn.codergege.training.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.codergege.training.dao.UserDao;
import cn.codergege.training.domain.User;

@Repository("userDao")
public class UserDaoImpl implements UserDao {
	@Resource
	private SessionFactory sessionFactory;
	private Session session;
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	@Override
	public User getUser(String loginName, String password) {
		session = getSession();
		String hql = "from User u where u.loginName = ? and u.password = ?";
		User user = (User) session.createQuery(hql).setString(0, loginName).setString(1, password).uniqueResult();
		
		return user;
	}

}
