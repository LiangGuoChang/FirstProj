package lgc.bean.pojo;

/**
 * ������ʱ��������ά��ʱ�����صĶ�ά����Ϣ
 * 
 * @author lgc
 *
 * @date 2017��6��28�� ����10:11:57
 */
public class WeiXinTemporaryQRCode {
	
	//��ȡ�Ķ�ά��ticket��ƾ���ticket��������Чʱ���ڻ�ȡ��ά�롣
	private String ticket;
	//�ö�ά����Чʱ�䣬����Ϊ��λ�� ��󲻳���2592000����30�죩��
	private int expireSeconds;
	//��ά��ͼƬ������ĵ�ַ�������߿ɸ��ݸõ�ַ����������Ҫ�Ķ�ά��ͼƬ
	private String url;
	
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpireSeconds() {
		return expireSeconds;
	}
	public void setExpireSeconds(int expireSeconds) {
		this.expireSeconds = expireSeconds;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
