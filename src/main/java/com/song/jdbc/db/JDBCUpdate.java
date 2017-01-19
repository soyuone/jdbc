package com.song.jdbc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCUpdate {

	public static void JDBCConnectionMySQLUpdate(){
		Connection conn = null;
		PreparedStatement prsmt = null;
		try {
			/**
			 * 1.加载数据库驱动
			 */
			Class.forName("com.mysql.jdbc.Driver");
			
			/**
			 * 2.通过DriverManager获取数据库连接
			 */
			
			// MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
			// 避免中文乱码要指定useUnicode和characterEncoding
			// 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定
			// 常见参数：
			// user 用户名
			// password 密码
			// autoReconnect 联机失败，是否重新联机（true/false）
			// maxReconnect 尝试重新联机次数
			// initialTimeout 尝试重新联机间隔
			// maxRows 传回最大行数
			// useUnicode 是否使用Unicode字体编码（true/false）
			// characterEncoding 何种编码（GB2312/UTF-8/…）
			// relaxAutocommit 是否自动提交（true/false）
			// capitalizeTypeNames 数据定义的名称以大写表示
			String url = "jdbc:mysql://localhost:3306/cloudhospital?user=root&password=admin&useUnicode=true&characterEncoding=UTF-8";
			
			// 一个Connection代表一个数据库连接
			conn = DriverManager.getConnection(url);
			
			/**
			 * 3.通过connection对象创建statement对象，Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
			 */
			String sql = "UPDATE tb_user SET EMAIL = ? WHERE ID < 10";
			
			//prepareStatement方法返回预编译的Statement对象，即将SQL语句提交到数据库进行预编译
			prsmt = conn.prepareStatement(sql);
			
			/**
			 * 4.使用statement执行SQL语句，executeQuery会返回结果的集合，否则返回空值 
			 */
			//PreparedStatement同样有executeUpdate(),executeQuery(),execute()三个方法
			prsmt.setString(1, "liverpool@lfc.com");//如果不清楚SQL语句中各参数的类型，则可以使用setObject()传入参数
			prsmt.executeUpdate();
			System.out.println("修改成功");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			/**
			 * 5.回收数据库资源，包括关闭ResultSet，Statement和Connection等资源
			 */
			if(null != prsmt){
				try {
					prsmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		JDBCConnectionMySQLUpdate();
	}
}
