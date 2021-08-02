package cn.isbut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Description:
 * @Author: BeforeOne
 * @Date: Created in 2021/5/27 14:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogIdAndTitleVO {
    private Long id;
    private String title;
}
