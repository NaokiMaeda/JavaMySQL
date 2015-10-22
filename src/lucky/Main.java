package lucky;

import java.util.Date;
import java.util.HashMap;

public class Main {
	
	public static void main(String[] args) {
		MySQL db = new MySQL("db_info.json");
		db.ConnectionDB();

		HashMap<String , Object> data = new HashMap<>();
		data.put("time" , new Date());
		data.put("format", "raw");
		data.put("freq", 38);
		data.put("data", new byte[] {0x63});
		data.put("device", "Device1");
		//db.insert(data);
		db.select();
		db.DisconnectionDB();
	}

}
