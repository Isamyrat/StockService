<#import "../parts/common.ftl" as c>

<@c.page>
    <head>
        <title>Product list</title>
        <link rel="stylesheet" href="../../static/table.css" />
        <link rel="stylesheet" href="../../static/search.css" />
        <link rel="stylesheet" href="../../static/button.css" />

    </head>

    <!--MDB Tables-->
    <div class="container mt-4">

        <h1 class="font-bold text-center">List of products</h1>

        <div class="card mb-4">
            <div class="card-body">
                <!-- Grid row -->
                <div class="row">
                    <div class="col-md-12 col-sm-12 center-col text-center">
                        <a href="/product/add/${stockId}"
                           class="highlight-button btn btn-medium button xs-margin-bottom-five"
                           data-abc="true">Add product</a>
                        <a href="/stock/findAll"
                           class="highlight-button btn btn-medium button xs-margin-bottom-five"
                           data-abc="true">Back to stock list</a>
                    </div>
                    <!-- Grid column -->
                    <div class="container">
                        <div class="row height d-flex justify-content-center align-items-center">

                            <div class="col-md-6">

                                <div class="form">
                                    <form action="/product/findAll/${stockId}" method="get">
                                        <input type="text" class="form-control form-input" name="productData"
                                               placeholder="Search anything..."/>
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
                        <th>Name</th>
                        <th>Price</th>
                        <th>Flag</th>
                        <th>View details</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <!--Table head-->
                    <!--Table body-->
                    <tbody id="product-list">
                    <#list products as product>
                        <tr data-price="${product.price}" data-name="${product.name}">
                            <td class="limitForProductName">${product.name}</td>
                            <td>${product.price}</td>
                            <#if product.flag == "MANUAL_MODE">
                                <td>Ручной режим</td>
                            <#else>
                                <td>Режим автоматический</td>
                            </#if>
                            <td>
                                <a href="/product/getById/${product.id}"
                                   class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                   data-abc="true">view</a>
                            </td>
                            <td>
                                <a href="/product/editForm/${product.id}"
                                   class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                   data-abc="true">edit</a>
                            </td>
                            <td>
                                <form action="/product/delete/${product.id}/${stockId}" method="post">
                                    Delete this item?
                                    <input type="submit"
                                           class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                           data-abc="true" value="Yes"/>
                                </form>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                    <!--Table body-->
                </table>
                <!--Table-->
                <div class="col-md-12 col-sm-12 center-col text-center">
                    <#if hasPrev><a href="${'/product/findAll/${stockId}?page=' + prev + '&productData=' + productData}"
                                    class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                    data-abc="true">
                            Prev</a>&nbsp;&nbsp;&nbsp;</#if>
                    <#if hasNext><a href="${'/product/findAll/${stockId}?page=' + next+ '&productData=' + productData}"
                                    class="highlight-button btn btn-medium button xs-margin-bottom-five"
                                    data-abc="true">Next</a></#if>

                </div>
            </div>
        </div>
    </div>
</@c.page>