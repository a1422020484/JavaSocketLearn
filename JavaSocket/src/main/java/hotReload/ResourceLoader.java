package hotReload;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源载入工具<br/>
 * 在配置ResourceFolder指定目录下的所有后缀为.xml或.properties的文件均为资源文件,资源名就是资源文件的文件名的前缀,<br/>
 * 例:Language.xml的资源名是Language,Language.properties的资源名是Language,
 * 所以只要以Language为前缀的资源文件被视为同一个资源.<br/>
 * <br/>
 * 每个资源名应该有一个对应的ResourceLoader类,在系统启动时,由载入工具加载所有资源文件,
 * 并调用已绑定的处理类的load方法,同时开始监控资源文件,当资源文件被创建或更新时重新调用处理类的load方法.<br/>
 * <br/>
 * 绑定资源文件和处理类的方法:<br/>
 * 处理类必须继承{@link ResourceLoader},将会调用处理类的
 * {@link ResourceLoader#getResourceName()}来获得绑定的资源名.<br/>
 * 例:<br/>
 * <code>
 * class LanguageManager extends ResourceLoader {<br/>
 * &nbsp;&nbsp;void load(...) {...}<br/>
 * }<br/>
 * </code> 将默认以Language为资源名绑定资源文件Language.xml或Language.properties...<br/>
 * 例:<br/>
 * <code>
 * class LanguageManager extends ResourceLoader {<br/>
 * &nbsp;&nbsp;void load(...) {...}<br/>
 * &nbsp;&nbsp;String getResourceName() {return "i18n"}<br/>
 * }<br/>
 * </code> 将以i18n为资源名绑定资源文件i18n.xml或i18n.properties...<br/>
 * <br/>
 * <b>注意:资源名匹配区分大小写,例i18n与I18N不等同.</b>
 *
 * @author zuojie.x
 */
public abstract class ResourceLoader {

	/**
	 * 在系统启动,资源文件创建或更新时,调用此方法.<br
	 * /. <br/>
	 * <b>注意:文件流会自动关闭,不需要显示的关闭.</b>
	 *
	 * @param is
	 *            文件输入流
	 */
	public abstract void load(InputStream is) throws IOException;

	/**
	 * 资源名对应资源文件的前缀,例Language.xml的资源名是Language.<br/>
	 * 资源名默认取类名的简单版,如果类名以'Manager'结束,那么只取前面的部分.<br/>
	 * <li>xx.xx.LanguageManager类的默认值就是Language.</li> <li>
	 * xx.xx.LanguageMan类的默认值就是LanguageMan.</li>
	 *
	 * @return 资源名
	 */
	public String getResourceName() {
		String classSimpleName = getClass().getSimpleName();
		int subEnd = 0;
		/*
		 * 检查是否以'Manager'结尾, 如果是,那么截取前面的部分,例LanguageManager则返回Language
		 */
		if (classSimpleName.endsWith("Manager")) {
			subEnd = classSimpleName.length() - 7;
		}
		return classSimpleName.substring(0, subEnd);
	}

	/**
	 * @return 加载优先级,值越小越优先加载,默认为10000.
	 */
	public int getPriority() {
		return 10000;
	}

	/**
	 * 此资源加载后,需要加载的其它资源的名称,名称规则与{@link #getResourceName()}相同.
	 * 只支持在服务器启动成功后文件变化时的加载,只支持一个后续加载.
	 * 
	 * @return
	 */
	public String afterLoad() {
		return null;
	}
}
