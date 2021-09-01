package cn.isbut.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Ryan
 * @Description: ip记录
 *
 */
@Slf4j
@Component
public class IpAddressUtils {
	/**
	 * 在Nginx等代理之后获取用户真实IP地址
	 *
	 * @param request 请求
	 * @return ip地址
	 */
	public static String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
				//根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					log.error("getIpAddress exception:", e);
				}
				assert inet != null;
				ip = inet.getHostAddress();
			}
		}
		return StringUtils.substringBefore(ip, ",");
	}

	private static DbSearcher searcher;
	private static Method method;

	/**
	 * 在服务启动时加载 ip2region.db 到内存中
	 * 解决打包jar后找不到 ip2region.db 的问题
	 */
	@PostConstruct
	private void initIp2regionResource() {
		try {
			InputStream inputStream = new ClassPathResource("/ipdb/ip2region.db").getInputStream();
			//将 ip2region.db 转为 ByteArray
			byte[] dbBinStr = FileCopyUtils.copyToByteArray(inputStream);
			DbConfig dbConfig = new DbConfig();
			searcher = new DbSearcher(dbConfig, dbBinStr);
			//二进制方式初始化 DBSearcher，需要使用基于内存的查找算法 memorySearch
			method = searcher.getClass().getMethod("memorySearch", String.class);
		} catch (Exception e) {
			log.error("initIp2regionResource exception:", e);
		}
	}

	/**
	 * 根据ip从 ip2region.db 中获取地理位置
	 *
	 * @param ip ip地址
	 * @return 地理位置
	 */
	public static String getCityInfo(String ip) {
		if (ip == null || !Util.isIpAddress(ip)) {
			log.error("Error: Invalid ip address");
			return null;
		}
		try {
			DataBlock dataBlock = (DataBlock) method.invoke(searcher, ip);
			String ipInfo = dataBlock.getRegion();
			if (!StringUtils.isEmpty(ipInfo)) {
				ipInfo = ipInfo.replace("|0", "");
				ipInfo = ipInfo.replace("0|", "");
			}
			return ipInfo;
		} catch (Exception e) {
			log.error("getCityInfo exception:", e);
		}
		return null;
	}
}