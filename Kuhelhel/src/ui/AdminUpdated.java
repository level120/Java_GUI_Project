package ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import common.RentPair;
import db.DBConnector;

public class AdminUpdated extends JPanel {

	private JTabbedPane		tab;
	private DeliveryPanel	pnDel;
	private TodayPanel		pnDay;
	private	AllPanel		pnAll;
		
	public AdminUpdated() {
		initialize();
	}
	
	private void initialize() {
		this.setLayout( null );
		
		tab		= new JTabbedPane();
		pnDel	= new DeliveryPanel();
		pnDay	= new TodayPanel();
		pnAll	= new AllPanel();
		
		this.setBackground( Color.WHITE );
		
		tab.addTab( "금일 대여목록", pnDel );
		tab.addTab( "금일 반납목록", pnDay );
		tab.addTab( "현재 대여중", pnAll );
				
		tab.setBounds( 10, 15, AdministratorFrame.WIDTH - 25, 175 );
		
		this.add( tab );
	}
		
}

class DeliveryPanel extends JPanel {
	
	public int list = 1;
	
	private	static DefaultTableModel model;
	
	private String[] title = { "물품명", "대여일", "반납 예정일", "연체일", "빌려간 사람" };
	private	JTable deliveryTab;
	
	public DeliveryPanel() {
		initialize();
		getData();
	}
	
	private void initialize() {
		this.setLayout( null );
		
		model			= new DefaultTableModel( title, 0 ) {
			@Override
			public boolean isCellEditable( int row, int column ) {
				return false;
			}
		};
		deliveryTab		= new JTable( model );
		
		JScrollPane scPan = new JScrollPane( deliveryTab );

		deliveryTab.addMouseListener( new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if ( e.getClickCount() == 2 ) {
					if ( !deliveryTab.getValueAt( deliveryTab.getSelectedRow(), 3 ).equals( "반납완료" ) ){
						returnCheck();
					}
					else {
						JOptionPane.showMessageDialog( null, "이미 반납처리 되었습니다.", "오류", JOptionPane.ERROR_MESSAGE );
					}
				}
			}
		});
		
		deliveryTab.setRowHeight( 25 );
		deliveryTab.setBackground( Color.WHITE );
		this.setBackground( Color.WHITE );
		
		scPan.setBounds( 0, 0, AdministratorFrame.WIDTH - 25, 205 );
		this.add( scPan );
	}
	
	private void returnCheck() {
		int flag = JOptionPane.showConfirmDialog( null, "반납하시겠습니까?", "반납확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
		
		if ( flag == 0 ) {
			int 		sum	= 0;
			DBConnector db	= new DBConnector();
			String		slt	= ((String)deliveryTab.getValueAt( deliveryTab.getSelectedRow(), 4 ) );
			
			int			bei = 0;
			for ( int i = 0, len = slt.length(); i < len; i++ ) {
				if ( slt.charAt( i ) == '(' ) {
					bei = i;
					break;
				}
			}
			
			/* Turn Update */
			String		pk	= slt.substring( bei + 1, slt.length() - 1 );
			String		sql	= "UPDATE SALE SET IS_TURN = 1"
							+ " WHERE MAIL = '" + pk + "' AND"
							+ " ID = (SELECT ID FROM PRODUCT WHERE NM = '" + deliveryTab.getValueAt( deliveryTab.getSelectedRow(), 0 ) + "')";
			System.err.println( sql );
			
			db.query2( sql );

			/* Sum calculate */
			sql = "SELECT PD.COUNT, SA.CNT"
					+ " FROM PRODUCT AS PD"
					+ " JOIN SALE AS SA"
					+ " ON PD.ID = SA.ID"
					+ " WHERE SA.ID ="
					+ " (SELECT ID FROM PRODUCT WHERE NM = '" + deliveryTab.getValueAt( deliveryTab.getSelectedRow(), 0 ) + "')"
					+ " AND SA.MAIL = '" + pk + "'";
			System.err.println( sql );
			db.query( sql );
			
			while ( db.queryCheck() ) {
				for ( int k = 0; k < 2; k++ ) {
					sum += Integer.parseInt( (String)db.getQuery( k + 1 ) );
					System.err.println( sum );
				}
			}
			
			/*  Product Count Update */
			sql	= "UPDATE PRODUCT SET COUNT = "
					+ sum
					+ " WHERE ID = (SELECT * FROM (SELECT ID FROM PRODUCT WHERE NM = '" + deliveryTab.getValueAt( deliveryTab.getSelectedRow(), 0 ) + "') AS TMP)";
			System.err.println( sql );
			
			db.query2( sql );
			
			db.close();
			
			getData();
		}
	}
	
	public static void getData() {
		DBConnector db	= new DBConnector();
		String		sql	= "SELECT PD.NM,"
				+ " DATE_FORMAT( SA.RENT_DATE, '%Y-%m-%d' ) AS RENT_DATE,"
				+ " DATE_FORMAT( SA.TURN_DATE, '%Y-%m-%d' ) AS TURN_DATE,"
				+ " DATEDIFF( DATE_FORMAT( NOW(), '%Y-%m-%d' ), DATE_FORMAT( SA.TURN_DATE, '%Y-%m-%d' ) ) AS DELAY,"
				+ " CM.NM,"
				+ " SA.IS_TURN,"
				+ " SA.MAIL"
				+ " FROM CUSTOMER AS CM"
				+ " JOIN SALE AS SA"
				+ " ON CM.MAIL = SA.MAIL"
				+ " JOIN PRODUCT AS PD"
				+ " ON SA.ID = PD.ID"
				+ " WHERE DATE_FORMAT( SA.RENT_DATE, '%Y-%m-%d' ) = DATE_FORMAT( NOW(), '%Y-%m-%d' )";
		System.err.println( sql );
		
		db.query( sql );
		
		int limit	= model.getRowCount();

		for ( int i = 0; i < limit; i++ ) {
			model.removeRow( 0 );
		}

		while ( db.queryCheck() ) {
			String[]	obj		= new String[ RentPair.size ];
			String		check	= "";

			for ( int k = 0; k < 5; k++ ) {
				if ( k != 3 )
					obj[ k ] = db.getQuery( k + 1 );
				else {
					String tmp = db.getQuery( k + 1 );
					if ( Integer.parseInt( tmp ) < 0 ) {
						obj[ k ] = Math.abs( Integer.parseInt( tmp ) ) + "일 남음";
					}
					else {
						obj[ k ] = tmp + "일 경과";
					}
				}
			}
			check = db.getQuery( obj.length + 1 );
			
			if ( check.equals( "1"  ) ) {
				obj[ 3 ] = "반납완료"; 
			}
			
			check = db.getQuery( obj.length + 2 );
			obj[ obj.length - 1 ] += "(" + check + ")";
			
			model.addRow( obj );	
		}
		db.close();
	}
	
}

