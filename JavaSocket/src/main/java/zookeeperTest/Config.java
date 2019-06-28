package zookeeperTest;

import java.io.Serializable;

public class Config implements Serializable {

	private static final long serialVersionUID = 1L;
	private String userNm;
	private String userPw;

	public Config() {
	}

	public Config(String userNm, String userPw) {
		this.userNm = userNm;
		this.userPw = userPw;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	@Override
	public String toString() {
		return "Config [userNm=" + userNm + ", userPw=" + userPw + "]";
	}

}
