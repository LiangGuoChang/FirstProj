package lgc.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * ������֤�����ι�����
 * 
 * @author lgc
 *
 * @date 2017��6��8�� ����3:53:44
 */

public class MyX509TrustManager implements X509TrustManager {

	/**
	 * ���ͻ���֤��
	 */
	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub

	}

	/**
	 * ��������֤��
	 */
	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub

	}

	/**
	 * ���������ε�X509����
	 */
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}


}
