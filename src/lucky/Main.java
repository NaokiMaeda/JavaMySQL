package lucky;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("読み込み成功");
		}catch (ClassNotFoundException e){
		}catch (Exception e){
		}
		MySQL db = new MySQL("db_info.json");
		db.ConnectionDB();
		
		ArrayList<String> list = new ArrayList<>();
		list.add("age");
		db.select(list);
		db.DisconnectionDB();
	}

}
