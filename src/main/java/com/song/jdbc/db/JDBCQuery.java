package com.song.jdbc.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <p>
 * Title: JDBC_[子系统统名]_[Connection]
 * </p>
 * <p>
 * Description: [JDBC连接MySQL]
 * </p>
 * 
 * @author SOYU
 * @version $Revision$ 2016年5月25日
 * @author (lastest modification by $Author$)
 * @since 20100901
 */
public class JDBCQuery {

	public static void jdbcConnectionMySQL(String tableName) {
		
		Connection conn = null;//数据库连接
		Statement stmt = null;
		ResultSet rs = null;//结果集
		
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
			
			String url = "jdbc:mysql://localhost:3306/cloudhospital?user=root&password=admin&useUnicode=true&characterEncoding=UTF8";

			// 一个Connection代表一个数据库连接
			conn = DriverManager.getConnection(url);

			/**
			 * 3.通过connection对象创建statement对象，Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
			 */
			stmt = conn.createStatement();
			
			/**
			 * 4.使用statement执行SQL语句，executeQuery会返回结果的集合，否则返回空值
			 */
			String sql = "SELECT * FROM " + tableName;
			rs = stmt.executeQuery(sql);
			
			/**
			 * 5.操作结果集
			 */
			while(rs.next()){
				int id = rs.getInt(1);
				String username = rs.getString(3);
				String name = rs.getString(4);
				String mobile = rs.getString(5);
				String idcard = rs.getString(6);
				String sscard = rs.getString(7);
				String passwd = rs.getString(11);
				
				System.out.println("id: " + id + ",username: " + username + ",name: " + name
						+ ",mobile: " + mobile + ",idcard: " + idcard
						+ ",sscard: " + sscard + ",passwd: " + passwd);
			}
			
			} catch (SQLException e) {
				e.printStackTrace();
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
				
				if(null != stmt){
					try {
						stmt.close();
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
		jdbcConnectionMySQL("tb_user");
	}
}
