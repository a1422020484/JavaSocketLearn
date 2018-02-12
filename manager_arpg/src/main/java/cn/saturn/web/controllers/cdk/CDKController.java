package cn.saturn.web.controllers.cdk;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.saturn.operation.TimeUtils;
import cn.saturn.web.code.cdkey.domain.CDKey;
import cn.saturn.web.code.cdkey.domain.CDKeyDAO;
import cn.saturn.web.code.cdkey.domain.CDKeyManager;
import cn.saturn.web.controllers.annotation.LoginCheck;
import cn.saturn.web.controllers.annotation.MainView;
import cn.saturn.web.controllers.annotation.NotBlank;
import cn.saturn.web.controllers.annotation.UserAuthority;
import cn.saturn.web.controllers.cdk.dao.PresetCDK;
import cn.saturn.web.controllers.mail.dao.PresetDAO;
import cn.saturn.web.controllers.mail.dao.PresetManager;
import cn.saturn.web.controllers.mail.dao.PresetModel;
import cn.saturn.web.redis.RedisKeys;
import cn.saturn.web.redis.RedisUtils;
import cn.saturn.web.utils.BsgridUtils;
import cn.saturn.web.utils.DateUtils;
import cn.saturn.web.utils.EasyUiUtils;
import cn.saturn.web.utils.ListExtUtil;
import cn.saturn.web.utils.PageModel;
import cn.saturn.web.utils.PageNoticeUtil;
import cn.saturn.web.utils.PresetCDKUtils;
import cn.saturn.web.utils.Utils;
import cn.saturn.web.utils.WebUtils;
import cn.saturn.web.utils.cdk.cdkey32;
import net.paoding.rose.web.Invocation;
import net.paoding.rose.web.annotation.Param;
import net.paoding.rose.web.annotation.Path;
import net.paoding.rose.web.annotation.rest.Get;
import net.paoding.rose.web.annotation.rest.Post;

@Path("")
public class CDKController {

	@Resource
	CDKeyDAO cdkDAO;
	
	@Resource
	PresetDAO presetDAO;

	public final String resourcePath = Utils.resourceFolder + "/cdk/";
	
	@MainView
	@LoginCheck
	@UserAuthority(PageModel.cdk_page)
	@Get("list")
	public String list(Invocation inv) throws Throwable {

		Utils.setAttributeValue(inv, "tableUrl", Utils.url(inv, "/cdk/listJson"));
		Utils.setAttributeValue(inv, "addUrl", Utils.url(inv, "/cdk/edit"));
		Utils.setAttributeValue(inv, "toDownloadUrl", Utils.url(inv, "/cdk/toDownload"));
		Utils.setAttributeValue(inv, "toDeleteUrl", Utils.url(inv, "/cdk/toDelete"));
		Utils.setAttributeValue(inv, "uploadUrl", Utils.url(inv, "/cdk/upload"));

		return "list";
	}
	
	@MainView
	@LoginCheck
	@Get("delete")
	public String delete(Invocation inv) throws Throwable {

		return "delete";
	}
	
	
	@Get("toDeleteCDK")
	public String toDeleteCDK(Invocation inv,  @Param("cdkText") String cdkText, @Param("playerId") int playerId) throws Throwable {
		String key = null;
		int type = 0;
		if(StringUtils.isNumeric(cdkText.trim())){
			type = Integer.parseInt(cdkText.trim());
		}else{
			key = cdkText.trim();
		}
		cdkDAO.deleteCDkey(key);
		cdkDAO.deleteCDtype(type);
		// 删除 redis 中的数据
	    RedisUtils.del(RedisKeys.K_CDKEY);
		return "@ 删除成功 ";
	}

	
	@MainView
	@LoginCheck
	@UserAuthority(PageModel.cdk_page)
	@Get("show")
	public String show(Invocation inv) throws Throwable {
		
		return "info";
	}
	
