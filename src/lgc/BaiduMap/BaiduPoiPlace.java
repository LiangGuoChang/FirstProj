package lgc.BaiduMap;

/**
 * ���ðٶȵ�ͼpoi���ĵ�ַ��Ϣ
 * 
 * @author lgc
 *
 * 2017��7��14�� ����10:23:46
 */
public class BaiduPoiPlace implements Comparable<BaiduPoiPlace>{

	//�ص�����
	private String name;
	//����
	private String lng;
	//ά��
	private String lat;
	//��ϸ��ַ
	private String address;
	//��ϵ�绰
	private String telephone;
	//����
	private int distance;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	@Override
	public int compareTo(BaiduPoiPlace o) {
		return this.distance-o.distance;
	}
	
	
}
