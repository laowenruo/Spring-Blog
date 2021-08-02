package cn.isbut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Ryan
 * @Description: 访客更新DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitLogUuidTimeDTO {
    private String uuid;
    private Date time;
    private Integer pv;
}
