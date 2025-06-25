package com.blog.mapper;

import com.blog.entity.Tag;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author Java小猪
 * @Date 2025/4/3 17:00
 */
@Mapper
public interface TagMapper {

    /**
     * 分页查询标签
     * @param name 标签名称
     * @return
     */
    Page<Tag> page(@Param("name") String name);

    /**
     * 新增标签
     * @param tag
     * @return
     */
    int insert(Tag tag);

    /**
     * 修改标签
     * @param tag
     * @return
     */
    int update(Tag tag);

    /**
     * 根据ID删除标签
     * @param id
     * @return
     */
    int deleteById(Integer id);

    /**
     * 批量删除标签
     * @param ids
     * @return
     */
    int deleteByIds(@Param("ids") List<Integer> ids);

    /**
     * 根据ID查询标签
     * @param id
     * @return
     */
    Tag getById(Integer id);

    /**
     * 根据名称查询标签
     * @param name
     * @return
     */
    Tag getByName(String name);

    /**
     * 查询所有标签
     * @return
     */
    List<Tag> list();

}