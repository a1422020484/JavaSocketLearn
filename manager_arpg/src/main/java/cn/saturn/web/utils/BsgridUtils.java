package cn.saturn.web.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import zyt.utils.ListUtils;

public class BsgridUtils {

	public static int startRow(int curPage, int pageSize) {
		return pageSize * (curPage - 1);
	}
	
	public static int startRowStart(int curPage, int pageSize) {
		if(curPage==1){
			return 0;
		}else{
		return pageSize * (curPage- 1);
		}
	}

	public static int endRow(int curPage, int pageSize, int totalRows) {
		// 检测页面数量
		if (pageSize == 0) {
			return totalRows; // 全部
		}
		// 计算结束
		int startRow = startRow(curPage, pageSize);
		int endRow = startRow + pageSize;
		// 检测是否超过上限
		if (endRow > totalRows) {
			endRow = totalRows;
		}
		return endRow;
	}

	public static final String emptyJson = "{\"success\": true, \"totalRows\": 0, \"curPage\": 1, \"data\": []}";

	public static String jsonStr(int totalRows, int curPage, int pageSize, List<Map<String, Object>> listDatas) {


		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		jsonMap.put("success", true);
		jsonMap.put("totalRows", totalRows);
		jsonMap.put("curPage", curPage);
		jsonMap.put("data", (listDatas != null) ? listDatas : new ArrayList<>());

		// 转成json
		String jsonString = JSON.toJSONString(jsonMap);
		
		//@SuppressWarnings("unchecked")
		//Map<String, String> map = JSON.parseObject(jsonString, Map.class);
		
		return jsonString;

	}
	
	/**
	 * 把数据转成Map数据
	 * 
	 * @param list
	 * @param action
	 * @return
	 */
	public static <T> List<Map<String, Object>> createListDatas(List<T> list, IConvert<T> action) {
		if (list.isEmpty() || action == null) {
			return null;
		}

		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		// 遍历器
		ListUtils.IAction<T> action0 = new ListUtils.IAction<T>() {
			@Override
			public boolean action(T data, Iterator<?> iter) {
				Map<String, Object> map = new HashMap<>();
				if (action.convert(data, map)) {
					// 添加对象
					retList.add(map);
				}
				return true;
			}
		};
		// 构造器
		ListUtils.action(list, action0, 0);

		return retList;
	}

	public interface IConvert<T> {
		public boolean convert(T obj, Map<String, Object> map);
	}

}
