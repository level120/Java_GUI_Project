package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import common.MyPair;
import db.DBConnector;

public class AdminPay extends JPanel implements ActionListener {

	public static double MEMBER		= 0.08;		// 8%
	public static double STUDENT	= 0.05;		// 5%
	public static double VIP		= 0.1;		// 10%
	
	public static long		price		= 0,
							point		= 0,
							total		= 0;
	public static double	discount	= 0.0,
							after		= 0.0;
	
	private	AdminSearch			searchModel;
	private AdministratorFrame	adminModel;
	
	private JLabel	pnPrice,
					pnPoint,
					pnDiscount,
					pnTotal,
					pnWon1, pnWon2, pnWon3, pnWon4,
					pnPercent,
					pnAfter;
	private JButton	btnPay;
	private static JFormattedTextField	tfPrice,
										tfPoint,
										tfDiscount,
										tfTotal,
										tfAfter;
	
	public AdminPay( AdminSearch obj, AdministratorFrame obj1 ) {
		this.searchModel	= obj;
		this.adminModel		= obj1;
		initialize();
		view();
	}
	
	private void view() {
		btnPay.addActionListener( this );
		
		pnPoint.setBounds( 25, 25, 150, 15 );
		pnPrice.setBounds( 25, 45, 150, 15 );
		pnDiscount.setBounds( 25, 65, 150, 15 );
		pnTotal.setBounds( 25, 85, 150, 15 );
		pnWon2.setBounds( 205, 25, 150, 15 );
		pnWon1.setBounds( 205, 45, 150, 15 );
		pnPercent.setBounds( 205, 65, 150, 15 );
		pnWon3.setBounds( 205, 85, 150, 15 );
		pnAfter.setBounds( 25, 105, 150, 15 );
		pnWon4.setBounds( 205, 105, 150, 15 );
		
		this.add( pnPrice );
		this.add( pnPoint );
		this.add( pnDiscount );
		this.add( pnTotal );
		this.add( pnAfter );
		this.add( pnWon1 );
		this.add( pnWon2 );
		this.add( pnWon3 );
		this.add( pnWon4 );
		this.add( pnPercent );
		
		tfPoint.setBounds( 85, 25, 100, 15 );
		tfPrice.setBounds( 85, 45, 100, 15 );
		tfDiscount.setBounds( 85, 65, 100, 15 );
		tfTotal.setBounds( 85, 85, 100, 15 );
		tfAfter.setBounds( 85, 105, 100, 15 );
		
		this.add( tfPrice );
		this.add( tfPoint );
		this.add( tfDiscount );
		this.add( tfTotal );
		this.add( tfAfter );
		
		btnPay.setBounds( 90, 145, 90, 25 );
		
		this.add( btnPay );
		
		tfPrice.setHorizontalAlignment( JFormattedTextField.RIGHT );
		tfPoint.setHorizontalAlignment( JFormattedTextField.RIGHT );
		tfDiscount.setHorizontalAlignment( JFormattedTextField.RIGHT );
		tfTotal.setHorizontalAlignment( JFormattedTextField.RIGHT );
		tfAfter.setHorizontalAlignment( JFormattedTextField.RIGHT );
		
		this.setBackground( Color.WHITE );
		this.setBorder( BorderFactory.createTitledBorder( "결제정보" ) );
	}
	
	private void initialize() {
		
		price	= point = total = 0;
		discount = after = 0.0;
		
		this.setLayout( null );
		
		pnPrice		= new JLabel( "판매가" );
		pnPoint		= new JLabel( "포인트" );
		pnDiscount	= new JLabel( "할인율" );
		pnTotal		= new JLabel( "합    계" );
		pnAfter		= new JLabel( "잔    액" );
		pnWon1		= new JLabel( "원" );
		pnWon2		= new JLabel( "원" );
		pnWon3		= new JLabel( "원" );
		pnWon4		= new JLabel( "원" );
		pnPercent	= new JLabel( "%" );
		
		tfPrice		= new JFormattedTextField( new NumberFormatter() );
		tfPoint		= new JFormattedTextField( new NumberFormatter() );
		tfDiscount	= new JFormattedTextField( new NumberFormatter() );
		tfTotal		= new JFormattedTextField( new NumberFormatter() );
		tfAfter		= new JFormattedTextField( new NumberFormatter() );
		
		btnPay		= new JButton( "결제진행" );
		
		pnPrice.setFont( new Font( "Bold", Font.BOLD, 12 ) );
		pnPoint.setFont( new Font( "Bold", Font.BOLD, 12 ) );
		pnDiscount.setFont( new Font( "Bold", Font.BOLD, 12 ) );
		pnTotal.setFont( new Font( "Bold", Font.BOLD, 12 ) );
		pnAfter.setFont( new Font( "Bold", Font.BOLD, 12 ) );
		pnWon1.setFont( new Font( "Bold", Font.BOLD, 12 ) );
		pnWon2.setFont( new Font( "Bold", Font.BOLD, 12 ) );
		pnWon3.setFont( new Font( "Bold", Font.BOLD, 12 ) );
		pnWon4.setFont( new Font( "Bold", Font.BOLD, 12 ) );
		pnPercent.setFont( new Font( "Bold", Font.BOLD, 12 ) );
		
		btnPay.setFont( new Font( "Bold", Font.BOLD, 12 ) );
		
		tfPrice.setColumns( 10 );
		tfPoint.setColumns( 10 );
		tfDiscount.setColumns( 10 );
		tfTotal.setColumns( 10 );
		tfAfter.setColumns( 10 );
		
		tfPrice.setEditable( false );
		tfPoint.setEditable( false );
		tfDiscount.setEditable( false );
		tfTotal.setEditable( false );
		tfAfter.setEditable( false );
		
		tfPrice.setValue( price );
		tfPoint.setValue( point );
		tfDiscount.setValue( discount );
		tfTotal.setValue( total );
		tfAfter.setValue( after );
	}
	
