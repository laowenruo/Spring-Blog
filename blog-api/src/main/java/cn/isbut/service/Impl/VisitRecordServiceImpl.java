package cn.isbut.service.Impl;

import cn.isbut.entity.VisitRecord;
import cn.isbut.mapper.VisitRecordMapper;
import cn.isbut.service.VisitRecordService;
import org.springframework.stereotype.Service;

/**
 * @Description: 访问记录业务层实现
 * @Author: Mashiro
 * @Date: Created in 2021/5/30 16:42
 */
@Service
public class VisitRecordServiceImpl implements VisitRecordService {

    private final VisitRecordMapper visitRecordMapper;

    public VisitRecordServiceImpl(VisitRecordMapper visitRecordMapper) {
        this.visitRecordMapper = visitRecordMapper;
    }

    @Override
    public void saveVisitRecord(VisitRecord visitRecord) {
        visitRecordMapper.saveVisitRecord(visitRecord);
    }
}
