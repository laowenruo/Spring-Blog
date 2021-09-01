package cn.isbut.service;

import cn.isbut.entity.VisitRecord;

/**
 * @author Ryan
 */
public interface VisitRecordService {
	/**
	 * 保存访问记录
	 * @param visitRecord 访问记录
	 */
	void saveVisitRecord(VisitRecord visitRecord);
}
