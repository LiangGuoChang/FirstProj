package lgc.bean.event;

/**
 * ɨ���������ά���¼�
 * 1���û�δ��עʱ��eventΪ  subscribe
 * 2���û��ѹ�עʱ��eventΪ  SCAN
 * @author Administrator
 *
 */
public class QRCodeEvent extends BaseEvent {

	/*EventKey
	 * 
	 * 1��eventΪ  �� subscribe ʱ
	 * �¼�KEYֵ��qrscene_Ϊǰ׺������Ϊ��ά��Ĳ���ֵ
	 * 2��eventΪ  �� SCAN ʱ
	 * �¼�KEYֵ����һ��32λ�޷�����������������ά��ʱ�Ķ�ά��scene_id
	 * 
	 */
	private String EventKey;
	//��ά���ticket����������ȡ��ά��ͼƬ
	private String Ticket;
	
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	
}
