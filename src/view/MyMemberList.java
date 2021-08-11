
package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



import model.MyMemberDao;

public class MyMemberList extends JFrame
      implements  ActionListener, MouseListener {
	// Vector 는 ArrayList(실행할때 크기가 변하는 배열 ) 의 thread safe 버전
	Vector              v;     //  data list     - getMemberList()   
	Vector              cols;  //  목록의 제목들  
	
	// Fields
	DefaultTableModel   dtm;      // dtm -> JTable : grid  
	JTable              jTable;   // 보여줄 데이터가진 grid
	JScrollPane         pane;
	JPanel              topPane;
	JButton             btnInsert, btnRefresh; 
		
	MyMemberProc memProc = null;  // Memberㅖroc 를 조작하기위한  

	
	// 생성자
	public  MyMemberList() {
		super("회원관리 프로그램 v1.0");
		
		initComponent();
		
		this.setLocation(350, 250);
		this.setSize(600, 500);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}
	
	// 화면 배치
	private void initComponent() {
		
		// dtm      =   initTable();     // Jtable 에 보여줄 데이터 준비
		// jTable   =  new JTable( dtm  );
		
		jTable   =   new  JTable(  );
		jTable.setModel( 
			new DefaultTableModel( getDataList(), getColumn() ) {
				public boolean isCellEditable(int row, int column) {
					return false;
				} // 모든 줄이 편집불가능으로 처리됨
		    }
		);
		pane     =   new  JScrollPane( jTable );
		getContentPane().add( pane ); // this.add( pane );
		
		// 상단기능 준비
		topPane     =   new JPanel();
		btnInsert   =   new JButton("회원가입");
		topPane.add( btnInsert );
		
		btnRefresh  =   new JButton("새로고침");
		topPane.add( btnRefresh );
		
		
		
		getContentPane().add(  topPane,  BorderLayout.NORTH );
		
		//------------------------------------------
		// 버튼 기능연결 - 이벤트 연결
		this.btnInsert.addActionListener(this);
		this.btnRefresh.addActionListener(this);
		
		
		// jTable 안의 리스트를 클릭하면 - 마우스 이벤트가 발생하면
		this.jTable.addMouseListener( this );
		
		
	}


	// DefaultTableModel 설정 ( cols, v )
	private DefaultTableModel initTable() {
		// 제목줄설정
		cols    =   getColumn();
		// 데이터 처리;
		v       =   getDataList();
		
		DefaultTableModel  dtm = new DefaultTableModel(v, cols);
		return     dtm;
	}

	private Vector getColumn() {
		Vector   cols  =  new Vector();
		
		cols.add("아이디");
		//	titles.add("암호");
		cols.add("이름");
		cols.add("국가");
		cols.add("성별");
		//	titles.add("자기소개");
		cols.add("가입일");
		
		return   cols;
	}

	// 전체자료목록 조회
	private Vector getDataList() {
		MyMemberDao  mDao = new MyMemberDao();
		Vector       v    = mDao.getMemberList();
	
		return       v;
	}


	//--------------------------------------
	// Mouse 관련 이벤트
	// jTable 안의 row 를 클릭했을때
	@Override
	public void mouseClicked(MouseEvent e) {
		//System.out.println( e );
		int     row   = jTable.getSelectedRow();    // 클릭한 줄번호 ( 제목줄 다음부터 0~)
		int     col   = jTable.getSelectedColumn(); //클릭한 칸번호 ( 0~ )
		String  uid   = (String) jTable.getValueAt(row, 0);  // 클릭한 줄의 userid를 get
		String  name  = (String) jTable.getValueAt(row, 1);  // 클릭한 줄의 name를 get
		System.out.println( uid );
		if( memProc != null  )   // 열려있는 memProc 가 있다먄
			memProc.dispose();
		memProc   =  new MyMemberProc(  uid, this ); 
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	//--------------------------------
	//  버튼 클릭 이벤트
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand() + " 버튼 클릭");
		String  btnText = e.getActionCommand();
		switch( btnText  ) {
		case "회원가입": 
			//System.out.println( memProc );
			if( memProc != null  )
				memProc.dispose();  // memProc를 메모리에서 제거하라, 창닫기
			memProc = new MyMemberProc( this  );  
			   // this : MyMemberList 
			   // MymemebrProc 에서 MyMemberList 의 버튼을 조작할 수 있도록 정보전달
			break;
		case "새로고침": 
			jTableRefresh();
			break;
		
			
		}
		
	}

	// data 새로 고침
	public void jTableRefresh() {
		// JTable 을 새로 조회
		DefaultTableModel dtm = initTable();
		jTable.setModel( dtm );
		jTable.repaint();		
	}

	
	
	//-----------------------------------------
	// main 함수
	public static void main(String [] args) {
		new MyMemberList();
	}
		

}














