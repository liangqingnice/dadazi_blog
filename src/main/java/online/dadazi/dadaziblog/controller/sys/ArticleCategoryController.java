package online.dadazi.dadaziblog.controller.sys;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import jakarta.validation.constraints.NotNull;
import online.dadazi.dadaziblog.config.pojo.BasePojo;
import online.dadazi.dadaziblog.config.pojo.JsonResult;

import online.dadazi.dadaziblog.config.tools.ServletUtil;
import online.dadazi.dadaziblog.pojo.ArticleCategory;
import online.dadazi.dadaziblog.service.ArticleCategoryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * 文章分类控制层
 *
 * @author lqk
 */
@RestController("/SysArticleCategoryController")
@RequestMapping("/articleCategory")
public class ArticleCategoryController {
    @Resource
    private ArticleCategoryService articleCategoryService;


    /**
     * 文章列表
     *
     * @param category 筛选条件
     * @return {@link JsonResult }<{@link IPage }>
     */
    @GetMapping("/list")
    public JsonResult<Page<ArticleCategory>> getList(ArticleCategory category) {
        return JsonResult.success(articleCategoryService.getList(category));
    }

    /**
     * 新增文章分类
     *
     * @param articleCategory 新增分类实体
     * @return {@link JsonResult }<{@link ? }>
     */
    @PostMapping("/insert")
    public JsonResult<?> insertCategory(@RequestBody @Valid ArticleCategory articleCategory) {
        return articleCategoryService.insertArticleCategory(articleCategory);
    }

    /**
     * 更新文章分类
     *
     * @param articleCategory 文章分类实体
     * @return {@link JsonResult }
     */
    @PutMapping("/update")
    public JsonResult<?> updateCategory(@RequestBody @Valid ArticleCategory articleCategory) {
        return articleCategoryService.updateCategory(articleCategory);
    }

    /**
     * 根据ids批量删除
     *
     * @param ids 删除ids
     * @return {@link JsonResult }<{@link ? }>
     */
    @DeleteMapping("/del/{ids}")
    public JsonResult<?> delCategory(@PathVariable List<Long> ids) {
        return articleCategoryService.delCategory(ids);
    }

    /**
     * 获取分类信息
     *
     * @param id 删除id
     * @return 分类信息
     */
    @GetMapping("/info")
    public JsonResult<?> categoryInfo(@Valid @NotNull(message = "参数错误") Long id) {
        return JsonResult.success(articleCategoryService.getById(id));
    }

    /**
     * 文章分类列表(不分页）
     *
     * @return
     */
    @GetMapping("/articleCategoryList")
    public JsonResult<List<ArticleCategory>>  getArticleCategoryList(){
       return JsonResult.success(articleCategoryService.list()) ;
    }
}
