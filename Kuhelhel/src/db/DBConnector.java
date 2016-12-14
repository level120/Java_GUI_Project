package db;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import java.sql.PreparedStatement;

public class DBConnector {

	public Connection conn;
	public PreparedStatement ready;
	public ResultSet resSet;
	
	private String url	= "JDBC:mysql://localhost:3306/java_gui";
	private String id	= "root";
	private String pw	= "1234";
	
	public DBConnector() {
		connect();
	}
	
	private void connect() {
		try {
			Class.forName( "com.mysql.jdbc.Driver" );
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println( "Class Name Error" );
			JOptionPane.showMessageDialog( null, "서버와 연결할 수 없습니다.\n자세한 사항은 채팅로그를 참조하십시오.", "연결 에러", JOptionPane.ERROR_MESSAGE );
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection( url, id, pw );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println( "SQL connection error" );
			JOptionPane.showMessageDialog( null, "서버와 연결할 수 없습니다.\n자세한 사항은 채팅로그를 참조하십시오.", "연결 에러", JOptionPane.ERROR_MESSAGE );
			e.printStackTrace();
		}
		System.err.println( "Completely open MariaDB" );
	}
	
	public Boolean queryCheck() {
		try {
			return resSet.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, "서버와 연결할 수 없습니다.\n자세한 사항은 채팅로그를 참조하십시오.", "연결 에러", JOptionPane.ERROR_MESSAGE );
		}
		return false;
	}
	
	public String getQuery( int columnIndex ) {
		try {
			return resSet.getString( columnIndex );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, "서버와 연결할 수 없습니다.\n자세한 사항은 채팅로그를 참조하십시오.", "연결 에러", JOptionPane.ERROR_MESSAGE );
		}
		return "";
	}
	
	public String getQuery( String columnIndex ) {
		try {
			return resSet.getString( columnIndex );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, "서버와 연결할 수 없습니다.\n자세한 사항은 채팅로그를 참조하십시오.", "연결 에러", JOptionPane.ERROR_MESSAGE );
		}
		return "";
	}
		
	/*
	 * 결과값을 받지 않는 쿼리
	 * ex, insert, update, delete 
	 */
	public void query2( String sql ) {
		try {
			ready	= conn.prepareStatement( sql );
			ready.executeUpdate( sql );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, "서버와 연결할 수 없습니다.\n자세한 사항은 채팅로그를 참조하십시오.", "연결 에러", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	/*
	 * 결과값을 받는 쿼리
	 * ex, select
	 */
	public void query( String sql ) {
		try {
			ready	= conn.prepareStatement( sql );
			resSet	= ready.executeQuery(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, "서버와 연결할 수 없습니다.\n자세한 사항은 채팅로그를 참조하십시오.", "연결 에러", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	public void close() {
		try {
			conn.close();
			System.err.println( "Completely close MariaDB" );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println( "SQL disconnection error" );
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, "서버와 연결할 수 없습니다.\n자세한 사항은 채팅로그를 참조하십시오.", "연결 에러", JOptionPane.ERROR_MESSAGE );
		}
	}
}
