package lgc.bean.request;


/**
 * ͼƬ��Ϣ �û�--->���ں�
 * @author Administrator
 */
public class ImageMessage extends BaseMessage {

	//ͼƬ���ӣ���ϵͳ����
	private String PicUrl;
	//ͼƬ��Ϣý��id�����Ե��ö�ý���ļ����ؽӿ���ȡ���ݡ�
	private String MediaId;
	
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	
}
