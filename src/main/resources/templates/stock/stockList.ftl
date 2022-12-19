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

        <h1 class="font-bold text-center">List of stocks</h1>

        <div class="card mb-4">
            <div class="card-body">
                <!-- Grid row -->
                <div class="row">
                    <div class="col-md-12 col-sm-12 center-col text-center">
                        <a href="/stock/add"
                           class="highlight-button btn btn-medium button xs-margin-bottom-five"
                           data-abc="true">Add stock</a>
                        <a href="/"
                           class="highlight-button btn btn-medium button xs-margin-bottom-five"
                           data-abc="true">Back to main page</a>
                    </div>

                    <div class="container">
                        <div class="row height d-flex justify-content-center align-items-center">

                            <div class="col-md-6">

                                <div class="form">
                                    <form action="/stock/findAll" method="get">
                                        <input type="text" class="form-control form-input" name="name"
                                               placeholder="Search anything..."/>
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
                        <th>Name</th>
                        <th>Start date</th>
                        <th>End date</th>
                        <th>Feed link</th>
                        <th>Edit</th>
                        <th>Enable/Disable stock</th>
                        <th>Watch products</th>
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
                                <td class="limit">${stock.feedLink}</td>
                            <#else>
                                <td></td>
                            </#if>

                            <td>
                                <a href="/stock/editForm/${stock.id}"
                                   class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                   data-abc="true">Edit</a>
                            </td>

                            <td>
                                <#if stock.active == "ENABLE">
                                    <form action="/stock/disable/${stock.id}" method="post">
                                        Disable this stock?
                                        <input type="submit"
                                               class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                               data-abc="true" value="Yes"/>
                                    </form>
                                <#else>
                                    <form action="/stock/enable/${stock.id}" method="post">
                                        Enable this stock?
                                        <input type="submit"
                                               class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                               data-abc="true" value="Yes"/>
                                    </form>
                                </#if>
                            </td>
                            <td><a href="/product/findAll/${stock.id}"
                                   class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                   data-abc="true">watch</a></td>
                        </tr>
                    </#list>
                    </tbody>
                    <!--Table body-->
                </table>
                <!--Table-->
                <div class="col-md-12 col-sm-12 center-col text-center">
                    <#if hasPrev><a href="${'/stock/findAll?page=' + prev + '&name=' + name}"
                                    class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                    data-abc="true">Prev</a>&nbsp;&nbsp;&nbsp;</#if>
                    <#if hasNext><a href="${'/stock/findAll?page=' + next + '&name=' + name}"
                                    class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                    data-abc="true">Next</a></#if>
                </div>
            </div>
        </div>
    </div>

</@c.page>