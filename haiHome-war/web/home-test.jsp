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
        <!-- INIZIO caricamento bootstrap mediante MaxCDN -->
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <!-- FINE caricamento bootstrap mediante MaxCDN -->

        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
        <!--Tema bootstrap -->
        <link href="tutcss.css" rel="stylesheet">
        
        <!-- Import script Facebook -->
        <script type="text/javascript" src="include/js/login/FacebookScript.js"></script>
        <!-- Fine Import script Facebook -->
        <style>
            body {
                padding-top: 50px;
                padding-bottom: 20px;
            }
        </style>
        <!-- Robe di login2.jsp -->
        <link href='http://fonts.googleapis.com/css?family=PT+Sans:400,700' rel='stylesheet' type='text/css'>
        
        <link rel="stylesheet" href="include/css/login//reset.css"> <!-- CSS reset -->
        <link rel="stylesheet" href="include/css/login/style2.css"> <!-- Gem style -->
        <script src="include/js/login/modernizr.js"></script> <!-- Modernizr -->
        <!-- robe del login bello -->
        <link rel="stylesheet" href="include/css/login/normalize.css">
        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
        <link rel="stylesheet" href="include/css/login/style.css">
        <!-- Fine robe di login2.jsp -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>haiHome!!</title>

    </head>
    <body>
        <%@include file="/login2.jsp" %>
        <%@include file="/header3Login.jsp" %>
        <div class="container">
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
        </div>
        <%@include file="/footer.jsp" %>
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
