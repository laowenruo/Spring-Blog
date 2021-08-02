package cn.isbut.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: Mashiro
 * @Date: Created in 2021/5/30 10:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntroductionVO {
    private String avatar;
    private String name;
    private String github;
    private String qq;
    private String bilibili;
    //private String netease;
    private String email;

    private List<String> rollText = new ArrayList<>();
    private List<FavoriteVO> favorites = new ArrayList<>();
}
