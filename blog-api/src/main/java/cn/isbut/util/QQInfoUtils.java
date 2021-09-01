package cn.isbut.util;

import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author Ryan
 * @Description: 获取QQ昵称头像信息
 *
 */
public class QQInfoUtils {
	private static final RestTemplate restTemplate = new RestTemplate();
	private static final String QQ_NICKNAME_URL = "https://r.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg?uins={1}";
	private static final String QQ_AVATAR_URL = "https://q.qlogo.cn/g?b=qq&nk=%s&s=100";

	public static String getQQNickname(String qq) throws UnsupportedEncodingException {
		String res = restTemplate.getForObject(QQ_NICKNAME_URL, String.class, qq);
		assert res != null;
		byte[] bytes = res.getBytes(StandardCharsets.ISO_8859_1);
		String nickname = new String(bytes, "gb18030").split(",")[6].replace("\"", "");
		if ("".equals(nickname)) {
			return "nickname";
		}
		return nickname;
	}

	public static String getQQAvatarURL(String qq) {
		return String.format(QQ_AVATAR_URL, qq);
	}

	public static boolean isQQNumber(String nickname) {
		return nickname.matches("^[1-9][0-9]{4,10}$");
	}
}