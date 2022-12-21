<#macro login path>
    <div class="container">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <div class="card my-4">

                    <form action="${path}" method="post" name="jwtRequestDTO" class="card-body p-lg-4">
                        <h2 class="text-center text-dark">Вход</h2>
                        <div class="text-center">
                            <img src="https://cdn.pixabay.com/photo/2016/03/31/19/56/avatar-1295397__340.png"
                                 class="img-fluid profile-image-pic img-thumbnail rounded-circle my-3"
                                 width="200px" alt="profile" />
                        </div>

                        <div class="mb-3">
                            <input type="text" class="form-control" id="username" name="username"
                                   placeholder="логин" />
                        </div>
                        <div class="mb-3">
                            <input type="password" class="form-control" id="password" name="password"
                                   placeholder="пароль" />
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-color px-5 mb-5 w-100" style="background-color: #0e1c36;color: #fff">Войти</button>
                        </div>
                        <div class="form-text text-center mb-5 text-dark">
                            Зарегестрироваться? <a href="/user/registration" class="text-dark fw-bold"> Создание аккаунта</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</#macro>
<#macro logout>
    <form action="/logout" method="post">
        <input class="nav-link" type="submit" value="Выйти"/>
    </form>
</#macro>