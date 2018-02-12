package cn.saturn.web.utils;
 
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class ReadFilePath {
	
	public static String  readTxtFile(String filePath){
		
		StringBuffer sbuff= new StringBuffer();
        try {
                String encoding="UTF-8";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    while((lineTxt = bufferedReader.readLine()) != null){
                        System.out.println(lineTxt);
                        sbuff.append(lineTxt+"\n");
                    }
                    read.close();
        }else{
            System.out.println("找不到指定的文件");
        }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
     return sbuff.toString();
    }
     
    public static void main(String argv[]){
        String filePath = "F:\\map_result.txt";
//      "res/";
        String text=readTxtFile(filePath);
        @SuppressWarnings("unchecked")
		Map<Integer,Map<Integer,Map<Integer,Integer>>> maps = (Map<Integer,Map<Integer,Map<Integer,Integer>>>)JSON.parse(text);
        
        System.out.println(maps);
        
        /*Map maps = (Map)JSON.parse(text);
		for (Object map : maps.entrySet()){  
            System.out.println(((Map.Entry)map).getKey()+"     " + ((Map.Entry)map).getValue());  
        }*/
        
    }

}
