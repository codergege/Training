package cn.codergege.training.service;

import java.util.List;

import cn.codergege.training.domain.Candidate;

public interface CandidateService {
	List<Candidate> getByPage(Integer page, Integer rows, String sort, String order, Candidate candidate, Double credit1, Double credit2, Integer cid, Integer tid);
	Integer getTotal();
	int save(Candidate candidate);
	Candidate getCandidate(Integer cid);
	Candidate getCandidate(String name);
	void update(Candidate candidate);
	void delete(String cids);
	void rel(Candidate candidate, Integer tid);
	void unrel(String cids, Integer tid);
}
