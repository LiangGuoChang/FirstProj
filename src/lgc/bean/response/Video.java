package lgc.bean.response;

/**
 * ��Ƶ��ϢVideoMessage�е�һ��Ԫ��
 * @author lgc
 *
 * @date 2017��6��7�� ����9:58:44
 */
public class Video {

	//ͨ���زĹ����еĽӿ��ϴ���ý���ļ����õ���id
	private String MediaId;
	//��Ƶ��Ϣ�ı���(���Ǳ����е�)
	private String Title;
	//��Ƶ��Ϣ������(���Ǳ����е�)
	private String Description;
	
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
}
