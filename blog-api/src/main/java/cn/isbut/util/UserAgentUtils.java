package cn.isbut.util;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryan
 * @Description: UserAgent解析工具类
 *
 */
@Component
public class UserAgentUtils {
	private UserAgentAnalyzer uaa;

	public UserAgentUtils() {
		this.uaa = UserAgentAnalyzer
				.newBuilder()
				.hideMatcherLoadStats()
				.withField("OperatingSystemNameVersionMajor")
				.withField("AgentNameVersion")
				.build();
	}

	/**
	 * 从User-Agent解析客户端操作系统和浏览器版本
	 *
	 * @param userAgent 代理
	 * @return 截取结果
	 */
	public Map<String, String> parseOsAndBrowser(String userAgent) {
		UserAgent agent = uaa.parse(userAgent);
		String os = agent.getValue("OperatingSystemNameVersionMajor");
		String browser = agent.getValue("AgentNameVersion");
		Map<String, String> map = new HashMap<>(2);
		map.put("os", os);
		map.put("browser", browser);
		return map;
	}
}
