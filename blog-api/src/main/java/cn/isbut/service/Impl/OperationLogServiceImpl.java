package cn.isbut.service.Impl;

import cn.isbut.exception.PersistenceException;
import cn.isbut.util.IpAddressUtils;
import cn.isbut.util.UserAgentUtils;
import cn.isbut.entity.OperationLog;
import cn.isbut.mapper.OperationLogMapper;
import cn.isbut.service.OperationLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description: 操作日志业务层实现
 * @Author: Mashiro
 * @Date: Created in 2021/5/30 14:47
 */
@Service
public class OperationLogServiceImpl implements OperationLogService {

    private final OperationLogMapper operationLogMapper;
    private final UserAgentUtils userAgentUtils;

    public OperationLogServiceImpl(OperationLogMapper operationLogMapper, UserAgentUtils userAgentUtils) {
        this.operationLogMapper = operationLogMapper;
        this.userAgentUtils = userAgentUtils;
    }


    @Override
    public void saveOperationLog(OperationLog log) {
        String ipSource = IpAddressUtils.getCityInfo(log.getIp());
        Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
        String os = userAgentMap.get("os");
        String browser = userAgentMap.get("browser");
        log.setIpSource(ipSource);
        log.setOs(os);
        log.setBrowser(browser);
        if (operationLogMapper.saveOperationLog(log) != 1) {
            throw new PersistenceException("日志添加失败");
        }
    }

    @Override
    public void deleteOperationLogById(Long id) {
        if (operationLogMapper.deleteOperationLogById(id) != 1){
            throw new PersistenceException("删除日志失败");
        }
    }

    @Override
    public List<OperationLog> getOperationLogListByDate(String startDate, String endDate) {
        return operationLogMapper.getOperationLogListByDate(startDate, endDate);
    }
}
