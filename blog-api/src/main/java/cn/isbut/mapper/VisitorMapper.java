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
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return 访客列表
	 */
	List<Visitor> getVisitorListByDate(String startDate, String endDate);

	/**
	 * 昨日新增访客
	 * @return 访客
	 */
	List<String> getNewVisitorIpSourceByYesterday();

	/**
	 * 判断是否存在uuid
	 * @param uuid 访客标识
	 * @return  成功与否状态
	 */
	int hasUUID(String uuid);

	/**
	 * 保存访客
	 * @param visitor 访客
	 * @return 成功与否状态
	 */
	int saveVisitor(Visitor visitor);

	/**
	 * 更新最后一次访问
	 * @param dto 访客dto
	 * @return 成功与否状态
	 */
	int updatePVAndLastTimeByUUID(VisitLogUuidTime dto);

	/**
	 * 删除访客
	 * @param id 访客id
	 * @return 成功与否状态
	 */
	int deleteVisitorById(Integer id);
}
