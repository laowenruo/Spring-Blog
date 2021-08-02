package cn.isbut.mapper;

import cn.isbut.entity.CityVisitor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Ryan
 * @Description:
 */
@Mapper
@Repository
public interface CityVisitorMapper {

    List<CityVisitor> getCityVisitorLis();

    int saveCityVisitor(CityVisitor cityVisitor);


}
