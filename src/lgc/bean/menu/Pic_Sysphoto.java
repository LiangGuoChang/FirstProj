package lgc.bean.menu;

/**
 * pic_sysphoto ϵͳ���շ�ͼ
 * ����ϵͳ���շ�ͼ�û������ť��΢�ſͻ��˽�����ϵͳ�����������ղ�����
 * �Ὣ�������Ƭ���͸������ߣ��������¼��������ߣ�ͬʱ����ϵͳ����������ܻ��յ��������·�����Ϣ��
 * 
 * (ע)��֧��΢��iPhone5.4.1���ϰ汾����Android5.4���ϰ汾��΢���û����ɰ汾΢���û������û�л�Ӧ��������Ҳ�����������յ��¼�����
 * 
 * @author lgc
 *
 * @date 2017��6��9�� ����8:55:05
 */
public class Pic_Sysphoto extends Button{

	//pic_sysphoto
	private String type;
	//rselfmenu_1_0
	private String key;
	private Button[] sub_button;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Button[] getSub_button() {
		return sub_button;
	}
	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
	
	
}
