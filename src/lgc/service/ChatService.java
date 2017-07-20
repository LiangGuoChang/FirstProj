package lgc.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import lgc.bean.database.Knowledge;
import lgc.util.DataBaseUtil;
import oracle.net.aso.d;

/**
 * ���������
 * 
 * @author lgc
 *
 * 2017��7��19�� ����2:33:59
 */
public class ChatService {

	/**
	 * �õ�����·��
	 * @return String
	 */
	public static String getIndexDir(){
		//�õ�class�ļ����ڵ�·��
		String classPath=ChatService.class.getResource("/").getPath();
		//��·��classPath�е�%20�滻Ϊ�ո�
		classPath=classPath.replaceAll("%20", " ");
		//�����洢λ��
		System.out.println(classPath+"index/");
		return classPath+"index/";
	}
	
	/**
	 * ��������
	 */
	public static void createIndex(){
		//ȡ���ʴ�֪ʶ��������ʴ�
		DataBaseUtil baseUtil=new DataBaseUtil();
		List<Knowledge> knowledges=baseUtil.findAllKnowledge();
		Directory directory=null;
		IndexWriter indexWriter=null;
		try {
			directory=FSDirectory.open(new File(getIndexDir()));
			IndexWriterConfig config=new IndexWriterConfig(Version.LUCENE_46, new IKAnalyzer(true));
			indexWriter=new IndexWriter(directory, config);
			Document document=null;
			//�����ʴ�֪ʶ�⣬��������
			for (Knowledge knowledge : knowledges) {
				document=new Document();
				//��question���зִʴ洢
				document.add(new TextField("question", knowledge.getQuestion(),Store.YES));
				//id��answer��category���ִʴ洢
				document.add(new IntField("id", knowledge.getId(), Store.YES));
				document.add(new StringField("answer", knowledge.getAnswer(), Store.YES));
				document.add(new IntField("category", knowledge.getCategory(), Store.YES));
				indexWriter.addDocument(document);
			}
			indexWriter.close();
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��������������ļ��м�����
	 * @param question
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private static Knowledge searchIndex(String question){
		Knowledge knowledge=null;
		try {
			Directory directory=FSDirectory.open(new File(getIndexDir()));
			IndexReader indexReader=IndexReader.open(directory);
			IndexSearcher indexSearcher=new IndexSearcher(indexReader);
			//ʹ�ò�ѯ����������Query
			QueryParser parser=new QueryParser(Version.LUCENE_46, "question", new IKAnalyzer(true));
			Query query=parser.parse(QueryParser.escape(question));
			//�����÷���ߵ��ĵ�
			TopDocs docs=indexSearcher.search(query, 1);
			if (docs.totalHits>0) {
				knowledge=new Knowledge();
				ScoreDoc[] scoreDocs=docs.scoreDocs;
				for (ScoreDoc scoreDoc : scoreDocs) {
					Document document=indexSearcher.doc(scoreDoc.doc);
					knowledge.setId(document.getField("id").numericValue().intValue());
					knowledge.setQuestion(document.get("question"));
					knowledge.setAnswer(document.get("answer"));
					knowledge.setCategory(document.getField("category").numericValue().intValue());
				}
			}
			indexReader.close();
			directory.close();
		} catch (Exception e) {
			knowledge=null;
			e.printStackTrace();
		}
		return knowledge;
	}
	
	/**
	 * ���췽��������question����answer
	 * @param openId �û�id
	 * @param createTime ����ʱ��
	 * @param question �û����͵�����
	 * @return
	 */
	public static String chat(String openId,String createTime,String question){
		String answer=null;
		int chatCategory=0;
		Knowledge knowledge=searchIndex(question);
		//�ҵ�ƥ����
		if (null != knowledge) {
			switch (knowledge.getCategory()) {
			case 1://��ͨ�Ի�
				answer=knowledge.getAnswer();
				//�����Ϊ�գ����ʴ�֪ʶ�������ѡ��һ��
				if ("".equals(answer)) {
					answer=new DataBaseUtil().getOneKnowledgeSub(knowledge.getId());
				}
				chatCategory=1;
				break;
				
			case 2://Ц��
				answer=new DataBaseUtil().getJoke();
				chatCategory=2;
				break;
				
			case 3://������
				//�ж���һ�ε���������
				int lastCategory=new DataBaseUtil().getLastCategory(openId);
				//��Ц�����������Ц��
				if (lastCategory==2) {
					answer=new DataBaseUtil().getJoke();
					chatCategory=2;
				}else {
					answer=knowledge.getAnswer();
					chatCategory=knowledge.getCategory();
				}
				break;
				
			}
		}else {
			//δ�ҵ�ƥ����
			answer=getDefaultAnswer();
			chatCategory=0;
		}
		//���������¼
		new DataBaseUtil().saveChatLog(openId, createTime, question, answer, chatCategory);
		return answer;
	}
	
	
	/**
	 * �����ȡһ��Ĭ�ϵĴ�
	 * 
	 * @return
	 */
	private static String getDefaultAnswer() {
		String []answer = {
			"Ҫ�������ĵ��ģ�",
			"�����㵽����˵ʲô�أ�",
			"û��������˵�ģ��ܷ񻻸�˵����",
			"��Ȼ�����������˼������ȴ������ȥ����",
			"������һͷ��ˮ�����µ�֪ʶ����Ԩ��ѽ��Ĥ��~",
			"��������������˵ʲô��Ҫ���㻻�ֱ�﷽ʽ��Σ�",
			"������Сѧ������������ʦ�̵ģ���������е�����Ŷ",
			"������仯̫�죬�����Ҳ����вţ�Ϊ����˵���Ҳ����ף�"
		};
		return answer[getRandomNumber(answer.length)];
	}

	/**
	 * ������� 0~length-1 ֮���ĳ��ֵ
	 * 
	 * @return int
	 */
	private static int getRandomNumber(int length) {
		Random random = new Random();
		return random.nextInt(length);
	}
	
}
