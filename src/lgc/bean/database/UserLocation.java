package lgc.bean.database;

/**
 * 
 * �û����͵ĵ���λ����Ϣ��
 * @author lgc
 *
 * 2017��7��14�� ����9:05:22
 */
public class UserLocation {

	//�û���  openId
	private String openId;
	//�û����͹����ľ���
	private String lng;
	//�û����͹�����ά��
	private String lat;
	//ת��Ϊ�ٶ�����ϵ����
	private String bd09Lng;
	//ת��Ϊ�ٶ�����ϵά��
	private String bd09Lat;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getBd09Lng() {
		return bd09Lng;
	}
	public void setBd09Lng(String bd09Lng) {
		this.bd09Lng = bd09Lng;
	}
	public String getBd09Lat() {
		return bd09Lat;
	}
	public void setBd09Lat(String bd09Lat) {
		this.bd09Lat = bd09Lat;
	}
	
	
	
	
}
