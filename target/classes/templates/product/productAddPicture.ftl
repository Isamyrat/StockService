<#import "../parts/common.ftl" as c>
<@c.page>
    <head>
        <title>Add picture to product</title>
        <link rel="stylesheet" href="../../static/addEditForm.css" />
    </head>
    <div class="addEdit-form">
        <h2 class="text-center text-dark">Добавление фотография к продукту</h2>
        <div class="form">
            <form method="post" action="/product/addPicture/${id}">
                <div class="form-icon">
                    <span><i class="icon bi-handbag"></i></span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control item" name="pictureLink" placeholder="Ссылку на фото"/>
                </div>

                <div class="form-group text-center">
                    <input type="submit" value="Отправить" class="btn btn-block create-account" />
                </div>
            </form>
            <div class="form-group text-center">
                <a href="/product/getById/${id}"><input type="submit" class="btn btn-block create-account"
                                                              value="Назад к продукту" /></a>
            </div>
        </div>
    </div>
</@c.page>