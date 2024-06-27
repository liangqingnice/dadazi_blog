package online.dadazi.dadaziblog.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.annotation.Resources;
import online.dadazi.dadaziblog.config.pojo.BasePojo;
import online.dadazi.dadaziblog.config.pojo.JsonResult;
import online.dadazi.dadaziblog.config.tools.ServletUtil;
import online.dadazi.dadaziblog.mapper.ArticleMapper;
import online.dadazi.dadaziblog.pojo.Article;
import online.dadazi.dadaziblog.pojo.ArticleCategory;
import online.dadazi.dadaziblog.service.ArticleCategoryService;
import online.dadazi.dadaziblog.mapper.ArticleCategoryMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author lqk
 */
@Service
public class ArticleCategoryServiceImpl extends ServiceImpl<ArticleCategoryMapper, ArticleCategory> implements ArticleCategoryService {


    @Resource
    private ArticleMapper articleMapper;

    @Override
    public Page<ArticleCategory> getList(ArticleCategory category) {
        Page<ArticleCategory> page = new Page<>(category.getPage(), category.getSize());
        LambdaQueryWrapper<ArticleCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(category.getCategoryAlias()), ArticleCategory::getCategoryAlias, category.getCategoryAlias());
        queryWrapper.like(StrUtil.isNotBlank(category.getCategoryName()), ArticleCategory::getCategoryName, category.getCategoryName());
        queryWrapper.eq(!Objects.isNull(category.getCreateUser()), ArticleCategory::getCreateUser, category.getCreateUser());

        return page(page, queryWrapper);
    }

    /**
     * 添加分类
     *
     * @param articleCategory 文章分类
     * @return {@link JsonResult }<{@link ? }>
     */
    @Override
    public JsonResult<?> insertArticleCategory(ArticleCategory articleCategory) {
        LambdaQueryWrapper<ArticleCategory> articleCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleCategoryLambdaQueryWrapper.eq(ArticleCategory::getCategoryName, articleCategory.getCategoryName());
        ArticleCategory categoryServiceNoDel = baseMapper.selectOne(articleCategoryLambdaQueryWrapper);
        if (!Objects.isNull(categoryServiceNoDel)) {
            return JsonResult.error("分类名称已存在,请换个名称重试");
        }
        //查询删除的数据
        ArticleCategory DelDbOne = baseMapper.selectDelOne(articleCategory);

        int flagCount = 0;
        if (Objects.nonNull(DelDbOne)) {
            Integer i = baseMapper.chanDelStatus(DelDbOne.getId(), "0");
            if(i==0){
                JsonResult.error("添加失败");
            }
            LambdaUpdateWrapper<ArticleCategory> lambda = new UpdateWrapper<ArticleCategory>().lambda();
            lambda.eq(ArticleCategory::getId,DelDbOne.getId());
            lambda.set(ArticleCategory::getCreateTime,LocalDateTime.now());
            lambda.set(ArticleCategory::getCreateUser,ServletUtil.getLoginId());
            lambda.set(ArticleCategory::getCategoryAlias,articleCategory.getCategoryAlias());
            flagCount = baseMapper.update(lambda);

            return  flagCount > 0 ? JsonResult.success("添加成功") : JsonResult.error("添加失败");
        }
        articleCategory.setCreateUser(ServletUtil.getLoginId());
        flagCount = baseMapper.insert(articleCategory);
        return flagCount > 0 ? JsonResult.success("添加成功") : JsonResult.error("添加失败");
    }

    /**
     * 更新文章分类
     *
     * @param articleCategory 文章分类
     * @return {@link JsonResult }<{@link ? }>
     */
    @Override
    public JsonResult<?> updateCategory(ArticleCategory articleCategory) {
        LambdaQueryWrapper<ArticleCategory> articleCategoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleCategoryLambdaQueryWrapper.eq(ArticleCategory::getCategoryName, articleCategory.getCategoryName());
        articleCategoryLambdaQueryWrapper.ne(ArticleCategory::getId, articleCategory.getId());
        ArticleCategory categoryServiceOne = baseMapper.selectOne(articleCategoryLambdaQueryWrapper);
        if (Objects.nonNull(categoryServiceOne)) {
            return JsonResult.success("修改失败!分类名称已存在");
        }
        int i = baseMapper.updateById(articleCategory);
        return i > 0 ? JsonResult.success("修改成功") : JsonResult.error("修改失败");
    }

    /**
     * 删除文章分类
     *
     * @param ids
     * @return
     */
    @Override
    public JsonResult<?> delCategory(List<Long> ids) {
        String categoryName = "";
        for (Long id : ids) {
            LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(Article::getCategoryId, id);
            Long l = articleMapper.selectCount(lambdaQueryWrapper);
            if (l > 0) {
                ArticleCategory articleCategory = baseMapper.selectById(id);
                if (Objects.nonNull(articleCategory)) {
                    categoryName = articleCategory.getCategoryName();
                }
                break;
            }
        }
        if (StrUtil.isNotBlank(categoryName)) {
            return JsonResult.error(categoryName + "下有文章,不可删除");
        }
        int i = baseMapper.deleteByIds(ids);


        return i > 0 ? JsonResult.success("删除成功") : JsonResult.error("删除失败");
    }
}