class TodayPanel extends JPanel {
	
	public int list = 1;
	
	private String[] title = { "물품명", "대여일", "반납 예정일", "연체일", "빌려간 사람" };
	private	JTable todayTab;
	private	DefaultTableModel model;
	
	public TodayPanel() {
		initialize();
		getData();
	}
	
	private void initialize() {
		this.setLayout( null );
		
		model			= new DefaultTableModel( title, 0 ) {
			@Override
			public boolean isCellEditable( int row, int column ) {
				return false;
			}
		};
		todayTab		= new JTable( model );
		
		JScrollPane scPan = new JScrollPane( todayTab );
		
		todayTab.addMouseListener( new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if ( !todayTab.getValueAt( todayTab.getSelectedRow(), 3 ).equals( "반납완료" ) ){
					returnCheck();
				}
				else {
					JOptionPane.showMessageDialog( null, "이미 반납처리 되었습니다.", "오류", JOptionPane.ERROR_MESSAGE );
				}
			}
		});
		
		todayTab.setRowHeight( 25 );
		todayTab.setBackground( Color.WHITE );
		this.setBackground( Color.WHITE );
		
		scPan.setBounds( 0, 0, AdministratorFrame.WIDTH - 25, 175 );
		this.add( scPan );
	}
	
	private void returnCheck() {
		int flag = JOptionPane.showConfirmDialog( null, "반납하시겠습니까?", "반납확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
		
		if ( flag == 0 ) {
			int 		sum	= 0;
			DBConnector db	= new DBConnector();
			String		slt	= ((String)todayTab.getValueAt( todayTab.getSelectedRow(), 4 ) );
			
			int			bei = 0;
			for ( int i = 0, len = slt.length(); i < len; i++ ) {
				if ( slt.charAt( i ) == '(' ) {
					bei = i;
					break;
				}
			}
			
			/* Turn Update */
			String		pk	= slt.substring( bei + 1, slt.length() - 1 );
			String		sql	= "UPDATE SALE SET IS_TURN = 1"
							+ " WHERE MAIL = '" + pk + "' AND"
							+ " ID = (SELECT ID FROM PRODUCT WHERE NM = '" + todayTab.getValueAt( todayTab.getSelectedRow(), 0 ) + "')";
			System.err.println( sql );
			
			db.query2( sql );

			/* Sum calculate */
			sql = "SELECT PD.COUNT, SA.CNT"
					+ " FROM PRODUCT AS PD"
					+ " JOIN SALE AS SA"
					+ " ON PD.ID = SA.ID"
					+ " WHERE SA.ID ="
					+ " (SELECT ID FROM PRODUCT WHERE NM = '" + todayTab.getValueAt( todayTab.getSelectedRow(), 0 ) + "')"
					+ " AND SA.MAIL = '" + pk + "'";
			System.err.println( sql );
			db.query( sql );
			
			while ( db.queryCheck() ) {
				for ( int k = 0; k < 2; k++ )
					sum += Integer.parseInt( (String)db.getQuery( k + 1 ) );
			}
			
			/*  Product Count Update */
			sql	= "UPDATE PRODUCT SET COUNT = "
					+ sum
					+ " WHERE ID = (SELECT * FROM (SELECT ID FROM PRODUCT WHERE NM = '" + todayTab.getValueAt( todayTab.getSelectedRow(), 0 ) + "') AS TMP)";
			System.err.println( sql );
			
			db.query2( sql );
			
			db.close();
			
			getData();
		}
	}
	
	private void getData() {
		DBConnector db	= new DBConnector();
		String		sql	= "SELECT PD.NM,"
				+ " DATE_FORMAT( SA.RENT_DATE, '%Y-%m-%d' ) AS RENT_DATE,"
				+ " DATE_FORMAT( SA.TURN_DATE, '%Y-%m-%d' ) AS TURN_DATE,"
				+ " DATEDIFF( DATE_FORMAT( NOW(), '%Y-%m-%d' ), DATE_FORMAT( SA.TURN_DATE, '%Y-%m-%d' ) ) AS DELAY,"
				+ " CM.NM,"
				+ " SA.IS_TURN,"
				+ " SA.MAIL"
				+ " FROM CUSTOMER AS CM"
				+ " JOIN SALE AS SA"
				+ " ON CM.MAIL = SA.MAIL"
				+ " JOIN PRODUCT AS PD"
				+ " ON SA.ID = PD.ID"
				+ " WHERE DATE_FORMAT( SA.TURN_DATE, '%Y-%m-%d' ) = DATE_FORMAT( NOW(), '%Y-%m-%d' )";
		System.err.println( sql );
		
		db.query( sql );
		
		int limit	= model.getRowCount();

		for ( int i = 0; i < limit; i++ ) {
			model.removeRow( 0 );
		}

		while ( db.queryCheck() ) {
			String[]	obj		= new String[ RentPair.size ];
			String		check	= "";

			for ( int k = 0; k < 5; k++ ) {
				if ( k != 3 )
					obj[ k ] = db.getQuery( k + 1 );
				else {
					String tmp = db.getQuery( k + 1 );
					if ( Integer.parseInt( tmp ) < 0 ) {
						obj[ k ] = Math.abs( Integer.parseInt( tmp ) ) + "일 남음";
					}
					else {
						obj[ k ] = tmp + "일 경과";
					}
				}
			}
			check = db.getQuery( obj.length + 1 );
			
			if ( check.equals( "1"  ) ) {
				obj[ 3 ] = "반납완료"; 
			}
			
			check = db.getQuery( obj.length + 2 );
			obj[ obj.length - 1 ] += "(" + check + ")";
			
			model.addRow( obj );
		}
		db.close();
		
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>( model );
		todayTab.setRowSorter( sorter );
	}
	
}

