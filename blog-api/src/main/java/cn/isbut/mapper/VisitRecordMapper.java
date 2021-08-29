package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.VisitRecord;

import java.util.List;

/**
 * @author Ryan
 * @Description: 访问记录持久层接口
 *
 */
@Mapper
@Repository
public interface VisitRecordMapper {
	/**
	 * 访问记录
	 * @param limit
	 * @return
	 */
	List<VisitRecord> getVisitRecordListByLimit(Integer limit);

	/**
	 * 保存访问记录
	 * @param visitRecord
	 * @return
	 */
	int saveVisitRecord(VisitRecord visitRecord);
}
