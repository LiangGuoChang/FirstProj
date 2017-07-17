package lgc.bean.request;


/**
 * 文本消息  用户--->公众号
 * @author Administrator
 *
 */
public class TextMessage extends BaseMessage {

	//消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
}
