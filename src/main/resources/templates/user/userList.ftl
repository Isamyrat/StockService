<#import "../parts/common.ftl" as c>
<@c.page>
    <head>
        <title>Список пользователей</title>
        <link rel="stylesheet" href="../../static/table.css" />
        <link rel="stylesheet" href="../../static/button.css" />
        <link rel="stylesheet" href="../../static/search.css" />
    </head>

    <!--MDB Tables-->
    <div class="container mt-4">

        <h1 class="font-bold text-center">Список пользователей</h1>

        <div class="card mb-4">
            <div class="card-body">
                <!-- Grid row -->
                <div class="row">
                    <!-- Grid column -->
                    <div class="container">
                        <div class="row height d-flex justify-content-center align-items-center">

                            <div class="col-md-6">

                                <div class="form">
                                    <form action="/user/findAll" method="get">
                                        <input type="text" class="form-control form-input" name="keyword"
                                               placeholder="Поищем?..."/>
                                    </form>
                                </div>

                            </div>
                        </div>
                    </div>
                    <!-- Grid column -->
                </div>
                <!-- Grid row -->
                <!--Table-->
                <table class="table table-striped">
                    <!--Table head-->
                    <thead>
                    <tr>
                        <th>ФИО</th>
                        <th>логин</th>
                        <th>именить пользователя</th>
                    </tr>
                    </thead>
                    <!--Table head-->
                    <!--Table body-->
                    <tbody id="user-list">
                    <#list users as user>
                        <!--for test id and username-->
                        <tr data-id="${user.id}" data-username="${user.username}">
                            <td>${user.fullName}</td>
                            <td>${user.username}</td>
                            <td><a href="/user/editForm/${user.id}"
                                   class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                   data-abc="true">изменить</a></td>
                        </tr>
                    </#list>
                    </tbody>
                    <!--Table body-->
                </table>
                <!--Table-->
                <div class="col-md-12 col-sm-12 center-col text-center">
                    <#if hasPrev><a href="${'/user/findAll?page=' + prev + '&keyword=' + keyword}"
                                    class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                    data-abc="true">Prev</a></#if>
                    <#if hasNext><a href="${'/user/findAll?page=' + next + '&keyword=' + keyword}"
                                    class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                    data-abc="true">Next</a></#if>
                </div>
            </div>
        </div>
    </div>
</@c.page>