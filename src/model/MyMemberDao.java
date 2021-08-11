package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

public class MyMemberDao {

	// Fields
	private String driver = "oracle.jdbc.OracleDriver";
	private String url    = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	private String dbuid  = "test";
	private String dbpwd  = "1234";
	
	private Connection         conn;
	private PreparedStatement  pstmt;
	private ResultSet          rs;
	
	// Constructor
	public  MyMemberDao() {
		open();
	}

	private void open() {
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, dbuid, dbpwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	} 
	
	public void close() {
		try {
			if( !conn.isClosed() ) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//---------------------------------------------
	// 회원 추가
	public int insertMember(MyMemberVo vo) {
		int aftcnt = 0;
		
		try {
			String sql  = "INSERT INTO MYMEMBER ";
			sql        += " ( USERID,  PASSWD,  USERNAME, CON, GENDER, INTRO )";
			sql        += "  VALUES ( ?,?,?,?,?,? )";
			pstmt       = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getUserid() );
			pstmt.setString(2, vo.getPasswd() );
			pstmt.setString(3, vo.getUsername() );
			pstmt.setString(4, vo.getCon() );
			pstmt.setString(5, vo.getGender() );
			pstmt.setString(6, vo.getIntro() );
			
			aftcnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println(e.getErrorCode() );
			if(e.getErrorCode() == 1) {
				String message = "존재하는 id 입니다\n다시 입력하세요";
				System.out.println( message );
				JOptionPane.showMessageDialog(null, 
						message, "아이디중복",
					    JOptionPane.OK_OPTION);
			}
		} finally {
			try {
				if( !pstmt.isClosed() ) pstmt.close();
			} catch (SQLException e) {
			}
		}
		return aftcnt;
	}

	// 개인조회
	// 아이디로 회원조회
	public MyMemberVo getMember(String uid) {
		MyMemberVo vo  = null;
		try {
			String  sql  = "SELECT  USERID, PASSWD, USERNAME, CON, GENDER, INTRO, INDATE";
			sql +=	" FROM   MYMEMBER"; 
			sql += 	" WHERE  USERID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uid);
			
			rs = pstmt.executeQuery();
			if( rs.next() ) {
				String  userid    = rs.getString( "userid" );
				String  passwd    = rs.getString( "passwd" );
				String  username  = rs.getString("username");
				String  con       = rs.getString("con");
				String  gender    = rs.getString("gender");
				String  intro     = rs.getString("intro");
				String  indate    = rs.getString("indate");
			
				vo = new MyMemberVo(userid, passwd, username, con, gender, intro, indate) ;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( !rs.isClosed() )    rs.close();
				if( !pstmt.isClosed() ) pstmt.close();
			} catch (SQLException e) {
			}
		}
		return  vo;
	}

	// 목록조회
	public Vector getMemberList() {
		Vector  v = new Vector(); 		
		//  Vector<MyMemberVo> v  =new Vector<>(); 이 원칙이나
		//  JTable 에는 Generic(Vector<MyMemberVo>) 이 안들어 간다 
		
		try {
			String  sql =  "SELECT  USERID, USERNAME, CON, GENDER, INDATE ";
			sql        +=  " FROM   MYMEMBER ";			
			pstmt       =  conn.prepareStatement(sql);
			
			rs         =   pstmt.executeQuery();
			while(rs.next()) {				
				Vector member = new Vector();
				member.add( rs.getString("USERID") );
				member.add( rs.getString("USERNAME") );
				member.add( rs.getString("CON") );
				member.add( rs.getString("GENDER") );
				member.add( rs.getString("INDATE") );
		        v.add( member );
		//  JTable 안에 들어가지 않음 
		//		MyMemberVo  vo = new MyMemberVo(userid, username, con, gender, indate);  // 사용불가
		//		v.add( vo );
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			try {
				if( !rs.isClosed() )    rs.close();
				if( !pstmt.isClosed() ) pstmt.close();
			} catch (SQLException e) {
			}
		}
		
		return  v;
	}

	// 삭제
	public int deleteMember(MyMemberVo vo) {
		int aftcnt  = 0;
		try {
			String sql = "DELETE FROM MYMEMBER WHERE USERID=? AND PASSWD=?";
			pstmt      =  conn.prepareStatement(sql);
			pstmt.setString(1, vo.getUserid() );
			pstmt.setString(2, vo.getPasswd() );
			
			aftcnt  = pstmt.executeUpdate();
						
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( !pstmt.isClosed() ) pstmt.close();
			} catch (SQLException e) {
			}
		}
		
		return aftcnt;
	}

	// 수정
	public int updateMember(MyMemberVo vo) {
		int aftcnt = 0;
		try {
			String sql  = "UPDATE MYMEMBER SET ";
			sql        += " USERNAME = ?,"; 
			sql        += " CON = ?,"; 
			sql        += " GENDER = ?,"; 
			sql        += " INTRO = ?"; 
			sql        += " WHERE USERID = ? "; 
			sql        += " AND   PASSWD = ? "; 
			pstmt       =  conn.prepareStatement(sql);
			pstmt.setString(1, vo.getUsername());
			pstmt.setString(2, vo.getCon());
			pstmt.setString(3, vo.getGender());
			pstmt.setString(4, vo.getIntro());
			pstmt.setString(5, vo.getUserid());
			pstmt.setString(6, vo.getPasswd());
			
			aftcnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if( !pstmt.isClosed() ) pstmt.close();
			} catch (SQLException e) {
			}
		}
		return aftcnt;
	}
	

	
}








