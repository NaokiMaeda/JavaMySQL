package lucky;

public class DBInfo {
	private String host;
	private String DBName;
	private String table;
	private String user;
	private String password;
	
	public DBInfo(){
		
	}
	
	public void setHost(String host){
		this.host = host;
	}
	
	public void setDBName(String DBName){
		this.DBName = DBName;
	}
	
	public void setTable(String table){
		this.table = table;
	}
	
	public void setUser(String user){
		this.user = user;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getHost(){
		return this.host;
	}
	
	public String getDBName(){
		return this.DBName;
	}
	
	public String getTable(){
		return this.table;
	}
	
	public String getUser(){
		return this.user;
	}
	
	public String getPassword(){
		return this.password;
	}

}
