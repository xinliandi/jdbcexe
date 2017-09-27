package com.xinliandi.jdbc;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import org.junit.Test;




public class JDBCTest {
	/**
	 * 
	 */
	@Test
	public void TestGetStudent() {
		//1.得到查询的类型
		int searchType=getSearchTypeFromConsole();
		
		//2.具体查询学生信息
		Student student=searchStudent(searchType);
		
	//3.打印学生信息
		printStudent(student);
	}
	/**
	 * 打印学生信息：若学生存在则打印其具体信息。若不存在，打印查无此人
	 * 
	 * @param student
	 */
	private void printStudent(Student student) {
		if (student != null) {
			System.out.println(student);
		} else {
			System.out.println("查无此人!");
		}
		
	}

	/**
	 * 具体查询学生信息的。返回一个student对象，若不存在，则返回一个null
	 * searchType 1或2
	 * @param searchType
	 * @return
	 */
	private Student searchStudent(int searchType) {
		String sql = "SELECT flowid, type, idcard, examcard,"
				+ "studentname, location, grade " + "FROM examstudent "
				+ "WHERE ";

		Scanner scanner = new Scanner(System.in);
		// 1.根据输入的searchType，提示用户输入信息
		//1.1若searchType为1，提示输入身份证号，1.2若searchType为2，提示输入准考证号
		//2.根据searchType确定sql
		if (searchType == 1) {
			System.out.print("请输入准考证号:");
			String examCard = scanner.next();
			sql = sql + "examcard = '" + examCard + "'";
		} else {
			System.out.print("请输入身份证号:");
			String examCard = scanner.next();
			sql = sql + "idcard = '" + examCard + "'";
		}
		
		
		//3.执行查询
		Student student = getStudent(sql);
		//4.若存在结果把结果封装为一个student对象
		return student;
	}
	/**
	 * 根据传入的 SQL 返回 Student 对象
	 * 
	 * @param sql
	 * @return
	 */
	private Student getStudent(String sql) {
		
		Student stu = null;

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = JDBCTools.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			if (resultSet.next()) {
				stu = new Student(resultSet.getInt(1), resultSet.getInt(2),
						resultSet.getString(3), resultSet.getString(4),
						resultSet.getString(5), resultSet.getString(6),
						resultSet.getInt(7));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTools.releaseDB(resultSet, statement, connection);
		}

		return stu;
	}
	/**
	 * 从控制台读入一个整数，确定要查询的类型
	 * 1.身份证，2.准考证 其他无效提示重新输入
	 * 
	 * @return
	 */
	private int getSearchTypeFromConsole() {
		System.out.print("请输入查询类型: 1. 用准考证查询. 2. 用身份证号查询 ");

		Scanner scanner = new Scanner(System.in);
		int type = scanner.nextInt();

		if (type != 1 && type != 2) {
			System.out.println("输入有误请重新输入!");
			throw new RuntimeException();
		}

		return type;
	}
	@Test
	public void testAddNewStudent() {
		Student student=getStudentFromConsole();
		addNewStudent(student);
	}
	/**
	 * 从控制台输入学生的信息
	 * @return
	 */
	private Student getStudentFromConsole() {
		Scanner scanner=new Scanner(System.in);
		Student student=new Student();
		System.out.print("FlowId:");
		student.setFlowID(scanner.nextInt());
		System.out.print("Type:");
		student.setType(scanner.nextInt());
		System.out.print("IdCard:");
		student.setIdCard(scanner.next());
		System.out.print("ExamCard:");
		student.setExamCard(scanner.next());
		System.out.print("StudentName:");
		student.setStudentName(scanner.next());
		System.out.print("Location:");
		student.setLocation(scanner.next());
		System.out.print("Grade:");
		student.setGrade(scanner.nextInt());
		
		return student;
	}
	public void addNewStudent(Student student) {
		//1准备一个sql语句
		String sql="INSERT INTO examstudent VALUES"
				+"("+student.getFlowID()
				+","+student.getType()
				+",'"+student.getIdCard()
				+"','"+student.getExamCard()
				+"','"+student.getStudentName()
				+"','"+student.getLocation()
				+"',"+student.getGrade()+")";
		//2调用JDBCTools类的update（sql）方法执行插入操作
		JDBCTools.update(sql);
	}

}
