package cn.isbut.mapper;

import cn.isbut.entity.ExceptionLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: ryan
 */
@Mapper
@Repository
public interface ExceptionLogMapper {

    /**
    * 新增异常日志
    * @param log
    * @return {@link int}
    * @author ryan
    */
    int saveExceptionLog(ExceptionLog log);

    /**
    * 删除日志
    * @param id
    * @return {@link int}
    * @author ryan
    */
    int deleteExceptionLogById(Long id);

    /**
    * 查询日志
    * @param startDate
    * @param endDate
    * @return {@link List< ExceptionLog>}
    * @author ryan
    */
    List<ExceptionLog> getExceptionLogListByDate(String startDate, String endDate);

}
