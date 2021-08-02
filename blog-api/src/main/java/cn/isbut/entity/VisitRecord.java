package cn.isbut.entity;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Ryan
 */
@Data
@NoArgsConstructor
public class VisitRecord {
    private Long id;
    private Integer pv;//访问量(点击量)
    private Integer uv;//独立用户
    private String date;//日期"02-23"

    public VisitRecord(Integer pv, Integer uv, String date) {
        this.pv = pv;
        this.uv = uv;
        this.date = date;
    }
}
