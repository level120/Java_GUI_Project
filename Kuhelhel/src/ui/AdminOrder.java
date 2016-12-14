package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import common.MyPair;

public class AdminOrder extends JPanel implements ActionListener {

	private	JButton		btnClick;
	public	JTextField	labBookCount,
						labMediaCount;
	
//	public	Vector< Common.MyPair >	vRes;
	
	private AdminPay			payModel;
	private AdministratorFrame	adminModel;
	
	public AdminOrder( AdminPay model1, AdministratorFrame model2 ) {
		payModel		= model1;
		adminModel 		= model2;
		initialize();
	}
	
	private void initialize() {
		this.setLayout( new BorderLayout() );
		this.setBorder( BorderFactory.createTitledBorder( "주문정보" ) );
		btnClick	= new JButton( "메뉴 정보" );
		btnClick.addActionListener( this );
		
		JPanel info	= new JPanel( null );
		
		JLabel labBook		= new JLabel( "도    서 :" );
		JLabel labMedia		= new JLabel( "미디어 :" );
		JLabel labBookLast	= new JLabel( "권" );
		JLabel labMediaLast	= new JLabel( "장" );
		
		labBookCount		= new JTextField( 10 );
		labMediaCount		= new JTextField( 10 );
					
		labBookCount.setEditable( false );
		labMediaCount.setEditable( false );
		
		labBookCount.setHorizontalAlignment( JTextField.RIGHT );
		labMediaCount.setHorizontalAlignment( JTextField.RIGHT );
				
		labBookCount.setText( "0" );
		labMediaCount.setText( "0" );
		
		info.setBackground( Color.WHITE );
						
		labBook.setBounds( 40, 25, 60, 15 );
		labMedia.setBounds( 40, 45, 60, 15);
		
		labBookCount.setBounds( 130, 25, 50, 15 );
		labMediaCount.setBounds( 130, 45, 50, 15 );
		
		labBookLast.setBounds( 190, 25, 30, 15 );
		labMediaLast.setBounds( 190, 45, 30, 15 );
		
		btnClick.setBounds( 80, 80, 90, 25 );
		
		info.add( labBook );
		info.add( labBookCount );
		info.add( labBookLast );
		info.add( labMedia );
		info.add( labMediaCount );
		info.add( labMediaLast );
				
		info.add( btnClick );
		
		this.add( info );
		this.setBackground( Color.WHITE );
	}
	
	public void applyPay() {
		long	price		= 0;
		
		for ( int i = 0, len = adminModel.selectedList.size(); i < len; i++ ) {
			price += Integer.parseInt( (String)adminModel.selectedList.get( i ).price ) *
					Integer.parseInt( (String)adminModel.selectedList.get( i ).selected );
		}
		
		AdminPay.price = price;
		payModel.setPrice( price );
		
		AdminPay.calculate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ( adminModel.selectedList != null ) {
			for ( MyPair myPair : adminModel.selectedList ) {
				System.err.println( myPair.code + "\t" + myPair.price + "\t" + myPair.selected );
			}
		}
		new AdminOrderDialog( this, adminModel );
	}
	
}
