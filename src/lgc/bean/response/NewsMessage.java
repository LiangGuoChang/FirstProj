package lgc.bean.response;

import java.util.List;

/**
 * ͼ����Ϣ
 * @author lgc
 *
 * @date 2017��6��7�� ����11:00:17
 */
public class NewsMessage extends BaseMessage {

	
	//ͼ����Ϣ����������Ϊ8������
	private int ArticleCount;
	//����ͼ����Ϣ��Ϣ��Ĭ�ϵ�һ��itemΪ��ͼ,ע�⣬���ͼ��������8���򽫻�����Ӧ
	private List<Article> Articles;
	
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<Article> getArticles() {
		return Articles;
	}
	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
	
}
