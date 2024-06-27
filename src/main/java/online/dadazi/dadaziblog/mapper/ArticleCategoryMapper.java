package online.dadazi.dadaziblog.mapper;

import online.dadazi.dadaziblog.pojo.ArticleCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author lqk
*/
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory> {

    /**
     * 查询符合条件最新的一条
     * @param articleCategoryParam
     * @return {@link Integer }
     */
    ArticleCategory selectDelOne(ArticleCategory articleCategoryParam);

    /**
     * 变更删除状态
     * @param id
     * @param delMark 删除状态
     * @return {@link Integer }
     */
    Integer chanDelStatus(@Param("id") Long id, @Param("delMark") String delMark);
}




