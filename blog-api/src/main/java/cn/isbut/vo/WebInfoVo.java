package cn.isbut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 底部信息展示
 * @Author: BeforeOne
 * @Date: Created in 2021/5/12 14:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebInfoVo {

    private Integer views;
    private Integer commentCount;

}
