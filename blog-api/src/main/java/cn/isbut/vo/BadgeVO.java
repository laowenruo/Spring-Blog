package cn.isbut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: Mashiro
 * @Date: Created in 2021/5/30 10:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadgeVO {private String title;
    private String url;
    private String subject;
    private String value;
    private String color;

}
