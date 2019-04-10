package ${EntityDtoPackageName};

import java.io.Serializable;
import java.util.Date;
import java.util.List;

<#if Remark? if_exists>
${Remark}
</#if>
<#if Lombok? if_exists>
${Lombok}
</#if>
public class ${ClassName}DTO implements Serializable {
    private static final long serialVersionUID = 1L;

    ${Properties}
<#if Constructor? if_exists>
    ${AllArgsConstructor}
    ${AllArgsConstructor}
</#if>
<#if Methods? if_exists>
    ${Methods}
</#if>
}