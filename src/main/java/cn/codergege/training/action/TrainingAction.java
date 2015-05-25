package cn.codergege.training.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.codergege.training.domain.Candidate;
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
	//excel part
	private String format;
	private String downloadFileName;
	private InputStream excelFile;
	
	//export excel candidate rel info
			public String relExport(){
				Workbook workbook = null;
				if("xls".equals(format)){
					workbook = new HSSFWorkbook();
				}
				if("xlsx".equals(format)){
					workbook = new XSSFWorkbook();
				}
				training = trainingService.getTraining(tid);
				//创建 sheet
				Sheet sheet = workbook.createSheet("培训-学员-关联信息");
				//创建 row
				Row row = sheet.createRow(0);
				//创建 cell
				row.createCell(0).setCellValue("培训项目班次名称");
				row.createCell(1).setCellValue("培训内容");	
				row.createCell(2).setCellValue("培训级别");
				row.createCell(3).setCellValue("培训时间");
				row.createCell(4).setCellValue("培训地点");
				row.createCell(5).setCellValue("培训学时");
				row.createCell(6).setCellValue("培训类型");
				row.createCell(7).setCellValue("培训机构");
				row.createCell(8).setCellValue("学分");
				
				
				Row r = sheet.createRow(1);
				r.createCell(0).setCellValue(training.getName());
				r.createCell(1).setCellValue(training.getContent());
				r.createCell(2).setCellValue(training.getLevel());
				r.createCell(3).setCellValue(training.getTrainingTime());
				r.createCell(4).setCellValue(training.getLocation());
				r.createCell(5).setCellValue(training.getCreditHour());
				r.createCell(6).setCellValue(training.getTrainingLx());
				r.createCell(7).setCellValue(training.getTrainingOrg());
				r.createCell(8).setCellValue(training.getCredit());
				
				
				Row row2 = sheet.createRow(2);
				//创建 cell
				row2.createCell(0).setCellValue("姓名");
				row2.createCell(1).setCellValue("性别");	
				row2.createCell(2).setCellValue("单位");
				row2.createCell(3).setCellValue("职务");
				row2.createCell(4).setCellValue("出生年月");
				row2.createCell(5).setCellValue("最高学历");
				row2.createCell(6).setCellValue("参加工作时间");
				row2.createCell(7).setCellValue("编制类型");
				row2.createCell(8).setCellValue("状态");
				//set to list
				List<Candidate> candidates = new ArrayList<Candidate>(training.getCandidates());
				for(int i = 0; i < candidates.size(); i ++){
					Candidate cc = candidates.get(i);
					Row rr = sheet.createRow(i+3);
					rr.createCell(0).setCellValue(cc.getName());
					rr.createCell(1).setCellValue(cc.getGender());
					rr.createCell(2).setCellValue(cc.getUnit());
					rr.createCell(3).setCellValue(cc.getPost());
					rr.createCell(4).setCellValue(cc.getBirthday());
					rr.createCell(5).setCellValue(cc.getDegree());
					rr.createCell(6).setCellValue(cc.getOperatingTime());
					rr.createCell(7).setCellValue(cc.getBzlx());
					rr.createCell(8).setCellValue(cc.getState());
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					
					workbook.write(baos);
		 
				} catch (IOException e) {
					e.printStackTrace();
				}
				byte[] aa = baos.toByteArray();
				excelFile = new ByteArrayInputStream(aa,0,aa.length); 
				try {
					baos.close();
					workbook.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "relExportXls";
			}
			
	//export excel candidate info
		public String export(){
			Workbook workbook = null;
			if("xls".equals(format)){
				workbook = new HSSFWorkbook();
			}
			if("xlsx".equals(format)){
				workbook = new XSSFWorkbook();
			}
			List<Training> trainings = trainingService.getAll();
			//创建 sheet
			Sheet sheet = workbook.createSheet("培训项目信息");
			//创建 row
			Row row = sheet.createRow(0);
			//创建 cell
			row.createCell(0).setCellValue("培训项目班次名称");
			row.createCell(1).setCellValue("培训内容");	
			row.createCell(2).setCellValue("培训级别");
			row.createCell(3).setCellValue("培训时间");
			row.createCell(4).setCellValue("培训地点");
			row.createCell(5).setCellValue("培训学时");
			row.createCell(6).setCellValue("培训类型");
			row.createCell(7).setCellValue("培训机构");
			row.createCell(8).setCellValue("学分");
			for(int i = 0; i < trainings.size(); i ++){
				Training t = trainings.get(i);
				Row r = sheet.createRow(i+1);
				r.createCell(0).setCellValue(t.getName());
				r.createCell(1).setCellValue(t.getContent());
				r.createCell(2).setCellValue(t.getLevel());
				r.createCell(3).setCellValue(t.getTrainingTime());
				r.createCell(4).setCellValue(t.getLocation());
				r.createCell(5).setCellValue(t.getCreditHour());
				r.createCell(6).setCellValue(t.getTrainingLx());
				r.createCell(7).setCellValue(t.getTrainingOrg());
				r.createCell(8).setCellValue(t.getCredit());
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				
				workbook.write(baos);
	 
			} catch (IOException e) {
				e.printStackTrace();
			}
			byte[] aa = baos.toByteArray();
			excelFile = new ByteArrayInputStream(aa,0,aa.length); 
			try {
				baos.close();
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "exportXls";
		}
		
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
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String tmp = sdf.format(new Date());
		String tmp2 = "培训项目信息";
		if(tid != null){
			tmp2 = "培训-学员-关联信息";
		}
		if(format.equals("xls")){
			this.downloadFileName = tmp2 + tmp + ".xls";
		}
		if(format.equals("xlsx")){
			this.downloadFileName = tmp2 + tmp + ".xlsx";
		}
		try {
			this.downloadFileName=new String(this.downloadFileName.getBytes("utf-8"), "iso8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public String getDownloadFileName() {
		return downloadFileName;
	}
	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	public InputStream getExcelFile() {
		return excelFile;
	}
	public void setExcelFile(InputStream excelFile) {
		this.excelFile = excelFile;
	}
}
