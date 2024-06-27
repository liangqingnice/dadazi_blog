package online.dadazi.dadaziblog.controller.sys;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import online.dadazi.dadaziblog.config.pojo.JsonResult;
import online.dadazi.dadaziblog.pojo.Article;
import online.dadazi.dadaziblog.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 文章控制层
 *
 * @author lqk
 */
@RestController("SysArticleController")
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    /**
     * 获取文章列表
     *
     * @param article
     * @return
     */
    @GetMapping("/list")
    JsonResult<Page<Article>> getArticleList( Article article) {
         return JsonResult.success(articleService.getList(article));
    }

    /**
     * 获取文章信息
     * @param id
     * @return
     */
    @GetMapping("/info")
    JsonResult<Article> getArticleList(@RequestParam("id") Long id) {
        return JsonResult.success( articleService.getById(id));
    }

    /**
     * 删除文章
     * @param ids
     * @return
     */
    @DeleteMapping("/del/{ids}")
    public JsonResult<?> delArticle(@PathVariable List<Long> ids) {
        return  articleService.removeByIds(ids)?JsonResult.success("删除成功"):JsonResult.error("删除失败");
    }

    /**
     * 新增文章分类
     *
     * @param article 文章实体
     * @return {@link JsonResult }<{@link ? }>
     */
    @PostMapping("/insert")
    public JsonResult<?> insertCategory(@RequestBody @Valid Article article) {
        return articleService.save(article)?JsonResult.success("新增成功"):JsonResult.error("新增失败");
    }

    /**
     * 更新文章
     *
     * @param article 文章分类实体
     * @return {@link JsonResult }
     */
    @PutMapping("/update")
    public JsonResult<?> updateCategory(@RequestBody @Valid Article article) {
        return articleService.updateById(article)?JsonResult.success("更新成功"):JsonResult.error("更新失败");
    }

    /**
     * 更新文章状态
     * @param map
     * @return
     */
    @PutMapping("/updateArticleState")
    public  JsonResult<?> updateCategory(@RequestBody Map<String ,Object> map) {
        LambdaUpdateWrapper<Article> wrapper = new LambdaUpdateWrapper<>();
        wrapper.set(Article::getState,map.get("state")).eq(Article::getId,map.get("id"));
      return   articleService.update(wrapper)?JsonResult.success("修改成功"):JsonResult.error("修改失败");
    }
}
