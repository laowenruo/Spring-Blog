package cn.isbut.util;

import org.apache.commons.codec.digest.MurmurHash3;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 * @author Ryan
 * @Description: Hash工具类
 *
 */
public class HashUtils {
	private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	public static String getMd5(CharSequence str) {
		return DigestUtils.md5DigestAsHex(str.toString().getBytes());
	}

	public static long getMurmurHash32(String str) {
		int i = MurmurHash3.hash32(str);
		return i < 0 ? Integer.MAX_VALUE - (long) i : i;
	}

	public static String getBC(CharSequence rawPassword) {
		return bCryptPasswordEncoder.encode(rawPassword);
	}

	public static boolean matchBC(CharSequence rawPassword, String encodedPassword) {
		return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
	}

}
