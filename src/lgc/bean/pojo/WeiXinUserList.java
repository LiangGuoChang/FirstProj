package lgc.bean.pojo;

import java.util.List;

/**
 * ���ںŹ�ע���б�
 * 
 * @author lgc
 *
 * @date 2017��7��5�� ����9:28:43
 */
public class WeiXinUserList {

	/*
	{
		"total":2,
		"count":2,
		"data":
				{
					"openid":["","OPENID1","OPENID2"]
				},
		"next_openid":"NEXT_OPENID"
	}
	*/
	
	//��ע������
	private int total;
	//��ȡ opendId�ĸ��������Ϊ10000��
	private int count;
	//��ȡ��opendId�б�
	private List<String> data;
	//��ȡ��opendId�б�����һ��opendId
	private String nextOpenId;
	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	public String getNextOpenId() {
		return nextOpenId;
	}
	public void setNextOpenId(String nextOpenId) {
		this.nextOpenId = nextOpenId;
	}
	
	public String getWeiXinUserList(){
		
		return "total::"+getTotal()+"\n"+"count::"+getCount()+"\n"+"data::"+getData()
		+"\n"+"nextOpenId::"+getNextOpenId();
	}
	
}
