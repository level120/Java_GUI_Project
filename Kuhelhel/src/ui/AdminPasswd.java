package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import db.DBConnector;

public class AdminPasswd extends JDialog implements ActionListener {

	private final int		WIDTH	= 480;
	private final int		HEIGHT	= 300;
	
	private DBConnector		db;
	
	private JPasswordField	oldPw;
	private JPasswordField	newPw;
	private JPasswordField	checkPw;
	private JButton			btnOK;
	private JButton			btnPre;

	public AdminPasswd() {
		initialize();
		logic();
		this.setVisible( true );
	}

	private void initialize() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension sc = toolkit.getScreenSize();

		this.setTitle( "�α��� ��й�ȣ ����" );
		this.setSize( WIDTH, HEIGHT );
		this.setLocation( sc.width / 4, sc.height / 4 );
		this.setResizable( false );
		this.setModal( true );
		this.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
	}

	private void logic() {
		JPanel p = new JPanel( null );
		JPanel pn = new JPanel( null );
		p.setLayout( null );
		pn.setBorder( BorderFactory.createTitledBorder( "��й�ȣ ����" ) );
		
//		openImage( "test2.png" );

		Font font = new Font( "Bold", Font.BOLD, 12 );
		
		JLabel lbOld	= new JLabel( "���� ��й�ȣ" );
		JLabel lbNew	= new JLabel( "�� ��й�ȣ" );
		JLabel lbCheck	= new JLabel( "�� ��й�ȣ Ȯ��" );
		
		lbOld.setFont( font );
		lbNew.setFont( font );
		lbCheck.setFont( font );
		
		db		= new DBConnector();
		
		oldPw	= new JPasswordField( 15 );
		newPw	= new JPasswordField( 15 );
		checkPw	= new JPasswordField( 15 );
		btnOK	= new JButton( "�Ϸ�" );
		btnPre	= new JButton( "���" );
		
		btnOK.addActionListener( this );
		btnPre.addActionListener( this );
		
		pn.add( lbOld );
		pn.add( lbNew );
		pn.add( lbCheck );
		pn.add( oldPw );
		pn.add( newPw );
		pn.add( checkPw );
		pn.add( btnOK );
		pn.add( btnPre );
		
		lbOld.setBounds( 55, 30, 100, 25 );
		oldPw.setBounds( 180, 30, 150, 25 );
		
		lbNew.setBounds( 55, 65, 100, 25);
		newPw.setBounds( 180, 65, 150, 25 );
		
		lbCheck.setBounds( 55, 100, 100, 25 );
		checkPw.setBounds( 180, 100, 150, 25);
		
		btnPre.setBounds( 130, 160, 70, 25 );
		btnOK.setBounds( 210, 160, 70, 25 );
		
		pn.setBackground( Color.WHITE );
		p.setBackground( Color.WHITE );
		p.add( pn );
		this.add( p );
		pn.setBounds( 25, 25, WIDTH - 50, HEIGHT - 70 );
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		// TODO Auto-generated method stub
		JButton tmp = ( JButton ) e.getSource();
		if ( tmp.equals( btnOK ) ) {
			Boolean	flag	= true;
			String	old		= oldPw.getText();
			String	newP	= newPw.getText();
			String	check	= checkPw.getText();

			if ( newP.equals( check ) && !newP.isEmpty() && !check.isEmpty() ) {
				String sql		= "SELECT PW FROM ENTER";
				db.query( sql );
				
				if ( db.queryCheck() ) {
					String oldPwCheck	= db.getQuery( "PW" );
					System.err.println( oldPwCheck + "\n" + old );

					if ( oldPwCheck.equals( old ) ) {
						flag = true;
						
						sql		= "UPDATE ENTER SET PW='" + check + "'";
						System.err.println( sql );
						db.query2( sql );
						
						JOptionPane.showMessageDialog( null, "����Ϸ�!", "�Ϸ�", JOptionPane.INFORMATION_MESSAGE );
						this.db.close();
						this.dispose();
					}

				}
			}

			if ( flag ) {
				JOptionPane.showMessageDialog( null, "��й�ȣ�� �ٽ� Ȯ���ϼ���.", "����", JOptionPane.WARNING_MESSAGE );
			}
		}
		else {
			this.db.close();
			this.dispose();
		}
	}

}
