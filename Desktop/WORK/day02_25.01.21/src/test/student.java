package test;

import oracle.jdbc.driver.OracleDriver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class student {

	static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {

		System.out.println("학생성적관리프로그램 (ver 0.8.0)");
		String menu = "1.학생목록 2.학생등록 3.학생정보 4.성적입력 5.성적수정 0.종료";
		String input = null;

		while (true) {
			System.out.println(menu);
			input = scan.nextLine();

			if ( input.equals("0") ) break;

			else if ( input.equals("1") ) studentlist();

			else if ( input.equals("2") ) studentAdd();

			else if ( input.equals("3") ) studentInfo();
			
			else if ( input.equals("4") ) scoreInput();
			
			else if ( input.equals("5") ) scoreUpdate();
		}

	}
	
	public static void studentlist() { // 완료

		String sql = "select num, name from student";
		
		String url = "jdbc:oracle:thin:@localhost:49159:XE";
		Properties info = new Properties();
		info.setProperty("user", "scott");
		info.setProperty("password", "tiger");

		OracleDriver driver = new OracleDriver();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(url, info);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			System.out.println("------------");
			System.out.println("학번\t이름");
			System.out.println("------------");
			
			while ( rs.next() ) {
				System.out.print(rs.getString("num")+"\t");
				System.out.print(rs.getString("name")+"\t\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if ( rs != null ) { rs.close(); }
				if ( stmt != null ) { stmt.close(); }
				if ( conn != null ) { conn.close(); }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void studentAdd() { // 완료

		String sql = "insert into student values ((select to_char(sysdate, 'yy')||";
		sql += " lpad(nvl(max(rownum),0)+1, 3, 0) as num from student), '";

		String input = null;
		System.out.println("이름 > ");
		input = scan.nextLine();
		sql += input+"'";
		System.out.println("나이 > ");
		input = scan.nextLine();
		sql += ","+input+")";
		
		System.out.println(sql); // 정상 출력.

		String url = "jdbc:oracle:thin:@localhost:49159:XE";
		Properties info = new Properties();
		info.setProperty("user", "scott");
		info.setProperty("password", "tiger");

		OracleDriver driver = new OracleDriver();

		Connection conn = null;
		Statement stmt = null;

		try {
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(url, info);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if ( stmt != null ) { stmt.close(); }
				if ( conn != null ) { conn.close(); }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void studentInfo() { // 완료

		String sql = "select student.num, name, age, kor, eng, math,";
		sql += " rank() over (order by score.kor+score.eng+score.math desc) as rank";
		sql += " from student inner join score on student.num = score.num where student.num = ";
		
		String input = null;
		System.out.println("학번 > ");
		input = scan.nextLine();
		sql += input;
		
		System.out.println(sql); // 정상 출력
		
		String url = "jdbc:oracle:thin:@localhost:49159:xe";
		Properties info = new Properties();
		info.setProperty("user", "scott");
		info.setProperty("password", "tiger");

		OracleDriver driver = new OracleDriver();

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(url, info);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			System.out.println("-------------------------------------");
			System.out.println("학번\t이름\t나이\t국어\t영어\t수학\t등수");
			System.out.println("-------------------------------------");
			
			while ( rs.next() ) {
				System.out.print(rs.getString("num")+"\t");
				System.out.print(rs.getString("name")+"\t");
				System.out.print(rs.getString("age")+"\t");
				System.out.print(rs.getString("kor")+"\t");
				System.out.print(rs.getString("eng")+"\t");
				System.out.print(rs.getString("math")+"\t");
				System.out.println(rs.getString("rank"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if ( rs != null ) { rs.close(); }
				if ( stmt != null ) { stmt.close(); }
				if ( conn != null ) { conn.close(); }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void scoreInput() { // 완료

		String input = null;
		String sql = "insert into score values ( ";
		System.out.println("학번 > ");
		input = scan.nextLine();
		sql += input+",";
		System.out.println("국어 > ");
		input = scan.nextLine();
		sql += input+",";
		System.out.println("영어 > ");
		input = scan.nextLine();
		sql += input+",";
		System.out.println("수학 > ");
		input = scan.nextLine();
		sql += input+" )";
		
		System.out.println(sql); // 정상 출력

		String url = "jdbc:oracle:thin:@localhost:49159:xe";
		Properties info = new Properties();
		info.setProperty("user", "scott");
		info.setProperty("password", "tiger");

		OracleDriver driver = new OracleDriver();

		Connection conn = null;
		Statement stmt = null;

		try {
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(url, info);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if ( stmt != null ) { stmt.close(); }
				if ( conn != null ) { conn.close(); }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void scoreUpdate() { // 완료

		String input = null;
		String sql = "update score set kor=";
		System.out.println("학번 > ");
		String stuNum = scan.nextLine();
		System.out.println("국어 > ");
		input = scan.nextLine();
		sql += input+", eng=";
		System.out.println("영어 > ");
		input = scan.nextLine();
		sql += input+", math=";
		System.out.println("수학 > ");
		input = scan.nextLine();
		sql += input+" where num ="+stuNum;
		
		System.out.println(sql); // 정상 출력

		String url = "jdbc:oracle:thin:@localhost:49159:xe";
		Properties info = new Properties();
		info.setProperty("user", "scott");
		info.setProperty("password", "tiger");

		OracleDriver driver = new OracleDriver();

		Connection conn = null;
		Statement stmt = null;

		try {
			DriverManager.registerDriver(driver);
			conn = DriverManager.getConnection(url, info);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if ( stmt != null ) { stmt.close(); }
				if ( conn != null ) { conn.close(); }
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}