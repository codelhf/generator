package ${DaoPackageName}.base;

import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ExampleMapper;

public interface TableMapper<T> extends BaseMapper<T>, ExampleMapper<T> {

}