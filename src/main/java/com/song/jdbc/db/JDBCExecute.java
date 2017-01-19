package com.song.jdbc.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class JDBCExecute {
	
	public static void JDBCConnectionExecuteFunction(){
		Connection conn = null;
		PreparedStatement psm = null;
		ResultSet rs = null;
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
			
			DatabaseMetaData ds = conn.getMetaData(); //获得包含了关于数据库整体元数据信息 
			String databaseName = ds.getDatabaseProductName();//数据库名
			String dataVersion = ds.getDatabaseProductVersion();//数据库版本号
			String driverName = ds.getDriverName();//数据库驱动名
			String driverVersion = ds.getDriverVersion();//数据库驱动版本号
			String databaseURL = ds.getURL();//数据库URL
			String userName = ds.getUserName();//该连接的登录名
			System.out.println("成功建立连接，数据库信息如下：数据库名 - " + databaseName
					+ ",数据库版本号 - " + dataVersion + ",数据库驱动名 - " + driverName
					+ ",数据库驱动版本号 - " + driverVersion + ",数据库URL - "
					+ databaseURL + ",该连接的登录名 - " + userName);
			
			
			/**
			 * 3.通过connection对象创建statement对象，Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
			 */
			String sql = "SELECT * FROM tb_user WHERE ID < ?";
			psm = conn.prepareStatement(sql);
			
			/**
			 * 4.使用prepareStatement执行SQL语句，execute()方法可以执行任何SQL语句，其返回值是boolean类型，表明是否返回了ResultSet对象
			 */
			psm.setInt(1, 10);
			boolean bool = psm.execute();
			
			/**
			 * 5.操作结果集
			 */
			if(bool){
				rs = psm.getResultSet();	
				
				ResultSetMetaData rsmd = rs.getMetaData();//结果集元数据对象 
				int count = rsmd.getColumnCount();//字段个数
				System.out.println("the number of columns:"+count);
				String strName = rsmd.getColumnName(1);//字段名字
				System.out.println("the 1 column name:"+strName);
				
				while (rs.next()) {
					String id = rs.getString(1);//ResultSet()方法的getString()方法几乎可以获取除Blob之外的任意类型列的值
					String name = rs.getString(4);
					System.out.println("主键id：" + id + "，姓名:" + name);
				}
				
			}else{
				System.out.println("the first result is an update count or there is no result.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			/**
			 * 6.回收数据库资源，包括关闭ResultSet，Statement和Connection等资源
			 */
			if(null != rs){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(null != psm){
				try {
					psm.close();
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
		JDBCConnectionExecuteFunction();
	}

}
