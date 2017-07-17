package lgc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.mapper.OuterClassMapper;

import lgc.bean.pojo.Token;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * ͨ�ù�����
 * 
 * @author lgc
 *
 * @date 2017��6��9�� ����10:44:58
 */
public class CommonUtil {

//	private static Logger log = LoggerFactory.getLogger(CommonUtil.class);
	
	// access_tokenƾ֤��ȡ��GET����url
	public final static String token_url = 
			"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
	 * ����https���� 
	 * @param requestUrl ����URL��ַ
	 * @param requstMethod ���󷽷�(GET,POST)
	 * @param outPutStr �ύ������
	 * @return ����JSONObject���� 
	 */
	public static JSONObject httpsRequest(String requestUrl,String requstMethod,String outPutStr){
		
		JSONObject jsonObject=null;

		try {
			// ����SSLContext���󣬲�ʹ������ָ�������ι�������ʼ��
			TrustManager[] tm={new MyX509TrustManager()};
			SSLContext sslContext=SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			// ������SSLContext�����еõ�SSLSocketFactory����
			SSLSocketFactory sslf=sslContext.getSocketFactory();
	
			URL url=new URL(requestUrl);
			HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
			connection.setSSLSocketFactory(sslf);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			//��������ʽ
			connection.setRequestMethod(requstMethod);
			//��outPutStr��Ϊ��ʱ���������д����
			if (outPutStr!=null) {
				OutputStream out=connection.getOutputStream();
				// ע������ʽ
				out.write(outPutStr.getBytes("UTF-8"));
				out.close();
			}
			//��ȡ������
			InputStream in=connection.getInputStream();
			//��ȡ������������
			InputStreamReader inReader=new InputStreamReader(in,"UTF-8");//���ϱ����ʽ
			BufferedReader bReader=new BufferedReader(inReader);
			String resultStr=null;
			StringBuffer buffer=new StringBuffer();
			while ((resultStr=bReader.readLine())!=null) {
				buffer.append(resultStr);
			}
			//�ͷ���Դ
			bReader.close();
			inReader.close();
			in.close();
			in=null;
			connection.disconnect();
			
			//����JSONObject����
			jsonObject=JSONObject.fromObject(buffer.toString());
		} catch (IOException e) {
//				e.printStackTrace();
				System.out.println("IOException::"+"\n"+e.getMessage());
		} catch (KeyManagementException e) {
//				e.printStackTrace();
			System.out.println("KeyManagementException::"+"\n"+e.getMessage());
		}catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
			System.out.println("NoSuchAlgorithmException::"+"\n"+e.getMessage());
		} catch (NoSuchProviderException e) {
//			e.printStackTrace();
			System.out.println("NoSuchProviderException::"+"\n"+e.getMessage());
		}
		
		
		return jsonObject;
		
	}
	
	/**
	 * ��ȡƾ֤Token
	 * @param appID �������û�Ψһƾ֤appID
	 * @param appsecret �������û�Ψһƾ֤��Կappsecret
	 * @return
	 */
	public static Token getToken(String appID,String appsecret){
		Token token=null;
		String requestUrl=token_url.replace("APPID", appID).replace("APPSECRET", appsecret);
		//GET��������HTTPS�����ύ����
		JSONObject jsonObject=httpsRequest(requestUrl, "GET", null);
		if (jsonObject!=null) {
			try {
				token=new Token();
				token.setAccess_token(jsonObject.getString("access_token"));
				token.setExpires_in(jsonObject.getInt("expires_in"));
			} catch (JSONException  e) {
//				e.printStackTrace();
				token = null;
				// ��ȡtokenʧ��
//				log.error("��ȡtokenʧ�� errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
				System.out.println("��ȡtokenʧ��");
			}
		}
		
		
		return token;
		
	}
	
	/**
	 * url ���� UTF-8
	 * @param url
	 * @return ���ر����� url
	 */
	public static String urlEncodingUTF8(String url){
		String result=url;
		try {
			result=URLEncoder.encode(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * �������������ж��ļ���չ��
	 * 
	 * @param contentType ��������
	 * @return fileExt �����ļ���׺��
	 */
	public static String getFileExt(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType))
			fileExt = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileExt = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileExt = ".amr";
		else if ("video/mp4".equals(contentType))
			fileExt = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileExt = ".mp4";
		return fileExt;
	}
	
	/**
	 * ��ȡ���uuid
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// ȥ��"-"����
		String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
		return temp;
	}
	
}
