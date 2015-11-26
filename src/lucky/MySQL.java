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
	private	Statement			statement;
	private	PreparedStatement	preparedStatement;
	private	String				sql;
	private	StringBuilder		columnBuilder;
	private	StringBuilder		valueBuilder;
	
	//DBデータ
	private ResultSetMetaData					rsmd;
	private	ArrayList<String>					column;
	private	ArrayList<HashMap<String , Object>>	recodeList;
	
	public MySQL(String configFile){
		this.column = new ArrayList<>();
		setDBInfo(configFile);
	}
	
	public void ConnectionDB(){
		try {
			connection = DriverManager.getConnection(address , user , password);
			setColumn(column);
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
			
			for(int i = 1; i < column.size(); i++){		//Index 0は,idなのでinsert対象から除外
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
			
			preparedStatement = connection.prepareStatement(sql);
			for(int i = 1; i < column.size(); i++){
				preparedStatement.setObject(i, data.get(column.get(i)));
			}
			preparedStatement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void select(){
		if(!hasDB())	return;
		try {
			statement = connection.createStatement();
			sql = "select * from " + table;
			ResultSet result = statement.executeQuery(sql);
			recodeList = new ArrayList<>();
			while(result.next()){
				HashMap<String , Object> recode = new HashMap<>();
				for(int i = 0; i < column.size(); i++){
					recode.put(column.get(i) , result.getObject(column.get(i)));
					System.out.println(column.get(i) + result.getObject(i + 1));
				}
				recodeList.add(recode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<HashMap<String , Object>> select(ArrayList<String> select){
		if(!hasDB())	return null;
		try {
			statement = connection.createStatement();
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("select ");
			for(int i = 0; i < select.size(); i++){
				stringBuilder.append(select.get(i));
				stringBuilder.append(", ");
			}
			stringBuilder.delete(stringBuilder.length() -2 , stringBuilder.length());
			stringBuilder.append(" from ");
			stringBuilder.append(table);
			
			this.sql = stringBuilder.toString();
			
			ResultSet result = statement.executeQuery(this.sql);
			recodeList = new ArrayList<>();
			while(result.next()){
				HashMap<String , Object> recode = new HashMap<>();
				for(int i = 0; i < select.size(); i++){
					recode.put(select.get(i) , result.getObject(select.get(i)));
				}
				recodeList.add(recode);
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recodeList;
	}
	
	public void delete(String sql){
		
	}
	
	public void deleteAll(){
		if(!hasDB())	return;
		try {
			statement = connection.createStatement();
			sql = "truncate table " + table;
			statement.executeUpdate(sql);
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
	
	private void setColumn(ArrayList<String> column){
		try {
			statement = connection.createStatement();
			sql = "select * from " + table;
			ResultSet result = statement.executeQuery(sql);
			rsmd = result.getMetaData();
			for(int i = 1; i <= rsmd.getColumnCount(); i++){
				column.add(rsmd.getColumnName(i));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void setDBInfo(String configFile){
		try {
			DBInfo dbInfo = JSON.decode(new FileReader(configFile) , DBInfo.class);
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
