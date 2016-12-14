package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class AdminDiscount extends JPanel implements ActionListener {

	public JRadioButton rbtnMember,		rbtnNoMember,
						rbtnStudent,	rbtnNoStudent,
						rbtnVIP,		rbtnNoVIP;
	
	private ButtonGroup gpMember, gpStudent, gpVIP;
	
	private AdminPay	payModel;
	
	public AdminDiscount( AdminPay model ) {
		payModel	= model;
		initialize();
	}
	
	private void initialize() {
		this.setLayout( null );
		this.setBackground( Color.WHITE );
		this.setBorder( BorderFactory.createTitledBorder( "할인정보" ) );
		
		JLabel member = new JLabel( "회원" );
		JLabel student = new JLabel( "학생" );
		JLabel vip = new JLabel( "VIP" );
		
		rbtnMember		= new JRadioButton( "예" );
		rbtnNoMember	= new JRadioButton( "아니오" );
		
		gpMember		= new ButtonGroup();
		gpMember.add( rbtnMember );
		gpMember.add( rbtnNoMember );
		
		rbtnStudent		= new JRadioButton( "예" );
		rbtnNoStudent	= new JRadioButton( "아니오" );
		
		gpStudent		= new ButtonGroup();
		gpStudent.add( rbtnStudent);
		gpStudent.add( rbtnNoStudent );
		
		rbtnVIP			= new JRadioButton( "예" );
		rbtnNoVIP		= new JRadioButton( "아니오" );
		
		gpVIP			= new ButtonGroup();
		gpVIP.add( rbtnVIP );
		gpVIP.add( rbtnNoVIP );
		
		rbtnNoMember.setSelected( true );
		rbtnNoStudent.setSelected( true );
		rbtnNoVIP.setSelected( true );
		
		rbtnMember.addActionListener( this );
		rbtnNoMember.addActionListener( this );
		rbtnStudent.addActionListener( this );
		rbtnNoStudent.addActionListener( this );
		rbtnVIP.addActionListener( this );
		rbtnNoVIP.addActionListener( this );
		
		member.setBounds( 40, 25, 50, 15 );
		student.setBounds( 40, 45, 50, 15 );
		vip.setBounds( 40, 65, 50, 15 );
		
		rbtnMember.setBounds( 90, 25, 50, 15 );
		rbtnNoMember.setBounds( 140, 25, 100, 15 );
		rbtnStudent.setBounds( 90, 45, 50, 15 );
		rbtnNoStudent.setBounds( 140, 45, 100, 15 );
		rbtnVIP.setBounds( 90, 65, 50, 15 );
		rbtnNoVIP.setBounds( 140, 65, 100, 15 );
		
		rbtnMember.setBackground( Color.WHITE );
		rbtnNoMember.setBackground( Color.WHITE );
		rbtnStudent.setBackground( Color.WHITE );
		rbtnNoStudent.setBackground( Color.WHITE );
		rbtnVIP.setBackground( Color.WHITE );
		rbtnNoVIP.setBackground( Color.WHITE );
		
		this.add( member );
		this.add( student );
		this.add( vip );
		this.add( rbtnMember );
		this.add( rbtnNoMember );
		this.add( rbtnStudent );
		this.add( rbtnNoStudent );
		this.add( rbtnVIP );
		this.add( rbtnNoVIP );
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		// TODO Auto-generated method stub
		double check = 0.0;
		
		if ( rbtnMember.isSelected() ) {
			 check += AdminPay.MEMBER;
		}
		if ( rbtnStudent.isSelected() ) {
			check += AdminPay.STUDENT; 
		}
		if ( rbtnVIP.isSelected() ) {
			check += AdminPay.VIP;
		}
		
		AdminPay.discount = check;
		payModel.setDiscount( check );
		
		AdminPay.calculate();
	}

}
