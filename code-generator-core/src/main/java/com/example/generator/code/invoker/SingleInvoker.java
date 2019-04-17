package com.example.generator.code.invoker;

import com.example.generator.code.invoker.base.BaseBuilder;
import com.example.generator.code.invoker.base.BaseInvoker;
import com.example.generator.code.task.*;
import com.example.generator.config.ColumnOverride;
import com.example.generator.config.Configuration;
import com.example.generator.config.GeneratedKey;
import com.example.generator.db.ColumnInfo;
import com.example.generator.db.TypeUtil;
import com.example.generator.util.StringUtil;

import java.sql.SQLException;
import java.util.List;

/**
 * @Description: TODO
 * @Auther: liuhf
 * @CreateTime: 2019/3/16 21:17
 */
public class SingleInvoker extends BaseInvoker {

    //是否为视图, 针对单表
    private boolean isView;
    //指定列生成主键
    private GeneratedKey generatedKey;
    //重写列集合
    private List<ColumnOverride> columnOverrideList;

    public void setView(boolean view) {
        this.isView = view;
    }

    public void setGeneratedKey(GeneratedKey generatedKey) {
        this.generatedKey = generatedKey;
    }

    public void setColumnOverrideList(List<ColumnOverride> columnOverrideList) {
        this.columnOverrideList = columnOverrideList;
    }

    @Override
    protected void getTableInfo() throws SQLException {
        List<ColumnInfo> columnInfoList = connectionUtil.getMetaData(tableName);
        //修改用户自定义配置
        for (ColumnInfo columnInfo: columnInfoList) {
            String column = columnInfo.getColumnName();
            if (generatedKey != null && column.equals(generatedKey.getColumn())) {
                columnInfo.setPrimaryKey(true);
            }
            if (columnOverrideList != null) {
                for (ColumnOverride columnOverride: columnOverrideList) {
                    if (column.equals(columnOverride.getColumnName())) {
                        columnInfo.setJdbcType(columnOverride.getJdbcType());
//                        columnInfo.setJavaType(TypeUtil.jdbcTypeToJavaType(columnOverride.getJdbcType()));
                        break;
                    }
                }
            }
        }
        this.tableInfo = columnInfoList;
    }

    @Override
    protected void initTasks() {
        taskQueue.add(new ControllerTask(className, tableInfo, isView, configuration));
        taskQueue.add(new ServiceTask(className, tableInfo, isView, configuration));
        taskQueue.add(new ServiceImplTask(className, tableInfo, isView, configuration));
        taskQueue.add(new DaoTask(className, tableInfo, isView, configuration));
        taskQueue.add(new MapperTask(className, tableName, tableInfo, isView, configuration));
        taskQueue.add(new EntityTask(className, tableInfo, configuration));
        taskQueue.add(new EntityDtoTask(className, tableInfo, configuration));
    }

    public static class Builder extends BaseBuilder {
        private SingleInvoker invoker = new SingleInvoker();

        public Builder configuration(Configuration configuration) {
            invoker.setConfiguration(configuration);
            return this;
        }

        public Builder tableName(String tableName) {
            invoker.setTableName(tableName);
            return this;
        }

        public Builder className(String className) {
            invoker.setClassName(className);
            return this;
        }

        public Builder isView(boolean isView) {
            invoker.setView(isView);
            return this;
        }

        public Builder generatedKey(GeneratedKey generatedKey) {
            invoker.setGeneratedKey(generatedKey);
            return this;
        }

        public Builder columnOverrideList(List<ColumnOverride> columnOverrideList) {
            invoker.setColumnOverrideList(columnOverrideList);
            return this;
        }

        @Override
        public BaseInvoker build(){
            if (!isParametersValid()) {
                return null;
            }
            return invoker;
        }

        @Override
        public void checkBeforeBuild() throws Exception {
            if (invoker.getConfiguration() == null) {
                throw new RuntimeException("configuration can not be null");
            }
            if (StringUtil.isBlank(invoker.getTableName())) {
                throw new RuntimeException("Expect table's name, but get a blank String.");
            }
            if (StringUtil.isBlank(invoker.getClassName())) {
                invoker.setClassName(StringUtil.tableName2ClassName(invoker.getTableName()));
            }
        }
    }

}