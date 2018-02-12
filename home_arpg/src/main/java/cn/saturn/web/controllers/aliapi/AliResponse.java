package cn.saturn.web.controllers.aliapi;

import com.alibaba.fastjson.JSON;

public class AliResponse {
	
	 //特权码
    public static final State STATE_1 = new State(1, "成功");
    public static final State STATE_10 = new State(10, "请求参数错误/无效的请求数据,校验签名失败");
    public static final State STATE_184 = new State(184, "无效的特权码");
    
    private  long  id;
	private State state;
	private Data data;
	
	public String toJsonString() {
        return JSON.toJSON(this).toString();
    }
	
	public static class State {
        public int code; // 响应码
        public String msg; // 结果描述

        public State(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public String jsonString() {
            return JSON.toJSONString(this);
        }

		@Override
		public String toString() {
			return "State [code=" + code + ", msg=" + msg + "]";
		}
        
    }
	
	public  static class Data{
		
		public String creator;
		public String accountId;
		public String nickName;
		
		public Data() {
			super();
		}

		public Data(String creator, String accountId,String nickName) {
            this.creator = creator;
            this.accountId = accountId;
            this.nickName=nickName;
        }
		
	}

	public AliResponse() {
	}

	public AliResponse(long id, State state, Data data) {
		this.id = id;
		this.state = state;
		this.data = data;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}
