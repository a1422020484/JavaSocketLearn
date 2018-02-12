package cn.saturn.web.controllers.poke.dao;

public class TempPreset {
	
	private int id;
	private int presetid;
	private int preset1;
	private int num1;
	private int preset2;
	private int num2;
	private int preset3;
	private int num3;
	private int preset4;
	private int num4;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPresetid() {
		return presetid;
	}
	public void setPresetid(int presetid) {
		this.presetid = presetid;
	}
	public int getPreset1() {
		return preset1;
	}
	public void setPreset1(int preset1) {
		this.preset1 = preset1;
	}
	public int getNum1() {
		return num1;
	}
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public int getPreset2() {
		return preset2;
	}
	public void setPreset2(int preset2) {
		this.preset2 = preset2;
	}
	public int getNum2() {
		return num2;
	}
	public void setNum2(int num2) {
		this.num2 = num2;
	}
	public int getPreset3() {
		return preset3;
	}
	public void setPreset3(int preset3) {
		this.preset3 = preset3;
	}
	public int getNum3() {
		return num3;
	}
	public void setNum3(int num3) {
		this.num3 = num3;
	}
	public int getPreset4() {
		return preset4;
	}
	public void setPreset4(int preset4) {
		this.preset4 = preset4;
	}
	public int getNum4() {
		return num4;
	}
	public void setNum4(int num4) {
		this.num4 = num4;
	}
	public TempPreset() {
		super();
	}
	public TempPreset(int id, int presetid, int preset1, int num1, int preset2, int num2, int preset3, int num3,
			int preset4, int num4) {
		super();
		this.id = id;
		this.presetid = presetid;
		this.preset1 = preset1;
		this.num1 = num1;
		this.preset2 = preset2;
		this.num2 = num2;
		this.preset3 = preset3;
		this.num3 = num3;
		this.preset4 = preset4;
		this.num4 = num4;
	}
	@Override
	public String toString() {
		return "TempPreset [id=" + id + ", presetid=" + presetid + ", preset1=" + preset1 + ", num1=" + num1
				+ ", preset2=" + preset2 + ", num2=" + num2 + ", preset3=" + preset3 + ", num3=" + num3 + ", preset4="
				+ preset4 + ", num4=" + num4 + "]";
	}
	
}
