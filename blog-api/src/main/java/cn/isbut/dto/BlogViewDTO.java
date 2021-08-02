package cn.isbut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ryan
 * @Description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BlogViewDTO {
    private Long id;
    private Integer views;
}
