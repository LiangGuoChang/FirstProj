package lgc.bean.request;

/**
 * ������Ϣ  �û�--->���ں�
 * @author Administrator
 *
 */
public class VoiceMessage extends BaseMessage {

	//������Ϣý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ��ý��
	private String MediaID;
	//������ʽ��amr
	private String Format;
	//����ʶ������UTF8����
	private String Recognition;
	
	public String getMediaID() {
		return MediaID;
	}
	public void setMediaID(String mediaID) {
		MediaID = mediaID;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getRecognition() {
		return Recognition;
	}
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
	
}
