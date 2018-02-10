package nettyServer.util;

public class JsonQuotesUtil {
	 
  
    //恢复单引号(字符串)  
    public  static  String restoreSingleQuotes(String str)  
    {  
        return str.replaceAll("\"", "\'");  
    }  
      
      
    //恢复双引号  
    public  static String restoreDoubleQuote(String str)//恢复单引号（数组）  
    {  
        return str.replaceAll("\'", "\"");  
    }  
   
}
