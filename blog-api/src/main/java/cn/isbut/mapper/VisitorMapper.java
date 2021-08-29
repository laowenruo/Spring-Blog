package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.Visitor;
import cn.isbut.model.dto.VisitLogUuidTime;

import java.util.List;

/**
 * @author Ryan
 * @Description: 访客统计持久层接口
 */
@Mapper
@Repository
public interface VisitorMapper {
	/**
	 * 按日期搜索访客
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Visitor> getVisitorListByDate(String startDate, String endDate);

	/**
	 * 昨日新增访客
	 * @return
	 */
	List<String> getNewVisitorIpSourceByYesterday();

	/**
	 * 判断是否存在uuid
	 * @param uuid
	 * @return
	 */
	int hasUUID(String uuid);

	/**
	 * 保存访客
	 * @param visitor
	 * @return
	 */
	int saveVisitor(Visitor visitor);

	/**
	 * 更新最后一次访问
	 * @param dto
	 * @return
	 */
	int updatePVAndLastTimeByUUID(VisitLogUuidTime dto);

	/**
	 * 删除访客
	 * @param id
	 * @return
	 */
	int deleteVisitorById(Integer id);
}
