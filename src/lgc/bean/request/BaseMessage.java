package lgc.bean.request;

/**
 * ��Ϣ����    �û�--->���ں�
 * @author Administrator
 * 
 */

public class BaseMessage {

	//������΢�ź�
	private String ToUserName;
	//���ͷ�΢�ź�(һ��OpenID ���磺oA1HcvwJj9KKuPC6fmRWm8h6Qv4I)
	private String FromUserName;
	//����ʱ��
	private long CreateTime;
	//��Ϣ����(�ı�text/ͼƬimage/����Ϊvoice/��ƵΪvideo/С��ƵΪshortvideo/λ��location/����link/�¼�event)
	private String MsgType;
	//��Ϣid��64λ����
	private long MsgId;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public long getMsgId() {
		return MsgId;
	}
	public void setMsgId(long msgId) {
		MsgId = msgId;
	}
	
	
}
