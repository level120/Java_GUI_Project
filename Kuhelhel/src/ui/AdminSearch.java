package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.xml.stream.util.StreamReaderDelegate;

import db.DBConnector;

import javax.swing.JOptionPane;

/*
 *  AdmministratorFrame child panel
 */
public class AdminSearch extends JPanel implements ActionListener {

	public	int			maxSize = 0;

	private	JTextField	tf;
	private	JTable		tbMember;
	private	JButton		btnSearch;
	private	JButton		btnUpdate;
	private String[]	title = { "이름", "전화번호", "주소", "성별", "이메일", "포인트" };

	private	DefaultTableModel model;
	
	public	Boolean	flag;
	public	String	selectedMember = "";

	public AdminSearch() {
		initialize();
	}

	private void initialize() {
		this.setLayout( null );
		this.flag = false;

		model		= new DefaultTableModel( title, maxSize ) {
			@Override
			public boolean isCellEditable( int row, int column ) {
				return false;
			}
		};

		tf			= new JTextField( 10 );
		tbMember	= new JTable( model );
		btnSearch	= new JButton( "찾기" );
		btnUpdate	= new JButton( "갱신" );

		JScrollPane scPan = new JScrollPane( tbMember );
		tf.addActionListener( this );
		btnSearch.addActionListener( this );
		btnUpdate.addActionListener( this );

		tf.setBounds( 20, 25, 150, 25 );
		btnSearch.setBounds( 190, 25, 90, 25);
		btnUpdate.setBounds( 680 , 25, 90, 25);
		scPan.setBounds( 10, 65, AdministratorFrame.WIDTH - 25, 155 );

		this.setSize( new Dimension( AdministratorFrame.WIDTH, AdministratorFrame.HEIGHT / 6 + 5 ) );
		this.add( tf );
		this.add( btnSearch );
		this.add( btnUpdate );
		this.add( scPan );

		tbMember.setRowHeight( 25 );
		tbMember.setBackground( Color.WHITE );
		scPan.setBackground( Color.WHITE );
		this.setBackground( Color.WHITE );
		this.setBorder( BorderFactory.createTitledBorder( "회원검색" ) );

		reTry();
		
		tbMember.addMouseListener( new MouseListener() {
			
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
				flag = true;
				
				selectedMember = (String) tbMember.getValueAt( tbMember.getSelectedRow(), 4 );
				AdminPay.point = Integer.parseInt( (String) tbMember.getValueAt( tbMember.getSelectedRow(), 5 ) );
				
				if ( e.getClickCount() == 2 ) {
					new AdminMemberRenew( selectedMember );
				}
			}
		});
	}

	private void reTry() {
		DBConnector db	= new DBConnector();

		String	sql		= "SELECT * FROM CUSTOMER";
		db.query( sql );
		System.err.println( sql );

		int limit	= model.getRowCount();

		for ( int i = 0; i < limit; i++ ) {
			model.removeRow( 0 );
		}

		while ( db.queryCheck() ) {
			String[] tmp = new String[ title.length ];
			for ( int k = 0; k < title.length; k++ ) {
				if ( k != 3 )
					tmp[ k ] = db.getQuery( k + 1 );
				else {
					if ( db.getQuery( k + 1 ).equals( "0" ) )
						tmp[ k ] = "남";
					else
						tmp[ k ] = "여";
				}
			}
			model.addRow( tmp );
		}

		maxSize = model.getRowCount();

		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>( model );
		tbMember.setRowSorter(sorter);

		db.close();
	}

	private Boolean search() {
		if ( tf.getText().equals( "" ) ) {
			return false;
		}

		DBConnector	db	= new DBConnector();

		String sql = "SELECT * FROM CUSTOMER WHERE NM='" + tf.getText() + "'";
		db.query( sql );
		System.err.println( sql );

		int limit	= model.getRowCount();

		for ( int i = 0; i < limit; i++ ) {
			model.removeRow( 0 );
		}

		while ( db.queryCheck() ) {
			String[] tmp = new String[ title.length ];
			for ( int k = 0; k < title.length; k++ ) {
				tmp[ k ] = db.getQuery( k + 1 );
			}
			model.addRow( tmp );
		}

		maxSize = model.getRowCount();

		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>( model );
		tbMember.setRowSorter(sorter);

		db.close();

		if ( maxSize != 0 ) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if ( e.getActionCommand().equals( "갱신" ) ) {
			reTry();
			JOptionPane.showMessageDialog( null, "모든 정보를 가져왔습니다.", "확인", JOptionPane.INFORMATION_MESSAGE );
		}
		else {
			if ( search() ) {
				JOptionPane.showMessageDialog( null, "해당 정보를 찾았습니다.", "확인", JOptionPane.INFORMATION_MESSAGE );
			}
			else {
				JOptionPane.showMessageDialog( null, "찾는 정보가 없습니다.\n모두 찿고자 할 때는 '*'을 입력하십시오.", "확인", JOptionPane.WARNING_MESSAGE );
			}
		}
	}

}
