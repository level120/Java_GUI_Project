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
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import db.DBConnector;

public class AdminMemberRenew extends JDialog {

	private final	int			WIDTH	= 600;
	private final	int			HEIGHT	= 500;

	private JTextField			tfName,
								tfAddress,
								tfMail, tfMail2,
								tfTel, tfTel2,
								tfPoint;
	private JRadioButton		rbtnMan, rbtnWoman;
	private JComboBox<String>	tel1,
								mail1;
	private JButton				btnOk,
								btnPre;

	public AdminMemberRenew( String pk ) {
		initialize();
		declaer();
		join( pk );
		this.setVisible( true );
	}

	private void initialize() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension sc = toolkit.getScreenSize();

		this.setTitle( "맴버쉽 수정" );
		this.setSize( WIDTH, HEIGHT );
		this.setLocation( sc.width / 4, sc.height / 4 );
		this.setResizable( false );
		this.setModal( true );
		this.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
	}

	private void declaer() {
		String[] callList	= { "없음", "010", "011", "016", "017", "070" };
		String[] mailList	= { "직접입력", "네이버", "다음", "구글", "네이트", "야후" };

		ButtonGroup gp	= new ButtonGroup();
		JPanel	p		= new JPanel( null );
		JPanel 	pn		= new JPanel( null );

		Font	font	= new Font( "Bold", Font.BOLD, 12 );

		JLabel	name	= new JLabel( "이름" );
		JLabel	tel		= new JLabel( "연락처" );
		JLabel	address	= new JLabel( "주소" );
		JLabel	gender	= new JLabel( "성별" );
		JLabel	mail	= new JLabel( "이메일" );
		JLabel	pt		= new JLabel( "포인트" );
		JLabel	temp	= new JLabel( "-" );
		JLabel	at		= new JLabel( "@" );

		btnOk			= new JButton( "수정" );
		btnPre			= new JButton( "삭제" );

		tfName			= new JTextField( 10 );
		tfAddress		= new JTextField( 50 );
		tfMail			= new JTextField( 30 );
		tfTel			= new JTextField( 4 );
		tfTel2			= new JTextField( 4 );
		tfMail2			= new JTextField( 30 );
		tfPoint			= new JTextField( 10 );

		rbtnMan			= new JRadioButton( "남자" );
		rbtnWoman		= new JRadioButton( "여자" );

		tel1			= new JComboBox<>( callList );
		mail1			= new JComboBox<>( mailList );
		
		rbtnMan.setSelected( true );
		
		tfMail.setEditable( false );
		tfMail2.setEditable( false );
		mail1.setVisible( false );

		gp.add( rbtnMan );
		gp.add( rbtnWoman );

		pn.add( name );
		pn.add( tel );
		pn.add( address );
		pn.add( gender );
		pn.add( mail );
		pn.add( temp );
		pn.add( pt );
		pn.add( tfName );
		pn.add( tfAddress);
		pn.add( tfTel );
		pn.add( tfMail );
		pn.add( tfMail2 );
		pn.add( tel1 );
		pn.add( mail1 );
		pn.add( tfTel2 );
		pn.add( tfPoint );
		pn.add( rbtnMan );
		pn.add( rbtnWoman );
		pn.add( at );
		pn.add( btnOk );
		pn.add( btnPre );

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

		pn.setBorder( BorderFactory.createTitledBorder( "기존맴버 수정" ) );

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
		pt.setBounds( 25, 200, 100, 25 );
		tfPoint.setBounds( 145, 200, 100, 25 );

		btnOk.setBounds( 260, 280, 90, 25 );
		btnPre.setBounds( 150, 280, 90, 25 );

		btnOk.addActionListener( new BtnAction() );
		btnPre.addActionListener( new BtnAction() );
		mail1.addActionListener( new ComboAction() );
		
		tfPoint.setHorizontalAlignment( JFormattedTextField.CENTER );

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
	
	private void join( String pk ) {
		DBConnector db	= new DBConnector();
		
		String		sql	= "SELECT NM, CONTACT, ADDR, SEX, MAIL, POINT FROM CUSTOMER WHERE MAIL='" + pk + "'";
		db.query( sql );
		System.err.println( sql );

		String[] selected = new String[ 6 ];
		if ( db.queryCheck() ) {
			for ( int k = 0; k < 6; k++ )
				selected[ k ] = db.getQuery( k + 1 );
		}
		
		tfName.setText( selected[0] );
				
		String[] call = selected[1].split( "-" );
		if ( !call[0].equals( tel1.getItemAt( 0 ) ) ) {
			tfTel.setText( call[1] );
			tfTel2.setText( call[2] );
		}
		
		for ( int i = 0, len = tel1.getItemCount(); i < len; i++ ) {
			if ( call[ 0 ].equals( tel1.getItemAt( i ) ) )
				tel1.setSelectedIndex( i );
		}
		
		tfAddress.setText( selected[ 2 ] );
		
		if ( selected[ 3 ].equals( "0" ) ) 
			rbtnMan.setSelected( true );
		else
			rbtnWoman.setSelected( true );
		
		String[] email = selected[ 4 ].split( "@" );
		tfMail.setText( email[ 0 ] );
		
		String[] list = { "naver.com", "daum.net", "gmail.com", "nate.com", "yahoo.com" };
		for ( int i = 0; i < list.length; i++ ) {
			if ( email[ 1 ].equals( list[ i ] ) )
				mail1.setSelectedIndex( i + 1 );
			
			if ( ( i == list.length - 1 ) && tfMail2.getText().equals( "" ) ) {
				tfMail2.setText( email[ 1 ] );
			}
		}
		tfPoint.setText( selected[ 5 ] );
		db.close();
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
					JOptionPane.showMessageDialog( null, "이름을 입력하십시오.", "오류", JOptionPane.ERROR_MESSAGE );
				}
				else if ( tfTel.getText().isEmpty() || tfTel2.getText().isEmpty() || tel1.getSelectedIndex() == 0
						|| !tfTel.getText().matches( "\\d*" ) || !tfTel2.getText().matches( "\\d*" ) ) {
					JOptionPane.showMessageDialog( null, "전화번호가 올바르지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE );
				}
				else if ( tfAddress.getText().isEmpty() ) {
					JOptionPane.showMessageDialog( null, "주소를 입력하십시오.", "오류", JOptionPane.ERROR_MESSAGE );
				}
				else if ( !tfPoint.getText().matches( "\\d*" ) ) {
					JOptionPane.showMessageDialog( null, "포인트가 정상적인 형식이 아닙니다.", "오류", JOptionPane.ERROR_MESSAGE );
				}
				else {
					String sql = "UPDATE CUSTOMER SET NM = '" +	tfName.getText()
														 + "', CONTACT = '" + tel1.getSelectedItem() + "-" + tfTel.getText() + "-" + tfTel2.getText()
														 + "', ADDR = '" + tfAddress.getText()
														 + "', SEX = " + getSex()
														 + ", POINT = '" + tfPoint.getText()
														 + "' WHERE MAIL = '" + sMail + "'";
					System.err.println( sql );
					db.query2( sql );

					JOptionPane.showMessageDialog( null, "수정이 완료되었습니다.", "완료", JOptionPane.INFORMATION_MESSAGE );
					db.close();
					dispose();
				}
			}
			else {
				int reply = JOptionPane.showConfirmDialog( null, "삭제된 데이터는 복구되지 않습니다.\n정말로 삭제하시겠습니까?", "맴버쉽 해제", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );

				if ( reply == JOptionPane.YES_OPTION ) {
					if ( !tfName.getText().isEmpty() ) {
						Boolean	flag	= true;
						String	sql		= "SELECT IS_TURN FROM SALE WHERE MAIL = '" + sMail + "'";
						System.err.println( sql );
						
						db.query( sql );
						
						while ( db.queryCheck() ) {
							String trash;
							if ( ( trash = db.getQuery( 1 ) ).equals( "0" ) ) {
								JOptionPane.showMessageDialog( null, "아직 반납하지 않은 물품이 있습니다.\n모든 물품 반납 후 삭제가 가능합니다.", "오류", JOptionPane.ERROR_MESSAGE );
								db.close();
								flag = false;
								break;
							}
						}
						
						if ( flag ) {
							sql = "DELETE FROM SALE WHERE MAIL = '" + sMail + "'";
							System.err.println( sql );
							
							db.query2( sql );
							
							sql = "DELETE FROM CUSTOMER WHERE MAIL = '" + sMail + "'";
							System.err.println( sql );
							
							db.query2( sql );
							JOptionPane.showMessageDialog( null, "정상적으로 삭제되었습니다.", "삭제 완료", JOptionPane.INFORMATION_MESSAGE );
							db.close();
							dispose();
						}
					}
					else {
						JOptionPane.showMessageDialog( null, "빈 공간이 있습니다.\n다시 확인해 주세요.", "오류", JOptionPane.ERROR_MESSAGE );
					}
				}

			}
		}

	}

	class ComboAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			JComboBox<String> tmp = ( JComboBox<String> ) e.getSource();
			String select = ( String ) tmp.getSelectedItem();
			if ( select.equals( "직접입력" ) ) {
				tfMail2.setEditable( true );
				tfMail2.setText( "" );
			}
			else if ( select.equals( "네이버" ) ) {
				tfMail2.setEditable( false );
				tfMail2.setText( "naver.com" );
			}
			else if ( select.equals( "다음" ) ) {
				tfMail2.setEditable( false );
				tfMail2.setText( "daum.net" );
			}
			else if ( select.equals( "구글" ) ) {
				tfMail2.setEditable( false );
				tfMail2.setText( "gmail.com" );
			}
			else if ( select.equals( "네이트" ) ) {
				tfMail2.setEditable( false );
				tfMail2.setText( "nate.com" );
			}
			else if ( select.equals( "야후" ) ) {
				tfMail2.setEditable( false );
				tfMail2.setText( "yahoo.com" );
			}
		}

	}

}
