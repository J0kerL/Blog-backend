package com.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.blog.dto.CategoryDTO;
import com.blog.dto.CategoryPageQueryDTO;
import com.blog.entity.Category;
import com.blog.exception.CategoryNotFoundException;
import com.blog.mapper.ArticleMapper;
import com.blog.mapper.CategoryMapper;
import com.blog.result.PageResult;
import com.blog.service.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;
    
    @Resource
    private ArticleMapper articleMapper;

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        long total = page.getTotal();
        List<Category> list = page.getResult();
        return new PageResult(total, list);
    }

    @Override
    public void addCategory(CategoryDTO categoryDTO) {
        // 检查分类名称是否已存在
        if (categoryMapper.getByName(categoryDTO.getName()) != null) {
            throw new CategoryNotFoundException("分类名称已存在");
        }
        
        Category category = new Category();
        BeanUtil.copyProperties(categoryDTO, category);
        categoryMapper.addCategory(category);
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.getById(categoryDTO.getId());
        if (category == null) {
            throw new CategoryNotFoundException("分类不存在");
        }
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        categoryMapper.updateCategory(category);
    }

    @Override
    @Transactional
    public void deleteByIds(String ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        for (Integer id : idList) {
            Category category = categoryMapper.getById(id);
            if (category == null) {
                throw new CategoryNotFoundException("分类不存在");
            }
            
            // 检查该分类下是否有文章
            int articleCount = articleMapper.countByCategoryId(id);
            if (articleCount > 0) {
                throw new CategoryNotFoundException("请先清空该分类下的文章");
            }
        }
        categoryMapper.deleteByIds(idList);
    }

    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }
}