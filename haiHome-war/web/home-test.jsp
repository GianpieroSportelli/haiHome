<%-- 
    Document   : home-test
    Created on : 3-mar-2016, 12.06.05
    Author     : gianp_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
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
        <link rel="stylesheet" href="include/css/prova_list.css">
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js" ></script>
        <script src="include/js/prova_list.js"></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>haiHome!!</title>
        <!-- INIZIO caricamento bootstrap mediante MaxCDN -->
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <!-- FINE caricamento bootstrap mediante MaxCDN -->
    </head>
    <body>
        <div class="container">
            <%@include file="/jspf/header.jspf" %>
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
            <%@include file="/jspf/footer.jspf" %>
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
        
    </body>
</html>
