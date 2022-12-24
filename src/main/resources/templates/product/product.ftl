<#import "../parts/common.ftl" as c>

<@c.page>
    <head>
        <title>Товар</title>
        <link rel="stylesheet" href="../../static/product.css"/>
    </head>

    <div class='container-fluid'>
        <div class="card mx-auto col-md-3 col-10 mt-5">
            <#if size == 0>
            <#else>
                <#list pictures as picture>
                    <#if picture.linkOfImages??>
                        <img class='mx-auto img-thumbnail'
                             src="${picture.linkOfImages}"
                             width="400px" height="400px"/>
                        <div class="card-body text-center mx-auto">
                            <div class='cvp'>
                                <form action="/product/deleteImage/${product.id}/${picture.id}" method="post">
                                    <label>Хотите удалить эту фотку?</label>
                                    <input type="submit"
                                           class="btn cart px-auto"
                                           data-abc="true" value="Yes"/>
                                </form>
                            </div>
                        </div>
                    </#if>
                </#list>
            </#if>
            <div class="card-body text-center mx-auto">
                <div class='cvp'>
                    <h5 class="card-title font-weight-bold" style="max-width: 400px">${product.name}</h5>
                    <p class="card-text">${product.price}</p>
                    <a href="/product/findAll/${stockId}" class="btn cart px-auto">Назад к спискам товаров</a>
                    <a href="/product/addPicture/${product.id}" class="btn cart px-auto">Добавить фотографию к
                        товару</a>
                </div>
            </div>
        </div>
    </div>

</@c.page>