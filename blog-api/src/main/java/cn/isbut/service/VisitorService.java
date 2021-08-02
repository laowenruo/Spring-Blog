package cn.isbut.service;

import cn.isbut.dto.VisitLogUuidTimeDTO;
import cn.isbut.entity.Visitor;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * @Description:
 * @Author: ryan
 */
public interface VisitorService {

    @Async
    void saveVisitor(Visitor visitor);

    void deleteVisitor(Long id, String uuid);

    void updatePVAndLastTimeByUUID(VisitLogUuidTimeDTO dto);

    boolean hasUUID(String uuid);

    List<Visitor> getVisitorListByDate(String startDate, String endDate);

    List<String> getNewVisitorIpSourceByYesterday();


}