	public void setPrice( long price ) {
		AdminPay.price = price;
		tfPrice.setValue( price );
	}
	
	public void setPoint( long point ) {
		AdminPay.point = point;
		tfPoint.setValue( point );
	}
	
	public void setDiscount( double discount ) {
		AdminPay.discount = discount;
		tfDiscount.setValue( discount );
	}
	
	public void setTotal( long total ) {
		AdminPay.total = total;
		tfTotal.setValue( total );
	}
	
	public static void calculate() {
		total = price - (int)( price * discount );
		after = ( point - total ) < 0 ? 0 : ( point - total );
		tfPrice.setValue( price );
		tfPoint.setValue( point );
		tfDiscount.setValue( discount * 100 );
		tfTotal.setValue( total );
		tfAfter.setValue( after );
		
//		System.err.println( "price : \t" + price );
//		System.err.println( "point : \t" + point );
//		System.err.println( "discount : \t" + discount );
//		System.err.println( "total : \t" + total );
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		// TODO Auto-generated method stub
		if ( !searchModel.flag ) {
			JOptionPane.showMessageDialog( null, "사용자가 선택되지 않았습니다.", "오류", JOptionPane.ERROR_MESSAGE );
		}
		else if ( total == 0 ) {
			JOptionPane.showMessageDialog( null, "물품이 선택되지 않았습니다.", "오류", JOptionPane.ERROR_MESSAGE );
		}
		else {
			if ( point < total ) {
				JOptionPane.showMessageDialog( null, "포인트가 부족합니다.", "잔액부족", JOptionPane.ERROR_MESSAGE );
			}
			else {
				DBConnector	db	= new DBConnector();
				String		sql	= "SELECT NO FROM SALE";
				int			no	= 0;
				System.err.println( sql );
				
				db.query( sql );
				
				while ( db.queryCheck() ) {
					int tmp = Integer.parseInt( db.getQuery( 1 ) );
					if ( tmp > no ) {
						no = tmp;
					}
				}
				
				for ( MyPair value : adminModel.selectedList ) {
					sql	= "INSERT INTO SALE VALUE( "
							+ ++no
							+ ", '" + searchModel.selectedMember 
							+ "', '" + value.code
							+ "', " + value.selected
							+ ", " + total
							+ ", DATE_FORMAT( NOW(), '%Y-%m-%d' ), DATE_FORMAT( NOW() + INTERVAL 7 DAY, '%Y-%m-%d' ), 0 )";

					System.err.println( sql );
					db.query2( sql );

					sql	= "UPDATE PRODUCT SET COUNT = "
							+ ( Integer.parseInt( (String)value.count ) - Integer.parseInt( (String)value.selected ) )
							+ " WHERE ID = '" + value.code + "'";
					System.err.println( sql );
					db.query2( sql );
				}
				sql	= "UPDATE CUSTOMER SET POINT = "
					+ ( point  - total )
					+ " WHERE MAIL = '" + searchModel.selectedMember + "'";
				System.err.println( sql );
				db.query2( sql );

				db.close();
				JOptionPane.showMessageDialog( null, total + "원이 정상적으로 결제되었습니다. ", "계산완료!", JOptionPane.INFORMATION_MESSAGE );
				DeliveryPanel.getData();
				AllPanel.getData();
				
				new AdministratorFrame();
				adminModel.dispose();
			}
		}
	}

}