class AllPanel extends JPanel {
	
	public int list = 1;
	
	private	static DefaultTableModel model;
	
	private String[] title = { "물품명", "대여일", "반납 예정일", "연체일", "빌려간 사람" };
	private	JTable rentTab;
	
	public AllPanel() {
		initialize();
		getData();
	}
	
	private void initialize() {
		this.setLayout( null );
		
		model			= new DefaultTableModel( title, 0 ) {
			@Override
			public boolean isCellEditable( int row, int column ) {
				return false;
			}
		};
		rentTab		= new JTable( model );
		
		JScrollPane scPan = new JScrollPane( rentTab );
		
		rentTab.addMouseListener( new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if ( !rentTab.getValueAt( rentTab.getSelectedRow(), 3 ).equals( "반납완료" ) ){
					returnCheck();
				}
				else {
					JOptionPane.showMessageDialog( null, "이미 반납처리 되었습니다.", "오류", JOptionPane.ERROR_MESSAGE );
				}
			}
		});
		
		rentTab.setRowHeight( 25 );
		rentTab.setBackground( Color.WHITE );
		this.setBackground( Color.WHITE );
		
		scPan.setBounds( 0, 0, AdministratorFrame.WIDTH - 25, 175 );
		this.add( scPan );
	}
	
	private void returnCheck() {
		int flag = JOptionPane.showConfirmDialog( null, "반납하시겠습니까?", "반납확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
		
		if ( flag == 0 ) {
			int 		sum	= 0;
			DBConnector db	= new DBConnector();
			String		slt	= ((String)rentTab.getValueAt( rentTab.getSelectedRow(), 4 ) );
			
			int			bei = 0;
			for ( int i = 0, len = slt.length(); i < len; i++ ) {
				if ( slt.charAt( i ) == '(' ) {
					bei = i;
					break;
				}
			}
			
			/* Turn Update */
			String		pk	= slt.substring( bei + 1, slt.length() - 1 );
			String		sql	= "UPDATE SALE SET IS_TURN = 1"
							+ " WHERE MAIL = '" + pk + "' AND"
							+ " ID = (SELECT ID FROM PRODUCT WHERE NM = '" + rentTab.getValueAt( rentTab.getSelectedRow(), 0 ) + "')";
			System.err.println( sql );
			
			db.query2( sql );

			/* Sum calculate */
			sql = "SELECT PD.COUNT, SA.CNT"
					+ " FROM PRODUCT AS PD"
					+ " JOIN SALE AS SA"
					+ " ON PD.ID = SA.ID"
					+ " WHERE SA.ID ="
					+ " (SELECT ID FROM PRODUCT WHERE NM = '" + rentTab.getValueAt( rentTab.getSelectedRow(), 0 ) + "')"
					+ " AND SA.MAIL = '" + pk + "'";
			System.err.println( sql );
			db.query( sql );
			
			while ( db.queryCheck() ) {
				for ( int k = 0; k < 2; k++ )
					sum += Integer.parseInt( (String)db.getQuery( k + 1 ) );
			}
			
			/*  Product Count Update */
			sql	= "UPDATE PRODUCT SET COUNT = "
					+ sum
					+ " WHERE ID = (SELECT * FROM (SELECT ID FROM PRODUCT WHERE NM = '" + rentTab.getValueAt( rentTab.getSelectedRow(), 0 ) + "') AS TMP)";
			System.err.println( sql );
			
			db.query2( sql );
			
			db.close();
			
			getData();
		}
	}
	
	public static void getData() {
		DBConnector db	= new DBConnector();
		String		sql	= "SELECT PD.NM,"
				+ " DATE_FORMAT( SA.RENT_DATE, '%Y-%m-%d' ) AS RENT_DATE,"
				+ " DATE_FORMAT( SA.TURN_DATE, '%Y-%m-%d' ) AS TURN_DATE,"
				+ " DATEDIFF( DATE_FORMAT( NOW(), '%Y-%m-%d' ), DATE_FORMAT( SA.TURN_DATE, '%Y-%m-%d' ) ) AS DELAY,"
				+ " CM.NM,"
				+ " SA.IS_TURN,"
				+ " SA.MAIL"
				+ " FROM CUSTOMER AS CM"
				+ " JOIN SALE AS SA"
				+ " ON CM.MAIL = SA.MAIL"
				+ " JOIN PRODUCT AS PD"
				+ " ON SA.ID = PD.ID"
				+ " WHERE SA.IS_TURN = 0";
		System.err.println( sql );
		
		db.query( sql );
		
		int limit	= model.getRowCount();

		for ( int i = 0; i < limit; i++ ) {
			model.removeRow( 0 );
		}

		while ( db.queryCheck() ) {
			String[]	obj		= new String[ RentPair.size ];
			String		check	= "";
			
			for ( int k = 0; k < 5; k++ ) {
				if ( k != 3 )
					obj[ k ] = db.getQuery( k + 1 );
				else {
					String tmp = db.getQuery( k + 1 );
					if ( Integer.parseInt( tmp ) < 0 ) {
						obj[ k ] = Math.abs( Integer.parseInt( tmp ) ) + "일 남음";
					}
					else {
						obj[ k ] = tmp + "일 경과";
					}
				}
			}
			check = db.getQuery( obj.length + 1 );
			
			if ( check.equals( "1"  ) ) {
				obj[ 3 ] = "반납완료"; 
			}
			
			check = db.getQuery( obj.length + 2 );
			obj[ obj.length - 1 ] += "(" + check + ")";
			
			model.addRow( obj );
		}
		db.close();
	}
	
}