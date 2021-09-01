package cn.isbut.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.isbut.annotation.OperationLogger;
import cn.isbut.model.vo.Result;
import cn.isbut.service.AboutService;

import java.util.Map;

/**
 * @author Ryan
 * @Description: 关于我页面后台管理
 *
 */
@RestController
@RequestMapping("/admin")
public class AboutAdminController {

	AboutService aboutService;

	/**
	 * 获取关于我页面配置
	 *
	 * @return result
	 */
	@GetMapping("/about")
	public Result about() {
		return Result.ok("请求成功", aboutService.getAboutSetting());
	}

	/**
	 * 修改关于我页面
	 *
	 * @param map 设置map
	 * @return result
	 */
	@OperationLogger("修改关于我页面")
	@PutMapping("/about")
	public Result updateAbout(@RequestBody Map<String, String> map) {
		aboutService.updateAbout(map);
		return Result.ok("修改成功");
	}

	@Autowired
	public void setAboutService(AboutService aboutService) {
		this.aboutService = aboutService;
	}
}