	@UserAuthority(PageModel.cdk_page)
	@Get("toCheck")
	public String toCheck(Invocation inv,  @Param("cdkText") String cdkText, @Param("playerId") int playerId) throws Throwable {
		String key = null;
		int type = 0;
		if(StringUtils.isNumeric(cdkText)){
			type = Integer.parseInt(cdkText);
		}else{
			key = cdkText;
		}
		
		String overTime = "";
		String keyType = "";
		String totalNum = "";
		String useCount = "";
		String useLimit = "";
		String usedNum = "";
		String leftNum = "";
		
		List<CDKey> cdKeyList = null;
		CDKey cdKey  = null;
		if(type != 0){
			cdKey = CDKeyManager.getByType(type);
			if(cdKey != null && !cdKey.isGlobal()){
				if(playerId != 0){
					cdKeyList = CDKeyManager.getUsedList(type, playerId);
				}
			}
		}
		
		if(key != null){
			cdKey = CDKeyManager.getCdKey(key);
		}
		
		if(cdKey == null){
			return "@cdk不存在!";
		}
		    
        type = cdKey.getType();
		overTime = cdKey.getOverTime();
		useCount = cdKey.getUseCount()+"";
		useLimit = cdKey.getUseLimit() + "";
		if(cdKey.isGlobal()){
			keyType = "唯一码";
			totalNum = "1";
			usedNum = cdKey.getUsedNum()+"";
			leftNum = (cdKey.getUseLimit() - cdKey.getUsedNum())+"";
		}else{		
			keyType = "普通码";
			int total = CDKeyManager.getAllNumByType(cdKey.getType());
			int used = CDKeyManager.getUsedNumByType(cdKey.getType());
			totalNum = total +"";
			if(key!=null){
				if(cdKey.getPlayerid()>0){
					usedNum = "已使用";
					leftNum = "playerid:"+cdKey.getPlayerid();
				}else{
					usedNum = "未使用";
					leftNum = (total - used)+"";
				}
			}else{
				usedNum = used +"";
				leftNum = (total - used)+"";
			}
		}
		if(cdKeyList == null)
			key = cdKey.getKey();
		else{
			for(CDKey key2 : cdKeyList){
				key += key2.getKey()+",";
			}
		}
		HttpServletRequest request = inv.getRequest();
		request.setAttribute("key", key);
		request.setAttribute("type", type);
		request.setAttribute("overTime", overTime);
		request.setAttribute("keyType", keyType);
		request.setAttribute("totalNum", totalNum);
		request.setAttribute("useCount", useCount);
		request.setAttribute("useLimit", useLimit);
		request.setAttribute("usedNum", usedNum);
		request.setAttribute("leftNum", leftNum);
		return "cdkTable";
	}
	
	
	@UserAuthority(PageModel.cdk_page)
	@Get("upload")
	public String upload(Invocation inv) throws Throwable {
		Utils.setAttributeValue(inv, "toUploadUrl", Utils.url(inv, "/cdk/toUploadUrl"));
		return "upload";
	}

	@UserAuthority(PageModel.cdk_page)
	@Post("toUploadUrl")
	public String toUpload(MultipartFile file) throws Throwable {
		final int batchCount = 2000;

		if (file == null)
			return "@ file not found";

		BufferedReader reader = null;
		try {
			InputStreamReader fs = new InputStreamReader(file.getInputStream());
			reader = new BufferedReader(fs);
			String str = null;

			List<CDKey> batchList = new ArrayList<>();

			while ((str = reader.readLine()) != null) {
				String[] strs = str.split(",");
				if (strs.length >= 6) {
					CDKey cdKey = new CDKey();
					cdKey.setKey(strs[0]);
					cdKey.setGlobal(strs[1].equals("1"));
					cdKey.setOverTime(strs[2]);
					cdKey.setAward(strs[3]);
					cdKey.setPlatformLimit(strs[4]);
					cdKey.setType(Integer.parseInt(strs[5]));
					cdKey.setUseCount(strs.length>6?Integer.parseInt(strs[6]):1);
					cdKey.setUseLimit(strs.length>7?Integer.parseInt(strs[7].replaceAll("\"", "").trim()):0);
					cdKey.setEnable(false);
					cdKey.setPlayerid(0);
					cdKey.setServerid(0);

					batchList.add(cdKey);

					// 大于
					if (batchList.size() >= batchCount) {
						try {
							cdkDAO.insertOrUpdate(batchList);
						} catch (Exception e) {
							e.printStackTrace();
						}
						// 清除
						batchList.clear();
					}

				}
			}

			if (batchList.size() > 0) {
				try {
					cdkDAO.insertOrUpdate(batchList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 清除
				batchList.clear();
			}

		} catch (Exception e) {

		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (Exception e) {
			}
		}

		return "@ upload ok!";
	}

	@Get("showAward")
	public String showAward(Invocation inv) {
		return "cdk_award";
	}

	@LoginCheck
	@UserAuthority(PageModel.cdk_page)
	@Post("delAward")
	public String delAward(Invocation inv, @Param("id") int id, @Param("row") int row) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@" + EasyUiUtils.getFail();

		PresetCDK presetData = model.getValue(PresetCDK.class);

		// 条件
		List<String> reqList = ListExtUtil.arrayToList(presetData.getAwards());
		reqList.remove(row);
		presetData.setAwards(ListExtUtil.listToArray(reqList, new String[0]));

		if (!model.setValue(presetData))
			return "@" + EasyUiUtils.getFail();

		PresetManager.getInstance().insertByDAO(model);

		return "@" + EasyUiUtils.getSuccess();
	}

	@LoginCheck
	@UserAuthority(PageModel.cdk_page)
	@Post("saveAward")
	public String saveRewAndReq(Invocation inv, @Param("id") long id, @Param("item_id") long item_id,
			@Param("item_num") long item_num) {
		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return "@{'msg':'找不到数据'}";
		PresetCDK presetData = model.getValue(PresetCDK.class);

		// 条件
		List<String> awardList = ListExtUtil.arrayToList(presetData.getAwards());
		awardList.add(item_id + "_" + item_num);
		if (awardList.size() > 20)
			return "@{'msg':'必须小于20个'}";
		presetData.setAwards(ListExtUtil.listToArray(awardList, new String[0]));

		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);
		String result = PresetCDKUtils.getJsonItem(awardList.size() - 1, item_id, item_num);
		// 转成json
		return "@" + result;
	}

