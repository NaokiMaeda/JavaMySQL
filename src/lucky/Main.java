package lucky;

public class Main {

	public static void main(String[] args) {
		MySQL db = new MySQL();
		db.ConnectionDB();
		db.select();
		db.DisconnectionDB();
	}

}
