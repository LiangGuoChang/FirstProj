package lgc.bean.menu;

/**
 * scancode_waitmsg ɨ�����ʾ
 * ɨ�����¼��ҵ�������Ϣ�����С���ʾ���û������ť��΢�ſͻ��˽�����ɨһɨ���ߣ�
 * ���ɨ������󣬽�ɨ��Ľ�����������ߣ�ͬʱ����ɨһɨ���ߣ�
 * Ȼ�󵯳�����Ϣ�����С���ʾ�������ܻ��յ��������·�����Ϣ��
 * 
 * (ע)��֧��΢��iPhone5.4.1���ϰ汾����Android5.4���ϰ汾��΢���û����ɰ汾΢���û������û�л�Ӧ��������Ҳ�����������յ��¼�����
 * 
 * @author lgc
 *
 * @date 2017��6��9�� ����8:39:42
 */
public class Scancode_Waitmsg extends Button {

	//scancode_waitmsg
	private String type;
	//rselfmenu_0_0
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
