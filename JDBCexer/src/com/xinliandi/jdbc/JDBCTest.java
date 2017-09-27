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
		//1.�õ���ѯ������
		int searchType=getSearchTypeFromConsole();
		
		//2.�����ѯѧ����Ϣ
		Student student=searchStudent(searchType);
		
	//3.��ӡѧ����Ϣ
		printStudent(student);
	}
	/**
	 * ��ӡѧ����Ϣ����ѧ���������ӡ�������Ϣ���������ڣ���ӡ���޴���
	 * 
	 * @param student
	 */
	private void printStudent(Student student) {
		if (student != null) {
			System.out.println(student);
		} else {
			System.out.println("���޴���!");
		}
		
	}

	/**
	 * �����ѯѧ����Ϣ�ġ�����һ��student�����������ڣ��򷵻�һ��null
	 * searchType 1��2
	 * @param searchType
	 * @return
	 */
	private Student searchStudent(int searchType) {
		String sql = "SELECT flowid, type, idcard, examcard,"
				+ "studentname, location, grade " + "FROM examstudent "
				+ "WHERE ";

		Scanner scanner = new Scanner(System.in);
		// 1.���������searchType����ʾ�û�������Ϣ
		//1.1��searchTypeΪ1����ʾ�������֤�ţ�1.2��searchTypeΪ2����ʾ����׼��֤��
		//2.����searchTypeȷ��sql
		if (searchType == 1) {
			System.out.print("������׼��֤��:");
			String examCard = scanner.next();
			sql = sql + "examcard = '" + examCard + "'";
		} else {
			System.out.print("���������֤��:");
			String examCard = scanner.next();
			sql = sql + "idcard = '" + examCard + "'";
		}
		
		
		//3.ִ�в�ѯ
		Student student = getStudent(sql);
		//4.�����ڽ���ѽ����װΪһ��student����
		return student;
	}
	/**
	 * ���ݴ���� SQL ���� Student ����
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
	 * �ӿ���̨����һ��������ȷ��Ҫ��ѯ������
	 * 1.���֤��2.׼��֤ ������Ч��ʾ��������
	 * 
	 * @return
	 */
	private int getSearchTypeFromConsole() {
		System.out.print("�������ѯ����: 1. ��׼��֤��ѯ. 2. �����֤�Ų�ѯ ");

		Scanner scanner = new Scanner(System.in);
		int type = scanner.nextInt();

		if (type != 1 && type != 2) {
			System.out.println("������������������!");
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
	 * �ӿ���̨����ѧ������Ϣ
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
		//1׼��һ��sql���
		String sql="INSERT INTO examstudent VALUES"
				+"("+student.getFlowID()
				+","+student.getType()
				+",'"+student.getIdCard()
				+"','"+student.getExamCard()
				+"','"+student.getStudentName()
				+"','"+student.getLocation()
				+"',"+student.getGrade()+")";
		//2����JDBCTools���update��sql������ִ�в������
		JDBCTools.update(sql);
	}

}
