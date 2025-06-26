package com.blog.service.impl;

import com.blog.dto.CommentPageQueryDTO;
import com.blog.mapper.CommentMapper;
import com.blog.result.PageResult;
import com.blog.service.CommentService;
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
}