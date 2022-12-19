<#import "../parts/common.ftl" as c>
<@c.page>
    <head>
        <title>Stock add/edit</title>
        <link rel="stylesheet" href="../../static/addEditForm.css">
    </head>
    <#if add>
        <#assign urlAction>${'/product/add/' + stockId}</#assign>
        <#assign submitTitle>Create Product</#assign>
    <#else>
        <#assign urlAction>${'/product/edit/' + product.id}</#assign>
        <#assign submitTitle>Update Product</#assign>
    </#if>
    <div class="addEdit-form">
        <h2 class="text-center text-dark"><#if add>Create Product Note:<#else>Edit a Product:</#if></h2>
        <div class="form">
            <form action="${urlAction}" name="product" method="POST">
                <div class="form-icon">
                    <span><i class="icon bi-handbag"></i></span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control item" name="name" placeholder="Product name"
                           value="${(product.name)!''}" />
                </div>
                <div class="form-group">
                    <input type="number" step="0.01" min="0.0" class="form-control item" name="price" placeholder="Product price"
                           value="${(product.price)!''}" />
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
                <a href="/product/findAll/${stockId}"> <input type="submit" class="btn btn-block create-account"
                                                              value="Back to  List"> /</a>
            </div>
        </div>
    </div>
</@c.page>