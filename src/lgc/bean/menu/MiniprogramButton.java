package lgc.bean.menu;

/**
 * ��ת��С����ť
 * @author lgc
 *
 * @date 2017��6��8�� ����6:49:50
 */
public class MiniprogramButton extends Button {

	private String type;
	//��ҳ���ӣ��û�����˵��ɴ����ӣ�������1024�ֽڡ�typeΪminiprogramʱ����֧��С������ϰ汾�ͻ��˽��򿪱�url��
	private String url;
	//С�����appid������֤���ںſ����ã�
	private String appid;
	//С�����ҳ��·��
	private String pagepath;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPagepath() {
		return pagepath;
	}
	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}
	
}
