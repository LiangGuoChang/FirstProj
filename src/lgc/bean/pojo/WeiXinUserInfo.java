package lgc.bean.pojo;

import java.util.List;

/**
 * ΢���û��Ļ�����Ϣ
 * @author lgc
 *
 * @date 2017��7��4�� ����2:38:29
 */
public class WeiXinUserInfo {

	/*
	{
		   "subscribe": 1, 
		   "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", 
		   "nickname": "Band", 
		   "sex": 1, 
		   "language": "zh_CN", 
		   "city": "����", 
		   "province": "�㶫", 
		   "country": "�й�", 
		   "headimgurl":  "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4
		eMsv84eavHiaiceqxibJxCfHe/0",
		  "subscribe_time": 1382694957,
		  "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
		  "remark": "",
		  "groupid": 0,
		  "tagid_list":[128,2]
		}
	*/
	
	/* �û��Ƿ��ĸù��ںű�ʶ��ֵΪ0ʱ��������û�û�й�ע�ù��ںţ���ȡ����������Ϣ��ֻ��openid��UnionID���ڸù��ںŰ󶨵���΢�ſ���ƽ̨�˺�ʱ���У�
	 * ֵΪ1ʱ��������û��ѹ�ע�ù��ں�
	 */
	private int subscribe;
	//�û��ı�ʶ
	private String openId;
	//�û����ǳ�
	private String nickName;
	//�û����Ա�ֵΪ1ʱ�����ԣ�ֵΪ2ʱ��Ů�ԣ�ֵΪ0ʱ��δ֪
	private int sex;
	//���ҵ������԰汾��zh_CN ���壬zh_TW ���壬en Ӣ�Ĭ��Ϊzh-CN
	private String language;
	//�û����ڳ���
	private String city;
	//�û�����ʡ��
	private String province;
	//�û����ڹ���
	private String country;
	//�û�û��ͷ��ʱ����Ϊ�ա����û�����ͷ��ԭ��ͷ��URL��ʧЧ
	private String headImgUrl;
	//�û���עʱ�䣬Ϊʱ���������û�����ι�ע����ȡ����עʱ��
	private String subscribeTime;
	//ֻ�����û������ںŰ󶨵�΢�ſ���ƽ̨�ʺź󣬲Ż���ָ��ֶΡ�
	private String unionId;
	//���ں���Ӫ�߶Է�˿�ı�ע�����ں���Ӫ�߿���΢�Ź���ƽ̨�û��������Է�˿��ӱ�ע
	private String remark;
	//�û����ڵķ���ID
	private String groupId;
	//�û������ϵı�ǩID�б�
	private List<Integer> tagIdList;
	
	public int getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}
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
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
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
	public String getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public List<Integer> getTagIdList() {
		return tagIdList;
	}
	public void setTagIdList(List<Integer> tagIdList) {
		this.tagIdList = tagIdList;
	}
	
	public String getWeixinUserInfo(){
		String sex="";
		if (getSex() == 1) {
			sex="��";
		}else if (getSex() == 2) {
			sex="Ů";
		}else {
			sex="δ֪";
		}
		return "subscribe::"+getSubscribe()+"\n"+"openid::"+getOpenId()+"\n"+"nickname::"+getNickName()
				+"\n"+"sex::"+sex+"\n"+"language::"+getLanguage()+"\n"+"city::"+getCity()+"\n"
				+"province::"+getProvince()+"\n"+"country::"+getCountry()+"\n"+"headimgurl::"+getHeadImgUrl()+"\n"
				+"subscribe_time::"+getSubscribeTime()+"\n"+"remark::"+getRemark()+"\n"+"groupid::"+getGroupId()
				+"\n"+"tagid_list::"+getTagIdList()+"\n"+"unionid::"+getUnionId();
	}
	
}
