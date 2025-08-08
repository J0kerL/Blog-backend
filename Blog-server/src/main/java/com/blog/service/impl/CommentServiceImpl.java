package com.blog.service.impl;

import com.blog.dto.AddCommentDTO;
import com.blog.dto.CommentAdminPageQueryDTO;
import com.blog.dto.CommentPageQueryDTO;
import com.blog.exception.ArticleIdNotNullException;
import com.blog.exception.CommentNotNullException;
import com.blog.exception.CreateCommentFailException;
import com.blog.exception.UserIdNotNullException;
import com.blog.mapper.CommentMapper;
import com.blog.result.PageResult;
import com.blog.service.CommentService;
import com.blog.vo.AddCommentVO;
import com.blog.vo.CommentAdminVO;
import com.blog.vo.CommentVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-01-27
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public PageResult pageQuery(CommentPageQueryDTO commentPageQueryDTO) {
        log.info("分页查询评论，参数：{}", commentPageQueryDTO);

        PageHelper.startPage(commentPageQueryDTO.getPage(), commentPageQueryDTO.getPageSize());
        Page<CommentVO> page = commentMapper.pageQuery(commentPageQueryDTO);

        long total = page.getTotal();
        List<CommentVO> list = page.getResult();

        return new PageResult(total, list);
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        log.info("批量删除评论，ids：{}", ids);

        if (ids == null || ids.isEmpty()) {
            throw new RuntimeException("删除的评论ID不能为空");
        }

        int result = commentMapper.batchDelete(ids);
        if (result == 0) {
            throw new RuntimeException("删除评论失败");
        }

        log.info("成功删除{}条评论", result);
    }

    @Override
    public AddCommentVO createComment(AddCommentDTO addCommentDTO) {
        log.info("创建评论，参数：{}", addCommentDTO);

        // 参数校验
        if (addCommentDTO.getArticleId() == null) {
            throw new ArticleIdNotNullException("文章ID不能为空");
        }
        if (addCommentDTO.getContent() == null || addCommentDTO.getContent().trim().isEmpty()) {
            throw new CommentNotNullException("评论内容不能为空");
        }

        // 从上下文中获取当前登录用户ID
        Integer currentUserId = com.blog.context.UserContextHolder.getCurrentId();
        if (currentUserId == null) {
            throw new UserIdNotNullException("用户未登录");
        }
        addCommentDTO.setUserId(currentUserId);

        // 设置创建时间
        if (addCommentDTO.getCreateTime() == null) {
            addCommentDTO.setCreateTime(java.time.LocalDateTime.now());
        }

        // 插入评论
        int result = commentMapper.insertComment(addCommentDTO);
        if (result == 0) {
            throw new CreateCommentFailException("创建评论失败");
        }

        // 查询并返回创建的评论信息
        AddCommentVO addCommentVO = commentMapper.selectCommentById(addCommentDTO.getId());
        if (addCommentVO == null) {
            throw new CreateCommentFailException("创建评论失败");
        }

        log.info("成功创建评论，ID：{}，用户ID：{}", addCommentVO.getId(), currentUserId);
        return addCommentVO;
    }

    @Override
    public Long getTotalCommentCount(Integer articleId) {
        log.info("获取文章{}的总评论数量", articleId);

        if (articleId == null) {
            throw new RuntimeException("文章ID不能为空");
        }

        Long count = commentMapper.countByArticleId(articleId);
        log.info("文章{}的总评论数量为：{}", articleId, count);

        return count != null ? count : 0L;
    }

    @Override
    public PageResult adminPageQuery(CommentAdminPageQueryDTO commentAdminPageQueryDTO) {
        log.info("管理端分页查询评论，参数：{}", commentAdminPageQueryDTO);
        PageHelper.startPage(commentAdminPageQueryDTO.getPage(), commentAdminPageQueryDTO.getPageSize());
        Page<CommentAdminVO> page = commentMapper.adminPageQuery(commentAdminPageQueryDTO);
        long total = page.getTotal();
        List<CommentAdminVO> pageResult = page.getResult();
        return new PageResult(total, pageResult);
    }
}