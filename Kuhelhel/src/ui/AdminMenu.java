package ui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class AdminMenu extends JMenuBar {

	public	JMenu		tbConf;			// Main Tab
	public	JMenuItem	itemPasswd,		// change passwd
						itemMember,		// join member
						itemReMember,	// refactor member
						itemPay,		// pay window
						itemExit;		// previous window

	public AdminMenu() {
		initialize();
	}

	private void initialize() {
		tbConf = new JMenu( "Configure" );

		itemPasswd		= new JMenuItem( "비밀번호 변경" );
		itemMember		= new JMenuItem( "새 맴버 등록" );
		itemReMember	= new JMenuItem( "기존 맴버 수정" );
		itemPay			= new JMenuItem( "결제 정보" );
		itemExit		= new JMenuItem( "로그아웃" );

		tbConf.add( itemPasswd );
		tbConf.addSeparator();
		tbConf.add( itemMember );
		tbConf.add( itemReMember );
		tbConf.addSeparator();
		tbConf.add( itemPay );
		tbConf.addSeparator();
		tbConf.add( itemExit );

		this.add( tbConf );
		
		itemReMember.setEnabled( false );
	}

}
