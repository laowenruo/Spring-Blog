package cn.isbut.mapper;

import cn.isbut.entity.VisitRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ryan
 * @Description:
 */
@Mapper
@Repository
public interface VisitRecordMapper {

    int saveVisitRecord(VisitRecord visitRecord);

    /**
    * 按天数查询访问记录
    * @param limit
    * @return {@link List< VisitRecord>}
    * @author Mashiro
    * @data 2021/5/28 14:26
    *
    */
    List<VisitRecord> getVisitRecordListByLimit(Integer limit);


}
