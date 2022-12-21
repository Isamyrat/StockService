<#import "parts/common.ftl" as c>

<@c.page>
    <h1> Привет <#if __user??>${__user.name} </#if></h1>
</@c.page>