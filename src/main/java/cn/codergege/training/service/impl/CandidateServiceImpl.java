package cn.codergege.training.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.codergege.training.dao.CandidateDao;
import cn.codergege.training.dao.TrainingDao;
import cn.codergege.training.domain.Candidate;
import cn.codergege.training.domain.Training;
import cn.codergege.training.service.CandidateService;

@Service("candidateService")
public class CandidateServiceImpl implements CandidateService {
	@Resource
	CandidateDao candidateDao;
	@Resource
	TrainingDao trainingDao;
	@Override
	public List<Candidate> getByPage(Integer page, Integer rows, String sort, String order, Candidate candidate, Double credit1, Double credit2, Integer cid, Integer tid) {
		return candidateDao.getByPage(page, rows, sort, order, candidate, credit1, credit2, cid, tid);
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
	@Override
	public void rel(Candidate candidate, Integer tid) {
		Training training = trainingDao.getTraining(tid);
		candidate.getTrainings().add(training);
		//candidateDao.delete(candidate.getCid().toString());
		//candidateDao.add(candidate);
		candidateDao.relUpdate(candidate);
	}
	@Override
	public Candidate getCandidate(String name) {
		return candidateDao.getCandidate(name);
	}
	@Override
	public void unrel(String cids, Integer tid) {
		// 先取出这些
		List<Candidate> candidates = candidateDao.getCandidates(cids);
		//去掉 tid 的 training
		for(Candidate c: candidates){
			for(Training t: c.getTrainings()){
				if(t.getTid() == tid){
					c.getTrainings().remove(t);
					candidateDao.update(c);
				}
			}
		}
		
	}
	@Override
	public List<Candidate> getAll() {
		return candidateDao.getAll();
	}

}
