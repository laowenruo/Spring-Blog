package cn.isbut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 分页结果
 * @Author: Mashiro
 * @Date: Created in 2021/5/27 21:22
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageResultVO<T> {

    private Integer totalPage;//总页数
    private List<T> list;//数据

}
