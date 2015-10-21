package lucky;

import java.sql.Timestamp;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		MySQL db = new MySQL();
		db.ConnectionDB();

		HashMap<String , Object> data = new HashMap<>();
		data.put("time" , new Timestamp(System.currentTimeMillis()));
		data.put("format", "raw");
		data.put("freq", 38);
		data.put("data", new byte[] {0x63});
		data.put("device", "Device1");
		db.insert(data);
		db.DisconnectionDB();
	}

}
