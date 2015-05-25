package cn.codergege.training.dao;

import java.util.List;

import cn.codergege.training.domain.Training;

public interface TrainingDao {
	int getTotal();
	List<Training> getByPage(Integer page, Integer rows, String sort, String order, Training training, Double credit1, Double credit2, Integer tid, Integer cid);
	void add(Training training);
	Training getTraining(Integer tid);
	void update(Training training);
	void delete(String tids);
	List<Training> getAll();
}
