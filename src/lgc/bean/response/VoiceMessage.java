package lgc.bean.response;

/**
 * 语音消息
 * @author lgc
 *
 * @date 2017年6月7日 上午9:56:49
 */
public class VoiceMessage extends BaseMessage {

	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}

	
}
