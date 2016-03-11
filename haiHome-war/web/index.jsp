<%-- 
    Document   : index.jsp
    Created on : 10-nov-2015, 16.56.24
    Author     : gianp_000
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta name="google-signin-client_id" content="495487496441-r9l7mppbotcf6i3rt3cl7fag77hl0v62.apps.googleusercontent.com" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        
        <!-- meta tag googleplus login-->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>haiHome!! - Search Page</title>
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
        <link rel="stylesheet" href="tutcss.css">
        
        <link rel="stylesheet" href="include/css/login/normalize.css">
        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
        <link rel="stylesheet" href="include/css/login/style.css">
        
        <style>
            body {
                padding-top: 50px;
                padding-bottom: 20px;
            }
        </style>        
        
    </head>
    <body>
        <!--[if lt IE 7]>
            <p class="browsehappy">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
        
        <a href="login2.jsp">LOGIN</a>
        <%@include file="/login.jsp" %> 
        
        <%@include file="/header3Login.jsp" %>
        
        <%@include file="/formAutocomplete.jsp" %> 
        
        <%@include file="/footer.jsp" %>
        
    </body>
</html>
