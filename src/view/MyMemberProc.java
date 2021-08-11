package view;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.MyMemberDao;
import model.MyMemberVo;
import java.awt.Color;

public class MyMemberProc extends JFrame 
       implements ActionListener {

	// Component 부품들
	JPanel             p;
	JTextField         txtId, txtName, txtIndate;
	JPasswordField     txtPwd;
	JComboBox          cbCon;
	JRadioButton       rbMan, rbWoman;
	JTextArea          taIntro;
	JScrollPane        pane;
	JButton            btnInsert, btnDelete, btnUpdate, btnReset;
	
	ButtonGroup   group1;
	
	MyMemberList myMemberList;
	
	// Layout
	GridBagLayout       gb;
	GridBagConstraints  gbc;
	private JButton btnNewButton;
	
	// 생성자
	public  MyMemberProc() {
		this.setTitle("회원정보상세보기");
		initComponent();		
	}
	
	// MyMemberList 에서 회원가입을 클릭햇을때 - 회원가입
	public MyMemberProc( MyMemberList myMemberList ) {
		// myMemberList : 넘어온 목록화면(MyMemberList class)
		this(); // 기본생성자 호출
		
		this.myMemberList = myMemberList;
		
		btnEditOnOff( true );
	}

	private void btnEditOnOff(boolean sw) {
		this.btnInsert.setEnabled( sw );
		this.btnDelete.setEnabled( !sw );
		this.btnUpdate.setEnabled( !sw );
		this.btnNewButton.setVisible( !sw );
	}

	// MyMemberList 에서 Jtable 목록을  클릭햇을때 - 회웑정보 수정,삭제
	public MyMemberProc(String uid, MyMemberList myMemberList) {
		this();
		
		txtId.setText(  uid );
		
		viewData( uid );  // 넘어온 uid로 data 를 조회하여 화면에 출력
		
		this.myMemberList = myMemberList;
		
		btnEditOnOff( false );
	
		
	}

	// 넘어온 uid로 data 를 조회하여 화면에 출력 
	private void viewData(String uid) {
		MyMemberDao   mDao  = new MyMemberDao();
		MyMemberVo    vo    = mDao.getMember(uid);
		setViewData( vo );
		
	}

	// 화면에 부품을 배치
	private void initComponent() {
		
		//  레이아웃 설정
		gb            =  new GridBagLayout();
		getContentPane().setLayout(gb);
		
		gbc           =  new GridBagConstraints();
		gbc.fill      =  GridBagConstraints.BOTH;
		gbc.weightx   =  1.0;
		gbc.weighty   =  1.0;
		
		// 아이디
		JLabel    lblId  =  new JLabel("아이디");
		txtId            =  new JTextField(20);
		gbAdd ( lblId, 0, 0, 1, 1  );
		gbAdd ( txtId, 1, 0, 3, 1  );
		
		// 암호	
		JLabel    lblPwd = new JLabel("암호");
        txtPwd           = new JPasswordField(20);
        gbAdd ( lblPwd, 0, 1, 1, 1 );
        gbAdd ( txtPwd, 1, 1, 3, 1 );
        
		// 이름		
        JLabel    lblName = new JLabel("이름");
        txtName           = new JTextField(20);
        gbAdd ( lblName, 0, 2, 1, 1 );
        gbAdd ( txtName, 1, 2, 3, 1 );
		
        // 국가 
        JLabel    lblJob = new JLabel("국가");
        String [] arrJob = {"ko", "us", "ua", "fr", "jp", "ch"};
        cbCon            = new JComboBox( arrJob );
        gbAdd ( lblJob, 0, 3, 1, 1 );
        gbAdd ( cbCon,  1, 3, 3, 1 );
        
		// 성별
        JLabel    lblGender = new JLabel("성별");
        
        rbMan               = new JRadioButton("남자", false); 
        rbWoman             = new JRadioButton("여자", false);
        
        // RadioButton 그룹지정 : 2개중 하나만 선택
        group1 = new ButtonGroup();
        group1.add(rbMan);
        group1.add(rbWoman);
        
        // 패녈준비
        JPanel     pGender  = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pGender.add( rbMan );
        pGender.add( rbWoman );
        
        gbAdd ( lblGender, 0, 4, 1, 1 );
        gbAdd ( pGender,   1, 4, 3, 1 );
        
		// 자기소개
        JLabel    lblIntro  = new JLabel("자기소개");
        taIntro             = new JTextArea(5, 20);   // 5 rows, 20 cols  
        pane                = new JScrollPane(taIntro);
        
        gbAdd ( lblIntro, 0, 9, 1, 1 );
        gbAdd ( pane,  1, 9, 3, 1 );
        
		// 가입일
        JLabel    lblIndate = new JLabel("가입일");
        txtIndate           = new JTextField(20);
        
        Date   today  = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd E"); 
        String now = sdf.format( today );
        txtIndate.setText(now);
        txtIndate.setEditable(false);
        
        gbAdd ( lblIndate, 0, 10, 1, 1 );
        gbAdd ( txtIndate, 1, 10, 3, 1 );

        // 버튼들
        JPanel  pButton = new JPanel();
        btnInsert       = new JButton("추가");      
        btnDelete       = new JButton("삭제");      
        btnUpdate       = new JButton("수정");      
        btnReset        = new JButton("취소");      
        btnReset.setForeground(Color.RED);
        
        pButton.add( btnInsert ); 
        pButton.add( btnDelete ); 
        pButton.add( btnUpdate ); 
        pButton.add( btnReset  ); 
        
        // ----------------------------------------
        // 기능 연결 : 버튼 이벤트 연결
        // 추가버튼클릭시 수행할 코딩연결
        // this 지정 : ActionListener 를 implements 한 객체
        // 모든 클릭코딩은 actionPerformed(ActionEvent e) 에 작성한다
        this.btnInsert.addActionListener( this );
        this.btnDelete.addActionListener( this );
        this.btnUpdate.addActionListener( this );
        this.btnReset.addActionListener( this );
      
        // txtId 에 key 이벤트 연결
        this.txtId.addKeyListener( new KeyListener() {
						
			// keypress : 키를 눌렀다가 땔때
			// Enter Key 가 눌러지면 조회한다
			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();  // enter : 10
				//System.out.println(code); 
				if(code == 10
				    &&  !txtId.getText().trim().equals("") ) {  // 조회
					MyMemberDao mDao  = new MyMemberDao();
					String  uid = txtId.getText().trim();							
					MyMemberVo vo = mDao.getMember( uid );
					if ( vo != null ) {
						System.out.println( vo );
						setViewData( vo );  // vo ->  textfield 에 출력
					}
					mDao.close();		
				}
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
                
        //-----------------------------------------
        
        gbAdd ( pButton, 0, 11, 4, 1 );
        
        btnNewButton = new JButton("\uC870\uD68C");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		System.out.println("조회를 클릭하였습니다");
        	}
        });
        pButton.add(btnNewButton);
		
		// 화면 띄우기
		this.setLocation(1000, 200);
		this.setSize(350, 500);
		this.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );
		this.setVisible(true);
	}

	// frame jtextfield <- vo
	protected void setViewData(MyMemberVo vo) {
		this.txtId.setText( vo.getUserid() );
		this.txtPwd.setText( vo.getPasswd() );
		this.txtName.setText( vo.getUsername() );
		this.cbCon.setSelectedItem( vo.getCon() );
		
		if(vo.getGender().equals("남") ) this.rbMan.setSelected(true);
		if(vo.getGender().equals("여") ) this.rbWoman.setSelected(true);
		
		this.taIntro.setText( vo.getIntro() );
		this.txtIndate.setText( vo.getIndate() );
		
	}

	private void gbAdd(JComponent c, int x, int y, int w, int h) {
		gbc.gridx         = x;
		gbc.gridy         = y;
		gbc.gridwidth     = w;
		gbc.gridheight    = h;
		gb.setConstraints(c, gbc);
		gbc.insets = new Insets(2, 2, 2, 2);
		getContentPane().add(c, gbc);
	}

	// 이벤트 처리
	// 기능부여
	@Override
	public void actionPerformed(ActionEvent e) {
		//System.out.println( e.getActionCommand() + "버튼 클릭");
		switch( e.getActionCommand() ) {
		case "추가":
			insertMember();  // 회원추가
			resetMember();   // 화면  clear
			
			myMemberList.jTableRefresh(); // 새로고침을 클릭했을때 수행하는 함수
			   // myMemberList form 에 있는 jTableRefresh() 함수를 호출  
			
			this.dispose();    // 창닫기
			break;
		case "수정":
			updateMember();
			break;
		case "삭제":
			deleteMember();
			break;
		case "취소":
			resetMember();
			break;
			
		}
		
	}
	
	//------------------------------------------------------
	//추가버튼 클릭
	private void insertMember() {

		MyMemberDao  mDao = new  MyMemberDao();
		MyMemberVo   vo   = getViewData();
		mDao.insertMember( vo );	
				
		mDao.close();
		
	}

	// 화먄에 있는 부품(컴포넌트들의 입략값들을 vo 에 저장
	private MyMemberVo getViewData() {
		String  userid   = this.txtId.getText();				;
		String  passwd   = new String(this.txtPwd.getPassword());
		String  username = this.txtName.getText();
		String  con      = (String) this.cbCon.getSelectedItem();
		
		String  gender   = "null"; // "남", "여", null
		if(this.rbMan.isSelected())    gender = "남";
		if(this.rbWoman.isSelected())  gender = "여";
				
        String  intro    = this.taIntro.getText(); 
        String  indate   = this.txtIndate.getText();
		MyMemberVo vo = new MyMemberVo(
				userid, passwd, username, con, gender, intro, indate); 
		return  vo;
	}

	// 수정버튼 클릭
	private void updateMember() {
		
		MyMemberDao  mDao = new MyMemberDao();
		MyMemberVo   vo   = getViewData();
		String       uid  = vo.getUserid();
		String       pwd  = vo.getPasswd();
		if(  uid.equals("") || pwd.equals("") ) {
			String  msg = "아이디와 암호를 입력하세요";
			JOptionPane.showMessageDialog(this, msg);
			return;
		}
		
		String msg = "";
		int aftcnt  = mDao.updateMember( vo );
		if (aftcnt == 0) {
			msg = "수정되지 않았습니다";
		} else {
			msg = "수정되었습니다";
		}
		JOptionPane.showMessageDialog(this, msg);
		this.dispose();
		
	}

	// 삭제 버톤 클릭
    private void deleteMember() {
		
    	MyMemberDao   mDao = new MyMemberDao();
    	MyMemberVo    vo   = getViewData();      // 삭제할 자료를 화면에서 가져온다(아이디,암호)
		String        uid  = vo.getUserid();
		String        pwd  = vo.getPasswd();
		if(  uid.equals("") || pwd.equals("")  ) {
			String msg  = "아이디와 암호를 입력하세요";
			JOptionPane.showMessageDialog(this, msg);
			return;
		}
//		if(  !pwd.equals( passord )  ) {
//			String msg = "비밀번호가 틀립니다";
//			JOptionPane.showMessageDialog(this, msg);
//			return;
//		}
		
		
		int x = JOptionPane.showConfirmDialog(this,  "정말 삭제하시겠습니까?",
				"삭제", JOptionPane.YES_NO_OPTION);
		if(x == JOptionPane.NO_OPTION)
			return;
		
		int aftcnt  = mDao.deleteMember( vo );
		if( aftcnt != 0 ) {
			myMemberList.jTableRefresh(); // 목록새로고침
			String  message = "삭제 되었습니다";
			JOptionPane.showMessageDialog(this, message);
			this.dispose();
		} else {
			String  message = "삭제 되지 않았습니다";
			JOptionPane.showMessageDialog(this, message);
		}
			
		
	}

	// 취소 버튼 클릭
	private void resetMember() {
		this.txtId.setText("");
		this.txtPwd.setText("");
		this.txtName.setText("");
		this.cbCon.setSelectedIndex(0);
		this.group1.clearSelection();
		//this.rbMan.setSelected(false);
		//this.rbWoman.setSelected(false);
		this.taIntro.setText("");
		this.txtIndate.setText("");
		this.txtId.grabFocus();
		
	}

	// test 용 main 추가
	//public static void main(String[] args) {
	//	new MyMemberProc();
	//}
}

















