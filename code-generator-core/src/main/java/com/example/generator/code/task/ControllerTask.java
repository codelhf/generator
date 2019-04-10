package com.example.generator.code.task;

import com.example.generator.code.generator.ControllerGenerator;
import com.example.generator.code.task.base.BaseTask;
import com.example.generator.code.task.base.FileUtil;
import com.example.generator.code.task.base.FreemarkerUtil;
import com.example.generator.db.ColumnInfo;
import com.example.generator.code.generator.ControllerGenerator;
import com.example.generator.code.task.base.BaseTask;
import com.example.generator.code.task.base.FreemarkerUtil;
import com.example.generator.db.ColumnInfo;
import com.example.generator.util.StringUtil;
import com.example.generator.util.StringUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO
 * @Auther: liuhf
 * @CreateTime: 2019/3/16 21:17
 */
public class ControllerTask extends BaseTask {

    public ControllerTask(String className, boolean isView) {
        super(className, isView);
    }

    @Override
    public void run() throws IOException, TemplateException {
        // 生成Controller填充数据
        System.out.println("Generating " + className + "Controller.java");
        Map<String, String> controllerData = new HashMap<>();

        controllerData.put("ControllerPackageName", configuration.getServiceGenerator().getController());
        controllerData.put("BasePackageName", configuration.getCommentGenerator().getBasePackageName());
        controllerData.put("ServicePackageName", configuration.getServiceGenerator().getService());
        controllerData.put("EntityPackageName", configuration.getCommonGenerator().getModelGenerator().getTargetPackage());

        String clazzName = StringUtil.firstToLowerCase(className);
        controllerData.put("ClassName", className);
        controllerData.put("className", clazzName);

        String title = className + "Controller";
        String description = className + "控制层";
        controllerData.put("Remark", ControllerGenerator.generateRemark(title, description));

        ColumnInfo primaryKeyColumn = getPrimaryKeyColumnInfo(tableInfo);
        controllerData.put("listRemark", ControllerGenerator.listRemark(className));
        controllerData.put("list", ControllerGenerator.list(className));
        controllerData.put("selectRemark", ControllerGenerator.selectRemark(className, primaryKeyColumn));
        controllerData.put("select", ControllerGenerator.select(className, primaryKeyColumn));
        if (!isView) {
            controllerData.put("insertRemark", ControllerGenerator.insertRemark(className, clazzName));
            controllerData.put("insert", ControllerGenerator.insert(className, clazzName));
            controllerData.put("updateRemark", ControllerGenerator.updateRemark(className, clazzName, primaryKeyColumn));
            controllerData.put("update", ControllerGenerator.update(className, clazzName, primaryKeyColumn));
            controllerData.put("deleteRemark", ControllerGenerator.deleteRemark(className, primaryKeyColumn));
            controllerData.put("delete", ControllerGenerator.delete(className, primaryKeyColumn));
        }

        String targetProject = configuration.getServiceGenerator().getTargetProject();
        String targetPackage = configuration.getServiceGenerator().getController();

        String filePath = FileUtil.getBasicProjectPath() + targetProject + FileUtil.package2Path(targetPackage);
        String fileName = className + "Controller.java";

        int type = FreemarkerUtil.FileTypeEnum.CONTROLLER.getCode();
        boolean generate = configuration.getServiceGenerator().isGenerator();
        // 生成Controller文件
        FileUtil.generateToCode(filePath, fileName, controllerData, type, generate, false);
    }
}
