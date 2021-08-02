package cn.isbut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: BeforeOne
 * @Date: Created in 2021/5/27 14:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveBlogVO {
    private Long id;
    private String title;
    private String day;
}
