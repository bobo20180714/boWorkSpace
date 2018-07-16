package commom;

public class ObjKey { // 对象索引类
	private int ID;
	private String Name;

	public ObjKey() {
		ID = 0;
		Name = "";
	}

	public ObjKey(int operatorID, String operatorName) {
		ID = operatorID;
		Name = operatorName.toUpperCase(); // 统一转换成大写字母
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
}
