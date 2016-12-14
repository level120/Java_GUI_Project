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
			JOptionPane.showMessageDialog( null, "������ ������ �� �����ϴ�.\n�ڼ��� ������ ä�÷α׸� �����Ͻʽÿ�.", "���� ����", JOptionPane.ERROR_MESSAGE );
			e.printStackTrace();
		}
		
		try {
			conn = DriverManager.getConnection( url, id, pw );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println( "SQL connection error" );
			JOptionPane.showMessageDialog( null, "������ ������ �� �����ϴ�.\n�ڼ��� ������ ä�÷α׸� �����Ͻʽÿ�.", "���� ����", JOptionPane.ERROR_MESSAGE );
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
			JOptionPane.showMessageDialog( null, "������ ������ �� �����ϴ�.\n�ڼ��� ������ ä�÷α׸� �����Ͻʽÿ�.", "���� ����", JOptionPane.ERROR_MESSAGE );
		}
		return false;
	}
	
	public String getQuery( int columnIndex ) {
		try {
			return resSet.getString( columnIndex );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, "������ ������ �� �����ϴ�.\n�ڼ��� ������ ä�÷α׸� �����Ͻʽÿ�.", "���� ����", JOptionPane.ERROR_MESSAGE );
		}
		return "";
	}
	
	public String getQuery( String columnIndex ) {
		try {
			return resSet.getString( columnIndex );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, "������ ������ �� �����ϴ�.\n�ڼ��� ������ ä�÷α׸� �����Ͻʽÿ�.", "���� ����", JOptionPane.ERROR_MESSAGE );
		}
		return "";
	}
		
	/*
	 * ������� ���� �ʴ� ����
	 * ex, insert, update, delete 
	 */
	public void query2( String sql ) {
		try {
			ready	= conn.prepareStatement( sql );
			ready.executeUpdate( sql );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, "������ ������ �� �����ϴ�.\n�ڼ��� ������ ä�÷α׸� �����Ͻʽÿ�.", "���� ����", JOptionPane.ERROR_MESSAGE );
		}
	}
	
	/*
	 * ������� �޴� ����
	 * ex, select
	 */
	public void query( String sql ) {
		try {
			ready	= conn.prepareStatement( sql );
			resSet	= ready.executeQuery(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog( null, "������ ������ �� �����ϴ�.\n�ڼ��� ������ ä�÷α׸� �����Ͻʽÿ�.", "���� ����", JOptionPane.ERROR_MESSAGE );
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
			JOptionPane.showMessageDialog( null, "������ ������ �� �����ϴ�.\n�ڼ��� ������ ä�÷α׸� �����Ͻʽÿ�.", "���� ����", JOptionPane.ERROR_MESSAGE );
		}
	}
}
