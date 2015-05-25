package cn.codergege.training.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import cn.codergege.training.dao.TrainingDao;
import cn.codergege.training.domain.Training;

@Repository("trainingDao")
public class TrainingDaoImpl implements TrainingDao {
	@Resource
	private SessionFactory sessionFactory;
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	@Override
	public int getTotal() {
		String hql = "select count(*) from Training";
		
		return ((Number)getSession().createQuery(hql).uniqueResult()).intValue();
	}

	@Override
	public List<Training> getByPage(Integer page, Integer rows, String sort, String order, Training training, Double credit1, Double credit2, Integer tid, Integer cid) {
		String hql = "from Training where 1=1";
		if(cid != null){
			hql = "from Training as t inner join fetch t.candidates as c where c.cid = " + cid;
		}
		if(tid != null){
			hql += " and tid = " + tid;
		}
		if(training.getName() != null){
			hql += " and name like '%" + training.getName() + "%'";
		}
		if(training.getLocation() != null){
			hql += " and location like '%" + training.getLocation() + "%'";
		}
		if(training.getCreditHour() != null){
			hql += " and creditHour = " + training.getCreditHour();
		}
		if(training.getTrainingLx() != null){
			hql += " and trainingLx like '%" + training.getTrainingLx() + "%'";
		}
		if(training.getTrainingOrg() != null){
			hql += " and trainingOrg like '%" + training.getTrainingOrg() + "%'";
		}
		if(training.getContent() != null){
			hql += " and content like '%" + training.getContent() + "%'";
		}
		if(credit1 != null){
			hql += " and credit >=" + credit1;
		}
		if(credit2 != null){
			hql += " and credit <=" + credit2;
		}

		if(sort != null && order != null){
			hql += " order by " + sort + " " + order;
		}
		if(page == null){
			return getSession().createQuery(hql).list();
		}
		return getSession().createQuery(hql).setFirstResult((page-1)*rows).setMaxResults(rows).list();
	}
	@Override
	public void add(Training training) {
		getSession().save(training);
	}
	@Override
	public Training getTraining(Integer tid) {
		String hql = "from Training where tid = " + tid;
		return (Training) getSession().createQuery(hql).uniqueResult();
	}
	@Override
	public void update(Training training) {
		getSession().update(training);
	}
	@Override
	public void delete(String tids) {
		String hql = "delete from Training where tid in (" + tids + ")";
		getSession().createQuery(hql).executeUpdate();
	}
	@Override
	public List<Training> getAll() {
		String hql = "from Training";
		return getSession().createQuery(hql).list();
	}
}
