package lgc.bean.menu;

/**
 * scancode_push ɨ�����¼�
 * ɨ�����¼��û������ť��΢�ſͻ��˽�����ɨһɨ���ߣ�
 * ���ɨ���������ʾɨ�����������URL��������URL�����һὫɨ��Ľ�����������ߣ������߿����·���Ϣ��
 * 
 * (ע)��֧��΢��iPhone5.4.1���ϰ汾����Android5.4���ϰ汾��΢���û����ɰ汾΢���û������û�л�Ӧ��������Ҳ�����������յ��¼�����
 * 
 * @author lgc
 *
 * @date 2017��6��9�� ����8:47:23
 */
public class Scancode_Push extends Button {

	//scancode_push
	private String type;
	//rselfmenu_0_1
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
