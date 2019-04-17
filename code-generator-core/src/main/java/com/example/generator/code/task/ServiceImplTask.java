package com.example.generator.code.task;

import com.example.generator.code.generator.ServiceGenerator;
import com.example.generator.code.generator.ServiceImplGenerator;
import com.example.generator.code.task.base.BaseTask;
import com.example.generator.code.task.base.FileUtil;
import com.example.generator.code.task.base.FreemarkerUtil;
import com.example.generator.config.Configuration;
import com.example.generator.db.ColumnInfo;
import com.example.generator.util.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO
 * @Auther: liuhf
 * @CreateTime: 2019/3/16 21:17
 */
public class ServiceImplTask extends BaseTask {

    public ServiceImplTask(String className, List<ColumnInfo> tableInfo, boolean isView, Configuration configuration) {
        super(className, tableInfo, isView, configuration);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成ServiceImpl填充数据
        System.out.println("Generating " + className + "ServiceImpl.java");
        Map<String, String> serviceImplData = new HashMap<>();

        serviceImplData.put("ServiceImplPackageName", configuration.getServiceGenerator().getServiceImpl());
        serviceImplData.put("BasePackageName", configuration.getCommentGenerator().getBasePackageName());
        serviceImplData.put("ResponseClass", ServiceGenerator.responseClass);
        serviceImplData.put("EntityPackageName", configuration.getCommonGenerator().getModelGenerator().getTargetPackage());
        serviceImplData.put("EntityDTOPackageName", configuration.getCommonGenerator().getDtoGenerator().getTargetPackage());
        serviceImplData.put("DaoPackageName", configuration.getCommonGenerator().getDaoGenerator().getTargetPackage());
        serviceImplData.put("ServicePackageName", configuration.getServiceGenerator().getService());

        String clazzName = StringUtil.firstToLowerCase(className);
        serviceImplData.put("ClassName", className);
        serviceImplData.put("className", clazzName);

        String title = className + "ServiceImpl";
        String description = className + "业务层";
        serviceImplData.put("Remark", ServiceImplGenerator.generateRemark(title, description, configuration));

        ColumnInfo primaryKeyColumnInfo = getPrimaryKeyColumnInfo(tableInfo);
        serviceImplData.put("listRemark", ServiceImplGenerator.listRemark(className, configuration));
        serviceImplData.put("list", ServiceImplGenerator.list(className, clazzName));
        serviceImplData.put("selectRemark", ServiceImplGenerator.selectRemark(className, primaryKeyColumnInfo, configuration));
        serviceImplData.put("select", ServiceImplGenerator.select(className, clazzName, primaryKeyColumnInfo));
        if (!isView) {
            serviceImplData.put("insertRemark", ServiceImplGenerator.insertRemark(className, clazzName, configuration));
            serviceImplData.put("insert", ServiceImplGenerator.insert(className, clazzName));
            serviceImplData.put("updateRemark", ServiceImplGenerator.updateRemark(className, clazzName, primaryKeyColumnInfo, configuration));
            serviceImplData.put("update", ServiceImplGenerator.update(className, clazzName, primaryKeyColumnInfo));
            serviceImplData.put("deleteRemark", ServiceImplGenerator.deleteRemark(className, primaryKeyColumnInfo, configuration));
            serviceImplData.put("delete", ServiceImplGenerator.delete(className, clazzName, primaryKeyColumnInfo));
        }

        String targetProject = configuration.getServiceGenerator().getTargetProject();
        String targetPackage = configuration.getServiceGenerator().getServiceImpl();

        String filePath = FileUtil.getGeneratePath(configuration.getConfigFilePath(), targetProject, targetPackage);
        String fileName = className + "ServiceImpl.java";
        int type = FreemarkerUtil.FileTypeEnum.SERVICE_IMPL.getCode();
        boolean generate = configuration.getServiceGenerator().isGenerator();
        // 生成ServiceImpl文件
        FileUtil.generateToCode(filePath, fileName, serviceImplData, type, generate, false);
    }
}