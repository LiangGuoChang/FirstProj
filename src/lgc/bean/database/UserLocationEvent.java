package lgc.bean.database;

/**
 * Զ�����ݿ��� user_location_event ���javabean����
 * @author lgc
 *
 * @date 2017��7��3�� ����5:15:49
 */
public class UserLocationEvent {

	//�û���  openId
	private String fromUserName;
	//��Ϣ����ʱ��
	private long createTime;
	//�û���γ��
	private String latitude;
	//�û��ľ���
	private String longitude;
	//�û�λ�õľ�ȷ��
	private String precision;
	//��γ��ת������û�λ����Ϣ
	private String address;
	
	
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	
}
