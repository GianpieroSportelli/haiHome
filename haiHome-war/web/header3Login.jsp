<%-- 
    Document   : header2
    Created on : 10-nov-2015, 10.07.01
    Author     : gianp_000
--%>
<%@page import="org.json.JSONObject"%> 
<%@page import="java.util.ArrayList"%> 
<%@page import="javax.servlet.http.HttpSession"%>

<script type="text/javascript">
    // Wait for the page to load first
    /* window.onload = function () {
     var a = document.getElementById("FacebookButton");
     
     //Set code to run when the link is clicked
     // by assigning a function to "onclick"
     a.onclick = function () {
     // Carica il file .js Facebook
     $.getScript("include/js/FacebookLogin.js", function () {
     });
     };
     };*/
</script>


<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <%
                        //Si salva tutti i dati, senza doverli mandarli nuovamente con una request
                        HttpSession sessione = request.getSession();
                        Object log = sessione.getAttribute("Loggato");
                        boolean loggedMenu = false;
                        //Object log = request.getAttribute("Loggato");
                        if (log != null) {
                            Boolean loggato = (Boolean) sessione.getAttribute("Loggato");
                            if (loggato.booleanValue() == true) {
                                loggedMenu = true;
                                //JSONObject datiUtente = (JSONObject) request.getAttribute("JSONList");
                                //System.out.println(datiUtente.toString());
                            }
                        }
                        if (loggedMenu) {
                            JSONObject datiUtente = (JSONObject) sessione.getAttribute("JSONList");
                    %>  
                    <div class="dropdown">
                        <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true">
                            Ciao, <%= datiUtente.getString("Nome")%>
                            <span class="caret"></span>
                        </button>
                        <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
                            <li><a href="UserProfile.jsp">Profilo</a></li>
                            <li><a href="#">Preferiti</a></li>
                            <li class="divider"></li>
                            <li>
                                <div class="row">
                                    <div class="col-md-12">
                                        <div>
                                            <button onClick="checkUser();">Logout</button>
                                        </div>
                                    </div>
                                </div>
                        </ul>
                    </div>
                    <%    } else { %> 
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b>Login</b> <span class="caret"></span></a>
                    <ul id="login-dp" class="dropdown-menu">
                        <li>
                            <div class="row">
                                <div class="col-md-12">
                                    Login via
                                    <div class="social-buttons">
                                        <button id="FacebookButton" onclick="">Facebook</button>
                                        <a href="#" class="btn btn-tw"><i class="fa fa-twitter"></i>Facebook</a>
                                        <a href="#" class="btn btn-tw"><i class="fa fa-twitter"></i>Google</a>

                                        <%@include file="/googleplus.jsp" %> 
                                    </div>

                                    <form class="form" role="form" method="post" action="login" accept-charset="UTF-8" id="login-nav">
                                        <div class="form-group">
                                            <label class="sr-only" for="exampleInputEmail2">Email address</label>
                                            <input type="email" class="form-control" id="exampleInputEmail2" placeholder="Email address" required>
                                        </div>
                                        <div class="form-group">
                                            <label class="sr-only" for="exampleInputPassword2">Password</label>
                                            <input type="password" class="form-control" id="exampleInputPassword2" placeholder="Password" required>
                                        </div>
                                        <div class="form-group">
                                            <button type="submit" class="btn btn-primary btn-block">Sign in</button>
                                        </div>
                                    </form>

                                    <div class="bottom text-center">
                                        New here ? <a href="#"><b>Join Us</b></a>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </ul>    
                    <% }%>

                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>