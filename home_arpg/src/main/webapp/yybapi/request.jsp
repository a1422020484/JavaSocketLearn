<%@page import="cn.saturn.web.controllers.yybapi.dao.OpRecordManager"%>
<%@page import="cn.saturn.web.utils.MD5Util"%>
<%@page import="java.util.Base64"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.reflect.Field"%>
<%@page import="javax.crypto.Mac"%>
<%@page import="javax.crypto.spec.SecretKeySpec"%>
<%@page import="javax.crypto.SecretKey"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
public static class YYBRequestBase{
	public long timestamp;//时间戳
	public String sig;//签名
	public String appKey;
	
	public boolean checkTimestamp(){
    	long localTimestamp = System.currentTimeMillis();
    	if(localTimestamp/1000 - timestamp>5*60)
    		return true;
    	return true;
    }
    
    public boolean checkSig(HttpServletRequest request){
    	try{
    		String path = request.getContextPath() +request.getServletPath();
    		path = URLEncoder.encode(path, "UTF-8");
    		String str = "GET&"+path;
        	
    		String requestStr = "";
        	TreeMap<String, Object> map = new TreeMap<>();
        	Field[] fields = this.getClass().getFields();
        	String[] types1={"int","java.lang.String","boolean","char","float","double","long","short","byte"};  
            String[] types2={"Integer","java.lang.String","java.lang.Boolean","java.lang.Character","java.lang.Float","java.lang.Double","java.lang.Long","java.lang.Short","java.lang.Byte"};  
            for (int j = 0; j < fields.length; j++) {  
                fields[j].setAccessible(true);  
                // 字段名  
              //System.out.print(fields[j].getName() + ":");  
                // 字段值  
                for(int i=0;i<types1.length;i++){  
                    if(fields[j].getType().getName()  
                            .equalsIgnoreCase(types1[i])|| fields[j].getType().getName().equalsIgnoreCase(types2[i])){  
                        try {  
                            //System.out.print(fields[j].get(this)+"     ");  
                            map.put(fields[j].getName(), fields[j].get(this));
                        } catch (Exception e) {  
                            e.printStackTrace();  
                        }   
                    }  
                }  
            }
            TreeMap<String, Object> params = new TreeMap<>();
            params.putAll(map);
            for (Iterator<String> it = params.keySet().iterator(); it.hasNext();) {// 遍例集合 
            	String key = it.next();
                //System.out.println("key->"+key +",value->"+params.get(key));  
                if("sig".equals(key) || "appKey".equals(key))
                	continue;
                requestStr+=(key+"="+params.get(key)+"&");
            }  
            
            requestStr = requestStr.substring(0, requestStr.length()-1);
            str += "&"+URLEncoder.encode(requestStr,"UTF-8");
            //System.out.println("str->"+str);
            String mySig = YYBUtil.HmacSHA1Encrypt(str, appKey+"&");
            //System.out.println("sig->"+sig);
            //System.out.println("mySig->"+mySig);
            if(mySig.equals(sig))
            	return true;
        	return false;
    	}catch(Exception e){
    		e.printStackTrace();
    		return false;
    	}
    }
    
}

public static class YYBRequestSvrList extends YYBRequestBase{  
    public String appid;//手Q appid
    public String area;//标示拉取的区服是QQ区的还是微信区的（QQ区：qq，微信区：wx）
}

public static class YYBRequestRoleInfo extends YYBRequestBase{  
    public String appid;//手Q appid
    public String area;//标示拉取的区服是QQ区的还是微信区的（QQ区：qq，微信区：wx）
    public String openid;
    public String partition;
    public String pkey;
    
    public boolean checkPkey(){
    	String myPkey = MD5Util.MD5(openid+appKey+timestamp).toLowerCase();
    	//System.out.println("pkey->"+pkey+",myPkey->"+myPkey);
    	if(!myPkey.equals(pkey))
    		return false;
    	return true;
    }
}

public static class YYBRequestGameOp extends YYBRequestBase{  
    public String appid;//手Q appid
    public String area;//标示拉取的区服是QQ区的还是微信区的（QQ区：qq，微信区：wx）
    public String openid;
    public String partition;
    public String pkey;
    public String billno;
    public String roleid;
    public String midas_billno;
    public String money;
    public String gold;
    
    public boolean checkPkey(){
    	String myPkey = MD5Util.MD5(openid+appKey+timestamp).toLowerCase();
    	//System.out.println("pkey->"+pkey+",myPkey->"+myPkey);
    	if(!myPkey.equals(pkey))
    		return false;
    	return true;
    }
    
    public boolean checkDuplication(){
    	return !OpRecordManager.isExists(billno);
    }
}

public static class YYBUtil{
	private static final String MAC_NAME = "HmacSHA1";    
    private static final String ENCODING = "UTF-8";
    
    public static String HmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception     
    {           
        byte[] data=encryptKey.getBytes(ENCODING);  
        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称  
        SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);   
        //生成一个指定 Mac 算法 的 Mac 对象  
        Mac mac = Mac.getInstance(MAC_NAME);   
        //用给定密钥初始化 Mac 对象  
        mac.init(secretKey);    
          
        byte[] text = encryptText.getBytes(ENCODING);    
        //完成 Mac 操作  
        byte[] rawHmac = mac.doFinal(text);
        String base64Text = new String(Base64.getEncoder().encode(rawHmac), ENCODING);
        return base64Text;
    }
}
%>