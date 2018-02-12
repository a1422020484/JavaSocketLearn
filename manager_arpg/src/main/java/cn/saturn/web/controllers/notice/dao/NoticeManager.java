package cn.saturn.web.controllers.notice.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import cn.saturn.web.utils.BsgridUtils;
import net.paoding.rose.web.Invocation;
import zyt.spring.component.ComponentManager;

@Component
public class NoticeManager implements ComponentManager.IComponent {
	@Autowired
	protected NoticeDAO dao;

	public AtomicLong idMaker = new AtomicLong();

	public NoticeModel makNoticeModel(NoticeModel model) {
		if (model.getId() <= 0)
			model.setId(newId());
		return model;
	}

	/**
	 * 创建一个新的ID
	 * 
	 * @return
	 */
	private long newId() {
		return idMaker.incrementAndGet();
	}

	public NoticeDAO getDAO() {
		return dao;
	}

	@Override
	public boolean reload(ApplicationContext context) {
		long maxId = 1;
		try {
			maxId = dao.getMaxId();
		} catch (Exception e) {
			maxId = 1;
		}
		
		idMaker.set(maxId);
		return true;
	}

	@Override
	public void release() {
	}

	public String listJson(Invocation inv) throws IOException {
		HttpServletRequest request = inv.getRequest();

		// 读取参数
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		int totalRows = dao.getCount();

		// 计算起始和结束
		int startRow = BsgridUtils.startRow(curPage, pageSize);
		// 遍历
		List<NoticeModel> list = dao.get(startRow, pageSize);

		List<Map<String, Object>> listDatas = new ArrayList<>();
		// 转换数据
		for (NoticeModel model : list) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", model.getId());
			map.put("s_id", model.getS_id());
			map.put("content", model.getNotice());
			map.put("enable", model.isEnable() ? "开启" : "关闭");
			map.put("imgs", model.getImgs());
			listDatas.add(map);
		}

		// 生成json
		String jsonStr = BsgridUtils.jsonStr(totalRows, curPage, pageSize, listDatas);
		return "@" + jsonStr;
	}
}
