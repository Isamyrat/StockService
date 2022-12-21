<#import "login.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Сервис акций</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>

            <#if !(__user??)>
                <li class="nav-item">
                    <a class="nav-link" href="/login">Логин</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/user/registration">Регистрация</a>
                </li>
            <#else>
                <#if __adminRole>
                    <li class="nav-item">
                        <a class="nav-link" href="/user/findAll">Список пользователей</a>
                    </li>
                </#if>

                <li class="nav-item">
                    <a class="nav-link" href="/stock/findAll">Список акциии</a>
                </li>

                <li class="nav-item">
                    <@l.logout />
                </li>
            </#if>
        </ul>
    </div>
</nav>
