package com.blog.service.impl;

import com.blog.context.UserContextHolder;
import com.blog.dto.ArticleAddDTO;
import com.blog.dto.ArticlePageQueryDTO;
import com.blog.dto.ArticleUpdateDTO;
import com.blog.entity.Article;
import com.blog.exception.ArticleNotFoundException;
import com.blog.mapper.*;
import com.blog.result.PageResult;
import com.blog.service.ArticleService;
import com.blog.vo.ArticleVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.blog.constant.Constant.ARTICLE_NOT_FOUND;

/**
 * @author Diamond
 * @create 2025-06-04 14:26
 */
@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ArticleTagMapper articleTagMapper;
    @Resource
    private CommentMapper commentMapper;

    /**
     * 新增文章
     *
     * @param articleAddDTO
     */
    @Override
    @Transactional
    public void add(ArticleAddDTO articleAddDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleAddDTO, article);
        
        // 如果没有提供摘要，自动从内容生成
        if ((article.getSummary() == null || article.getSummary().trim().isEmpty()) 
            && article.getContent() != null) {
            article.setSummary(generateSummaryFromContent(article.getContent()));
        }
        
        // 获取当前登录用户ID
        Integer currentUserId = UserContextHolder.getCurrentId();
        article.setAuthorId(currentUserId);
        articleMapper.insert(article);

        // 处理标签
        if (articleAddDTO.getTagIds() != null && !articleAddDTO.getTagIds().isEmpty()) {
            articleTagMapper.batchInsert(article.getId(), articleAddDTO.getTagIds());
        }
    }

    /**
     * 更新文章
     *
     * @param articleUpdateDTO
     */
    @Override
    @Transactional
    public void update(ArticleUpdateDTO articleUpdateDTO) {
        // 判断文章是否存在
        if (articleMapper.selectById(articleUpdateDTO.getId()) == null) {
            // 不存在 返回错误信息
            throw new ArticleNotFoundException(ARTICLE_NOT_FOUND);
        }
        // 存在 执行更新操作
        Article article = new Article();
        BeanUtils.copyProperties(articleUpdateDTO, article);
        
        // 如果没有提供摘要但更新了内容，自动从内容生成摘要
        if ((article.getSummary() == null || article.getSummary().trim().isEmpty()) 
            && article.getContent() != null) {
            article.setSummary(generateSummaryFromContent(article.getContent()));
        }
        
        articleMapper.update(article);

        // 更新标签
        if (articleUpdateDTO.getTagIds() != null) {
            articleTagMapper.deleteByArticleId(article.getId());
            if (!articleUpdateDTO.getTagIds().isEmpty()) {
                articleTagMapper.batchInsert(article.getId(), articleUpdateDTO.getTagIds());
            }
        }
    }

    /**
     * 根据ID删除文章
     *
     * @param id
     */
    @Override
    @Transactional
    public void deleteById(Integer id) {
        // 根据id查询文章
        Article article = articleMapper.selectById(id);
        if (article == null) {
            // 不存在 返回错误信息
            throw new ArticleNotFoundException(ARTICLE_NOT_FOUND);
        }
        // 存在，删除文章
        articleMapper.deleteById(id);
        // 删除文章-标签关联
        articleTagMapper.deleteByArticleId(id);
        // 删除文章评论
        commentMapper.deleteByArticleId(id);
    }

    /**
     * 批量删除文章
     *
     * @param ids
     */
    @Override
    @Transactional
    public void batchDelete(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        // 检查所有文章是否存在
        for (Integer id : ids) {
            Article article = articleMapper.selectById(id);
            if (article == null) {
                // 不存在 返回错误信息
                throw new ArticleNotFoundException(ARTICLE_NOT_FOUND);
            }
        }
        // 批量删除文章
        articleMapper.batchDelete(ids);
        // 批量删除文章-标签关联
        articleTagMapper.batchDeleteByArticleIds(ids);
        // 批量删除文章评论
        commentMapper.batchDeleteByArticleIds(ids);
    }

    /**
     * 获取文章详情
     *
     * @param id
     * @return
     */
    @Override
    public ArticleVO getById(Integer id) {
        // 根据id查询文章
        Article article = articleMapper.selectById(id);
        if (article == null) {
            // 不存在 返回错误信息
            throw new ArticleNotFoundException(ARTICLE_NOT_FOUND);
        }
        
        // 检查文章访问权限：草稿文章只有作者能访问
        Integer currentUserId = UserContextHolder.getCurrentId();
        if (article.getStatus() == 0) { // 草稿状态
            if (currentUserId == null || !currentUserId.equals(article.getAuthorId())) {
                // 未登录或不是作者，无权访问草稿
                throw new ArticleNotFoundException(ARTICLE_NOT_FOUND);
            }
        }
        
        // 存在且有权限 执行查询操作
        return convertToVO(article);
    }

    /**
     * 分页查询文章列表
     *
     * @param articlePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(ArticlePageQueryDTO articlePageQueryDTO) {
        // 设置当前用户ID，用于过滤草稿文章
        Integer currentUserId = UserContextHolder.getCurrentId();
        articlePageQueryDTO.setCurrentUserId(currentUserId);
        
        PageHelper.startPage(articlePageQueryDTO.getPage(), articlePageQueryDTO.getPageSize());
        Page<Article> page = articleMapper.pageQuery(articlePageQueryDTO);

        // 将Article转换为ArticleVO
        List<ArticleVO> articleVOs = page.getResult().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());

        return new PageResult(page.getTotal(), articleVOs);
    }

    private ArticleVO convertToVO(Article article) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);

        // 设置分类名称
        articleVO.setCategoryName(categoryMapper.getById(article.getCategoryId()).getName());

        // 设置作者名称
        articleVO.setAuthorName(userMapper.getById(article.getAuthorId()).getUsername());

        // 设置标签
        articleVO.setTags(articleTagMapper.selectTagNamesByArticleId(article.getId()));

        return articleVO;
    }

    /**
     * 从Markdown内容自动生成摘要
     *
     * @param content Markdown格式的文章内容
     * @return 生成的摘要
     */
    private String generateSummaryFromContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            return "暂无内容摘要";
        }

        // 移除Markdown语法，生成纯文本摘要
        String plainText = content
                // 移除标题语法
                .replaceAll("#{1,6}\\s+", "")
                // 移除粗体语法
                .replaceAll("\\*\\*(.*?)\\*\\*", "$1")
                // 移除斜体语法
                .replaceAll("\\*(.*?)\\*", "$1")
                // 移除代码块
                .replaceAll("```[\\s\\S]*?```", "")
                // 移除行内代码
                .replaceAll("`(.*?)`", "$1")
                // 移除图片语法
                .replaceAll("!\\[.*?\\]\\(.*?\\)", "")
                // 移除链接语法
                .replaceAll("\\[.*?\\]\\(.*?\\)", "")
                // 移除引用语法
                .replaceAll("^>\\s+", "")
                // 移除列表语法
                .replaceAll("^[-*+]\\s+", "")
                .replaceAll("^\\d+\\.\\s+", "")
                // 移除表格语法
                .replaceAll("\\|.*\\|", "")
                .replaceAll("[-:]+", "")
                // 替换多个换行为单个空格
                .replaceAll("\\n+", " ")
                // 移除多余空格
                .replaceAll("\\s+", " ")
                .trim();

        // 截取前200个字符作为摘要
        if (plainText.length() > 200) {
            return plainText.substring(0, 200) + "...";
        }
        
        return plainText.isEmpty() ? "暂无内容摘要" : plainText;
    }
}
