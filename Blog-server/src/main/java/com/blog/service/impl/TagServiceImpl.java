package com.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.blog.dto.TagDTO;
import com.blog.entity.Tag;
import com.blog.exception.BaseException;
import com.blog.mapper.ArticleTagMapper;
import com.blog.mapper.TagMapper;
import com.blog.result.PageResult;
import com.blog.service.TagService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Java小猪
 * @Date 2025/4/3 17:00
 */
@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    /**
     * 分页查询标签
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public PageResult page(Integer page, Integer pageSize, String name) {
        PageHelper.startPage(page, pageSize);
        Page<Tag> p = tagMapper.page(name);
        return new PageResult(p.getTotal(), p.getResult());
    }

    /**
     * 新增标签
     * @param tagDTO
     */
    @Override
    public void addTag(TagDTO tagDTO) {
        // 检查标签名称是否已存在
        Tag existTag = tagMapper.getByName(tagDTO.getName());
        if (existTag != null) {
            throw new BaseException("标签名称已存在");
        }

        Tag tag = new Tag();
        BeanUtil.copyProperties(tagDTO, tag);
        tagMapper.insert(tag);
    }

    /**
     * 修改标签
     * @param tagDTO
     */
    @Override
    public void updateTag(TagDTO tagDTO) {
        // 检查标签是否存在
        Tag tag = tagMapper.getById(tagDTO.getId());
        if (tag == null) {
            throw new BaseException("标签不存在");
        }

        // 检查标签名称是否已被其他标签使用
        Tag existTag = tagMapper.getByName(tagDTO.getName());
        if (existTag != null && !existTag.getId().equals(tagDTO.getId())) {
            throw new BaseException("标签名称已存在");
        }

        BeanUtil.copyProperties(tagDTO, tag);
        tagMapper.update(tag);
    }

    /**
     * 批量删除标签
     * @param ids
     */
    @Override
    public void deleteByIds(String ids) {
        List<Integer> tagIds = Arrays.stream(ids.split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        // 检查标签是否被文章使用
        for (Integer tagId : tagIds) {
            int count = articleTagMapper.countByTagId(tagId);
            if (count > 0) {
                Tag tag = tagMapper.getById(tagId);
                String tagName = tag != null ? tag.getName() : "ID为" + tagId;
                throw new BaseException("标签【" + tagName + "】正在被文章使用，无法删除");
            }
        }

        tagMapper.deleteByIds(tagIds);
    }

    /**
     * 查询所有标签
     * @return
     */
    @Override
    public List<Tag> list() {
        return tagMapper.list();
    }

}