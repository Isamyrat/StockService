<#import "../parts/common.ftl" as c>
<@c.page>
    <head>
        <title>Акции</title>
        <link rel="stylesheet" href="../../static/addEditForm.css" />
    </head>
    <#if add>
        <#assign urlAction>${'/product/add/' + stockId}</#assign>
        <#assign submitTitle>Добавление товара</#assign>
    <#else>
        <#assign urlAction>${'/product/edit/' + product.id}</#assign>
        <#assign submitTitle>Изменение товара</#assign>
    </#if>
    <div class="addEdit-form">
        <h2 class="text-center text-dark"><#if add>Добавление товара:<#else>Изменение товара:</#if></h2>
        <div class="form">
            <form action="${urlAction}" name="product" method="POST">
                <div class="form-icon">
                    <span><i class="icon bi-handbag"></i></span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control item" name="name" placeholder="Название товара"
                           value="${(product.name)!''}" />
                </div>
                <div class="form-group">
                    <input type="number" step="0.01" min="0.0" class="form-control item" name="price" placeholder="Цена товара"
                           value="${(product.price)!''}" />
                </div>
                <#if errorsMap??>
                    <div class="col-sm-3" id="product-edit">
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
                <a href="/product/findAll/${stockId}"> <input type="submit" class="btn btn-block create-account"
                                                              value="Назад" /></a>
            </div>
        </div>
    </div>
</@c.page>