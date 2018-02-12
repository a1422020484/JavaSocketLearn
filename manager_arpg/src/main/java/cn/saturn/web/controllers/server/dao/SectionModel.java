package cn.saturn.web.controllers.server.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import proto.ProtocolWeb;
import zyt.utils.ListUtils;
import cn.saturn.web.model.auto.IModel;

import com.alibaba.fastjson.annotation.JSONField;

public class SectionModel implements IModel {
	public static final int state_Hide = 0; // 隐藏
	public static final int state_Normal = 1; // 正常
	public static final int state_New = 2; // 维护
	public static final String[] stateStrs = new String[] { "隐藏", "正常", "新区" };

	private long id;
	private String name; // 名称
	private int state; // 状态
	private boolean recommend; // 是否推荐
	private String platforms; // 平台信息, 用于筛选平台
	private String tag = "";

	public ProtocolWeb.ServerSection toProtobuf() {
		ProtocolWeb.ServerSection.Builder b = ProtocolWeb.ServerSection.newBuilder();
		b.setSectionId((int) this.getId());
		b.setName(this.getName());
		b.setState(this.getState());
		b.setRecommend(this.isRecommend());

		return b.build();
	}

	@JSONField(serialize = false)
	public String getSectionStr() {
		return "[" + this.id + "]" + this.name;
	}

	@Override
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getState() {
		return state;
	}

	public String getStateStr() {
		return stateStrs[state];
	}

	public void setState(int state) {
		this.state = state;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	@JSONField(serialize = false)
	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getPlatforms() {
		return platforms;
	}

	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}

	/** 检测是否开放平台 **/
	public boolean filtrate(String platform, String version) {
		return filtrate(platforms, platform, version);
	}

	/**
	 * 筛选平台版本
	 * 
	 * @param filtrateStr
	 *            筛选文本
	 * @param platform
	 *            平台
	 * @param version
	 *            版本
	 * @return
	 */
	public static boolean filtrate(String filtrateStr, String platform, String version) {
		// 判断筛选字段是否为空
		if (StringUtils.isEmpty(filtrateStr)) {
			return true; // 空, 全部通過
		}
		// 字符串拆分
		String[] filtrates = filtrateStr.split(";");
		int pcount = (filtrates != null) ? filtrates.length : 0;
		if (pcount <= 0) {
			return true; // 拆分错误
		}
		// 转成list
		List<String> flist = new ArrayList<>();
		for (int i = 0; i < pcount; i++) {
			flist.add(filtrates[i]);
		}

		// 截取出版本筛选字段
		List<String> versionKeys = ListUtils.findAll(flist, new ListUtils.IFilter<String>() {
			@Override
			public boolean check(String d) {
				return d.charAt(0) == '@';
			}
		}, 0);

		// 判断版本
		int vkcount = (versionKeys != null) ? versionKeys.size() : 0;
		if (vkcount > 0) {
			for (int i = 0; i < vkcount; i++) {
				String versionKey = versionKeys.get(i);
				if (versionKey == null) {
					continue;
				}
				// 从当前数组中移除
				flist.remove(versionKey); // 移除处理
				pcount--;

				// 版本检测
				String v0 = versionKey.substring(1);

				// 先确定第二个参数是是比较符号
				char fc = v0.charAt(0);
				if (fc == '>') {
					v0 = v0.substring(1);
					if (version.compareTo(v0) <= 0) {
						return false; // 版本过小
					}
				} else if (fc == '<') {
					v0 = v0.substring(1);
					if (version.compareTo(v0) >= 0) {
						return false; // 版本不对
					}
				} else {
					// 直接比较
					if (version.compareTo(v0) != 0) {
						return false; // 版本不对
					}
				}
			}
		}

		// 筛选平台
		if (pcount <= 0) {
			return true; // 没有平台筛选
		}

		// 验证是否存在相符的平台
		boolean result = false;
		for (int i = 0; i < pcount; i++) {
			// 判断字段是否为空
			String filtrate = flist.get(i);
			if (StringUtils.isEmpty(filtrate)) {
				continue;
			}

			// 检测第一个字符是否是!, 做非检测.
			char fc = filtrate.charAt(0);
			if (fc == '!') {
				// !非处理
				String p = filtrate.substring(1);
				if (platform.equals(p)) {
					return false; // 这个平台过滤, 其他都符合. 是这个平台的直接失败
				}
				result = true; // 其他默认可以通过
				continue;
			}

			// 正常检测
			if (filtrate.equals(platform)) {
				return true; // 完全符合, 直接通过.
			}
		}
		return result;
	}

	public static void main(String[] args) {
		// String filtrate = "Saturn;@1.2.0;uc;360";
		String filtrate = "@>1.2.0;!360;uc";
		String platform = "Saturn";
		String version = "1.2.0";
		System.out.println(filtrate(filtrate, platform, version));

		// // 生成一个Pattern,同时编译一个正则表达式
		// Pattern p = Pattern.compile("[@;]");
		// // 用Pattern的split()方法把字符串按"/"分割
		// String[] results = p.split(filtrate);
		// System.out.println(Arrays.toString(results));
	}

}
