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

		itemPasswd		= new JMenuItem( "��й�ȣ ����" );
		itemMember		= new JMenuItem( "�� �ɹ� ���" );
		itemReMember	= new JMenuItem( "���� �ɹ� ����" );
		itemPay			= new JMenuItem( "���� ����" );
		itemExit		= new JMenuItem( "�α׾ƿ�" );

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
