package com.example.generator.api;

import com.example.generator.code.invoker.Many2ManyInvoker;
import com.example.generator.code.invoker.One2ManyInvoker;
import com.example.generator.code.invoker.SingleInvoker;
import com.example.generator.code.invoker.base.Invoker;
import com.example.generator.code.task.CommonTask;
import com.example.generator.config.*;
import com.example.generator.util.Messages;

import java.io.IOException;
import java.util.List;

/**
 * @Description: TODO
 * @Auther: liuhf
 * @CreateTime: 2019/3/6 21:25
 */
public class CodeGenerator {

    /** The configuration. */
    private Configuration configuration;

    public CodeGenerator(Configuration configuration) {
        if (configuration == null) {
            throw new IllegalArgumentException(Messages.getString("RuntimeError.2")); //$NON-NLS-1$
        } else {
            this.configuration = configuration;
        }
    }

    public void generate(ProgressCallback callback) {
        if (callback == null) {
            callback = new NullProgressCallback();
        }

        try {
            //generate common
            CommonTask commonTask = new CommonTask(configuration);
            commonTask.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<TableConfiguration> tableConfigurationList = configuration.getTablesConfiguration();
        if (tableConfigurationList != null) {
            for (TableConfiguration tableConfiguration: tableConfigurationList) {
                String tableName = tableConfiguration.getTableName();
                String domainName = tableConfiguration.getDomainName();
                List<ColumnOverride> columnOverrideList = tableConfiguration.getColumnOverrides();
                GeneratedKey generatedKey = tableConfiguration.getGeneratedKey();
                single(tableName, domainName, columnOverrideList, generatedKey, false, configuration);
            }
        }

        List<ViewConfiguration> viewConfigurationList = configuration.getViewsConfiguration();
        if (viewConfigurationList != null) {
            for (ViewConfiguration viewConfiguration: viewConfigurationList) {
                String viewName = viewConfiguration.getViewName();
                String domainName = viewConfiguration.getDomainName();
                List<ColumnOverride> columnOverrideList = viewConfiguration.getColumnOverrides();
                single(viewName, domainName, columnOverrideList, null, true, configuration);
            }
        }
        callback.done();
    }

    public static void single(String tableName, String className,
                              List<ColumnOverride> columnOverrideList, GeneratedKey generatedKey,
                              boolean isView, Configuration configuration) {
        Invoker invoker = new SingleInvoker.Builder()
                .configuration(configuration)
                .tableName(tableName)
                .className(className)
                .columnOverrideList(columnOverrideList)
                .generatedKey(generatedKey)
                .isView(isView)
                .build();
        invoker.execute();
    }

    public static void one2many(String tableName, String className,
                                String parentTableName, String parentClassName,
                                String foreignKey, Configuration configuration) {
        Invoker invoker = new One2ManyInvoker.Builder()
                .setConfiguration(configuration)
                .setTableName(tableName)
                .setClassName(className)
                .setParentTableName(parentTableName)
                .setParentClassName(parentClassName)
                .setForeignKey(foreignKey)
                .build();
        invoker.execute();
    }

    public static void many2many(String tableName, String className,
                                 String parentTableName, String parentClassName,
                                 String relationTableName, String foreignKey,
                                 String parentForeignKey, Configuration configuration) {
        Invoker invoker = new Many2ManyInvoker.Builder()
                .setConfiguration(configuration)
                .setTableName(tableName)
                .setClassName(className)
                .setParentTableName(parentTableName)
                .setParentClassName(parentClassName)
                .setRelationTableName(relationTableName)
                .setForeignKey(foreignKey)
                .setParentForeignKey(parentForeignKey)
                .build();
        invoker.execute();
    }



//    private void writeGeneratedFile(GeneratedJavaFile gjf, ProgressCallback callback, boolean overWrite, boolean merge)
//            throws InterruptedException, IOException {
//        File targetFile;
//        String source;
//        try {
//            File directory = shellCallback.getDirectory(gjf.getTargetProject(), gjf.getTargetPackage());
//            targetFile = new File(directory, gjf.getFileName());
//            if (targetFile.exists()) {
//                if (overWrite) {
//                    source = gjf.getFormattedContent();
//                    warnings.add(getString("Warning.11", targetFile.getAbsolutePath()));
//                } else if (merge) {
//                    source = shellCallback.mergeJavaFile(gjf.getFormattedContent(), targetFile,
//                            MergeConstants.OLD_ELEMENT_TAGS, gjf.getFileEncoding());
//                } else {
//                    source = gjf.getFormattedContent();
//                    targetFile = getUniqueFileName(directory, gjf.getFileName());
//                    warnings.add(getString("Warning.2", targetFile.getAbsolutePath()));
//                }
//            } else {
//                source = gjf.getFormattedContent();
//            }
//
//            callback.checkCancel();
//            callback.startTask(Messages.getString("Progress.15", targetFile.getName()));
//            writeFile(targetFile, source, gjf.getFileEncoding());
//        } catch (ShellException e) {
//            warnings.add(e.getMessage());
//        }
//    }
}
