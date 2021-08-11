package model;

public class MyMemberVo {
	// field
	private  String  userid;
	private  String  passwd;
	private  String  username;
	private  String  con;
	private  String  gender;
	private  String  intro;
	private  String  indate;
	
	// Constructor 생성자
	public MyMemberVo() {}
	
	public MyMemberVo(String userid, String passwd, String username, 
			String con, String gender, String intro, String indate) {
		this.userid = userid;
		this.passwd = passwd;
		this.username = username;
		this.con = con;
		this.gender = gender;
		this.intro = intro;
		this.indate = indate;
	}

	public MyMemberVo(String userid, String username, String con,
			String gender, String indate) {
		this.userid = userid;
		this.username = username;
		this.con = con;
		this.gender = gender;
		this.indate = indate;
	}

	// Getter / Setter
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getIndate() {
		return indate;
	}
	public void setIndate(String indate) {
		this.indate = indate;
	}

	// toString
	@Override
	public String toString() {
		return "MyMemberVo [userid=" + userid + ", passwd=" + passwd + ", username=" + username + ", con=" + con
				+ ", gender=" + gender + ", intro=" + intro + ", indate=" + indate + "]";
	}
	
	
	
}
