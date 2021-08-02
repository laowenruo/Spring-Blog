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
public class About {
    private Long id;
    private String nameEn;
    private String nameZh;
    private String value;
}
