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
	 * @param limit 限制
	 * @return 访问记录
	 */
	List<VisitRecord> getVisitRecordListByLimit(Integer limit);

	/**
	 * 保存访问记录
	 * @param visitRecord 访问记录
	 * @return 成功与否状态
	 */
	int saveVisitRecord(VisitRecord visitRecord);
}
