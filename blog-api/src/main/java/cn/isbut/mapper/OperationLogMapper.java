package cn.isbut.mapper;

import cn.isbut.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ryan
 * @Description:
 */
@Mapper
@Repository
public interface OperationLogMapper {

    int saveOperationLog(OperationLog log);

    int deleteOperationLogById(Long id);

    List<OperationLog> getOperationLogListByDate(String startDate, String endDate);



}
