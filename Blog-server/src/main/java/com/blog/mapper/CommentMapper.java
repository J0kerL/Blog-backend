package com.blog.mapper;

import com.blog.dto.CommentPageQueryDTO;
import com.blog.vo.CommentVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Diamond
 * @create 2025-06-04 14:54
 */
public interface CommentMapper {

    /**
     * 分页查询评论
     * @param commentPageQueryDTO
     * @return
     */
    Page<CommentVO> pageQuery(CommentPageQueryDTO commentPageQueryDTO);

    /**
     * 批量删除评论
     * @param ids
     * @return
     */
    int batchDelete(@Param("ids") List<Integer> ids);

    /**
     * 根据文章ID删除评论
     * @param articleId
     * @return
     */
    int deleteByArticleId(@Param("articleId") Integer articleId);

    /**
     * 批量删除文章评论
     * @param articleIds
     * @return
     */
    int batchDeleteByArticleIds(@Param("articleIds") List<Integer> articleIds);

    /**
     * 统计评论总数
     * @return
     */
    @Select("select count(*) from comment")
    Long countComments();

    /**
     * 根据用户ID统计评论数量
     * @param userId
     * @return
     */
    @Select("select count(*) from comment where user_id = #{userId}")
    Long countByUserId(@Param("userId") Integer userId);

}
