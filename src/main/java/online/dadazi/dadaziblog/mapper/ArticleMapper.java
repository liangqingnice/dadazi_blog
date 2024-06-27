package online.dadazi.dadaziblog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import online.dadazi.dadaziblog.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章mapper
 * @author lqk
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 文章分页查询
     *
     * @param page 分页
     * @param article 实体
     * @return {@link IPage }<{@link Article }>
     */
    Page<Article> selectPageVo(IPage<Article> page, Article article);


}




