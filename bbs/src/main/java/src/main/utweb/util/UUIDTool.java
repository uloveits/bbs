package src.main.utweb.util;
import java.util.UUID;  

public class UUIDTool {
	public static String creatUUID () {
		String uuid = UUID.randomUUID().toString(); //获取UUID并转化为String对象  
        uuid = uuid.replace("-", "");  
		return uuid;
	}
}
