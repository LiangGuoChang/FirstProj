package lgc.bean.pojo;

/**
 * ���ں�ƾ֤
 * ����HTTPS����֮��΢�ŷ�����������ƾ֤��json����
 * �磺{"access_token":"ACCESS_TOKEN","expires_in":7200}
 * @author lgc
 *
 * @date 2017��6��9�� ����11:44:18
 */
public class Token {

	//��ȡ����ƾ֤
	private String access_token;
	//ƾ֤��Чʱ�䣬��λ����
	private int expires_in;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	
}
