package lgc.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lgc.bean.pojo.SNSUserInfo;
import lgc.bean.pojo.WeiXinGroups;
import lgc.bean.pojo.WeiXinMedia;
import lgc.bean.pojo.WeiXinOauth2Token;
import lgc.bean.pojo.WeiXinPermanentQRCode;
import lgc.bean.pojo.WeiXinTemporaryQRCode;
import lgc.bean.pojo.WeiXinUserInfo;
import lgc.bean.pojo.WeiXinUserList;
import lgc.bean.response.Article;
import lgc.bean.response.Music;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 高级接口类
 * @author lgc
 *
 * @date 2017年6月26日 下午2:35:52
 */
public class AdvancedUtil {

//	private static Logger log = LoggerFactory.getLogger(AdvancedUtil.class);
	
	/**
	 * 组装文本客服消息
	 * 
	 * @param openId 消息发送的对象
	 * @param content 文本内容
	 * @return
	 */
	public static String makeTextCustomMessage(String openId,String content){
		content=content.replace("\"", "\\\"");
		String jsonMsg="{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
		return String.format(jsonMsg, openId,content);
	}
	
	/**
	 * 组装图片客服消息
	 * 
	 * @param openId 消息发送对象
	 * @param mediaId 媒体文件id
	 * @return
	 */
	public static String makeImageCustomMessage(String openId, String mediaId) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, mediaId);
	}
	
	/**
	 * 组装语音客服消息
	 * 
	 * @param openId 消息发送对象
	 * @param mediaId 媒体文件id
	 * @return
	 */
	public static String makeVoiceCustomMessage(String openId, String mediaId) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, mediaId);
	}
	
	/**
	 * 组装视频客服消息
	 * 
	 * @param openId 消息发送对象
	 * @param mediaId 媒体文件id
	 * @param thumbMediaId 视频消息缩略图的媒体id
	 * @return
	 */
	public static String makeVideoCustomMessage(String openId, String mediaId, String thumbMediaId) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"video\",\"video\":{\"media_id\":\"%s\",\"thumb_media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, mediaId, thumbMediaId);
	}
	
	/**
	 * 组装音乐客服消息
	 * 
	 * @param openId 消息发送对象
	 * @param music 音乐对象
	 * @return
	 */
	public static String makeMusicCustomMessage(String openId, Music music) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"music\",\"music\":%s}";
		jsonMsg = String.format(jsonMsg, openId, JSONObject.fromObject(music).toString());
		// 将jsonMsg中的thumbmediaid替换为thumb_media_id
		jsonMsg = jsonMsg.replace("thumbmediaid", "thumb_media_id");
		return jsonMsg;
	}
	
	/**
	 * 组装图文客服消息      (点击跳转到外链)
	 * 
	 * @param openId 消息发送对象
	 * @param articleList 图文消息列表
	 * @return
	 */
	public static String makeNewsCustomMessage(String openId, List<Article> articleList) {
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
		jsonMsg = String.format(jsonMsg, openId, JSONArray.fromObject(articleList).toString().replaceAll("\"", "\\\""));
		// 将jsonMsg中的picUrl替换为picurl
		jsonMsg = jsonMsg.replace("picUrl", "picurl");
		return jsonMsg;
	}
	
	/**
	 * 组装图文客服消息  (跳转到图文页面)
	 * 
	 * @param openId  消息发送对象
	 * @param mediaId 媒体文件id
	 * @return
	 */
	public static String makeMpNewsCustomMessage(String openId,String mediaId){
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"mpnews\",\"mpnews\":{\"media_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, mediaId);
	}
	
	/**
	 * 组装卡券客服消息
	 * 
	 * @param openId  消息发送对象
	 * @param cardId  卡卷id
	 * @return
	 */
	public static String makeWxCardCustomMessage(String openId,String cardId){
		String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"wxcard\",\"wxcard\":{\"card_id\":\"%s\"}}";
		return String.format(jsonMsg, openId, cardId);
	}
	
	/**
	 * 发送客服消息
	 * 
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg json格式的客服消息（包括touser、msgtype和消息内容）
	 * @return true | false
	 */
	public static boolean sendCustomMessage(String accessToken, String jsonMsg) {
//		log.info("消息内容：{}", jsonMsg);
		boolean result = false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 发送客服消息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);

		if (null != jsonObject) {
			int errorCode = jsonObject.getInt("errcode");
			String errorMsg = jsonObject.getString("errmsg");
			if (0 == errorCode) {
				result = true;
//				log.info("客服消息发送成功 errcode:{} errmsg:{}", errorCode, errorMsg);
				System.out.println("客服消息发送成功 errcode::"+errorCode+"\n"+"errorMsg::"+ errorMsg);
			} else {
//				log.error("客服消息发送失败 errcode:{} errmsg:{}", errorCode, errorMsg);
				System.out.println("客服消息发送成功 errcode::"+errorCode+"\n"+"errorMsg::"+ errorMsg);
			}
		}

		return result;
	}
	
	/**
	 * 获取网页授权凭证
	 * 
	 * @param appId  公众号的 appID
	 * @param appSecret 公众号的 appsecret
	 * @param code 用户同意授权，获取的code
	 * @return WeiXinOauth2Token  返回网页授权信息类实例
	 */
	public static WeiXinOauth2Token getOauth2AccessToken(String appId,String appSecret,String code){
		WeiXinOauth2Token wot=null;
		//拼接请求网址
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl=requestUrl.replace("APPID", appId);
		requestUrl=requestUrl.replace("SECRET", appSecret);
		requestUrl=requestUrl.replace("CODE", code);
		//获取网页授权凭证 
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (jsonObject != null) {
			try {
				wot=new WeiXinOauth2Token();
				wot.setAccessToken(jsonObject.getString("access_token"));
				wot.setExpiresIn(jsonObject.getInt("expires_in"));
				wot.setOpenId(jsonObject.getString("openid"));
				wot.setRefreshToken(jsonObject.getString("refresh_token"));
				wot.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wot=null;
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				System.out.println("获取网页授权凭证失败"+"\n"+" errcode::"+errcode+"\n"+"errorMsg::"+ errmsg);
			}
			
		}
		
		return wot;
	}
	
	/**
	 * 刷新网页授权凭证
	 *  
	 * @param appId 公众号的 appID
	 * @param refreshToken  公众号的 appsecret
	 * @return
	 */
	public static WeiXinOauth2Token refreshOauth2AccessToken(String appId,String refreshToken){
		WeiXinOauth2Token wot=null;
		//拼接请求网址 
		String requestUrl="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		requestUrl=requestUrl.replace("APPID", appId);
		requestUrl=requestUrl.replace("REFRESH_TOKEN", refreshToken);
		//刷新网页授权凭证 
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (jsonObject != null) {
			try {
				wot=new WeiXinOauth2Token();
				wot.setAccessToken(jsonObject.getString("access_token"));
				wot.setExpiresIn(jsonObject.getInt("expires_in"));
				wot.setOpenId(jsonObject.getString("openid"));
				wot.setRefreshToken(jsonObject.getString("refresh_token"));
				wot.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				wot=null;
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				System.out.println("刷新网页授权凭证失败"+"\n"+" errcode::"+errcode+"\n"+"errorMsg::"+ errmsg);
			}
			
		}
		
		return wot;
	}
	
	/**
	 * 通过网页授权拉取用户信息
	 * 
	 * @param accessToken  网页授权接口调用凭证
	 * @param openId 用户的唯一标识
	 * @param language 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 * @return SNSUserInfo 返回用户实例对象
	 */
	public static SNSUserInfo  getSNSUserInfo(String accessToken,String openId,String language){
		SNSUserInfo user=null;
		//拼接请求网址      lang 表示返回国家地区语音版本   zh_CN 简体中文 ，zh_TW 繁体 中文 ，en 英语
		String requestUrl="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
		requestUrl=requestUrl.replace("OPENID", openId);
		requestUrl=requestUrl.replace("zh_CN", language);
		//获取用户信息
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (jsonObject !=null) {
			try {
				user=new SNSUserInfo();
				user.setOpenId(jsonObject.getString("openid"));
				user.setNickName(jsonObject.getString("nickname"));
				user.setSex(jsonObject.getInt("sex"));
				user.setProvince(jsonObject.getString("province"));
				user.setCity(jsonObject.getString("city"));
				user.setCountry(jsonObject.getString("country"));
				user.setHeadImgUrl(jsonObject.getString("headimgurl"));
				user.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"),List.class));
			} catch (Exception e) {
				user=null;
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				System.out.println("刷新网页授权凭证失败"+"\n"+" errcode::"+errcode+"\n"+"errorMsg::"+ errmsg);
			}
		}
		
		return user;
	}
	
	/**
	 * 创建临时带参数二维码
	 * 
	 * @param accessToken  接口访问凭证 (CommonUtil.getToken)
	 * @param expireSeconds  该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
	 * @param sceneId  场景值ID，临时二维码时为32位非0整型
	 * @return WeiXinQRCode对象实例
	 */
	public static WeiXinTemporaryQRCode createTemporaryQRCode(String accessToken,int expireSeconds,int sceneId){
		WeiXinTemporaryQRCode weiXinQRCode=null;
		//拼接请求网址
		String requestUrl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
		requestUrl=requestUrl.replace("TOKENPOST", accessToken);
		//需要提交参数
		String jsonMsg="{\"expire_seconds\": %d, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
		//获取二维码信息
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, expireSeconds,sceneId));
		if (jsonObject != null) {
			try {
				weiXinQRCode=new WeiXinTemporaryQRCode();
				weiXinQRCode.setTicket(jsonObject.getString("ticket"));
				weiXinQRCode.setExpireSeconds(jsonObject.getInt("expire_seconds"));
				weiXinQRCode.setUrl(jsonObject.getString("url"));
				
				System.out.println("创建临时带参数二维码成功！");
				System.out.println(jsonObject.toString());
				
			} catch (Exception e) {
				weiXinQRCode=null;
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				System.out.println("创建临时带参数二维码失败"+"\n"+" errcode::"+errcode+"\n"+"errorMsg::"+ errmsg);
			}
		}
		return weiXinQRCode;
		
	}
	
	/**
	 * 创建永久带参数二维码
	 * sceneId 场景值ID为  int 类型
	 * @param accessToken 接口访问凭证 (CommonUtil.getToken)
	 * @param sceneId 场景值ID 永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @return WeiXinQRCode对象实例
	 * 
	 * @请求提交 POST 的参数 如 {"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
	 */
	public static WeiXinPermanentQRCode createPermanentQRCode(String accessToken,int sceneId){
		WeiXinPermanentQRCode weiXinQRCode=null;
		//拼接请求网址
		String requestUrl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
		requestUrl=requestUrl.replace("TOKENPOST", accessToken);
		//需要提交的参数
		String jsonMsg="{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
		//获取二维码信息
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, sceneId));
		
		if (jsonObject != null) {
			try {
				weiXinQRCode=new WeiXinPermanentQRCode();
				weiXinQRCode.setTicket(jsonObject.getString("ticket"));
				weiXinQRCode.setUrl(jsonObject.getString("url"));
				
				System.out.println("创建永久带参数二维码成功！");
				System.out.println(jsonObject.toString());
				
			} catch (Exception e) {
				weiXinQRCode=null;
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				System.out.println("创建永久带参数二维码失败"+"\n"+" errcode::"+errcode+"\n"+"errorMsg::"+ errmsg);
				System.out.println(e.getMessage());
			}
		}
		return weiXinQRCode;
		
	}
	
	/**
	 * 创建永久带参数二维码
	 * sceneStr 场景值ID为  String 类型
	 * 
	 * @param accessToken 接口访问凭证  (CommonUtil.getToken)
	 * @param sceneStr 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段   
	 * @return WeiXinQRCode对象实例
	 * 
	 *  @请求提交 POST 的参数 如 {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "123"}}}
	 */
	public static WeiXinPermanentQRCode createPermanentQRCode(String accessToken,String sceneStr){
		WeiXinPermanentQRCode weiXinQRCode=null;
		//拼接请求网址
		String requestUrl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
		requestUrl=requestUrl.replace("TOKENPOST", accessToken);
		//需要提交的参数 
		String jsonMsg="{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": %s}}}";
		//获取二维码信息
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, sceneStr));
		if (jsonObject != null) {
			try {
				weiXinQRCode=new WeiXinPermanentQRCode();
				weiXinQRCode.setTicket(jsonObject.getString("ticket"));
				weiXinQRCode.setUrl(jsonObject.getString("url"));
				
				System.out.println("创建永久带参数二维码成功！");
				System.out.println(jsonObject.toString());
				
			} catch (Exception e) {
				weiXinQRCode=null;
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				System.out.println("创建永久带参数二维码失败"+"\n"+" errcode::"+errcode+"\n"+"errorMsg::"+ errmsg);
			}
		}
		return weiXinQRCode;
		
	}
	
	/**
	 * 根据 ticket 获取二维码
	 * 
	 * @param ticket 二维码ticket
	 * @param savePath 保存路径
	 * @return filePath 文件名
	 */
	public static String getQRCode(String ticket,String savePath){
		String filePath=null;
		//拼接请求网址
		String requestUrl="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
		//对 ticket 进行 URL 编码，以避免出现空格
		ticket=CommonUtil.urlEncodingUTF8(ticket);
		requestUrl=requestUrl.replace("TICKET", ticket);
		try {
			URL url=new URL(requestUrl);
			HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			if (!savePath.endsWith("/")) {
				savePath+="/";
			}
			//将 ticket 做为文件名
			filePath=savePath+ticket+".jpg";
			//将微信服务器返回的输入流，写入文件中
			InputStream in=connection.getInputStream();
			BufferedInputStream bStream=new BufferedInputStream(in);
			FileOutputStream fos=new FileOutputStream(new File(filePath));
			byte[] bytes=new byte[1024*4];
			int size=0;
			while ((size=bStream.read(bytes))!=-1) {
				fos.write(bytes, 0, size);
			}
			
			fos.close();
			bStream.close();
			in.close();
			connection.disconnect();
			
			System.out.println("根据ticket换取二维码成功"+"\n"+"filePath::"+filePath);
			
		} catch (Exception e) {
			filePath=null;
			System.out.println("根据ticket换取二维码失败::"+e.getMessage());
		}
		
		
		return filePath;
		
	}
	
	/**
	 * 获取用户基本信息
	 * 
	 * @param accessToken 接口访问凭证  (CommonUtil.getToken)
	 * @param openId 用户的标识
	 * @return WeiXinUserInfo 返回用户信息对象实例
	 */
	public static WeiXinUserInfo getUserInfo(String accessToken,String openId){
		WeiXinUserInfo userInfo=null;
		//拼接请求地址
		String requestUrl="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
		requestUrl=requestUrl.replaceAll("OPENID", openId);
		
		System.out.println("requestUrl::"+requestUrl);
		
		//发起请求，获取用户信息
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (jsonObject != null) {
			try {
				userInfo=new WeiXinUserInfo();
				
				if (jsonObject.containsKey("unionid")) {
					userInfo.setUnionId(jsonObject.getString("unionid"));
				}else {
					userInfo.setUnionId("公众号未绑定到微信开放平台");
				}
				userInfo.setSubscribe(jsonObject.getInt("subscribe"));
				userInfo.setOpenId(jsonObject.getString("openid"));
				userInfo.setNickName(jsonObject.getString("nickname"));
				userInfo.setSex(jsonObject.getInt("sex"));
				userInfo.setCity(jsonObject.getString("city"));
				userInfo.setProvince(jsonObject.getString("province"));
				userInfo.setCountry(jsonObject.getString("country"));
				userInfo.setLanguage(jsonObject.getString("language"));
				userInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
				userInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
				
				
				userInfo.setRemark(jsonObject.getString("remark"));
				userInfo.setGroupId(jsonObject.getString("groupid"));
				userInfo.setTagIdList(JSONArray.toList(jsonObject.getJSONArray("tagid_list"),List.class));
				
				System.out.println("获取用户信息成功！");
				System.out.println("getUserInfo::"+jsonObject.toString());
				
			} catch (Exception e) {
				userInfo=null;
				if (jsonObject.getInt("subscribe") == 0) {
					System.out.println("用户未关注该公众号！"+"\n"+"openid::"+jsonObject.getString("openid"));
					System.out.println("unionid::"+jsonObject.getString("unionid"));
				}else {
					int errcode=jsonObject.getInt("errcode");
					String errmsg=jsonObject.getString("errmsg");
					System.out.println("获取用户信息失败：："+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
					
					System.out.println("getUserInfo::"+jsonObject.toString());
					
					System.out.println("获取用户信息失败::"+e.getMessage());
				}
			}
		}
		return userInfo;
	}
	
	 
	/**
	 * 拉取关注用户列表
	 * 
	 * @param accessToken 接口凭证
	 * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
	 * @return 返回WeiXinUserList实例对象
	 */
	public static WeiXinUserList getWeiXinUserList(String accessToken,String nextOpenId){
		WeiXinUserList userList=null;
		//拼接请求网址
		if (nextOpenId == null) {
			nextOpenId="";
		}
		String requestUrl="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
		requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
		requestUrl=requestUrl.replace("NEXT_OPENID", nextOpenId);
		//发起请求
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (jsonObject != null) {
			try {
				userList=new WeiXinUserList();
				userList.setTotal(jsonObject.getInt("total"));
				userList.setCount(jsonObject.getInt("count"));
				userList.setData(JSONArray.toList(jsonObject.getJSONObject("data").getJSONArray("openid"), List.class));
				userList.setNextOpenId(jsonObject.getString("next_openid"));
				
				System.out.println("获取关注用户列表成功！");
				System.out.println("getWeiXinUserList::"+jsonObject.toString());
				
			} catch (Exception e) {
				userList=null;
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				System.out.println("获取关注用户列表失败：："+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
				
				System.out.println("getUserInfo::"+jsonObject.toString());
				
				System.out.println("获取关注用户列表失败::"+e.getMessage());
			}
		}
		
		return userList;
	}
	
	/**
	 * 获取用户分组信息
	 * 
	 * @param accessToken 接口凭证
	 * @return 返回 List<WeiXinGroups> 分组列表
	 */
	public static List<WeiXinGroups> getWeiXinGroups(String accessToken){
		List<WeiXinGroups> groups=null;
		//拼接请求网址
		String requestUrl="https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
		requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
		//发起请求
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (jsonObject != null) {
			try {
				groups=JSONArray.toList(jsonObject.getJSONArray("groups"),WeiXinGroups.class);
				
				System.out.println("查询分组成功！");
				System.out.println("getWeiXinGroups::"+jsonObject.toString());
			} catch (Exception e) {
				groups=null;
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				System.out.println("查询分组失败：："+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
				
				System.out.println("getUserInfo::"+jsonObject.toString());
				
				System.out.println("查询分组失败::"+e.getMessage());
			}
		}
		return groups;
	}
	
	
	/**
	 * 创建分组
	 * @说明  需要 POST 数据 {"group":{"name":"groupName"}}
	 * 
	 * @param accessToken 接口凭证
	 * @param groupName 分组名称
	 * @return  WeiXinGroups 返回分组实例对象
	 */
	public static WeiXinGroups createGroup(String accessToken,String groupName){
		WeiXinGroups groups=null;
		//拼接请求网址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
		requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
		//需要提交的数据
		String postStr="{\"group\":{\"name\":\"%s\"}}";
		postStr=String.format(postStr, groupName);
		//发起请求
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", postStr);
		if (jsonObject != null) {
			try {
				groups=new WeiXinGroups();
				groups.setId(jsonObject.getJSONObject("group").getInt("id"));
				groups.setName(jsonObject.getJSONObject("group").getString("name"));
				
				System.out.println("创建分组成功！");
				System.out.println("createGroup::"+jsonObject.toString());
			} catch (Exception e) {
				groups=null;
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				System.out.println("创建分组失败：："+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
				
				System.out.println("getUserInfo::"+jsonObject.toString());
				
				System.out.println("创建分组失败::"+e.getMessage());
			}
		}
		return groups;
	}
	
	/**
	 * 修改指定 id 的分组的对应名称
	 * @说明  需要 POST 数据 {"group":{"id":108,"name":"groupName"}}
	 * 
	 * @param accessToken 接口凭证
	 * @param groupId 分组的id
	 * @param groupName 修改后的分组名称
	 * @return 返回 true 表示修改成功;false 表示修改失败
	 */
	public static boolean updateGroup(String accessToken,int groupId,String groupName){
		boolean result=false;
		//拼接请求网址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要 POST 的json数据
		String jsonData = "{\"group\": {\"id\": %d, \"name\": \"%s\"}}";
		jsonData=String.format(jsonData, groupId,groupName);
		//发起请求
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
		if (jsonObject != null) {
			int errcode=jsonObject.getInt("errcode");
			String errmsg=jsonObject.getString("errmsg");
			if (errcode == 0) {
				result=true;
				System.out.println("修改分组成功：："+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
			}else {
				result=false;
				System.out.println("修改分组失败：："+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
			}
		}
		return result;
	}
	
	/**
	 * 移动指定openId的用户到指定groupId的分组中
	 * 
	 * @param accessToken 接口凭证
	 * @param opendId 用户标识
	 * @param groupId 分组的id
	 * @return true 表示移动成功,false 表示移动失败
	 * 
	 * @说明 需要提交的json数据  "{\"openid\":\"%s\",\"to_groupid\":%d}"
	 */
	public static boolean moveMemberGroup(String accessToken,String openId,int groupId){
		boolean result=false;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData = "{\"openid\":\"%s\",\"to_groupid\":%d}";
		jsonData=String.format(jsonData, openId,groupId);
		//发起请求
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
		if (jsonObject != null) {
			int errcode=jsonObject.getInt("errcode");
			String errmsg=jsonObject.getString("errmsg");
			if (errcode == 0) {
				result=true;
				System.out.println("移动用户成功：："+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
			}else {
				result=false;
				System.out.println("移动用户失败：："+"\n"+"errcode::"+errcode+"\n"+"errmsg::"+errmsg);
			}
		}
		return result;
	}
	
	/**
	 * 上传媒体文件到微信服务器
	 * 
	 * @param accessToken 接口凭证
	 * @param type 媒体文件类型( 有图片（image）、语音（voice）、视频（video）和缩略图（thumb）)
	 * @param mediaFileUrl 媒体文件的路径
	 * @return WeiXinMedia 返回WeiXinMedia对象实例
	 * 
	 * @说明 http请求方式：POST/FORM，使用https
	 * 目前只成功上传了 image 类型
	 * 
	 */
	public static WeiXinMedia uploadTemporaryMedia(String accessToken, String type, String mediaFileUrl){
		WeiXinMedia weiXinMedia=null;
		//拼接请求网址
		String requestUrl="http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
		requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		// 定义数据分隔符
//		String boundary = "------------"+CommonUtil.getUUID();
		String boundary = "------------"+System.currentTimeMillis();

		try {
			URL url=new URL(requestUrl);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);//post方式不能使用缓存
			//设置请求头Content-Type
			connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			//获取媒体文件上传的输出流(即向微信服务器写数据)
			OutputStream outputStream=connection.getOutputStream();
			
			//读取获得媒体文件
			URL mediaUrl=new URL(mediaFileUrl);
			HttpURLConnection mediaConn=(HttpURLConnection) mediaUrl.openConnection();
			mediaConn.setDoOutput(true);
			mediaConn.setRequestMethod("GET");
			//从请求头中获取内容
			String content_type=mediaConn.getHeaderField("Content-Type");
			String header=mediaConn.getHeaderField(null);
			
			System.out.println("header::"+header);
			System.out.println("getContenttype::"+mediaConn.getContentType());
//			content_type="audio/amr";
			System.out.println("content_type::"+content_type);
			
			//根据内容判断文件扩展名
			String fileExt=CommonUtil.getFileExt(content_type);
			
			//请求体开始部分写入微信服务器
			outputStream.write(("--" + boundary + "\r\n").getBytes());
//			String Disposition=String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file1%s\"\r\n", fileExt);
			String Disposition=String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file3%s\"\r\n", fileExt);
			String Type=String.format("Content-Type: %s\r\n\r\n", content_type);
			System.out.println("Disposition::"+Disposition);
			System.out.println("Type::"+Type);
			outputStream.write(String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file3%s\"\r\n", fileExt).getBytes());
			outputStream.write(String.format("Content-Type: %s\r\n\r\n", content_type).getBytes());
			
			//媒体文件内容写入微信服务器
			BufferedInputStream bis=new BufferedInputStream(mediaConn.getInputStream());
			byte[] bytes=new byte[8096];
			int size=0;
			while ((size=bis.read(bytes))!=-1) {
				outputStream.write(bytes, 0, size);
			}
			
			//请求体结束部分写入微信服务器
			outputStream.write(("\r\n"+"--"+ boundary + "--\r\n").getBytes());
			outputStream.flush();
			outputStream.close();
			bis.close();
			mediaConn.disconnect();
			
			//读取媒体文件上传的输入流(从微信服务器中读取)
			InputStream inputStream=connection.getInputStream();
			InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"UTF-8");
			BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
			StringBuffer buffer=new StringBuffer();
			String str=null;
			
			while (true) {
				System.out.println("available::"+inputStream.available());
				if (inputStream.available()>0) {
					while ((str=bufferedReader.readLine())!=null) {
						buffer.append(str);
					}
				}else if(inputStream.available()==0){
					System.out.println("与服务器的链接已中断");
					break;
				}
			}
			
			bufferedReader.close();
			inputStreamReader.close();
			//释放资源
			inputStream.close();
			inputStream=null;
			
			System.out.println("buffer::"+buffer.toString());
			
			//解析输入流得到返回结果
			JSONObject jsonObject=JSONObject.fromObject(buffer.toString());
			
			System.out.println("jsonObject::"+jsonObject);
			
			weiXinMedia=new WeiXinMedia();
			weiXinMedia.setMediaType(jsonObject.getString("type"));
			if ("thumb".equals(type)) {
				weiXinMedia.setMediaId(jsonObject.getString("thumb_media_id"));
			}else {
				weiXinMedia.setMediaId(jsonObject.getString("media_id"));
			}
			weiXinMedia.setCreateAt(jsonObject.getInt("created_at"));
			
			System.out.println("uploadTemporaryMedia--成功上传临时文件：："+"\n"+jsonObject);
			
		}catch (SocketException e) {
			System.out.println("uploadTemporaryMedia--上传临时文件失败：："+"\n"+e.toString());
		}catch (Exception e) {
			weiXinMedia=null;
			System.out.println("uploadTemporaryMedia--上传临时文件失败：："+"\n"+e.toString());
		}
		
		return weiXinMedia;
	}
	
	/**
	 * 从微信服务器上下载对应 mediaId 的媒体文件
	 * 
	 * @param accessToken 接口凭证
	 * @param mediaId 媒体文件id
	 * @param savePath 保存文件位置
	 * @return filePath 返回文件的路径
	 * 
	 * @说明 可下载用户发送过来的媒体文件
	 */
	public static String downLoadMedia(String accessToken, String mediaId, String savePath){
		String filePath=null;
		// 拼接请求地址
		String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
		try {
			URL url=new URL(requestUrl);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			if (!savePath.endsWith("/")) {
				savePath+="/";
			}
			//根据内容类型获取文件扩展名，做为文件名
			String fileExt=CommonUtil.getFileExt(connection.getHeaderField("Content-Type"));
			filePath=savePath+mediaId+fileExt;
			
			InputStream inputStream=connection.getInputStream();
			BufferedInputStream bis=new BufferedInputStream(inputStream);
			FileOutputStream fileOutputStream=new FileOutputStream(new File(filePath));
			int size=0;
			byte bytes[]=new byte[1024*4];
			while ((size=bis.read(bytes)) != -1) {
				fileOutputStream.write(bytes, 0, size);
			}
			//释放资源
			fileOutputStream.close();
			bis.close();
			inputStream.close();
			inputStream=null;
			connection.disconnect();
			System.out.println("文件下载成功-filePath::"+filePath);
		} catch (Exception e) {
			filePath=null;
			System.out.println("文件下载失败-filePath::"+filePath);
			System.out.println("downLoadMedia::"+e.toString());
		}
		
		return filePath;
	}
	
	
}