	@LoginCheck
	@UserAuthority(PageModel.cdk_page)
	@Post("updateAward")
	public String updateAward(Invocation inv, @Param("id") long id, @Param("row") int row,
			@Param("item_id") int item_id, @Param("item_num") int item_num) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null && row >= 0)
			return "@{'msg':'找不到数据'}";
		PresetCDK presetData = model.getValue(PresetCDK.class);
		// 条件
		String[] awards = presetData.getAwards();
		awards[row] = PresetCDKUtils.getAwards(item_id, item_num);
		presetData.setAwards(awards);

		if (!model.setValue(presetData))
			return "@{'msg':'数据匹配失败'}";

		PresetManager.getInstance().insertByDAO(model);
		String result = PresetCDKUtils.getJsonItem(row, item_id, item_num);
		// 转成json
		return "@" + result;
	}

	@UserAuthority(PageModel.cdk_page)
	@Get("edit")
	public String edit(Invocation inv) {
		Utils.setAttributeValue(inv, "toEdit", Utils.url(inv, "/cdk/toEdit"));

		PresetModel model = null;
		PresetCDK presetData = null;
		model = new PresetModel();
		//model.resetId();
		model.setType(PresetManager.presetType_cdk);
		presetData = new PresetCDK();
		presetData.setId((int) PresetManager.getInstance().newId());
		
		model.setValue(presetData);
		//PresetManager.getInstance().insertByDAO(model);
		int modelId=presetDAO.insertNew(model);
		Utils.setAttributeValue(inv, "id", modelId);
		return "edit";
	}

	@UserAuthority(PageModel.cdk_page)
	@Post("toEdit")
	public String toEdit(Invocation inv, @Param("name") String name, @Param("id") long id, @Param("global") int global,
			@Param("num") int num, @Param("fontStr") String fontStr, @Param("overTime") String overTime,
			@Param("platformLimit") String platformLimit, @Param("type") int type, @Param("cdklength") int cdklength, 
			@Param("useCount") int useCount, @Param("useLimit") int useLimit) throws Throwable {
		//cdklength
		final int batchCount = 2000;
		PresetModel model = PresetManager.getInstance().get(id);

		if (model == null)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, id + " 创建对象不存在!");
		PresetCDK presetData = model.getValue(PresetCDK.class);
		if (presetData == null)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, id + " 创建对象不存在!");

		// 做更新操作
		if (fontStr == null || (fontStr.length() < 1 && fontStr.length() <= 6))
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "CDK前缀为空或者  1~6 位置");

		if (StringUtils.isEmpty(name))
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "CDK name 不能为空.");

		if (num <= 0)
			return "@" + PageNoticeUtil.notic(PageNoticeUtil.ERROR, "数量必须大于0.");
		
		//默认cdk长度为11位
		if (cdklength <= 0 || cdklength > 17){
			cdklength=11;
		}
		// 编辑
		// 新增
		// 检测pid是否被使用了
		List<PresetModel> all = PresetManager.getPresetList(PresetManager.presetType_cdk);
		int count = (all != null) ? all.size() : 0;
		for (int i = 0; i < count; i++) {
			PresetModel model0 = all.get(i);
			PresetCDK presetData0 = model0.getValue(PresetCDK.class);
			if (presetData0 == null) {
				continue;
			}
		}
		model.setRemark("");
		presetData.setId((int) model.getId());
		presetData.setNum(num);
		presetData.setName(name);
		presetData.setFontStr(fontStr);
		presetData.setOverTime(WebUtils.Html.getDate0(overTime));
		presetData.setCreateTime(WebUtils.Html.getDate0(DateUtils.getNowDay()));
		presetData.setType(type);
		presetData.setUseCount(useCount==0?1:useCount);
		presetData.setUseLimit(useLimit);
		model.setValue(presetData);
		List<CDKey> batchList = new ArrayList<>();

		int seed = (int) (Math.random() * 100.0D) * 21474836;
		List<String> lst = ListExtUtil.arrayToList(presetData.getAwards());
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lst.size(); i++)
			sb.append(lst.get(i)).append(i < lst.size() - 1 ? ";" : "");
		
		// 写入文件
		File file = new File(resourcePath + fontStr + "_" + name + "_" + presetData.getCreateTime() + ".csv");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));

		for (int i = 0; i < num; i++) {
			int time32 = (int) System.currentTimeMillis();
			String cdk = cdkey32.encodekey(time32, seed + i, 0);
			cdk=cdk.substring(0, cdklength);
			CDKey cdKey = new CDKey();
			cdKey.setGlobal(global == 1);
			cdKey.setEnable(false);
			cdKey.setPlayerid(0);
			cdKey.setServerid(0);
			cdKey.setAward(sb.toString());
			cdKey.setKey(fontStr + cdk);
			cdKey.setOverTime(overTime);
			cdKey.setPlatformLimit(platformLimit);
			cdKey.setType(type);
			cdKey.setUseCount(useCount==0?1:useCount);
			cdKey.setUseLimit(useLimit);
			batchList.add(cdKey);

			bw.write(cdKey.getKey() + "," + global + "," + cdKey.getOverTime() + "," + cdKey.getAward() + ","
					+ cdKey.getPlatformLimit() + "," + cdKey.getType() + "," + cdKey.getUseCount() + "," + cdKey.getUseLimit() + "\t\n");

			if (batchList.size() >= batchCount || i >= num - 1) {
				try {
					cdkDAO.insertOrUpdate(batchList);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 清除
				batchList.clear();
			}
		}

		PresetManager.getInstance().updateByDAO(model);
		bw.close();

		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "创建激活码成功.");
	}

	@UserAuthority(PageModel.cdk_page)
	@Get("toDownload")
	public void download(Invocation inv, @NotBlank @Param("id") int id) {

		PresetModel model = PresetManager.getInstance().get(id);
		if (model == null)
			return;
		PresetCDK presetData = model.getValue(PresetCDK.class);
		if (presetData == null)
			return;

		try {
			File file = new File(resourcePath + presetData.toFileName());
			if(!file.exists()){
				System.out.println("找不到cdk文件->"+presetData.toFileName());
			}
			
			String filename = file.getName();
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			inv.getResponse().reset();
			inv.getResponse().addHeader("Content-Disposition",
					"attachment;filename=" + new String(filename.getBytes("utf-8"), "ISO-8859-1"));
			inv.getResponse().addHeader("Content-Length", "" + file.length());
			inv.getResponse().setContentType("application/octet-stream");

			OutputStream toClient = new BufferedOutputStream(inv.getResponse().getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// 删除预设页面
	@LoginCheck
	@UserAuthority(PageModel.cdk_page)
	@Get("toDelete")
	public String toRemove(Invocation inv, @NotBlank @Param("id") int id) {
		String defualUrl = Utils.url(inv, "/cdk/list");
		PresetManager.toDelete(inv, id, defualUrl);
		return "@" + PageNoticeUtil.notic(PageNoticeUtil.INFO, "删除成功", "cdk/list?mainView=true");
	}

	@Post("listJson")
	public String listJson(Invocation inv) throws IOException {

		// 转换接口
		BsgridUtils.IConvert<PresetModel> action = new BsgridUtils.IConvert<PresetModel>() {

			@Override
			public boolean convert(PresetModel model, Map<String, Object> map) {
				map.put("id", model.getId());
				map.put("remark", model.getRemark());
				// 解析数据
				PresetCDK presetData = model.getValue(PresetCDK.class);
				if (presetData != null) {

					map.put("id", presetData.getId());
					map.put("num", presetData.getNum());
					map.put("name", presetData.getName());
					map.put("fontStr", presetData.getFontStr());
					map.put("cdkfile", presetData.toFileName());
					map.put("overTime", TimeUtils.toString(presetData.getOverTime(), "yyyy-MM-dd"));
					map.put("platformLimit", presetData.getPlatfromLimit());
					map.put("type", presetData.getType());
					map.put("useCount", presetData.getUseCount()==0?"1":presetData.getUseCount());
					map.put("useLimit", presetData.getUseLimit()==0?"无上限":presetData.getUseLimit());
					// 处理时间
					int createTime = presetData.getCreateTime();
					if (createTime <= 0) {
						map.put("createTime", "无限期");
					} else {
						map.put("createTime", TimeUtils.toString(createTime, "yyyy-MM-dd"));
					}
				}
				return true;
			}

		};

		return PresetManager.listJson(inv, PresetManager.presetType_cdk, action);

	}
}
