package ${ServiceImplPackageName};

import ${DaoPackageName}.${ClassName}Mapper;
import ${EntityPackageName}.${ClassName};
import ${EntityDTOPackageName}.${ClassName}DTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

<#if Remark? if_exists>
${Remark}
</#if>
@Service
public class ${ClassName}Service {

    @Autowired
    private ${ClassName}Mapper ${className}Mapper;

<#if listRemark? if_exists>
    ${listRemark}
</#if>
<#if list? if_exists>
    ${list}
</#if>

<#if insertRemark? if_exists>
    ${insertRemark}
</#if>
<#if insert? if_exists>
    ${insert}
</#if>

<#if selectRemark? if_exists>
    ${selectRemark}
</#if>
<#if select? if_exists>
    ${select}
</#if>

<#if updateRemark? if_exists>
    ${updateRemark}
</#if>
<#if update? if_exists>
    ${update}
</#if>

<#if deleteRemark? if_exists>
    ${deleteRemark}
</#if>
<#if delete? if_exists>
    ${delete}
</#if>

}
