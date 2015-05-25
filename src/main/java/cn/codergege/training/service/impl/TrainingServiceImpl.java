package cn.codergege.training.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.codergege.training.dao.TrainingDao;
import cn.codergege.training.domain.Training;
import cn.codergege.training.service.TrainingService;

@Service("trainingService")
public class TrainingServiceImpl implements TrainingService {
	@Resource
	private TrainingDao trainingDao;
	@Override
	public int getTotal() {
		return trainingDao.getTotal();
	}

	@Override
	public List<Training> getByPage(Integer page, Integer rows, String sort, String order, Training training, Double credit1, Double credit2, Integer tid, Integer cid) {
		return trainingDao.getByPage(page, rows, sort, order, training, credit1, credit2, tid, cid);
	}

	@Override
	public void add(Training training) {
		trainingDao.add(training);
	}

	@Override
	public Training getTraining(Integer tid) {
		return trainingDao.getTraining(tid);
	}

	@Override
	public void update(Training training) {
		trainingDao.update(training);
	}

	@Override
	public void delete(String tids) {
		trainingDao.delete(tids);
	}

	@Override
	public List<Training> getAll() {
		return trainingDao.getAll();
	}

}
