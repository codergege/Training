package cn.codergege.training.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.codergege.training.domain.Training;
import cn.codergege.training.service.TrainingService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@Controller
@Scope("prototype")
public class TrainingAction extends ActionSupport implements ModelDriven<Training>, Preparable{

	@Resource
	private TrainingService trainingService;
	private Training training;
	private Map<String, Object> dataMap;
	private JSONObject resultObj;
	private List<Map<String, Object>> pageData;
	private Integer page;
	private Integer rows;
	private String sort;
	private String order;
	private String name;
	private String location;
	private Double creditHour;
	private String trainingLx;
	private String trainingOrg;
	private String content;
	private Double credit1;
	private Double credit2;
	private Integer tid;
	private Integer cid;
	private String tids;
	
	public void prepareList(){
		pageData = new ArrayList<Map<String, Object>>();
		training = new Training();
	}
	public String list(){
		int total = trainingService.getTotal();
		List<Training> trainings = trainingService.getByPage(page, rows, sort, order, training, credit1, credit2, tid, cid);
		for(Training t: trainings){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tid", t.getTid());
			map.put("name", t.getName());
			map.put("content", t.getContent());
			map.put("level", t.getLevel());
			map.put("trainingTime", t.getTrainingTime());
			map.put("location", t.getLocation());
			map.put("creditHour", t.getCreditHour());
			map.put("trainingLx", t.getTrainingLx());
			map.put("trainingOrg", t.getTrainingOrg());
			map.put("credit", t.getCredit());
			pageData.add(map);
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", total);
		resultMap.put("rows", pageData);
		resultObj = JSONObject.fromObject(resultMap);
		return "list";
	}
	
	//add
	public void prepareAdd(){
		training = new Training();
		dataMap = new HashMap<String, Object>();
	}
	public String add(){
		String message = "增加培训项目 [" + training.getName() + "] 成功！";
		String success = "true";
		try {
			trainingService.add(training);
		} catch (Exception e) {
			message = "增加培训项目失败, 出错信息: " + e.getMessage();
			success = "false";
		}
		dataMap.put("message", message);
		dataMap.put("success", success);
		return "add";
	}
	
	//update
	public void prepareUpdate(){
		training = trainingService.getTraining(tid);
		dataMap = new HashMap<String, Object>();
	}
	public String update(){
		String message = "更新培训项目 [" + training.getName() + "] 信息成功！";
		String success = "true";
		try {
			trainingService.update(training);
		} catch (Exception e) {
			message = "更新培训项目 [" + training.getName() + "] 信息失败, 失败信息: " + e.getMessage();
			success = "false";
		}
		dataMap.put("message", message);
		dataMap.put("success", success);
		return "update";
	}
	
	//delete
	public void prepareDelete(){
		dataMap = new HashMap<String, Object>();
	}
	public String delete(){
		String message = "删除项目成功！";
		String success = "true";
		try {
			trainingService.delete(tids);
		} catch (Exception e) {
			message = "删除项目失败, 失败信息: " + e.getMessage();
			success = "false";
		}
		dataMap.put("message", message);
		dataMap.put("success", success);
		return "delete";
	}
	//candidate
	public String candidate(){
		
		return "candidate";
	}
	@Override
	public void prepare() throws Exception {
	}

	@Override
	public Training getModel() {
		return training;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}

	public JSONObject getResultObj() {
		return resultObj;
	}

	public void setResultObj(JSONObject resultObj) {
		this.resultObj = resultObj;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getCreditHour() {
		return creditHour;
	}

	public void setCreditHour(Double creditHour) {
		this.creditHour = creditHour;
	}

	public String getTrainingLx() {
		return trainingLx;
	}

	public void setTrainingLx(String trainingLx) {
		this.trainingLx = trainingLx;
	}

	public String getTrainingOrg() {
		return trainingOrg;
	}

	public void setTrainingOrg(String trainingOrg) {
		this.trainingOrg = trainingOrg;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getCredit1() {
		return credit1;
	}

	public void setCredit1(Double credit1) {
		this.credit1 = credit1;
	}

	public Double getCredit2() {
		return credit2;
	}

	public void setCredit2(Double credit2) {
		this.credit2 = credit2;
	}
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public String getTids() {
		return tids;
	}
	public void setTids(String tids) {
		this.tids = tids;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}

}
