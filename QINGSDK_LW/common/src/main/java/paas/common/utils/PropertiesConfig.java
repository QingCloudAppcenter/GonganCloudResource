package paas.common.utils;



import javax.annotation.Resource;
import java.io.*;
import java.util.Properties;

/**
 * Java读写修改Property文件
 * @author xiewanzhi
 * @date 2011-4-7上午09:19:03
 * @version 1.0
 */
public class PropertiesConfig {

	private static String fileName = "src/main/resources/config.properties";
	/**
	 * 根据KEY，读取文件对应的值
	 * @param key 键
	 * @return key对应的值
	 */
	public static String readData(String key) {
		Properties props = new Properties();
		try {
			InputStream  inputStream = new FileInputStream(fileName);

			//InputStream in = new BufferedInputStream(resource.getInputStream());
			props.load(inputStream);
			inputStream.close();
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 修改或添加键值对 如果key存在，修改, 反之，添加。
	 * @param key 键
	 * @param value 键对应的值
	 */
	public static void writeData(String key, String value) {

		Properties prop = new Properties();
		try {
			//InputStream  inputStream = PropertiesConfig.class.getResourceAsStream("/config.properties");

			InputStream fis = new FileInputStream(fileName);
			prop.load(fis);
			//一定要在修改值之前关闭fis
			fis.close();

			OutputStream fos = new FileOutputStream(fileName);
			prop.setProperty(key, value);
			//保存，并加入注释
			prop.store(fos, "Update '" + key + "' value");
			fos.close();
		} catch (IOException e) {
			System.err.println("Visit  for updating " + value + " value error");
		}
	}

	/**
	 * 测试demo
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println(PropertiesConfig.readData("dlp_jdbc.properties", "db_port"));
		//System.out.println(PropertiesConfig.readData("dlp_jdbc.properties", "db_port"));
		System.out.println(PropertiesConfig.readData( "protocol"));
		PropertiesConfig.writeData("db_port", "test..");
	}
}
