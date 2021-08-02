package cn.isbut.service;

import java.util.Map;

/**
 * @author Ryan
 * @Description:
 */
public interface AboutService {

    void updateAbout(Map<String, String> map);

    boolean getAboutCommentEnabled();

    Map<String, String> getAboutInfo();

    Map<String, String> getAboutSetting();
}
