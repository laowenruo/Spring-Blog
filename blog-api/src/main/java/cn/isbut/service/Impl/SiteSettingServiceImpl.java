package cn.isbut.service.Impl;

import cn.isbut.exception.PersistenceException;
import cn.isbut.vo.BadgeVO;
import cn.isbut.vo.CopyrightVO;
import cn.isbut.vo.FavoriteVO;
import cn.isbut.vo.IntroductionVO;
import cn.isbut.common.RedisKey;
import cn.isbut.entity.SiteSetting;
import cn.isbut.mapper.SiteSettingMapper;
import cn.isbut.service.RedisService;
import cn.isbut.service.SiteSettingService;
import cn.isbut.util.JacksonUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 站点设置业务层实现
 * @Author: Mashiro
 * @Date: Created in 2021/5/30 10:06
 */
@Service
public class SiteSettingServiceImpl implements SiteSettingService {

    private final SiteSettingMapper siteSettingMapper;
    private final RedisService redisService;
    private static final Pattern PATTERN = Pattern.compile("\"(.*?)\"");

    public SiteSettingServiceImpl(SiteSettingMapper siteSettingMapper,
                                  RedisService redisService) {
        this.siteSettingMapper = siteSettingMapper;
        this.redisService = redisService;
    }

    @Override
    public void updateSiteSetting(List<LinkedHashMap> siteSettings, List<Integer> deleteIds) {
        for (Integer id : deleteIds) {//删除
            deleteOneSiteSettingById(id);
        }
        for (LinkedHashMap s : siteSettings) {
            SiteSetting siteSetting = JacksonUtils.convertValue(s, SiteSetting.class);
            if (siteSetting.getId() != null) {//修改
                updateOneSiteSetting(siteSetting);
            } else {//添加
                saveOneSiteSetting(siteSetting);
            }
        }
        deleteSiteInfoRedisCache();
    }

    @Override
    public String getWebTitleSuffix() {
        return siteSettingMapper.getWebTitleSuffix();
    }

    @Override
    public Map<String, List<SiteSetting>> getList() {
        //获取所有sitesettingList
        List<SiteSetting> siteSettingList = siteSettingMapper.getSiteSettingList();
        //根据不同区域，分别存放sitesitting
        Map<String, List<SiteSetting>> map = new HashMap<>();
        //1基础设置，2页脚徽标，3资料卡
        List<SiteSetting> type1 = new ArrayList<>();
        List<SiteSetting> type2 = new ArrayList<>();
        List<SiteSetting> type3 = new ArrayList<>();
        //遍历 siteSettingList 判断type
        for (SiteSetting siteSetting : siteSettingList) {
            if (siteSetting.getType() == 1){
                type1.add(siteSetting);
            }else if (siteSetting.getType() == 2){
                type2.add(siteSetting);
            }else if (siteSetting.getType() == 3){
                type3.add(siteSetting);
            }
        }
        map.put("type1", type1);
        map.put("type2", type2);
        map.put("type3", type3);
        return map;
    }

    @Override
    public Map<String, Object> getSiteInfo() {
        //尝试从缓存中获取
        String redisKey = RedisKey.SITE_INFO_MAP;
        Map<String, Object> siteInfoMapFromRedis = redisService.getMapByValue(redisKey);
        if (siteInfoMapFromRedis != null){
            return siteInfoMapFromRedis;
        }
        //从数据库中获取
        List<SiteSetting> siteSettingList = siteSettingMapper.getSiteSettingList();
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> siteInfo = new HashMap<>();
        List<BadgeVO> badges = new ArrayList<>();
        IntroductionVO introduction = new IntroductionVO();
        List<FavoriteVO> favorites = new ArrayList<>();
        List<String> rollTexts = new ArrayList<>();
        for (SiteSetting s : siteSettingList) {
            if (s.getType() == 1) {
                if ("copyright".equals(s.getNameEn())) {
                    CopyrightVO copyright = JacksonUtils.readValue(s.getValue(), CopyrightVO.class);
                    siteInfo.put(s.getNameEn(), copyright);
                } else {
                    siteInfo.put(s.getNameEn(), s.getValue());
                }
            } else if (s.getType() == 2) {
                BadgeVO badge = JacksonUtils.readValue(s.getValue(), BadgeVO.class);
                badges.add(badge);
            } else if (s.getType() == 3) {
                if ("avatar".equals(s.getNameEn())) {
                    introduction.setAvatar(s.getValue());
                } else if ("name".equals(s.getNameEn())) {
                    introduction.setName(s.getValue());
                } else if ("github".equals(s.getNameEn())) {
                    introduction.setGithub(s.getValue());
                } else if ("qq".equals(s.getNameEn())) {
                    introduction.setQq(s.getValue());
                } else if ("bilibili".equals(s.getNameEn())) {
                    introduction.setBilibili(s.getValue());
                } else if ("email".equals(s.getNameEn())) {
                    introduction.setEmail(s.getValue());
                } else if ("favorite".equals(s.getNameEn())) {
                    FavoriteVO favoriteVO = JacksonUtils.readValue(s.getValue(), FavoriteVO.class);
                    favorites.add(favoriteVO);
                } else if ("rollText".equals(s.getNameEn())) {
                    Matcher m = PATTERN.matcher(s.getValue());
                    while (m.find()) {
                        rollTexts.add(m.group(1));
                    }
                }
            }
        }
        introduction.setFavorites(favorites);
        introduction.setRollText(rollTexts);
        map.put("introduction", introduction);
        map.put("siteInfo", siteInfo);
        map.put("badges", badges);
        redisService.saveMapToValue(redisKey, map);
        return map;
    }

    @Transactional
    public void saveOneSiteSetting(SiteSetting siteSetting) {
        if (siteSettingMapper.saveSiteSetting(siteSetting) != 1) {
            throw new PersistenceException("配置添加失败");
        }
    }

    @Transactional
    public void updateOneSiteSetting(SiteSetting siteSetting) {
        if (siteSettingMapper.updateSiteSetting(siteSetting) != 1) {
            throw new PersistenceException("配置修改失败");
        }
    }

    @Transactional
    public void deleteOneSiteSettingById(Integer id) {
        if (siteSettingMapper.deleteSiteSettingById(id) != 1) {
            throw new PersistenceException("配置删除失败");
        }
    }

    /**
     * 删除站点信息缓存
     */
    private void deleteSiteInfoRedisCache() {
        redisService.deleteCacheByKey(RedisKey.SITE_INFO_MAP);
    }
}
