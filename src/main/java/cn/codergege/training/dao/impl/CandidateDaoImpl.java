package cn.codergege.training.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Repository;

import cn.codergege.training.dao.CandidateDao;
import cn.codergege.training.domain.Candidate;
import cn.codergege.training.domain.Training;

@Repository("candidateDao")
public class CandidateDaoImpl implements CandidateDao {
	@Resource
	private SessionFactory sessionFactory;
	private Session session;
	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	@Override
	public List<Candidate> getByPage(Integer page, Integer rows, String sort, String order, Candidate candidate, Double credit1, Double credit2, Integer cid, Integer tid) {
		String hql = "from Candidate where 1=1 ";
		if(tid != null){
			hql = "from Candidate as c inner join fetch c.trainings as t where t.tid = " + tid; 
		}
		if(cid != null){
			hql += " and cid = " + cid;
		}
		if(candidate.getName() != null){
			hql += " and name like '%" + candidate.getName() + "%'";
		}
		if(candidate.getGender() != null){
			hql += " and gender like '%" + candidate.getGender() + "%'";
		}
		if(candidate.getUnit() != null){
			hql += " and unit like '%" + candidate.getUnit() + "%'";
		}
		if(candidate.getBzlx() != null){
			hql += " and bzlx like '%" + candidate.getBzlx() + "%'";
		}
		if(candidate.getState() != null){
			hql += " and state like '%" + candidate.getState() + "%'";
		}
		
		if(sort != null && order != null){
			hql += "order by " + sort + " " + order;
		}
		if(page == null){
			return getSession().createQuery(hql).list();
		}
		List<Candidate> candidates = getSession().createQuery(hql).setFirstResult((page-1)*rows).setMaxResults(rows).list();
		return candidates;
	}
	@Override
	public Integer getTotal() {
		String hql = "select count(*) from Candidate";
		Integer total = ((Number)getSession().createQuery(hql).uniqueResult()).intValue();
		return total;
	}
	@Override
	public int add(Candidate candidate) {
		int retVal = 0;
		retVal = (int) getSession().save(candidate);
		System.out.println(retVal);
		return retVal;
		
	}

	@Override
	public Candidate getCandidate(Integer cid) {
		return (Candidate) getSession().get(Candidate.class, cid);
	}

	@Override
	public void update(Candidate candidate) {
		//session = getSession();
		//session.clear();
		getSession().update(candidate);
	}

	@Override
	public void delete(String cids) {
		String hql = "delete from Candidate where cid in (" + cids + ")";
		getSession().createQuery(hql).executeUpdate();
	}
	@Override
	public Candidate getCandidate(String name) {
		System.out.println(name.trim());
		String hql = "from Candidate where name like ?";
		return (Candidate) getSession().createQuery(hql).setString(0, name.trim()).uniqueResult();
	}
	@Override
	public List<Candidate> getCandidates(String cids) {
		String hql = "from Candidate where cid in (" + cids + ")";
		return getSession().createQuery(hql).list();
	}
	@Override
	public List<Candidate> getAll() {
		String hql = "from Candidate";
		return getSession().createQuery(hql).list();
	}
	@Override
	public void merge(Candidate candidate) {
		getSession().merge(candidate);
	}
	@Override
	public void refresh(Candidate candidate) {
		getSession().refresh(candidate);
	}
	public void setSession(Session session) {
		this.session = session;
	}
	@Override
	public void relUpdate(final Candidate candidate) {
		getSession().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				//先删除再插入
				String sql = null;
				PreparedStatement stmt = null;
				sql = "delete from CANDIDATE_TRAINING where cid = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, candidate.getCid());
				stmt.execute();
				stmt.close();
				
				//再插入
				sql = "insert into CANDIDATE_TRAINING(CID,TID) values(?, ?)";
				conn.setAutoCommit(false);
				stmt = conn.prepareStatement(sql);
				int cid = candidate.getCid();
				for(Training t: candidate.getTrainings()) {
					stmt.setInt(1, cid);
					stmt.setInt(2, t.getTid());
					stmt.addBatch();
				}
				stmt.executeBatch();
				conn.commit();
				stmt.close();
				//conn.close();
			}
		});
	}
}
