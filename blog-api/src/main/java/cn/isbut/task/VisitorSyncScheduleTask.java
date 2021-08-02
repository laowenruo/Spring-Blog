package cn.isbut.task;

import cn.isbut.common.RedisKey;
import cn.isbut.dto.VisitLogUuidTimeDTO;
import cn.isbut.entity.CityVisitor;
import cn.isbut.entity.VisitRecord;
import cn.isbut.service.*;
import com.mashiro.service.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:
 * @Author: Mashiro
 * @Date: Created in 2021/5/30 23:08
 */
@Component
public class VisitorSyncScheduleTask {
    @Autowired
    private RedisService redisService;
    @Autowired
    private VisitLogService visitLogService;
    @Autowired
    private VisitorService visitorService;
    @Autowired
    private VisitRecordService visitRecordService;
    @Autowired
    private CityVisitorService cityVisitorService;

    /**
     * 清空当天Redis访客标识
     * 记录当天的PV和UV
     * 更新当天所有访客的PV和最后访问时间
     * 更新城市新增访客UV数
     */
    public void syncVisitInfoToDatabase(){
        //清空当天Redis的访客标识Set，以便统计每日UV
        redisService.deleteCacheByKey(RedisKey.IDENTIFICATION_SET);
        //获取昨天的所有访问日志
        List<VisitLogUuidTimeDTO> yesterdayLogList = visitLogService.getUUIDAndCreateTimeByYesterday();
        //用Set去重得到当天所有访客的uuid
        //为避免缓存击穿导致第二天的数据统计不准确，以数据库访问日志为准，而不从Redis中获取这个Set
        //比如在这个定时任务执行期间，产生大量访客的请求，而这些访客的uuid都在任务执行结束后被清空了，没有被第二天的定时任务记录到
        Set<String> uuidSet = new HashSet<>();
        Map<String, Integer> PVMap = new HashMap<>();
        Map<String, Date> lastTimeMap = new HashMap<>();
        yesterdayLogList.forEach(log -> {
            String uuid = log.getUuid();
            Date createTime = log.getTime();
            //记录当天访客的uuid
            uuidSet.add(uuid);
            //对应uuid的PV++
            PVMap.merge(uuid, 1, Integer::sum);
            //因为sql中order by create_time desc，直接put第一次出现的uuid的createTime，就是最后一次访问时间
            lastTimeMap.putIfAbsent(uuid, createTime);
        });
        int pv = yesterdayLogList.size();
        int uv = uuidSet.size();
        //获取昨天的日期字符串
        String date = new SimpleDateFormat("MM-dd").format(DateUtils.addDays(new Date(), -1));
        //记录当天的PV和UV
        visitRecordService.saveVisitRecord(new VisitRecord(pv, uv, date));
        //更新当天所有访客的PV和最后访问时间到数据库
        uuidSet.forEach(uuid -> {
            VisitLogUuidTimeDTO uuidPVTimeDTO = new VisitLogUuidTimeDTO(uuid, lastTimeMap.get(uuid), PVMap.get(uuid));
            visitorService.updatePVAndLastTimeByUUID(uuidPVTimeDTO);
        });
        //查询当天新增访客的ip来源
        List<String> ipSource = visitorService.getNewVisitorIpSourceByYesterday();
        Map<String, Integer> cityVisitorMap = new HashMap<>();
        ipSource.forEach(i -> {
            if (i.startsWith("中国")) {
                String[] split = i.split("\\|");
                if (split.length == 4) {
                    String city = split[2];
                    cityVisitorMap.merge(city, 1, Integer::sum);
                }
            }
        });
        //更新城市新增访客UV数
        cityVisitorMap.forEach((k, v) -> {
            cityVisitorService.saveCityVisitor(new CityVisitor(k, v));
        });
    }
}
