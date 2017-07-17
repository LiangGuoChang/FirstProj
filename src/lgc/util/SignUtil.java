package lgc.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * У�鹤����
 * @author lgc
 *
 */
public class SignUtil {
	
	/**
	 * Token���ɿ����߿���������д����������ǩ������Token��ͽӿ�URL�а�����Token���бȶԣ��Ӷ���֤��ȫ�ԣ�
	 * ���������ҽ�Token����Ϊlgc1
	 */
	private final String TOKEN="lgc1";

	/**
	 * ���򷽷�
	 * @param token
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	 public static String sort(String token, String timestamp, String nonce) {
     	String[] strArray = {token, timestamp, nonce};
     	Arrays.sort(strArray);
     	StringBuilder sb = new StringBuilder();
     	for (String str : strArray) {
             sb.append(str);
         }
         return sb.toString();
     }
	 
	 /**
	  * ���ַ�������sha1����
   	  * @param str ��Ҫ���ܵ��ַ���
	  * @return ���ܺ������
	  */
      public static String sha1(String str) {
          try {
              MessageDigest digest = MessageDigest.getInstance("SHA-1");
              digest.update(str.getBytes());
              byte messageDigest[] = digest.digest();
              // Create Hex String
              StringBuffer hexString = new StringBuffer();
              // �ֽ�����ת��Ϊ ʮ������ ��
              for (int i = 0; i < messageDigest.length; i++) {
                  String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                  if (shaHex.length() < 2) {
                      hexString.append(0);
                  }
                  hexString.append(shaHex);
              }
              return hexString.toString();
  
          } catch (NoSuchAlgorithmException e) {
              e.printStackTrace();
          }
         return "";
     }
}
