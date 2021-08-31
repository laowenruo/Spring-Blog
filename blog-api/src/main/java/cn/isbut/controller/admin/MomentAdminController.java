package cn.isbut.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.isbut.annotation.OperationLogger;
import cn.isbut.entity.Moment;
import cn.isbut.model.vo.Result;
import cn.isbut.service.MomentService;

import java.util.Date;

/**
 * @author Ryan
 * @Description: 博客动态后台管理
 *
 */
@RestController
@RequestMapping("/admin")
public class MomentAdminController {

	MomentService momentService;

	/**
	 * 分页查询动态列表
	 *
	 * @param pageNum  页码
	 * @param pageSize 每页条数
	 * @return result
	 */
	@GetMapping("/moments")
	public Result moments(@RequestParam(defaultValue = "1") Integer pageNum,
	                      @RequestParam(defaultValue = "10") Integer pageSize) {
		String orderBy = "create_time desc";
		PageHelper.startPage(pageNum, pageSize, orderBy);
		PageInfo<Moment> pageInfo = new PageInfo<>(momentService.getMomentList());
		return Result.ok("请求成功", pageInfo);
	}

	/**
	 * 更新动态公开状态
	 *
	 * @param id        动态id
	 * @param published 是否公开
	 * @return  result
	 */
	@OperationLogger("更新动态公开状态")
	@PutMapping("/moment/published")
	public Result updatePublished(@RequestParam Integer id, @RequestParam Boolean published) {
		momentService.updateMomentPublishedById(id, published);
		return Result.ok("操作成功");
	}

	/**
	 * 根据id查询动态
	 *
	 * @param id 动态id
	 * @return result
	 */
	@GetMapping("/moment")
	public Result moment(@RequestParam Integer id) {
		return Result.ok("获取成功", momentService.getMomentById(id));
	}

	/**
	 * 删除动态
	 *
	 * @param id 动态id
	 * @return result
	 */
	@OperationLogger("删除动态")
	@DeleteMapping("/moment")
	public Result deleteMoment(@RequestParam Integer id) {
		momentService.deleteMomentById(id);
		return Result.ok("删除成功");
	}

	/**
	 * 发布动态
	 *
	 * @param moment 动态实体
	 * @return result
	 */
	@OperationLogger("发布动态")
	@PostMapping("/moment")
	public Result saveMoment(@RequestBody Moment moment) {
		if (moment.getCreateTime() == null) {
			moment.setCreateTime(new Date());
		}
		momentService.saveMoment(moment);
		return Result.ok("添加成功");
	}

	/**
	 * 更新动态
	 *
	 * @param moment 动态实体
	 * @return result
	 */
	@OperationLogger("更新动态")
	@PutMapping("/moment")
	public Result updateMoment(@RequestBody Moment moment) {
		if (moment.getCreateTime() == null) {
			moment.setCreateTime(new Date());
		}
		momentService.updateMoment(moment);
		return Result.ok("修改成功");
	}

	@Autowired
	public void setMomentService(MomentService momentService) {
		this.momentService = momentService;
	}
}
