package cn.isbut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ryan
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityVisitor {
    private String city;//城市名称
    private Integer uv;//独立访客数量
}
