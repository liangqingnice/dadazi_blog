package online.dadazi.dadaziblog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.dadazi.dadaziblog.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author lqk
*/
public interface ArticleService extends IService<Article> {
    /**
     * 获取文章列表
     * @param article 列表
     * @return 文章列表
     */
    Page<Article> getList(Article article);
}
