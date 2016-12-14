package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import db.DBConnector;

public class Authority extends JFrame implements ActionListener {
	
	private DBConnector 	db;
	
	private BufferedImage 	img;	// BG Image
	private JButton 		btnGuest;	// Into Guest Mode
	private JButton 		btnJoin;	// Into Administrator Mode
	private JPasswordField	pwField = new JPasswordField( 15 );
	
	private final int WIDTH		= 600;
	private final int HEIGHT	= 400;
	
	public Authority() {
		initialize();
		logic();
		this.setVisible( true );
	}
	
	private void initialize() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension sc = toolkit.getScreenSize();

		this.setTitle( "멀티미디어 관리 프로그램" );
		this.setSize( WIDTH, HEIGHT );
		this.setLocation( sc.width / 3, sc.height / 4 );
		this.setResizable( false );
		this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
	
	private void logic() {
		openImage( "test.jpg" );
		
		PicturePanel	bg		= new PicturePanel();
		JPanel			panBg	= new JPanel();
		JLabel			passwd	= new JLabel( "Password" );
		
		this.pwField	= new JPasswordField( 15 );
		this.btnGuest	= new JButton( "문의" );
		this.btnJoin	= new JButton( "접속" );
		
		this.db			= new DBConnector();
		
		pwField.addActionListener( this );
		btnGuest.addActionListener( this );
		btnJoin.addActionListener( this );
		
		bg.setLayout( null );
		
		this.setLayout( null );
		panBg.setBounds( WIDTH / 4, (int)(HEIGHT / 1.6), WIDTH / 2,  (int)(HEIGHT / 5.71) );
		
		panBg.add( passwd );
		panBg.add( pwField );
		panBg.add( btnGuest );
		panBg.add( btnJoin );
		
		panBg.setBackground( new Color( 0, 0, 0, 50 ) );
		bg.add( panBg );
		
		bg.setBounds( 0, 0, getWidth(), getHeight() );
		this.add(bg);
		
		this.setPreferredSize( new Dimension( WIDTH,  HEIGHT ) );
		this.pack();
	}
	
	private void openImage( String path ) {
		try {
			img = ImageIO.read( new File( path ) );
		} catch ( IOException e ) {
			System.err.println( "err image" );
		}
	}
	
	/*
	 * Into JPanel, Add to picture
	 */
	class PicturePanel extends JPanel {		
		@Override
		public void paintComponent( Graphics g ){
			super.paintComponent( g );
			g.drawImage( img, 0, 0, img.getWidth(), img.getHeight(), null );
		}

		public Dimension getPreferredSize() {
			if( img==null )
				return new Dimension( 600, 400 );
			else
				return new Dimension( img.getWidth(), img.getHeight() );
		}
	}
	
	@Override
	public void actionPerformed( ActionEvent e ) {
		// TODO Auto-generated method stub
		if( e.getActionCommand() == "문의" ) {
			JOptionPane.showMessageDialog(
					this,
					" 문의 사항이 있을 시 다음으로 연락주시기 바랍니다.\n\n"
					+ "E-Mail\t: think9573@tu.ac.kr\n"
					+ "Tel \t:051-629-1161(컴퓨터공학과)\n",
					"Infomation",
					JOptionPane.INFORMATION_MESSAGE );
			//new GuestFrame();
		}
		else {
			Boolean flag = true;
			
			String sql = "SELECT PW FROM ENTER";
			this.db.query( sql );
			
			if ( this.db.queryCheck() ) {
				String pw = this.db.getQuery( "PW" );
				if ( !pw.equals( null ) && pw.equals( pwField.getText() ) ) {
					System.err.println( sql );
					flag = false;
					this.db.close();
					new AdministratorFrame();
					this.dispose();
				}
			}
			if ( flag ) {
				JOptionPane.showMessageDialog( null, "비밀번호가 올바르지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE );
			}
		}
	}
		
}
