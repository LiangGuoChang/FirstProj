package lgc.bean.database;

/**
 * �ʴ�֪ʶ��
 * @author lgc
 *
 * 2017��7��18�� ����5:58:19
 */
public class Knowledge {

	//�ʴ�֪ʶid
	private int id;
	//����
	private String question;
	//�ش�
	private String answer;
	//���ͣ�1-��ͨ���죬2-Ц����3-������
	private int category;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
}
