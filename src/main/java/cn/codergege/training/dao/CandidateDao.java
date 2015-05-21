package cn.codergege.training.dao;

import java.util.List;

import cn.codergege.training.domain.Candidate;

public interface CandidateDao {
	List<Candidate> getByPage(Integer page, Integer rows, String sort, String order, Candidate candidate, Double credit1, Double credit2);
	Integer getTotal();
	int add(Candidate candidate);
	Candidate getCandidate(Integer cid);
	void update(Candidate candidate);
	void delete(String cids);
}
