package cn.isbut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description:
 * @Author: BeforeOne
 * @Date: Created in 2021/5/27 14:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewBlogVO {
    private Long id;
    private String firstPicture;
    private String title;
    private Date createTime;
}
