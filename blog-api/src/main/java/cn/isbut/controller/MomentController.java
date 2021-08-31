package cn.isbut.controller;

import cn.isbut.util.JwtUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.isbut.annotation.AccessLimit;
import cn.isbut.annotation.VisitLogger;
import cn.isbut.entity.Moment;
import cn.isbut.entity.User;
import cn.isbut.model.vo.PageResult;
import cn.isbut.model.vo.Result;
import cn.isbut.service.MomentService;
import cn.isbut.service.impl.UserServiceImpl;

/**
 * @author Ryan
 * @Description: 动态
 *
 */
@RestController
public class MomentController {

	MomentService momentService;

	UserServiceImpl userService;

	/**
	 * 分页查询动态List
	 *
	 * @param pageNum 页码
	 * @param jwt     博主访问Token
	 * @return result
	 */
	@VisitLogger(behavior = "访问页面", content = "动态")
	@GetMapping("/moments")
	public Result moments(@RequestParam(defaultValue = "1") Integer pageNum,
	                      @RequestHeader(value = "Authorization", defaultValue = "") String jwt) {
		boolean adminIdentity = false;
		if (JwtUtils.judgeTokenIsExist(jwt)) {
			try {
				String subject = JwtUtils.getTokenBody(jwt).getSubject();
				if ("admin:".startsWith(subject)) {
					String username = subject.replace("admin:", "");
					User admin = (User) userService.loadUserByUsername(username);
					if (admin != null) {
						adminIdentity = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		PageInfo<Moment> pageInfo = new PageInfo<>(momentService.getMomentVOList(pageNum, adminIdentity));
		PageResult<Moment> pageResult = new PageResult<>(pageInfo.getPages(), pageInfo.getList());
		return Result.ok("获取成功", pageResult);
	}

	/**
	 * 给动态点赞
	 * 简单限制一下点赞
	 *
	 * @param id 动态id
	 * @return result
	 */
	@AccessLimit(seconds = 86400, maxCount = 1, msg = "不可以重复点赞哦")
	@VisitLogger(behavior = "点赞动态")
	@PostMapping("/moment/like")
	public Result like(@RequestParam Integer id) {
		momentService.addLikeByMomentId(id);
		return Result.ok("点赞成功");
	}

	@Autowired
	public void setMomentService(MomentService momentService) {
		this.momentService = momentService;
	}

	@Autowired
	public void setUserService(UserServiceImpl userService) {
		this.userService = userService;
	}
}
