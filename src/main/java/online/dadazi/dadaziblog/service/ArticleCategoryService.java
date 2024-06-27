package online.dadazi.dadaziblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.dadazi.dadaziblog.config.pojo.JsonResult;
import online.dadazi.dadaziblog.pojo.ArticleCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author lqk
 */
public interface ArticleCategoryService extends IService<ArticleCategory> {

    /**
     * 获取分类列表
     *
     * @param category 分类
     * @return {@link Page }<{@link ArticleCategory }>
     */
    Page<ArticleCategory> getList(ArticleCategory category);

    /**
     * 添加分类名称
     *
     * @param articleCategory 文章分类
     * @return {@link JsonResult }<{@link ? }>
     */
    JsonResult<?> insertArticleCategory(ArticleCategory articleCategory);

    /**
     * 更新文章分类
     *
     * @param articleCategory 文章分类
     * @return {@link JsonResult }<{@link ? }>
     */
    JsonResult<?> updateCategory(ArticleCategory articleCategory);

    /**
     * 删除文章分类
     *
     * @param ids
     * @return
     */
    JsonResult<?> delCategory(List<Long> ids);
}
