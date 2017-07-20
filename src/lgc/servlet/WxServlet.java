package lgc.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lgc.service.ChatService;
import lgc.service.MessageService;
import lgc.util.SignUtil;

public class WxServlet extends HttpServlet {

	/**
	 * Token���ɿ����߿���������д����������ǩ������Token��ͽӿ�URL�а�����Token���бȶԣ��Ӷ���֤��ȫ�ԣ�
	 * ���������ҽ�Token����Ϊlgc1
	 */
	private final String TOKEN="lgc1";
	
	
	/**
	 * У��ʹ�����Ϣ
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("����doPost");
		
		// ��������Ӧ�ı��������ΪUTF-8����ֹ��������
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		/*// ����΢�ŷ�������������ʱ���ݹ�����4������ signature timestamp nonce echostr
		String signature=req.getParameter("signature");//΢�ż���ǩ��signature����˿�������д��token�����������е�timestamp������nonce����
		String timestamp=req.getParameter("timestamp");//ʱ���
		String nonce=req.getParameter("nonce");//�����
		String echostr=req.getParameter("echostr");//����ַ���
		
		//���� TOKEN timestamp nonce
		String sortStr=SignUtil.sort(TOKEN, timestamp, nonce);
		
		//sha1����
		String mySignature=SignUtil.sha1(sortStr);
		
		//У��ǩ��
		if (mySignature != null && mySignature != "" && mySignature.equals(signature)) {
			
			PrintWriter writer =resp.getWriter();
			try {
				String respXml= MessageService.processRequest(req);
				writer.write(respXml);
				
				System.out.println("���ںŻظ�::"+"\n"+respXml);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			writer.close();
			writer=null;
		} else {
			System.out.println("У��ʧ�ܡ�");
		}*/
		
		
		
		PrintWriter writer =resp.getWriter();
		try {
			String respXml= MessageService.processRequest(req);
			writer.write(respXml);
			
			System.out.println("���ںŻظ�::"+"\n"+respXml);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		writer.close();
		writer=null;
		
	}
	
	
	/**
	 * ������֤�����������������ǣ�������֤ʱ��get��������ʱ����post����
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("��ʼУ��");
		
		// ����΢�ŷ�������������ʱ���ݹ�����4������ signature timestamp nonce echostr
		String signature=req.getParameter("signature");//΢�ż���ǩ��signature����˿�������д��token�����������е�timestamp������nonce����
		String timestamp=req.getParameter("timestamp");//ʱ���
		String nonce=req.getParameter("nonce");//�����
		String echostr=req.getParameter("echostr");//����ַ���
		
		System.out.println("echostr::"+echostr);
		
		
		//���� TOKEN timestamp nonce
		String sortStr=SignUtil.sort(TOKEN, timestamp, nonce);
		
		//sha1����
		String mySignature=SignUtil.sha1(sortStr);
		
		//У��ǩ��
		PrintWriter writer =resp.getWriter();
		if (mySignature != null && mySignature != "" && mySignature.equals(signature)) {
          System.out.println("У��ɹ���");
          //�������ɹ����echostr��΢�ŷ��������յ���������Ż�ȷ�ϼ�����ɡ�
          writer.write(echostr);
		} else {
          System.out.println("У��ʧ�ܡ�");
		}
		
		writer.close();
		writer=null;
		
	}
	
	@Override
	public void init() throws ServletException {
		File indexDir=new File(ChatService.getIndexDir());
		//�������Ŀ¼�����ڣ��򴴽�Ŀ¼
		if (!indexDir.exists()) {
			ChatService.createIndex();
		}
	}
	
	
}
