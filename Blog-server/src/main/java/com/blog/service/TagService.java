package com.blog.service;

import com.blog.dto.TagDTO;
import com.blog.entity.Tag;
import com.blog.result.PageResult;

import java.util.List;

/**
 * @Author Java小猪
 * @Date 2025/4/3 17:00
 */
public interface TagService {

    /**
     * 分页查询标签
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    PageResult page(Integer page, Integer pageSize, String name);

    /**
     * 新增标签
     * @param tagDTO
     */
    void addTag(TagDTO tagDTO);

    /**
     * 修改标签
     * @param tagDTO
     */
    void updateTag(TagDTO tagDTO);

    /**
     * 批量删除标签
     * @param ids
     */
    void deleteByIds(String ids);

    /**
     * 查询所有标签
     * @return
     */
    List<Tag> list();

}