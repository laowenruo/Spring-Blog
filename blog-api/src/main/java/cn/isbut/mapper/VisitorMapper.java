package cn.isbut.mapper;

import cn.isbut.dto.VisitLogUuidTimeDTO;
import cn.isbut.entity.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ryan
 * @Description:
 */
@Mapper
@Repository
public interface VisitorMapper {

    /**
    * 添加访客
    * @param visitor
    * @return {@link int}
    * @author Mashiro
    * @date 2021/5/30 15:57
    */
    int saveVisitor(Visitor visitor);

    /**
    * 删除访客
    * @param id
    * @return {@link int}
    * @author Mashiro
    * @date 2021/5/30 15:57
    */
    int deleteVisitorById(Long id);

    /**
    * 更新访客pv和最后访问时间
    * @param dto
    * @return {@link int}
    * @author Mashiro
    * @date 2021/5/30 15:57
    */
    int updatePVAndLastTimeByUUID(VisitLogUuidTimeDTO dto);

    /**
    * 查询是否存在某个uuid
    * @param uuid
    * @return {@link int}
    * @author Mashiro
    * @date 2021/5/30 15:57
    */
    int hasUUID(String uuid);

    /**
    * 查询访客
    * @param startDate
    * @param endDate
    * @return {@link List< Visitor>}
    * @author Mashiro
    * @date 2021/5/30 15:57
    */
    List<Visitor> getVisitorListByDate(String startDate, String endDate);

    /**
    * 查询昨天的所有新增访客的ip来源
    * @param
    * @return {@link List< String>}
    * @author Mashiro
    * @date 2021/5/30 15:57
    */
    List<String> getNewVisitorIpSourceByYesterday();


}
