package lgc.bean.pojo;

/**
 * ΢����ʱý���ļ�(�ز�)
 * 
 * @author lgc
 *
 * @date 2017��7��6�� ����9:57:22
 */
public class WeiXinMedia {

	//ý���ļ����ͣ��ֱ���ͼƬ��image����������voice������Ƶ��video��������ͼ��thumb����Ҫ������Ƶ�����ָ�ʽ������ͼ��
	private String mediaType;
	//ý���ļ�id
	private String mediaId;
	//ý���ļ�����ʱ��
	private int createAt;
	
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public int getCreateAt() {
		return createAt;
	}
	public void setCreateAt(int createAt) {
		this.createAt = createAt;
	}
	
	public String getWeixinMedia(){
		return "mediaType::"+getMediaType()+"\n"+"mediaId::"+getMediaId()+"\n"+"createAt::"+getCreateAt();
	}
	
}
