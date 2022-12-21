<#import "../parts/common.ftl" as c>
<@c.page>
    <head>
        <title>Stock list</title>
        <link rel="stylesheet" href="../../static/table.css" />
        <link rel="stylesheet" href="../../static/button.css" />
        <link rel="stylesheet" href="../../static/search.css" />

    </head>

    <!--MDB Tables-->
    <div class="container mt-4">

        <h1 class="font-bold text-center">Список акции</h1>

        <div class="card mb-4">
            <div class="card-body">
                <!-- Grid row -->
                <div class="row">
                    <div class="col-md-12 col-sm-12 center-col text-center">
                        <a href="/stock/add"
                           class="highlight-button btn btn-medium button xs-margin-bottom-five"
                           data-abc="true">Добавить акцию</a>
                        <a href="/"
                           class="highlight-button btn btn-medium button xs-margin-bottom-five"
                           data-abc="true">Назад в главную страницу</a>
                    </div>

                    <div class="container">
                        <div class="row height d-flex justify-content-center align-items-center">

                            <div class="col-md-6">

                                <div class="form">
                                    <form action="/stock/findAll" method="get">
                                        <input type="text" class="form-control form-input" name="name"
                                               placeholder="Поищем?..."/>
                                    </form>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
                <!-- Grid row -->
                <!--Table-->
                <table class="table table-striped">
                    <!--Table head-->
                    <thead>
                    <tr>
                        <th>Название акции</th>
                        <th>Дата начало акции</th>
                        <th>Конец акции</th>
                        <th>Фид акции</th>
                        <th>Изменить</th>
                        <th>Активировать/Дизактивировать</th>
                        <th>Посмотреть товары акции</th>
                    </tr>
                    </thead>
                    <!--Table head-->
                    <!--Table body-->
                    <tbody id="stock-list">
                    <#list stocks as stock>
                        <tr data-id="${stock.id}" data-name="${stock.name}">
                            <td>${stock.name}</td>
                            <td>${stock.startDate}</td>
                            <td>${stock.endDate}</td>
                            <#if stock.feedLink??>
                                <td class="limit"> ${stock.feedLink}</td>
                            <#else>
                                <td></td>
                            </#if>

                            <td>
                                <a href="/stock/editForm/${stock.id}"
                                   class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                   data-abc="true">Изменить</a>
                            </td>

                            <td>
                                <#if stock.active == "ENABLE">
                                    <form action="/stock/disable/${stock.id}" method="post">
                                        Дизактивировать акцию?
                                        <input type="submit"
                                               class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                               data-abc="true" value="Да"/>
                                    </form>
                                <#else>
                                    <form action="/stock/enable/${stock.id}" method="post">
                                        Активировать акцию?
                                        <input type="submit"
                                               class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                               data-abc="true" value="Да"/>
                                    </form>
                                </#if>
                            </td>
                            <td><a href="/product/findAll/${stock.id}"
                                   class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                   data-abc="true">Посмотреть</a></td>
                        </tr>
                    </#list>
                    </tbody>
                    <!--Table body-->
                </table>
                <!--Table-->
                <div class="col-md-12 col-sm-12 center-col text-center">
                    <#if hasPrev><a href="${'/stock/findAll?page=' + prev + '&name=' + name}"
                                    class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                    data-abc="true">Prev</a></#if>
                    <#if hasNext><a href="${'/stock/findAll?page=' + next + '&name=' + name}"
                                    class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                    data-abc="true">Next</a></#if>
                </div>
            </div>
        </div>
    </div>

</@c.page>