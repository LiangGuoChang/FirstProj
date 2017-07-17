package lgc.servlet;

import java.io.UnsupportedEncodingException;
import java.util.List;

import lgc.bean.pojo.Token;
import lgc.bean.pojo.WeiXinGroups;
import lgc.bean.pojo.WeiXinMedia;
import lgc.bean.pojo.WeiXinPermanentQRCode;
import lgc.bean.pojo.WeiXinTemporaryQRCode;
import lgc.bean.pojo.WeiXinUserInfo;
import lgc.bean.pojo.WeiXinUserList;
import lgc.util.AdvancedUtil;
import lgc.util.CommonUtil;

public class HelloWorld {
	
	/**
	 * 公众号的  appID 和 appsecret
	 */
	private static final String appID="wxdfead60d5e0dbcd6";
	private static final String appsecret="92663408c73b91e5749a0c5de20bd953";
	
	//其中一个用户的 openId
	private static final String user_openId="oA1HcvwJj9KKuPC6fmRWm8h6Qv4I";
	private static final String my_openId="oA1Hcv9PfGShFQfsHXEdjQrPGPmQ";
	
	public static void main(String[] args) {
		
		//获取接口凭证
		Token token=CommonUtil.getToken(appID, appsecret);
		String access_token=token.getAccess_token();
		System.out.println("access_token::"+access_token);
		/*
		getWeixinUserInfo(access_token,user_openId);
		
		getWexinUserLists(access_token, "");
		
		getWeixinGroups(access_token);
		
//		createGroup(access_token, "newthk1");
		
		updateGroup(access_token, 100, "family");
		
		moveMember(access_token, my_openId, 100);
		
		getWeixinGroups(access_token);*/
		
		// http://2d625a40.ngrok.io/WeiXinMedia/voice/msg1.amr
//		uploadMedia(access_token, "voice", "http://03b9ab9a.ngrok.io/WeiXinMedia/voice/uploadtest.jpg");
		
		WeiXinMedia media=AdvancedUtil.uploadTemporaryMedia(access_token, "voice", "http://2d625a40.ngrok.io/WeiXinMedia/voice/begin.mp3");
//		System.out.println(media.getWeixinMedia());
		
//		String downLoadfile=AdvancedUtil.downLoadMedia(access_token, mediaId, savePath);
		
	}
	
	//创建二维码
	private static void getQRcode(String access_token){

		System.out.println("access_token::"+access_token);
		//创建永久带参数二维码
		WeiXinPermanentQRCode weCode=AdvancedUtil.createPermanentQRCode(access_token, "1520");
		String ticket=weCode.getTicket();
//		int expire=weCode.getExpireSeconds();
		String url=weCode.getUrl();
//		System.out.println("ticket::"+ticket+"\n"+"expire::"+expire+"\n"+"url::"+url);
		System.out.println("ticket::"+ticket+"\n"+"url::"+url);
		
		//根据 ticket 获取二维码
		String savePath="D:/WebProj/myDownload";
		String filePath=AdvancedUtil.getQRCode(ticket, savePath);
		
		System.out.println("二维码路径：："+filePath);
		
	}
	
	//获取指定openId的用户的基本信息
	private static void getWeixinUserInfo(String access_token,String openId){
		WeiXinUserInfo userInfo=AdvancedUtil.getUserInfo(access_token, openId);
		/*byte[] bytes=userInfo.getWeixinUserInfo().getBytes();
		try {
			System.out.println(new String(bytes, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}*/
		
		System.out.println(userInfo.getWeixinUserInfo());
	}
	
	//拉取关注者列表
	private static void getWexinUserLists(String access_token,String nextOpenId){
		WeiXinUserList userList=AdvancedUtil.getWeiXinUserList(access_token, nextOpenId);
		System.out.println(userList.getWeiXinUserList());
	}
	
	//获取用户分组信息
	private static void getWeixinGroups(String access_token){
		List<WeiXinGroups> groups=AdvancedUtil.getWeiXinGroups(access_token);
		for (WeiXinGroups weiXinGroups : groups) {
			System.out.println(weiXinGroups.getWeiXinGroups());
		}
	}
	
	//创建分组
	private static void createGroup(String access_token,String groupName){
		WeiXinGroups groups=AdvancedUtil.createGroup(access_token, groupName);
		System.out.println(groups.getWeiXinGroups());
	}
	
	//修改分组
	private static void updateGroup(String access_token,int groupId,String groupName){
		boolean result=AdvancedUtil.updateGroup(access_token, groupId, groupName);
		System.out.println("修改分组：："+result);
	}
	
	//移动用户到指定分组
	private static void moveMember(String access_token,String openId,int groupId){
		boolean result=AdvancedUtil.moveMemberGroup(access_token, openId, groupId);
	}
	
	//上传媒体文件到微信服务器
	private static void uploadMedia(String accessToken, String type, String mediaFileUrl){
		WeiXinMedia media=AdvancedUtil.uploadTemporaryMedia(accessToken, type, mediaFileUrl);
		System.out.println(media.getWeixinMedia());
	}
}
