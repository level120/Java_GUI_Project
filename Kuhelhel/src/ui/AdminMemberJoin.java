package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import db.DBConnector;

public class AdminMemberJoin extends JDialog {

	private final	int			WIDTH	= 600;
	private final	int			HEIGHT	= 500;
	private 		int			point	= 0;
	
	private JTextField			tfName,
								tfAddress,
								tfMail, tfMail2,
								tfTel, tfTel2;
	private JRadioButton		rbtnMan, rbtnWoman;
	private JComboBox<String>	tel1,
								mail1;
	private JButton				btnOk,
								btnPre,
								btnCheck;
	private	String				mailFlag	= "";

	public AdminMemberJoin() {
		initialize();
		declaer();
		this.setVisible( true );
	}

	private void initialize() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension sc = toolkit.getScreenSize();

		this.setTitle( "�ɹ��� ���" );
		this.setSize( WIDTH, HEIGHT );
		this.setLocation( sc.width / 4, sc.height / 4 );
		this.setResizable( false );
		this.setModal( true );
		this.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
	}

	private void declaer() {
		String[] callList	= { "����", "010", "011", "016", "017", "070" };
		String[] mailList	= { "�����Է�", "���̹�", "����", "����", "����Ʈ", "����" };

		ButtonGroup gp	= new ButtonGroup();
		JPanel	p		= new JPanel( null );
		JPanel 	pn		= new JPanel( null );

		Font	font	= new Font( "Bold", Font.BOLD, 12 );

		JLabel	name	= new JLabel( "�̸�" );
		JLabel	tel		= new JLabel( "����ó" );
		JLabel	address	= new JLabel( "�ּ�" );
		JLabel	gender	= new JLabel( "����" );
		JLabel	mail	= new JLabel( "�̸���" );
		JLabel	temp	= new JLabel( "-" );
		JLabel	at		= new JLabel( "@" );

		btnOk			= new JButton( "���" );
		btnPre			= new JButton( "���" );
		btnCheck		= new JButton( "�̸��� Ȯ��" );

		tfName			= new JTextField( 10 );
		tfAddress		= new JTextField( 50 );
		tfMail			= new JTextField( 30 );
		tfTel			= new JTextField( 4 );
		tfTel2			= new JTextField( 4 );
		tfMail2			= new JTextField( 30 );

		rbtnMan			= new JRadioButton( "����" );
		rbtnWoman		= new JRadioButton( "����" );

		tel1			= new JComboBox<>( callList );
		mail1			= new JComboBox<>( mailList );

		rbtnMan.setSelected( true );
		
		gp.add( rbtnMan );
		gp.add( rbtnWoman );

		pn.add( name );
		pn.add( tel );
		pn.add( address );
		pn.add( gender );
		pn.add( mail );
		pn.add( temp );
		pn.add( tfName );
		pn.add( tfAddress);
		pn.add( tfTel );
		pn.add( tfMail );
		pn.add( tfMail2 );
		pn.add( tel1 );
		pn.add( mail1 );
		pn.add( tfTel2 );
		pn.add( rbtnMan );
		pn.add( rbtnWoman );
		pn.add( at );
		pn.add( btnOk );
		pn.add( btnPre );
		pn.add( btnCheck );

		name.setFont( font );
		tel.setFont( font );
		address.setFont( font );
		gender.setFont( font );
		mail.setFont( font );
		temp.setFont( font );

		p.setBackground( Color.WHITE );
		pn.setBackground( Color.WHITE );
		rbtnMan.setBackground( Color.WHITE );
		rbtnWoman.setBackground( Color.WHITE );

		pn.setBorder( BorderFactory.createTitledBorder( "�� �ɹ����" ) );

		name.setBounds( 25, 25, 100, 25 );
		tfName.setBounds( 145, 25, 100, 25 );
		tel.setBounds( 25, 60, 100, 25 );
		tel1.setBounds( 145, 60, 60, 25 );
		tfTel.setBounds( 210, 60, 70, 25 );
		temp.setBounds( 283, 60, 30, 25 );
		tfTel2.setBounds( 290, 60, 70, 25 );
		address.setBounds( 25, 95, 100, 25 );
		tfAddress.setBounds( 145, 95, 215, 25 );
		gender.setBounds( 25, 130, 100, 25);
		rbtnMan.setBounds( 145, 130, 100, 25 );
		rbtnWoman.setBounds( 255, 130, 100, 25 );
		mail.setBounds( 25, 165, 100, 25 );
		tfMail.setBounds( 145, 165, 100, 25);
		at.setBounds( 255, 165, 20, 25);
		tfMail2.setBounds( 275, 165, 100, 25);
		mail1.setBounds( 380, 165, 100, 25);

		btnOk.setBounds( 240, 240, 90, 25 );
		btnPre.setBounds( 130, 240, 90, 25 );
		btnCheck.setBounds( 350, 240, 130, 25 );

		btnOk.addActionListener( new BtnAction() );
		btnPre.addActionListener( new BtnAction() );
		btnCheck.addActionListener( new BtnCheck() );
		mail1.addActionListener( new ComboAction() );

		pn.setBounds( 25, 25, WIDTH - 50, HEIGHT - 100 );

		p.add( pn );
		this.add( p );
	}
	
	private int getSex() {
		if ( rbtnMan.isSelected() )
			return 0;
		else
			return 1;
	}
	
	class BtnCheck implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if ( tfMail.getText().matches( "\\w*" ) && tfMail2.getText().matches( "\\w*\\.\\w*" ) ) {
				DBConnector db	= new DBConnector();
				String sMail	= tfMail.getText() + "@" + tfMail2.getText();
				String sql		= "SELECT MAIL FROM CUSTOMER WHERE MAIL = '" + sMail + "'";

				System.err.println( sql );

				db.query( sql );

				if ( db.queryCheck() ) {
					JOptionPane.showMessageDialog( null, "�̸����� �̹� �����մϴ�.", "����", JOptionPane.ERROR_MESSAGE );
				}
				else {
					JOptionPane.showMessageDialog( null, "��� ������ �̸��� �ּ��Դϴ�.", "Ȯ��", JOptionPane.INFORMATION_MESSAGE );
					mailFlag = sMail;
				}
				
				db.close();
			}
			else {
				JOptionPane.showMessageDialog( null, "�ùٸ� �̸��� ������ �ƴմϴ�.", "����", JOptionPane.ERROR_MESSAGE );
			}
		}
		
	}

	class BtnAction implements ActionListener {

		@Override
		public void actionPerformed( ActionEvent e ) {
			// TODO Auto-generated method stub
			DBConnector db	= new DBConnector();
			String sMail	= tfMail.getText() + "@" + tfMail2.getText();
			JButton tmp		= ( JButton ) e.getSource();

			if ( btnOk.equals( tmp ) ) {
				if ( tfName.getText().isEmpty() ) {
					JOptionPane.showMessageDialog( null, "�̸��� �Է��Ͻʽÿ�.", "����", JOptionPane.ERROR_MESSAGE );
				}
				else if ( tel1.getSelectedIndex() != 0 ) {
					if ( tfTel.getText().isEmpty() || tfTel2.getText().isEmpty() || tel1.getSelectedIndex() == 0
							|| !tfTel.getText().matches( "\\d*" ) || !tfTel2.getText().matches( "\\d*" ) ) {
						JOptionPane.showMessageDialog( null, "��ȭ��ȣ�� �ùٸ��� �ʽ��ϴ�.", "����", JOptionPane.ERROR_MESSAGE );
					}
				}
				else if ( tfAddress.getText().isEmpty() ) {
					JOptionPane.showMessageDialog( null, "�ּҸ� �Է��Ͻʽÿ�.", "����", JOptionPane.ERROR_MESSAGE );
				}
				else if ( tfMail.getText().isEmpty() || tfMail2.getText().isEmpty()
						|| !tfMail2.getText().matches( "\\w*\\.\\w*" ) ) {
					JOptionPane.showMessageDialog( null, "�̸����� �ùٸ��� �ʽ��ϴ�.", "����", JOptionPane.ERROR_MESSAGE );
				}
				else if ( !mailFlag.equals( sMail ) ) {
					JOptionPane.showMessageDialog( null, "�̸��� �ߺ�Ȯ���� �ʿ��մϴ�.", "����", JOptionPane.ERROR_MESSAGE );
					System.err.println( "mailFlag : \t" + mailFlag + "\nsMail : \t\t" + sMail );
				}
				else {
					String sql = "INSERT INTO CUSTOMER VALUES('" +	tfName.getText() + "', '" +
																	tel1.getSelectedItem() + "-" + tfTel.getText() + "-" + tfTel2.getText() + "', '" +
																	tfAddress.getText() + "', " +
																	getSex() + ", '" +
																	tfMail.getText() + "@" + tfMail2.getText() + "', " +
																	point + ")";
					System.err.println( sql );
					db.query2( sql );
					
					JOptionPane.showMessageDialog( null, "���������� ��ϵǾ����ϴ�.", "�Ϸ�", JOptionPane.INFORMATION_MESSAGE );
					
					db.close();
					dispose();
				}
			}
			else {
				db.close();
				dispose();
			}
		}

	}

	class ComboAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JComboBox<String> tmp = ( JComboBox<String> ) e.getSource();
			String select = ( String ) tmp.getSelectedItem();
			if ( select.equals( "�����Է�" ) ) {
				tfMail2.setEditable( true );
				tfMail2.setText( "" );
			}
			else if ( select.equals( "���̹�" ) ) {
				tfMail2.setEditable( false );
				tfMail2.setText( "naver.com" );
			}
			else if ( select.equals( "����" ) ) {
				tfMail2.setEditable( false );
				tfMail2.setText( "daum.net" );
			}
			else if ( select.equals( "����" ) ) {
				tfMail2.setEditable( false );
				tfMail2.setText( "gmail.com" );
			}
			else if ( select.equals( "����Ʈ" ) ) {
				tfMail2.setEditable( false );
				tfMail2.setText( "nate.com" );
			}
			else if ( select.equals( "����" ) ) {
				tfMail2.setEditable( false );
				tfMail2.setText( "yahoo.com" );
			}
		}

	}

}
