package cn.isbut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Description:
 * @Author: Mashiro
 * @Date: Created in 2021/6/3 20:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveInfoVO {
    /**
     * id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 发表时间
     */
    private Date createTime;

}
