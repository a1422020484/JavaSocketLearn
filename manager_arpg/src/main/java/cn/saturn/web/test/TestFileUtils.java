package cn.saturn.web.test;

import java.io.File;
import java.io.IOException;

import cn.saturn.web.utils.FileUtils;

public class TestFileUtils {
	public static void main(String[] args) throws IOException {
		System.out.println(FileUtils.delAllFile2Direct(new File("F:\\导出表\\")));

		File newFile = new File("F:\\导出表\\");
		newFile.mkdirs();
	}
}
