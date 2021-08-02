package cn.isbut.controller;

import cn.isbut.dto.CommentDTO;
import cn.isbut.util.*;
import cn.isbut.vo.PageCommentVO;
import cn.isbut.vo.PageResultVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.isbut.annotation.AccessLimit;
import cn.isbut.common.Result;
import cn.isbut.entity.Comment;
import cn.isbut.entity.User;
import cn.isbut.service.BlogService;
import cn.isbut.service.CommentService;
import cn.isbut.service.Impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ryan
 * @Description:
 */
@Api(tags = "评论模块")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    MailProperties mailProperties;
    @Autowired
    MailUtils mailUtils;
    private String blogName;
    private String cmsUrl;
    private String websiteUrl;

    @Value("${custom.mail.blog.name}")
    public void setBlogName(String blogName) {
        this.blogName = blogName;
    }

    @Value("${custom.mail.url.cms}")
    public void setCmsUrl(String cmsUrl) {
        this.cmsUrl = cmsUrl;
    }

    @Value("${custom.mail.url.website}")
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    /**
    * 获取博客下面的评论列表
    * @param page
    * @param blogId
    * @param pageNum 页码
    * @param pageSize 每页个数
    * @return {@link Result}
    * @author ryan
    * @date 2021/6/1 19:37
    */
    @ApiOperation(value = "评论列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "所属页面（1.博客 2. 关于我（暂未开放））", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "blogId", value = "博客id", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "pageNum", value = "页码", required = true, defaultValue = "1", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", required = true, defaultValue = "10", dataType = "Integer", paramType = "query")
    })
    @GetMapping("/comments")
    public Result comments(@RequestParam Integer page,
                           @RequestParam(defaultValue = "") Long blogId,
                           @RequestParam(defaultValue = "1") Integer pageNum,
                           @RequestParam(defaultValue = "7") Integer pageSize){
        int i = checkCommentEnabled(page, blogId);
        if (i == 1){
            return Result.error("评论已经关闭");
        }
        Integer count = commentService.countByPageAndIsPublished(page, blogId);
        PageHelper.startPage(pageNum, pageSize);
        PageInfo<PageCommentVO> pageInfo = new PageInfo<>(commentService.getPageCommentList(page, blogId, (long) -1));
        PageResultVO<PageCommentVO> pageResult = new PageResultVO<>(pageInfo.getPages(), pageInfo.getList());
        Map<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("comments", pageResult);
        return Result.success(map);
    }

    @ApiOperation(value = "回复评论后重新加载父评论下所有评论")
    @ApiImplicitParam(name = "parentCommentId", value = "父评论Id",defaultValue = "", required = true, dataType = "Long", paramType = "query")
    @GetMapping("/comments/reply")
    public Result commentsReply(@RequestParam(defaultValue = "") Long parentCommentId){

        List<PageCommentVO> replyCommentList = commentService.getReplyCommentList(parentCommentId);
        Map<String, Object> map = new HashMap<>();
        map.put("replyCommentList",replyCommentList);
        return Result.success(map);
    }


    /**
    * 评论
    * @param commentDTO
    * @param request
    * @param jwt
    * @return {@link Result}
    * @author ryan
    * @date 2021/6/1 20:50
    */
    @ApiOperation(value = "评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentDTO", value = "评论DTO对象", required = true, dataType = "CommentDTO", paramType = "body"),
            @ApiImplicitParam(name = "jwt", value = "Token", required = true, dataType = "String", paramType = "header"),
    })
    @AccessLimit(seconds = 30, maxCount = 1, msg = "30秒内只能提交一次评论")
    @PostMapping("/comment")
    public Result postComment(@RequestBody CommentDTO commentDTO,
                              HttpServletRequest request,
                              @RequestHeader(value = "Authorization", defaultValue = "") String jwt){
        //评论内容合法性校验
        if (StringUtils.isEmpty(commentDTO.getContent()) || commentDTO.getContent().length() > 250 ||
                commentDTO.getPage() == null || commentDTO.getParentCommentId() == null) {
            return Result.error("参数有误");
        }
        //是否访客的评论
        boolean isVisitorComment = false;
        //父评论
        Comment parentComment = null;
        //对于有指定父评论的评论，应该以父评论为准，只判断页面可能会被绕过“评论开启状态检测”
        if (commentDTO.getParentCommentId() != -1){
            //当前评论为子评论
            parentComment = commentService.getCommentById(commentDTO.getParentCommentId());
            //所属页面
            Integer page = parentComment.getPage();
            //所属文章id
            Long blogId = page == 0 ? parentComment.getBlog().getId() : null;
            commentDTO.setPage(page);
            commentDTO.setBlogId(blogId);
        }else {
            //当前评论为顶级评论
            if (commentDTO.getPage() != 0) {
                commentDTO.setBlogId(null);
            }
        }
        //判断是否可评论
        int checkResult = checkCommentEnabled(commentDTO.getPage(), commentDTO.getBlogId());
        if (checkResult == 1){
            return Result.error("评论已经关闭");
        }
        //博主评论
        //验证Token合法性
        if (JwtUtils.judgeTokenIsExist(jwt)){
            String subject;
            try {
                subject = JwtUtils.getTokenBody(jwt).getSubject();;
            } catch (Exception e) {
                e.printStackTrace();
                return Result.create(403, "Token已失效，请重新验证密码！");
            }
            if (subject.startsWith("admin:")){
                //Token验证通过，获取Token中用户名
                String username = subject.replace("admin:", "");
                User admin = (User) userServiceImpl.loadUserByUsername(username);
                if (admin == null) {
                    return Result.create(403, "博主身份Token已失效，请重新登录！");
                }
                setAdminComment(commentDTO, request, admin);
                isVisitorComment = false;
            }
        }else {//如果没有token，则为游客评论
                //评论内容合法性校验
            if (commentDTO.getContent().length() > 250 ||
                    commentDTO.getPage() == null || commentDTO.getParentCommentId() == null) {
                return Result.error("参数有误");
            }
            setVisitorComment(commentDTO, request);
            isVisitorComment = true;
        }
        commentService.saveComment(commentDTO);
        checkSendMail(commentDTO, isVisitorComment, parentComment);
        return Result.success("评论成功");
    }

    /**
     * 查询对应页面评论是否开启
     *
     * @param page   页面分类（0普通文章，1关于我，2友链）
     * @param blogId 如果page==0，需要博客id参数，校验文章是否公开状态
     * @return 0:公开可查询状态 1:评论关闭 2:该博客不存在 3:文章受密码保护
     */
    private int checkCommentEnabled(Integer page, Long blogId) {
        //普通博客
        if (page == 0) {
            Boolean commentEnabled = blogService.getCommentEnabledByBlogId(blogId);
            Boolean published = blogService.getPublishedByBlogId(blogId);
            //未查询到此博客
            if (commentEnabled == null || published == null) {
                return 2;
                //博客未公开
            } else if (!published) {
                return 2;
                //博客评论已关闭
            } else if (!commentEnabled) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * 设置博主评论属性
     *
     * @param commentDTO 评论DTO
     * @param request 获取ip
     * @param admin   博主信息
     */
    private void setAdminComment(CommentDTO commentDTO, HttpServletRequest request, User admin) {
        commentDTO.setAdminComment(true);
        commentDTO.setCreateTime(new Date());
        commentDTO.setPublished(true);
        commentDTO.setAvatar(admin.getAvatar());
        commentDTO.setNickname(admin.getNickname());
        commentDTO.setEmail(admin.getEmail());
        commentDTO.setIp(IpAddressUtils.getIpAddress(request));
        commentDTO.setNotice(false);
    }
    /**
     * 设置访客评论属性
     *
     * @param commentDTO 评论DTO
     * @param request 用于获取ip
     */
    private void setVisitorComment(CommentDTO commentDTO, HttpServletRequest request) {
        //获取评论的昵称
        String commentNickname = commentDTO.getNickname();
        try {
            //判断评论昵称是否为QQ
            if (QQInfoUtils.isQQNumber(commentNickname)) {
                commentDTO.setQq(commentNickname);
                commentDTO.setNickname(QQInfoUtils.getQQNickname(commentNickname));
                commentDTO.setAvatar(QQInfoUtils.getQQAvatarURL(commentNickname));
            } else {
                commentDTO.setNickname(commentDTO.getNickname().trim());
                setCommentRandomAvatar(commentDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            commentDTO.setNickname(commentDTO.getNickname().trim());
            setCommentRandomAvatar(commentDTO);
        }
        commentDTO.setAdminComment(false);
        commentDTO.setCreateTime(new Date());
        commentDTO.setPublished(true);//默认不需要审核
        commentDTO.setEmail(commentDTO.getEmail().trim());
        commentDTO.setIp(IpAddressUtils.getIpAddress(request));
    }

    /**
     * 对于昵称不是QQ号的评论，根据昵称Hash设置头像
     *
     * @param commentDTO 评论DTO
     */
    private void setCommentRandomAvatar(CommentDTO commentDTO) {
        //设置随机头像
        long nicknameHash = HashUtils.getMurmurHash32(commentDTO.getNickname());//根据评论昵称取Hash，保证每一个昵称对应一个头像
        long num = nicknameHash % 6 + 1;//计算对应的头像
        String avatar = "/img/comment-avatar/" + num + ".jpg";
        commentDTO.setAvatar(avatar);
    }

    /**
     * 判断是否发送邮件
     * 6种情况：
     * 1.我以父评论提交：不用邮件提醒
     * 2.我回复我自己：不用邮件提醒
     * 3.我回复访客的评论：只提醒该访客
     * 4.访客以父评论提交：只提醒我自己
     * 5.访客回复我的评论：只提醒我自己
     * 6.访客回复访客的评论(即使是他自己先前的评论)：提醒我自己和他回复的评论
     *
     * @param commentDTO          当前评论
     * @param isVisitorComment 是否访客评论
     * @param parentComment    父评论
     */
    private void checkSendMail(CommentDTO commentDTO, boolean isVisitorComment, Comment parentComment) {
        if (parentComment != null && !parentComment.getAdminComment() && parentComment.getNotice()) {
            //我回复访客的评论，且对方接收提醒，邮件提醒对方(3)
            //访客回复访客的评论(即使是他自己先前的评论)，且对方接收提醒，邮件提醒对方(6)
            sendMailToParentComment(parentComment, commentDTO);
        }
        if (isVisitorComment) {
            //访客以父评论提交，只邮件提醒我自己(4)
            //访客回复我的评论，邮件提醒我自己(5)
            if (parentComment == null || parentComment.getAdminComment()){
                sendMailToMe(commentDTO);
            }
        }
    }

    /**
     * 发送邮件提醒回复对象
     *
     * @param parentComment 父评论
     * @param commentDTO       当前评论
     */
    private void sendMailToParentComment(Comment parentComment, CommentDTO commentDTO) {
        String path = "";
        String title = "";
        if (commentDTO.getPage() == 0) {
            //普通博客
            title = parentComment.getBlog().getTitle();
            path = "/blog/" + commentDTO.getBlogId();
        } else if (commentDTO.getPage() == 1) {
            //关于我页面
            title = "关于我";
            path = "/about";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("parentNickname", parentComment.getNickname());
        map.put("nickname", commentDTO.getNickname());
        map.put("title", title);
        map.put("time", commentDTO.getCreateTime());
        map.put("parentContent", parentComment.getContent());
        map.put("content", commentDTO.getContent());
        map.put("url", websiteUrl + path);
        String toAccount = parentComment.getEmail();
        String subject = "您在 " + blogName + " 的评论有了新回复";
        mailUtils.sendHtmlTemplateMail(map, toAccount, subject, "guest.html");
    }

    /**
     * 发送邮件提醒我自己
     *
     * @param commentDTO 当前评论
     */
    private void sendMailToMe(CommentDTO commentDTO) {
        String path = "";
        String title = "";
        if (commentDTO.getPage() == 0) {
            //普通博客
            title = blogService.getTitleByBlogId(commentDTO.getBlogId());
            path = "/blog/" + commentDTO.getBlogId();
        } else if (commentDTO.getPage() == 1) {
            //关于我页面
            title = "关于我";
            path = "/about";
        }
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("time", commentDTO.getCreateTime());
        map.put("nickname", commentDTO.getNickname());
        map.put("content", commentDTO.getContent());
        map.put("ip", commentDTO.getIp());
        map.put("email", commentDTO.getEmail());
        map.put("status", commentDTO.getPublished() ? "公开" : "待审核");
        map.put("url", websiteUrl + path);
        map.put("manageUrl", cmsUrl + "/comments");
        String toAccount = mailProperties.getUsername();
        String subject = blogName + " 收到新评论";
        mailUtils.sendHtmlTemplateMail(map, toAccount, subject, "owner.html");
    }

}
