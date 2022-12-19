<#import "../parts/common.ftl" as c>

<@c.page>
    <head>
        <title>Product</title>
        <link rel="stylesheet" href="../../static/product.css" />
    </head>

    <div class='container-fluid'>
        <div class="card mx-auto col-md-3 col-10 mt-5">
            <#if size == 0>
            <#elseif size == 1>
                <#list pictures as picture>
                    <#if picture.linkOfImages??>
                        <img class='mx-auto img-thumbnail'
                             src="${picture.linkOfImages}"
                             width="400px" height="400px"/>
                    </#if>
                </#list>
            <#else>
                <div class="inner">
                    <div class="thumblist">
                        <#list pictures as picture>
                            <#if picture.linkOfImages??>
                                <img class='mx-auto img-thumbnail'
                                     src="${picture.linkOfImages}"
                                     width="auto" height="auto" alt="..."/>
                            </#if>
                        </#list>
                    </div>
                </div>
            </#if>
            <div class="card-body text-center mx-auto">
                <div class='cvp'>
                    <h5 class="card-title font-weight-bold" style="max-width: 400px">${product.name}</h5>
                    <p class="card-text">${product.price}</p>
                    <a href="/product/findAll/${stockId}" class="btn cart px-auto">Back to Product List</a>
                    <a href="/product/addPicture/${product.id}" class="btn cart px-auto">Add picture to product</a>
                </div>
            </div>
        </div>
    </div>

</@c.page>