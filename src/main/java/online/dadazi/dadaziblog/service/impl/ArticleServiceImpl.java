package online.dadazi.dadaziblog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import online.dadazi.dadaziblog.pojo.Article;
import online.dadazi.dadaziblog.service.ArticleService;
import online.dadazi.dadaziblog.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

/**
 * 文章实现
 * @author lqk
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{

    @Resource
    private ArticleMapper articleMapper;
    @Override
    public Page<Article> getList(Article article) {
        Page<Article> page = new Page<>(article.getPage(), article.getSize());
        return  articleMapper.selectPageVo(page,article);
    }
}




