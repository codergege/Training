package cn.codergege.training.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.apache.struts2.interceptor.RequestAware;
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
public class CandidateAction extends ActionSupport implements ModelDriven<Candidate>, Preparable, RequestAware {

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
	//excel part
	private String format;
	private String downloadFileName;
	private InputStream excelFile;
	private File importExcelFile;
	private String importExcelFileFileName;
	private Map<String, Object> request;
	
	//import excel part
	private Workbook createWorkbook(InputStream is) throws IOException{
		if(importExcelFileFileName.toLowerCase().endsWith("xls")){
			return new HSSFWorkbook(is);
		}
		if(importExcelFileFileName.toLowerCase().endsWith("xlsx")){
			return new XSSFWorkbook(is);
		}
		return null;
	}
	public String importExcel(){
		Integer totalCount = 0;
		Integer successCount = 0;
		Integer failCount = 0;
		List<FailInfo> failInfos = new ArrayList<FailInfo>();
		Workbook wb = null;
		try {
			wb = createWorkbook(new FileInputStream(importExcelFile));
			Sheet sheet = wb.getSheetAt(0);
			request.put("excelFileName", importExcelFileFileName);
			request.put("excelSheetName", sheet.getSheetName());
			totalCount = 0;
			for(int i = 1; i <=sheet.getLastRowNum(); i ++){
				Row row = sheet.getRow(i);
				if(row == null ) break;
				totalCount++;
				Candidate c = new Candidate();
				c.setName(row.getCell(0) == null ? "": row.getCell(0).getStringCellValue());
				c.setGender(row.getCell(1) == null ? "": row.getCell(1).getStringCellValue());
				c.setUnit(row.getCell(2) == null ? "": row.getCell(2).getStringCellValue());
				c.setPost(row.getCell(3) == null ? "": row.getCell(3).getStringCellValue());
				c.setBirthday(row.getCell(4) == null ? "": row.getCell(4).getStringCellValue());
				c.setDegree(row.getCell(5) == null ? "": row.getCell(5).getStringCellValue());
				c.setOperatingTime(row.getCell(6) == null ? "": row.getCell(6).getStringCellValue());
				c.setBzlx(row.getCell(7) == null ? "": row.getCell(7).getStringCellValue());
				c.setState(row.getCell(8) == null ? "": row.getCell(8).getStringCellValue());
				FailInfo fi = null;
				if(row.getCell(0) == null) {
					fi = new FailInfo();
					fi.setName("");
					fi.setInfo("第 " + (i + 1) + " 行" + "excel 中姓名列存在空的单元格");
				} else {
					fi = insert2db(c, i);
				}
				// 如果 fi == null 则 successCount ++;
				if(fi == null){
					successCount ++;
				} else {
					failCount ++;
					failInfos.add(fi);
				}
			}
			request.put("totalCount", totalCount);
			request.put("successCount", successCount);
			request.put("failCount", failCount);
			request.put("failInfos", failInfos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "importExcel";
	}

	private FailInfo insert2db(Candidate c, int i) {
		FailInfo fi = null;
		String rowinfo = "第 " + (i + 1) + " 行: ";
		Candidate tmpc = candidateService.getCandidate(c.getName());
		// 如果学员已经存在, 则直接返回 failInfo
		if (tmpc != null) {
			fi = new FailInfo();
			fi.setName(c.getName());
			fi.setInfo(rowinfo + "学员姓名已经存在");
			return fi;
		}
		try {
			//保存学员
			candidateService.save(c);
		} catch (Exception e) {
			//如果出现异常, 返回 failInfo
			fi = new FailInfo();
			fi.setName(c.getName());
			fi.setInfo(rowinfo + e.getMessage());
			return fi;
		}
		return null;
	}
	//export excel candidate rel info
		public String relExport(){
			Workbook workbook = null;
			if("xls".equals(format)){
				workbook = new HSSFWorkbook();
			}
			if("xlsx".equals(format)){
				workbook = new XSSFWorkbook();
			}
			candidate = candidateService.getCandidate(cid);
			//创建 sheet
			Sheet sheet = workbook.createSheet("学员信息");
			//创建 row
			Row row = sheet.createRow(0);
			//创建 cell
			row.createCell(0).setCellValue("姓名");
			row.createCell(1).setCellValue("性别");	
			row.createCell(2).setCellValue("单位");
			row.createCell(3).setCellValue("职务");
			row.createCell(4).setCellValue("出生年月");
			row.createCell(5).setCellValue("最高学历");
			row.createCell(6).setCellValue("参加工作时间");
			row.createCell(7).setCellValue("编制类型");
			row.createCell(8).setCellValue("状态");
			row.createCell(9).setCellValue("学分累计");
			Row r = sheet.createRow(1);
			r.createCell(0).setCellValue(candidate.getName());
			r.createCell(1).setCellValue(candidate.getGender());
			r.createCell(2).setCellValue(candidate.getUnit());
			r.createCell(3).setCellValue(candidate.getPost());
			r.createCell(4).setCellValue(candidate.getBirthday());
			r.createCell(5).setCellValue(candidate.getDegree());
			r.createCell(6).setCellValue(candidate.getOperatingTime());
			r.createCell(7).setCellValue(candidate.getBzlx());
			r.createCell(8).setCellValue(candidate.getState());
			double credit = 0;
			for(Training t: candidate.getTrainings()){
				credit += t.getCredit();
			}
			r.createCell(9).setCellValue(credit);
			Row row2 = sheet.createRow(2);
			//创建 cell
			row2.createCell(0).setCellValue("培训项目班次名称");
			row2.createCell(1).setCellValue("培训内容");	
			row2.createCell(2).setCellValue("培训级别");
			row2.createCell(3).setCellValue("培训时间");
			row2.createCell(4).setCellValue("培训地点");
			row2.createCell(5).setCellValue("培训学时");
			row2.createCell(6).setCellValue("培训类型");
			row2.createCell(7).setCellValue("培训机构");
			row2.createCell(8).setCellValue("学分");
			//set to list
			List<Training> trainings = new ArrayList<Training>(candidate.getTrainings());
			for(int i = 0; i < trainings.size(); i ++){
				Training tt = trainings.get(i);
				Row rr = sheet.createRow(i+3);
				rr.createCell(0).setCellValue(tt.getName());
				rr.createCell(1).setCellValue(tt.getContent());
				rr.createCell(2).setCellValue(tt.getLevel());
				rr.createCell(3).setCellValue(tt.getTrainingTime());
				rr.createCell(4).setCellValue(tt.getLocation());
				rr.createCell(5).setCellValue(tt.getCreditHour());
				rr.createCell(6).setCellValue(tt.getTrainingLx());
				rr.createCell(7).setCellValue(tt.getTrainingOrg());
				rr.createCell(8).setCellValue(tt.getCredit());
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
		List<Candidate> candidates = candidateService.getAll();
		//创建 sheet
		Sheet sheet = workbook.createSheet("学员信息");
		//创建 row
		Row row = sheet.createRow(0);
		//创建 cell
		row.createCell(0).setCellValue("姓名");
		row.createCell(1).setCellValue("性别");	
		row.createCell(2).setCellValue("单位");
		row.createCell(3).setCellValue("职务");
		row.createCell(4).setCellValue("出生年月");
		row.createCell(5).setCellValue("最高学历");
		row.createCell(6).setCellValue("参加工作时间");
		row.createCell(7).setCellValue("编制类型");
		row.createCell(8).setCellValue("状态");
		row.createCell(9).setCellValue("学分累计");
		
		for(int i = 0; i < candidates.size(); i ++){
			Candidate c = candidates.get(i);
			Row r = sheet.createRow(i+1);
			r.createCell(0).setCellValue(c.getName());
			r.createCell(1).setCellValue(c.getGender());
			r.createCell(2).setCellValue(c.getUnit());
			r.createCell(3).setCellValue(c.getPost());
			r.createCell(4).setCellValue(c.getBirthday());
			r.createCell(5).setCellValue(c.getDegree());
			r.createCell(6).setCellValue(c.getOperatingTime());
			r.createCell(7).setCellValue(c.getBzlx());
			r.createCell(8).setCellValue(c.getState());
			double credit = 0;
			for(Training t: c.getTrainings()){
				credit += t.getCredit();
			}
			r.createCell(9).setCellValue(credit);
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
		return "exportXls";
	}

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
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String tmp = sdf.format(new Date());
		String tmp2 = "学员信息";
		if(cid != null){
			tmp2 = "学员-培训-关联信息";
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
	public File getImportExcelFile() {
		return importExcelFile;
	}
	public void setImportExcelFile(File importExcelFile) {
		this.importExcelFile = importExcelFile;
	}
	
	@Override
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	public String getImportExcelFileFileName() {
		return importExcelFileFileName;
	}
	public void setImportExcelFileFileName(String importExcelFileFileName) {
		this.importExcelFileFileName = importExcelFileFileName;
	}

}
