package cn.codergege.training.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.codergege.training.domain.Candidate;
import cn.codergege.training.domain.Training;
import cn.codergege.training.service.CandidateService;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

/**
 * @author codergege
 *
 */
/**
 * @author codergege
 *
 */
@Controller
@Scope("prototype")
public class CandidateAction extends ActionSupport implements ModelDriven<Candidate>, Preparable {

	@Resource
	private CandidateService candidateService;
	private Candidate candidate;
	private JSONObject resultObj;
	private List<Map<String, Object>> pageData;
	private Map<String, Object> dataMap;
	private Integer page;
	private Integer rows;
	private String sort;
	private String order;
	private String name;
	private String gender;
	private String unit;
	private String bzlx;
	private String state;
	private Double credit1;
	private Double credit2;
	private String cids;
	private Integer cid;
	private Integer tid;
	private String message;
	private String success;
	
	//delete
		public void prepareDelete(){
			System.out.println(cids);
			dataMap = new HashMap<String, Object>();
		}
		public String delete(){
			String message = "删除学员信息成功！";
			String success = "true";
			try {
				candidateService.delete(cids);
			} catch (Exception e) {
				e.printStackTrace();
				message = "删除失败！出错信息:\n" + e.getMessage();
				success = "false";
			}
			dataMap.put("success", success);
			dataMap.put("message", message);
			return "delete";
		}
		//update
		public void prepareUpdate(){
			candidate = candidateService.getCandidate(cid);
		}
		public String update(){
			dataMap = new HashMap<String, Object>();
			String message = "更新学员信息[" + candidate.getUnit() + "-" + candidate.getName() + "]成功！";
			String success = "true";
			try {
				System.out.println(candidate);
				candidateService.update(candidate);
			} catch (Exception e) {
				e.printStackTrace();
				message = "更新[" + candidate.getUnit() + "-" + candidate.getName() + "]失败！出错信息:\n" + e.getMessage();
				success = "false";
			}
			dataMap.put("success", success);
			dataMap.put("message", message);
			return "update";
		}
		//rel
		public void prepareRel(){
			candidate = candidateService.getCandidate(name);
			dataMap = new HashMap<String, Object>();
			System.out.println("tid=" + tid);
		}
		public String rel(){
			try {
				message = "关联学员 [" + candidate.getName() + "] 成功！";
				success = "true";
				candidateService.rel(candidate, tid);
			} catch (Exception e) {
				if(candidate == null){
					message = "不存在姓名为 [" + name + "] 的学员";
				} else {
					message = "关联学员 [" + candidate.getName() + "] 失败, 出错信息: " + e.getMessage();
				}
				success = "false";
			}
			dataMap.put("message", message);
			dataMap.put("success", success);
			return "rel";
		}
		//unrel
		public void prepareUnrel(){
			dataMap = new HashMap<String, Object>();
		}
		public String unrel(){
			try {
				candidateService.unrel(cids, tid);
				message = "取消关联成功！";
				success = "true";
			} catch (Exception e) {
				message = "取下关联失败, 出错信息:" + e.getMessage();
				success = "false";
			}
			dataMap.put("message", message);
			dataMap.put("success", success);
			return "unrel";
		}
		//add
		public void prepareAdd(){
			candidate = new Candidate();
		}
		public String add(){
			System.out.println(candidate.getName());
			dataMap = new HashMap<String, Object>();
			String message = "保存学员信息[" + candidate.getUnit() + "-" + candidate.getName() + "]成功！";
			String success = "true";
			
			Integer retVal = 0;
			try {
				retVal = candidateService.save(candidate);
				if(retVal == null || retVal == 0){
					message = "保存[" + candidate.getUnit() + "-" + candidate.getName() + "]失败！";
					success = "false";
				}
			} catch (Exception e) {
				e.printStackTrace();
				message = "保存[" + candidate.getUnit() + "-" + candidate.getName() + "]失败！出错信息:\n" + e.getMessage();
				success = "false";
			}
			
			dataMap.put("success", success);
			dataMap.put("message", message);
			return "add";
		}
		
	// list
	public void prepareList(){
		candidate = new Candidate();
		pageData = new ArrayList<Map<String, Object>>();
		//准备 search 参数
		if(name != null){
			candidate.setName(name);
		}
		if(gender != null){
			candidate.setGender(gender);
		}
		if(unit != null){
			candidate.setUnit(unit);
		}
		if(bzlx != null){
			candidate.setBzlx(bzlx);
		}
		if(state != null){
			candidate.setState(state);
		}
	}
	/**
	 * @return
	 */
	public String list(){
	
		List<Candidate> candidates = candidateService.getByPage(page, rows, sort, order, candidate, credit1, credit2, cid, tid);
		for(Candidate c: candidates){
			// 判断 credit
			
			if(credit1 != null || credit2 != null){
				double tmpc = 0;
				for(Training t: c.getTrainings()){
					tmpc += t.getCredit();
				}
				if(credit1 == null ){
					credit1 = -100.0;
				}
				if(credit2 == null){
					credit2 = 10000000.0;
				}
				if(tmpc < credit1 || tmpc > credit2){
					continue;
				}
					
			}
			// 下面这段代码应该做成工具类
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cid", c.getCid());
			map.put("name", c.getName());
			map.put("gender", c.getGender());
			map.put("unit", c.getUnit());
			map.put("post", c.getPost());
			map.put("birthday", c.getBirthday());
			map.put("degree", c.getDegree());
			map.put("operatingTime", c.getOperatingTime());
			map.put("bzlx", c.getBzlx());
			map.put("state", c.getState());
			//calc credit
			double credit = 0;
			for(Training t: c.getTrainings()){
				credit += t.getCredit();	
			}
			map.put("credit", credit);
			pageData.add(map);
		}
		Integer total = 0;
		total = candidateService.getTotal();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("total", total);
		resultMap.put("rows", pageData);
		resultObj = JSONObject.fromObject(resultMap);
		return "list";
	}
	@Override
	public void prepare() throws Exception {
	}

	@Override
	public Candidate getModel() {
		return candidate;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
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
	public JSONObject getResultObj() {
		return resultObj;
	}
	public void setResultObj(JSONObject resultObj) {
		this.resultObj = resultObj;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBzlx() {
		return bzlx;
	}
	public void setBzlx(String bzlx) {
		this.bzlx = bzlx;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getCids() {
		return cids;
	}
	public void setCids(String cids) {
		this.cids = cids;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
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

}
