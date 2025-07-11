package com.blog.mapper;

import com.blog.dto.MenuPageQueryDTO;
import com.blog.entity.Menu;
import com.blog.vo.MenuVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author Java小猪
 * @Date 2025/4/14 14:25
 */
public interface MenuMapper {

    /**
     * 获取菜单
     * @return
     */
    @Select("SELECT * FROM menu ORDER BY sort ASC")
    List<MenuVO> getMenu();

    /**
     * 分页查询菜单
     * @param menuPageQueryDTO
     * @return
     */
    Page<Menu> pageQuery(MenuPageQueryDTO menuPageQueryDTO);

    /**
     * 根据ID查询菜单
     * @param id
     * @return
     */
    @Select("SELECT * FROM menu WHERE id = #{id}")
    Menu getById(Integer id);

    /**
     * 新增菜单
     * @param menu
     */
    @Insert("INSERT INTO menu (title, icon, path, component, parent_id, sort, hidden) " +
            "VALUES (#{title}, #{icon}, #{path}, #{component}, #{parentId}, #{sort}, #{hidden})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Menu menu);

    /**
     * 更新菜单
     * @param menu
     */
    @Update("UPDATE menu SET title = #{title}, icon = #{icon}, path = #{path}, " +
            "component = #{component}, parent_id = #{parentId}, sort = #{sort}, hidden = #{hidden} " +
            "WHERE id = #{id}")
    void update(Menu menu);

    /**
     * 删除菜单
     * @param id
     */
    @Delete("DELETE FROM menu WHERE id = #{id}")
    void deleteById(Integer id);

    /**
     * 批量删除菜单
     * @param ids
     */
    void deleteByIds(List<Integer> ids);

    /**
     * 根据标题查询菜单
     * @param title
     * @return
     */
    @Select("SELECT * FROM menu WHERE title = #{title}")
    Menu getByTitle(String title);

    /**
     * 根据路径查询菜单
     * @param path
     * @return
     */
    @Select("SELECT * FROM menu WHERE path = #{path}")
    Menu getByPath(String path);

    /**
     * 查询所有菜单
     * @return
     */
    @Select("SELECT * FROM menu ORDER BY sort ASC")
    List<Menu> list();

}
