<%@page import="org.json.JSONObject"%>
<!--
<header role="banner"> -->
    <nav class="navbar navbar-default">
        <div style="display:none">
            <div id="__user_type"><%= session.getAttribute("user-type") %></div>   
            <div id="__user_data"><%= session.getAttribute("user-data") %></div>
            <div id="__current_url"><%= request.getRequestURI().toString().replace(request.getContextPath().toString() + "/", "") %></div>
        </div>
        
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="index.jsp">HaiHome!!</a>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li><a id="logged-as"></a></li>
                <li><a id="go-profile" href="#0">Profilo</a></li>
                <li><a id="accesso" class='cd-signup' href='#myModal' data-toggle='modal'>Accesso</a></li>
                <li><a id="logout" href="#0">Logout</a></li> 
                <li><a id="help" href="#0">Aiuto</a></li> 
            </ul>
        </div>
    </nav> 
<%--
    <nav class="main-nav">
        <ul> 
            <%
                if (session.getAttribute("user-type") != null) {
                    /* link al profilo , o all'index se ci si trova correntemente
                         * nel profilo   */
                    String currentURL = request.getRequestURI().toString().replace(request.getContextPath().toString() + "/", "");
                    String url_target_page = "index.jsp", title_target_page = "Home";

                    if (!currentURL.contains("-profile.jsp")) {
                        url_target_page = ((String) session.getAttribute("user-type")) + "-profile.jsp";
                        title_target_page = "Profilo";
                    }

                    out.println("<li><a class='cd-signup' href='" + url_target_page + "'>"
                            + title_target_page + "</a></li>");
                    // form e bottone per logout 
                    out.println(
                            "<li><form id='form_logout' action='ServletController' method='POST'>"
                            + "<input type='hidden' name='action' value='user-logout' >"
                            + "<a class='cd-signup' href='javascript:;' onclick='parentNode.submit();'>Log out</a>"
                            + "</form></li>"
                    );
                } else {
                    // bottone apertura modal 
                    out.println("<li><a class='cd-signup' href='#myModal' data-toggle='modal'>Accesso</a></li> ");
                }
            %>
        </ul>
    </nav> --%>
        <!-- 
</header> -->

<%@include file="/include/html/modal-accesso.html"%>

