package lgc.bean.pojo;

import java.util.List;

/**
 * ͨ����ҳ��Ȩ��ȡ�û���Ϣ
 * 
 * @author lgc
 *
 * @date 2017��6��27�� ����10:21:33
 */
public class SNSUserInfo {

	/*
	 { 
	  	"openid":" OPENID",  
		 " nickname": NICKNAME,   
		 "sex":"1",   
		 "province":"PROVINCE"   
		 "city":"CITY",   
		 "country":"COUNTRY",    
		 "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ
		4eMsv84eavHiaiceqxibJxCfHe/46",  
		"privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],    
		 "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL" 
		} 
	 */
	
	// �û���ʶ
	private String openId;
	// �û��ǳ�
	private String nickName;
	// �Ա�1�����ԣ�2��Ů�ԣ�0��δ֪��
	private int sex;
	// ʡ��
	private String province;
	// ����
	private String city;
	// ����
	private String country;
	// �û�ͷ������
	private String headImgUrl;
	//�û���Ȩ
	private List<String> privilegeList;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public List<String> getPrivilegeList() {
		return privilegeList;
	}
	public void setPrivilegeList(List<String> privilegeList) {
		this.privilegeList = privilegeList;
	}
	
	
}
