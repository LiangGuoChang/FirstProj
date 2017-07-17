package lgc.bean.response;

/**
 * 图片消息
 * @author lgc
 *
 * @date 2017年6月7日 上午9:45:48
 */
public class ImageMessage extends BaseMessage {

	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		this.Image = image;
	}
	
}
