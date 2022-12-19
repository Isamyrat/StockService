<#import "../parts/common.ftl" as c>

<@c.page>
    <head>
        <title>Stock add/edit</title>
        <link rel="stylesheet" href="../../static/addEditForm.css" />
    </head>
    <#if add>
        <#assign urlAction>/stock/add</#assign>
        <#assign submitTitle>Create Stock</#assign>
    <#else>
        <#assign urlAction>${'/stock/edit/' + stock.id}</#assign>
        <#assign submitTitle>Update Stock</#assign>
    </#if>
    <div class="addEdit-form">
        <h2 class="text-center text-dark"><#if add>Create Stock Note:<#else>Edit a Stock:</#if></h2>
        <div class="form">
            <form action="${urlAction}" name="stock" method="POST">
                <div class="form-icon">
                    <span><i class="icon bi-percent"></i></span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control item" name="name" placeholder="Stock name"
                           value="${(stock.name)!''}" />
                </div>
                <div class="form-group">
                    <input type="datetime-local" class="form-control item" name="startDate"
                           placeholder="Stock start date"
                           value="${(stock.startDate)!''}" />
                </div>
                <div class="form-group">
                    <input type="datetime-local" class="form-control item" name="endDate" placeholder="Stock end date"
                           value="${(stock.endDate)!''}" />
                </div>
                <div class="form-group">
                    <textarea name="feedLink" class="form-control item">${(stock.feedLink)!""}</textarea>
                </div>
                <#if errorsMap??>
                    <div class="col-sm-3">

                        <#list errorsMap as propName, propValue>
                            <small id="passwordHelp" class="text-danger">
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
                                                 value="Back to Stock List" /></a>
            </div>
        </div>
    </div>
</@c.page>