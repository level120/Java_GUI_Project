package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.bind.util.ValidationEventCollector;

import common.MyPair;
import db.DBConnector;
import ui.AdminMemberJoin.BtnAction;

public class AdminOrderDialog extends JDialog {
	
	private AdminOrder			org;
	private AdministratorFrame	origin;

	private final int			WIDTH	= 800;
	private final int			HEIGHT	= 500;
	
	private JTable 				tbView;
	private DefaultTableModel	model;
	private String[]			title = { "유형", "책명", "출판사", "가격", "재고", "선택" };
	private JButton				btnOk,
								btnCalcel;
	private Vector< String >	vCode;
	
	public	Vector< common.MyPair >	vRes;
	
	public AdminOrderDialog( AdminOrder obj, AdministratorFrame obj1 ) {
		this.org	= obj;
		this.origin = obj1;
		initialize();
		view();
		this.setVisible( true );
	}
	
	private void initialize() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension sc = toolkit.getScreenSize();

		this.setTitle( "메뉴 목록" );
		this.setSize( WIDTH, HEIGHT );
		this.setLocation( sc.width / 4, sc.height / 4 );
		this.setResizable( false );
		this.setModal( true );
		this.setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
	}
	
	private void view() {
		model	= new DefaultTableModel( title, 0 ) {
			@Override
			public boolean isCellEditable( int row, int column ) {
				if ( column > title.length - 2 )
					return true;
				else
					return false;
			}
		};
		
		
		tbView	= new JTable( model );
		
		JPanel		p		= new JPanel( null );
		JPanel		p2		= new JPanel();
		JScrollPane scPan	= new JScrollPane( tbView );
		
		btnOk				= new JButton( "선택" );
		btnCalcel			= new JButton( "취소" );
		vCode				= new Vector<>();
		vRes				= new Vector<>();

		btnOk.addActionListener( new BtnClick() );
		btnCalcel.addActionListener( new BtnClick() );
				
		p.add( scPan );
		p2.add( btnOk );
		p2.add( btnCalcel );
		
		this.add( p, BorderLayout.CENTER );
		this.add( p2, BorderLayout.SOUTH );
		
		p.setBorder( BorderFactory.createTitledBorder( "멀티미디어 목록" ) );
		scPan.setBounds( 25, 25, WIDTH - 50, HEIGHT - 110 );
		
//		scPan.setSize( WIDTH - 40, HEIGHT - 500 );
		tbView.setRowHeight( 25 );
		p.setBackground( Color.WHITE );
		p2.setBackground( Color.WHITE );
		scPan.setBackground( Color.WHITE );
		
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>( model );
		tbView.setRowSorter(sorter);
		
		getData();

		model.addTableModelListener( new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				String temp = ( String ) model.getValueAt( e.getFirstRow(), e.getColumn() );
				if ( !temp.matches( "\\d*" ) ) {
					JOptionPane.showMessageDialog( null, "올바른 값을 입력하십시오.", "에러", JOptionPane.ERROR_MESSAGE );
					model.setValueAt( "0", e.getFirstRow(), e.getColumn() );
				}
				else if ( Integer.parseInt( temp ) > Integer.parseInt( ( String ) model.getValueAt( e.getFirstRow(), e.getColumn() - 1 ) ) ) {
					JOptionPane.showMessageDialog( null, "재고량의 범위를 초과하였습니다.", "에러", JOptionPane.ERROR_MESSAGE );
					model.setValueAt( "0", e.getFirstRow(), e.getColumn() );
				}
				else if ( Integer.parseInt( temp ) < 0 ) {
					JOptionPane.showMessageDialog( null, "선택은 음수가 될 수 없습니다.", "에러", JOptionPane.ERROR_MESSAGE );
					model.setValueAt( "0", e.getFirstRow(), e.getColumn() );
				}
			}
		});
	}
	
	private void getData() {
		DBConnector	db	= new DBConnector();
		
		String		sql	= "SELECT * FROM PRODUCT";
		db.query( sql );
		System.err.println( sql );
		
		while ( db.queryCheck() ) {
			Object[] tmp = new Object[ title.length ];
			vCode.addElement( ( String ) db.getQuery( 1 ) );
			for ( int k = 0; k < title.length - 1; k++ ) {
				tmp[ k ] = db.getQuery( k + 2 );
			}
			tmp[ title.length - 1 ] = "0";
			if ( origin.selectedList != null ) {
				int len = origin.selectedList.size();
				for ( int i = 0; i < len; i++ ) {
					if ( vCode.get( vCode.size() - 1 ).equals( origin.selectedList.get( i ).code ) ) {
						tmp[ title.length - 1 ] = origin.selectedList.get( i ).selected;
						break;
					}
				}
			}
			model.addRow( tmp );
		}
		
		db.close();
	}
	
	private void getReturn() {
		Boolean state	= true;
		int		len		= model.getRowCount();
		
		for ( int i = 0; i < len; i++ ) {
			if ( !model.getValueAt( i, title.length - 1 ).toString().matches( "\\d" ) ) {
				state = false;
				break;
			}
		}
		
		if ( state ) {
			check();
			JOptionPane.showMessageDialog( null, "선택되었습니다.", "완료!", JOptionPane.INFORMATION_MESSAGE );
			origin.selectedList = vRes;
			apply();
			org.applyPay();
			this.setVisible( false );
		}
		else {
			JOptionPane.showMessageDialog( null, "다시 확인해주세요.", "에러", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	private void apply() {
		int bookSum		= 0;
		int mediaSum	= 0;
				
		for ( int i = 0, len = vRes.size(); i < len; i++ ) {
			if ( ( (String) vRes.get( i ).code ).matches( "B\\d{3}" ) ) {
				bookSum += Integer.parseInt( ( String )vRes.get( i ).selected );
			}
			else if ( ( (String) vRes.get( i ).code ).matches( "M\\d{3}" ) ) {
				mediaSum += Integer.parseInt( ( String )vRes.get( i ).selected );
			}
		}
		org.labBookCount.setText( "" + bookSum );
		org.labMediaCount.setText( "" + mediaSum );
	}
	
	private void check() {
		int len = model.getRowCount();
		for ( int i = 0; i < len; i++ ) {
			if ( !model.getValueAt( i, title.length - 1 ).equals( "0" ) ) {
				vRes.addElement( new MyPair( new Object[] { vCode.get( i ), model.getValueAt( i, 3 ), model.getValueAt( i, 5 ), model.getValueAt( i, 4 ) } ) );
			}
		}
	}
	
	class BtnClick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if ( e.getActionCommand().equals( "선택" ) ) {
				getReturn();
			}
			else {
				setVisible( false );
			}
		}
		
	}
	
}
