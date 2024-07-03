package online.dadazi.dadaziblog.config.mybatis;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import org.apache.ibatis.type.*;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * sql类型转换
 *
 * @author lqk
 */
@MappedTypes({Object.class,String.class})
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {


    private final Class<T> clazz;

    public JsonTypeHandler(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.clazz = clazz;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        if (StrUtil.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json, clazz);
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        if (StrUtil.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json, clazz);
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

        String json = cs.getString(columnIndex);
        if (StrUtil.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json, clazz);
    }
}