<#import "../parts/common.ftl" as c>
<@c.page>
    <head>
        <title>User</title>
        <link rel="stylesheet" href="../../static/login.css" />
    </head>

    <#if add>
        <#assign urlAction>/user/registration</#assign>
        <#assign submitTitle>Добавить</#assign>
    <#else>
        <#assign urlAction>${'/user/edit/' + user.id}</#assign>
        <#assign submitTitle>Изменить</#assign>
    </#if>

    <div class="container">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <div class="card my-4">

                    <form action="${urlAction}" name="user" method="post" class="card-body p-lg-4">
                        <h2 class="text-center text-dark"><#if add>Регистрация:<#else>Изменение пользователя:</#if></h2>

                        <div class="text-center">
                            <img src="https://cdn.pixabay.com/photo/2016/03/31/19/56/avatar-1295397__340.png"
                                 class="img-fluid profile-image-pic img-thumbnail rounded-circle my-3"
                                 width="200px" alt="profile" />
                        </div>

                        <div class="mb-3">
                            <input type="text"
                                   class="form-control"
                                   id="fullName" name="fullName"
                                   value="${(user.fullName)!''}"
                            placeholder="ФИО" />
                        </div>
                        <div class="mb-3">
                            <input type="text"
                                   value="${(user.username)!''}"
                                   class="form-control"
                                   id="username" name="username"
                                   placeholder="логин" />
                        </div>
                        <#if user.id??>
                        <#else>
                            <div class="mb-3">
                                <input type="password" class="form-control" id="password" name="password"
                                       value="${(user.password)!''}"
                                       placeholder="пароль" />
                            </div>
                        </#if>
                        <#if errorsMap??>
                            <div class="col-sm-3" id="user-edit">
                                <#list errorsMap as propName, propValue>
                                    <small class="text-danger" error-name="${propName}" error-message="${propValue}" >
                                        ${propName} = ${propValue}
                                    </small>
                                    <br></br>
                                </#list>
                            </div>
                        </#if>
                        <div class="text-center">
                            <input type="submit" value="${submitTitle}" class="btn btn-color px-5 mb-5 w-100"
                                   style="background-color: #0e1c36;color: #fff"/>
                        </div>
                        <div class="form-text text-center mb-5 text-dark">
                            <#if add>В главную  страницу?<a
                                    href="/" class="text-dark fw-bold"> Главная страница</a> :
                            <#else>Назад к списку: <a href="/user/findAll" class="text-dark fw-bold"> Список пользователей®</a></#if>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</@c.page>