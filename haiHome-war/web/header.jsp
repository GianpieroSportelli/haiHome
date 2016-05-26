<%@page import="org.json.JSONObject"%>
<!--
<header role="banner"> -->
    <nav class="navbar navbar-default myHeader"> 
        <div style="display:none">
            <div id="__user_type"><%= session.getAttribute("user-type") %></div>   
            <div id="__user_data"><%= session.getAttribute("user-data") %></div>
            <div id="__current_url"><%= request.getRequestURI().toString().replace(request.getContextPath().toString() + "/", "") %></div>
        </div> 
        
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="index.jsp"><img src="images/hai_home_logo.png" /></a>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li><a id="logged-as" class="link-navbar"></a></li>
                <li><a id="go-profile" class="link-navbar" href="#0">Profilo</a></li>
                <li><a id="accesso" class="link-navbar" class='cd-signup' href='#modalLogin' data-toggle='modal'>Accesso</a></li>
                <li><a id="logout" class="link-navbar" href="#0">Logout</a></li> 
                <li><a id="help" class="link-navbar" href="#0">Aiuto</a></li> 
            </ul>
        </div>
    </nav> 
            
<%@include file="/include/html/modal-accesso.html"%>

