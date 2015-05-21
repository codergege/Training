package cn.codergege.training.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.codergege.training.dao.CandidateDao;
import cn.codergege.training.domain.Candidate;
import cn.codergege.training.service.CandidateService;

@Service("candidateService")
public class CandidateServiceImpl implements CandidateService {
	@Resource
	CandidateDao candidateDao;
	@Override
	public List<Candidate> getByPage(Integer page, Integer rows, String sort, String order, Candidate candidate, Double credit1, Double credit2) {
		return candidateDao.getByPage(page, rows, sort, order, candidate, credit1, credit2);
	}
	@Override
	public Integer getTotal() {
		return candidateDao.getTotal();
	}
	@Override
	public int save(Candidate candidate) {
		int retVal = 0;
		retVal = candidateDao.add(candidate);
		return retVal;
	}
	@Override
	public Candidate getCandidate(Integer cid) {
		
		return candidateDao.getCandidate(cid);
	}
	@Override
	public void update(Candidate candidate) {
		candidateDao.update(candidate);
	}
	@Override
	public void delete(String cids) {
		candidateDao.delete(cids);
	}

}
