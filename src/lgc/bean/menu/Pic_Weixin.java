package lgc.bean.menu;

/**
 * pic_weixin ΢����ᷢͼ
 * ����΢����ᷢͼ���û������ť��΢�ſͻ��˽�����΢����ᣬ���ѡ�������
 * ��ѡ�����Ƭ���͸������ߵķ��������������¼��������ߣ�ͬʱ������ᣬ�����ܻ��յ��������·�����Ϣ��
 * 
 * (ע)��֧��΢��iPhone5.4.1���ϰ汾����Android5.4���ϰ汾��΢���û����ɰ汾΢���û������û�л�Ӧ��������Ҳ�����������յ��¼�����
 * 
 * @author lgc
 *
 * @date 2017��6��9�� ����9:02:58
 */
public class Pic_Weixin extends Button {

	//pic_weixin
	private String type;
	//rselfmenu_1_2
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
