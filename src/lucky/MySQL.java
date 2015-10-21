package lucky;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

public class MySQL {
	private Connection connection;
	
	//DB情報
	private	String		address;
	private	String		host;
	private String		DBName;
	private	String		table;
	private String		user;
	private	String		password;

	//SQLクエリ
	private	Statement		statement;
	private	String			sql;
	private	StringBuilder	columnBuilder;
	private	StringBuilder	valueBuilder;
	
	//DBデータ型
	private ResultSetMetaData			rsmd;
	private	ArrayList<String>			column;
	private	HashMap<String , Object>	recode;
	
	public MySQL(){
		this.column = new ArrayList<>();
		this.recode = new HashMap<>();
		
		setDBInfo();
	}
	
	public void ConnectionDB(){
		try {
			connection = DriverManager.getConnection(address , user , password);
			getColumn(column);
			System.out.println("接続完了");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void DisconnectionDB(){
		if(!hasDB())	return;
		try {
			connection.close();
			System.out.println("切断完了");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insert(HashMap<String , Object> data){
		if(!hasDB())	return;
		try {
			statement = connection.createStatement();
			
			columnBuilder	= new StringBuilder();
			valueBuilder	= new StringBuilder();
			
			columnBuilder.append("(");
			valueBuilder.append("(");
			
			for(int i = 1; i < column.size(); i++){
				columnBuilder.append(column.get(i));
				columnBuilder.append(",");
				valueBuilder.append("?");
				valueBuilder.append(",");
			}
			
			columnBuilder.deleteCharAt(columnBuilder.length() - 1);
			valueBuilder.deleteCharAt(valueBuilder.length() - 1);
			columnBuilder.append(")");
			valueBuilder.append(")");
			
			sql = "insert into " + table;
			sql += columnBuilder.toString() + " values" + valueBuilder.toString();
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			for(int i = 1; i < column.size(); i++){
				preparedStatement.setObject(i, data.get(column.get(i)));
			}

			System.out.println(preparedStatement.toString());
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void select(){
		if(!hasDB())	return;
		try {
			statement = connection.createStatement();
			sql = "select * from receivedata";
			ResultSet result = statement.executeQuery(sql);
			while(result.next()){
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> getColumn(){
		return this.column;
	}
	
	private boolean hasDB(){
		if(connection == null)	return false;
		try {
			if(connection.isClosed())	return false;
			else						return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void getColumn(ArrayList<String> column){
		try {
			statement = connection.createStatement();
			sql = "select * from receivedata";
			ResultSet result = statement.executeQuery(sql);
			rsmd = result.getMetaData();
			for(int i = 1; i <= rsmd.getColumnCount(); i++){
				column.add(rsmd.getColumnName(i));
				System.out.print(rsmd.getColumnTypeName(i) + "\t");
				System.out.println(rsmd.getColumnClassName(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setDBInfo(){
		try {
			DBInfo dbInfo = JSON.decode(new FileReader("db_info.json") , DBInfo.class);
			this.host = dbInfo.getHost();
			this.DBName = dbInfo.getDBName();
			this.table = dbInfo.getTable();
			this.user = dbInfo.getUser();
			this.password = dbInfo.getPassword();
			address = "jdbc:mysql://" + host + ":3306/" + DBName;
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
