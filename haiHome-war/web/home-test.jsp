<%-- 
    Document   : home-test
    Created on : 3-mar-2016, 12.06.05
    Author     : gianp_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="google-signin-client_id" content="495487496441-r9l7mppbotcf6i3rt3cl7fag77hl0v62.apps.googleusercontent.com"></meta>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="include/css/bootstrap.min.css">
        <style>
            body {
                padding-top: 50px;
                padding-bottom: 20px;
            }
        </style>
        <link rel="stylesheet" href="include/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="include/css/main.css">
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js" ></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>haiHome!!</title>

    </head>
    <body>
        <div class="container">
            <%@include file="/header3Login.jsp" %>
            <div class="row">
                <div class="col-lg-3 col-md-6 col-md-offset-3 col-lg-offset-0">
                    <form class="form-horizontal" method="POST" action="ServletController" >
                        <div class="form-group">   
                            <input type="hidden" name="action" value="setCity">
                            <input type="text" class="form-control" id="city" placeholder="city" name="city" value="Torino" onkeypress="return event.keyCode != 13">
                        </div>
                        <div class="form-group">
                            <div class="col-xs-offset-3 col-xs-9">
                                <input type="submit" class="btn btn-primary" onclick="IsEmpty();" value="Submit" >
                                <input type="reset" class="btn btn-default" value="Reset">
                            </div>
                        </div> 
                    </form>
                </div>
            </div>
            <%@include file="/footer.jsp" %>
        </div>
        <script>
            function IsEmpty() {

                if (document.form.city.value == "")
                {
                    alert("empty city");
                }

                return;
            }
        </script>
        <!--<script language="javascript">
            function document.onkeydown() {
            if (event.keyCode == 116) {
            event.keyCode = 0;
                    event.cancelBubble = true;
                    return false;
            }
            }
        </script> -->
    </body>
</html>
