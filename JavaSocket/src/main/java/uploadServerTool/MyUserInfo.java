package uploadServerTool;

import com.jcraft.jsch.UserInfo;

public class MyUserInfo implements UserInfo {
	public String getPassword() {
		return null;
	}

	public boolean promptYesNo(String str) {
		return true;
	}

	public String getPassphrase() {
		return null;
	}

	public boolean promptPassphrase(String message) {
		return true;
	}

	public boolean promptPassword(String message) {
		return true;
	}

	public void showMessage(String message) {
	}
}
