package cn.isbut.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.isbut.entity.VisitRecord;
import cn.isbut.mapper.VisitRecordMapper;
import cn.isbut.service.VisitRecordService;

/**
 * @author Ryan
 * @Description: 访问记录业务层实现
 * 统计pv和uv
 */
@Service
public class VisitRecordServiceImpl implements VisitRecordService {

	VisitRecordMapper visitRecordMapper;

	@Override
	public void saveVisitRecord(VisitRecord visitRecord) {
		visitRecordMapper.saveVisitRecord(visitRecord);
	}

	@Autowired
	public void setVisitRecordMapper(VisitRecordMapper visitRecordMapper) {
		this.visitRecordMapper = visitRecordMapper;
	}
}
