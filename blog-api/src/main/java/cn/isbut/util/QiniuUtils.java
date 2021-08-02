package cn.isbut.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;

/**
 * @Description: 七牛云工具类
 * @Author: Mashiro
 * @Date: Created in 2021/7/17 22:16
 */
public class QiniuUtils {
    // 设置需要操作的账号的AK和SK
    private static final String ACCESS_KEY = "viIizti4QN2h-UJ6E1_JugNduMXnYMLmYcXwCmKv";
    private static final String SECRET_KEY = "2z6zNOChiY7Alu0DC5mPloMaYrGDrP3edoWojENR";

    // 要上传的空间名称
    private static final String BUCKET = "mashiroimages";

    // 密钥
    private static final Auth AUTH = Auth.create(ACCESS_KEY, SECRET_KEY);

    // 外链默认域名
    private static final String DOMAIN = "img.mashiro.org.cn";

    /**
     * 将图片上传到七牛云
     */
    public static String uploadImg(MultipartFile file) {
        // 构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 生成上传凭证，然后准备上传
        String key = getRandomCharacterAndNumber(10) + ".jpg";
        try {
            String upToken = AUTH.uploadToken(BUCKET);
            try {
                byte[] localFile = file.getBytes();
                Response response = uploadManager.put(localFile, key, upToken);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

                String returnPath = "http://" + DOMAIN + "/" + putRet.key;
                // 这个returnPath是获得到的外链地址,通过这个地址可以直接打开图片
                return returnPath;
            } catch (QiniuException ex) {
                Response r = ex.response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getRandomCharacterAndNumber(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";

            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
                // int choice = 97; // 指定字符串为小写字母
                val += (char) (choice + random.nextInt(26));
                // 数字
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
