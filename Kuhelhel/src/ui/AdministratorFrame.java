package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AdministratorFrame extends JFrame implements ActionListener {

	protected Vector< common.MyPair >	selectedList;
	
	protected AdminMenu		menu;
	
	protected AdminSearch	pnTop;
	protected AdminOrder	pnMiddle2;
	protected AdminDiscount	pnMiddle3;
	protected AdminPay		pnMiddle4;
	protected AdminUpdated	pnBottom;
	
	public static final int WIDTH	= 800;
	public static final int HEIGHT	= 750;
	
	public AdministratorFrame() {
		initialize();
		configure();
		this.setVisible( true );
	}
	
	private void configure() {
		JPanel pnMiddle = new JPanel( new GridLayout( 1, 0, 8, 0 ) );
		
		selectedList = new Vector<>();
		
		menu		= new AdminMenu();
		pnTop		= new AdminSearch();
		pnMiddle4	= new AdminPay( pnTop, this );
		pnMiddle3	= new AdminDiscount( pnMiddle4 );
		pnMiddle2	= new AdminOrder( pnMiddle4, this );
		pnBottom	= new AdminUpdated();
		
		pnMiddle.add( pnMiddle2);
		pnMiddle.add( pnMiddle3 );
		pnMiddle.add( pnMiddle4 );
				
		this.add( pnTop );
		this.add( pnMiddle );
		this.add( pnBottom );
		
		pnMiddle.setBackground( Color.WHITE );
		this.setBackground( Color.WHITE );
		
		menu.itemPasswd.addActionListener( this );
		menu.itemMember.addActionListener( this );
		menu.itemReMember.addActionListener( this );
		menu.itemPay.addActionListener( this );
		menu.itemExit.addActionListener( this );
		
		this.setJMenuBar( menu );
	}
	
	private void initialize() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension sc = toolkit.getScreenSize();

		this.setTitle( "멀티미디어 관리 프로그램 - 관리자 페이지" );
		this.setSize( WIDTH, HEIGHT );
		this.setLocation( sc.width / 3, sc.height / 5 );
		this.setResizable( false );
		this.setLayout( new GridLayout( 0, 1) );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		// TODO Auto-generated method stub
		System.err.println( "Throw MenuBar Event" );
		JMenuItem tmp = ( JMenuItem ) e.getSource();

		if ( tmp.equals( menu.itemPasswd ) ) {
			new AdminPasswd();
		}
		else if ( tmp.equals( menu.itemMember ) ) {
			new AdminMemberJoin();
		}
		else if ( tmp.equals( menu.itemReMember ) ) {
//			new AdminMemberRenew();
		}
		else if ( tmp.equals( menu.itemPay ) ) {
			JOptionPane.showMessageDialog( null, "결제정보 동작", "확인", JOptionPane.INFORMATION_MESSAGE );
		}
		else if ( tmp.equals( menu.itemExit ) ) {
			this.dispose();
			new Authority();
		}
	}
	
}
