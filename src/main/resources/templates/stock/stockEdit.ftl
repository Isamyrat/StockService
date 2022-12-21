<#import "../parts/common.ftl" as c>

<@c.page>
    <head>
        <title>Акции</title>
        <link rel="stylesheet" href="../../static/addEditForm.css" />
    </head>

    <#if add>
        <#assign urlAction>/stock/add</#assign>
        <#assign submitTitle>Добавление акции</#assign>
    <#else>
        <#assign urlAction>${'/stock/edit/' + stock.id}</#assign>
        <#assign submitTitle>Изменить акцию</#assign>
    </#if>

    <div class="addEdit-form">
        <h2 class="text-center text-dark"><#if add>Добавление акции:<#else>Изменение акции:</#if></h2>
        <div class="form">
            <form action="${urlAction}" name="stock" method="POST">
                <div class="form-icon">
                    <span><i class="icon bi-percent"></i></span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control item" name="name" placeholder="Название акции"
                           value="${(stock.name)!''}" />
                </div>
                <div class="form-group">
                    <input type="datetime-local" class="form-control item" name="startDate"
                           placeholder="Дата начало акции"
                           value="${(stock.startDate)!''}" />
                </div>
                <div class="form-group">
                    <input type="datetime-local" class="form-control item" name="endDate" placeholder="Конец акции"
                           value="${(stock.endDate)!''}" />
                </div>
                <div class="form-group">
                    <textarea name="feedLink" class="form-control item" placeholder="Для фида">${(stock.feedLink)!""}</textarea>
                </div>
                <#if errorsMap??>
                    <div class="col-sm-3" id="stock-edit">
                        <#list errorsMap as propName, propValue>
                            <small class="text-danger" error-name="${propName}" error-message="${propValue}">
                                ${propName} = ${propValue}
                            </small>
                            <br></br>
                        </#list>
                    </div>
                </#if>
                <div class="form-group text-center">
                    <input type="submit" value="${submitTitle}" class="btn btn-block create-account" />
                </div>
            </form>
            <div class="form-group text-center">
                <a href="/stock/findAll"> <input type="submit" class="btn btn-block create-account"
                                                 value="Назад ко всем акциям" /></a>
            </div>
        </div>
    </div>
</@c.page>