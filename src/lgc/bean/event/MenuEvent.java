package lgc.bean.event;

/**
 * ����˵��¼�(ָ�������ղ˵�����Ϊ����˵������Ӳ˵�����������ϱ�)
 * @author Administrator
 *
 */
public class MenuEvent extends BaseEvent {

	/*
	 * EventKey
	 * 
	 * 1��EventΪ �� CLICKʱ
	 * �¼�KEYֵ�����Զ���˵��ӿ���KEYֵ��Ӧ
	 * 2��EventΪ �� VIEWʱ
	 * �¼�KEYֵ�����õ���תURL
	 */
	
	private String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	
	
}
