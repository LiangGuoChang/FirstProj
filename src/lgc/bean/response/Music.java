package lgc.bean.response;

/**
 * ������ϢMusicMessage�е�һ��Ԫ��
 * @author lgc
 *
 * @date 2017��6��7�� ����10:07:52
 */
public class Music {

	//���ֱ���(���Ǳ����е�)
	private String Title;
	//��������(���Ǳ����е�)
	private String Description;
	//��������(���Ǳ����е�)
	private String MusicURL;
	//�������������ӣ�WIFI��������ʹ�ø����Ӳ�������(���Ǳ����е�)
	private String HQMusicUrl;
	//����ͼ��ý��id��ͨ���زĹ����еĽӿ��ϴ���ý���ļ����õ���id(�����е�)
	private String ThumbMediaId;
	
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
	public String getMusicURL() {
		return MusicURL;
	}
	public void setMusicURL(String musicURL) {
		MusicURL = musicURL;
	}
	public String getHQMusicUrl() {
		return HQMusicUrl;
	}
	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}
