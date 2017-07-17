package lgc.BaiduMap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import it.sauronsoftware.base64.Base64;
import lgc.bean.database.UserLocation;
import lgc.bean.response.Article;
import net.sf.json.JSONObject;

/**
 * �ٶȵ�ͼ������
 * 
 * @author lgc
 *
 * 2017��7��14�� ����10:32:26
 */
public class BaiduMapUtil {

	/**
	 * ���ݹؼ��ʣ�ת����İٶ�����ȥpoi�ܱ�
	 * 
	 * @param query �ؼ��� �磺�Ƶ�
	 * @param lat �ٶ�����ϵά��
	 * @param lng �ٶ�����ϵ����
	 * @param ak �ٶȵ�ͼ���������ak
	 * @return List<BaiduPoiPlace> ����poi�ܱߵ�ַ�б�
	 */
	public static List<BaiduPoiPlace> searchPoiPlace(String query,String lat,String lng,String ak){
		//ƴ�������ַ
		String requestUrl = "http://api.map.baidu.com/place/v2/search?&query=QUERY&location=LNG,LAT&radius=2000&output=xml&scope=2&page_size=10&page_num=0&ak=AK";
		List<BaiduPoiPlace> placeList=null;
		try {
			requestUrl=requestUrl.replace("QUERY", URLEncoder.encode(query,"UTF-8"));
			requestUrl=requestUrl.replace("LAT", lat.trim());//lat.trim()
			requestUrl=requestUrl.replace("LNG", lng.trim());//lng.trim()
			requestUrl=requestUrl.replace("AK", ak);
			
			System.out.println("searchPoiPlace::"+requestUrl);
			
			//����place apiԲ���������
			String respXml=httpRequest(requestUrl);
			//������ȡ����xml��ַ�б�
			placeList=parsePlaceXml(respXml);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			System.out.println("searchPoiPlace::"+e.toString());
		}
		
		return placeList;
	}
	
