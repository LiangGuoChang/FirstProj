package lgc.bean.menu;

/**
 * pic_photo_or_album ���ջ�����ᷢͼ
 * �������ջ�����ᷢͼ�û������ť��΢�ſͻ��˽�����ѡ�������û�ѡ�����ա����ߡ����ֻ����ѡ�񡱡�
 * �û�ѡ����������������̡�
 * 
 * (ע)��֧��΢��iPhone5.4.1���ϰ汾����Android5.4���ϰ汾��΢���û����ɰ汾΢���û������û�л�Ӧ��������Ҳ�����������յ��¼�����
 * 
 * @author lgc
 *
 * @date 2017��6��9�� ����8:59:12
 */
public class Pic_Photo_Or_Album extends Button {

	//pic_photo_or_album
	private String type;
	//rselfmenu_1_1
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
