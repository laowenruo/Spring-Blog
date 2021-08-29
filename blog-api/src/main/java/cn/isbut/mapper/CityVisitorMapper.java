package cn.isbut.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import cn.isbut.entity.CityVisitor;

import java.util.List;

/**
 * @author Ryan
 * @Description: 城市访客数量统计持久层接口
 *
 */
@Mapper
@Repository
public interface CityVisitorMapper {
	/**
	 * 查询城市访客数
	 * @return
	 */
	List<CityVisitor> getCityVisitorList();

	/**
	 * 保存城市访客
	 * @param cityVisitor
	 * @return
	 */
	int saveCityVisitor(CityVisitor cityVisitor);
}