	/**
	 * ����HTTP����
	 * @param requestUrl �����ַ
	 * @return String ���������ȡ��xml��Ϣ
	 */
	public static String httpRequest(String requestUrl){
		StringBuffer buffer=new StringBuffer();
		URL url;
		try {
			url = new URL(requestUrl);
			HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setDoInput(true);
			httpURLConnection.connect();
			//��ȡ���ص�������ת�����ַ�
			InputStream inputStream=httpURLConnection.getInputStream();
			InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"UTF-8");
			BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
			String str=null;
			while ((str=bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			//�ͷ���Դ
			inputStream.close();
			inputStream=null;
			httpURLConnection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("BaiduMapUtil::"+"\n"+e.toString());
		}
		return buffer.toString();
	}
	
	/**
	 * ����xml����
	 * @param placeXml xml����
	 * @return List<BaiduPoiPlace> ����poi������Ϣ�б�
	 */
	private static List<BaiduPoiPlace> parsePlaceXml(String placeXml){
		List<BaiduPoiPlace> placeList=null;
		try {
			Document document=DocumentHelper.parseText(placeXml);
			//��ȡ���ڵ�
			Element rootElement=document.getRootElement();
			//�Ӹ��ڵ��ȡ<results>
			Element results=rootElement.element("results");
			//��<results>�л�ȡ<result>����
			List<Element> resultList=results.elements("result");
			//�жϼ��ϴ�С
			if (resultList.size() > 0) {
				placeList=new ArrayList<BaiduPoiPlace>();
				//poi�ص�����
				Element nameElement=null;
				//poi��ϸ��ַ
				Element addressElement=null;
				//poi��γ��
				Element locationElement=null;
				//poi�绰��Ϣ
				Element phoneElement=null;
				//poi��չ��Ϣ
				Element detailInfoElement=null;
				//poi����
				Element distanceElement=null;
				//��������
				for (Element element : resultList) {
					nameElement=element.element("name");
					locationElement=element.element("location");
					addressElement=element.element("address");
					phoneElement=element.element("telephone");
					detailInfoElement=element.element("detail_info");
					//����BaiduPoiPlaceʵ��
					BaiduPoiPlace baiduPoiPlace=new BaiduPoiPlace();
					baiduPoiPlace.setName(nameElement.getText());
					baiduPoiPlace.setAddress(addressElement.getText());
					baiduPoiPlace.setLat(locationElement.element("lat").getText());
					baiduPoiPlace.setLng(locationElement.element("lng").getText());
					//��<telephone>Ԫ�ش���ʱ����ȡ�绰��Ϣ
					if (phoneElement != null) {
						baiduPoiPlace.setTelephone(phoneElement.getText());
					}
					//��<detail_info>Ԫ�ش���ʱ�����Ի�ȡ�������Ϣ
					if (detailInfoElement != null) {
						distanceElement=detailInfoElement.element("distance");
						//��<distance>Ԫ�ش���ʱ�����Ի�ȡ����
						if (distanceElement != null) {
							baiduPoiPlace.setDistance(Integer.parseInt(distanceElement.getText()));
						}
					}
					placeList.add(baiduPoiPlace);
				}
				//�������ɽ���Զ����
				Collections.sort(placeList);
			}
			
		} catch (Exception e) {
			System.out.println("BaiduMapUtil-parsePlaceXml::"+"\n"+e.toString());
		}
		return placeList;
	}
	
	/**
	 * ����poi���ĵ�ַ�б���װͼ����Ϣ�б�
	 * 
	 * @param placeList poi���ĵ�ַ�б�
	 * @param bd09Lng ת����İٶ����꾭��
	 * @param bd09Lat ת����İٶ�����ά��
	 * @return  List<Article> ����ͼ���б�
	 */
	public static List<Article> makeArticleList(List<BaiduPoiPlace> placeList,String bd09Lng,String bd09Lat){
		//��Ŀ�ĸ�·��
		String projBasePath="http://newtkwx.ngrok.club/FirstProj/";
		List<Article> articleList=new ArrayList<Article>();
		BaiduPoiPlace baiduPoiPlace=null;
		for (int i=0;i< /*placeList.size()*/ 8;i++) {
			baiduPoiPlace=placeList.get(i);
			Article article=new Article();
			article.setTitle(baiduPoiPlace.getName()+"\n����Լ"+baiduPoiPlace.getDistance()+"��");
			// P1��ʾ�û����͵�λ�ã�����ת���󣩣�p2��ʾ��ǰPOI����λ��
			article.setUrl(String.format(projBasePath+ "route.jsp?p1=%s,%s&p2=%s,%s", bd09Lng,bd09Lat,baiduPoiPlace.getLat(),baiduPoiPlace.getLng()));
			// ������ͼ�ĵ�ͼƬ����Ϊ��ͼ
			if (i == 0)
				article.setPicUrl(projBasePath + "images/poisearch.png");
			else
				article.setPicUrl(projBasePath + "images/navi.png");
			articleList.add(article);
		}
		return articleList;
	}
	
	/**
	 * ���û����͹����ľ�γ��ת���ɰٶ�����ϵ(gcj02--->baidu)
	 * @param lng �û�λ�õľ���
	 * @param lat �û�λ�õ�ά��
	 * @return UserLocation �����û�λ����Ϣ����
	 * 
	 * @˵�� �ٶ�����ת���ӿڷ����磺
	 *  {"error":0,"x":"MjMuMTE1OTMwOTc1Njk2","y":"MTE0LjQyMDIxMjIzMTU2"}
	 */
	
	public static UserLocation convertLatlng(String lng,String lat){
		// �ٶ�����ת���ӿ�
		String convertUrl = "http://api.map.baidu.com/ag/coord/convert?from=2&to=4&x={x}&y={y}";
		convertUrl = convertUrl.replace("{x}", lng);
		convertUrl = convertUrl.replace("{y}", lat);
		UserLocation userLocation=new UserLocation();
		try {
			String jsonConvert=httpRequest(convertUrl);
			JSONObject jsonObject=JSONObject.fromObject(jsonConvert);
			//��ת����ľ�γ�Ƚ���Base64����
			userLocation.setBd09Lng(Base64.decode(jsonObject.getString("x"),"UTF-8").trim());
			userLocation.setBd09Lat(Base64.decode(jsonObject.getString("y"),"UTF-8").trim());
		} catch (Exception e) {
			userLocation=null;
			e.printStackTrace();
		}
		return userLocation;
	}
}
