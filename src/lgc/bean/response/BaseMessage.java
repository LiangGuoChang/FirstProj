package lgc.bean.response;

/**
 * ��Ϣ����    ���ں�--->�û�
 * @author lgc
 *
 * @date 2017��6��7�� ����9:03:32
 */
public class BaseMessage {

	//���շ�΢�ź�(һ��OpenID ���磺oA1HcvwJj9KKuPC6fmRWm8h6Qv4I)
	private String ToUserName;
	//������΢�ź�
	private String FromUserName;
	//����ʱ��
	private long CreateTime;
	//��Ϣ����(�ı�text/ͼƬimage/����Ϊvoice/��ƵΪvideo/����music/ͼ��news)
	private String MsgType;
	
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

}
