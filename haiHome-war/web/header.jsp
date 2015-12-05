<%-- 
    Document   : header
    Created on : Sep 1, 2014, 10:01:26 AM
    Author     : giovanna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>

        <!-- to correctly display on mobile devices  -->
        <meta name="viewport" content="width=device-width, initial-scale=1">  

        <meta http-equiv="Content-Type" content="text/html; charset="utf-8">
              <title>JSP Gio Page</title>
        <!-- Include bootstrap CSS -->

        <link href="css/bootstrap.css" rel="stylesheet">    

        <link  rel="stylesheet" href="include/css/bootstrap.min.css">

        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">

        <link href="tutcss.css" rel="stylesheet">
        <!-- Include jQuery and bootstrap JS plugins  -->
        <script src="include/jquery/jquery-jquery-2.1.1.min.js"></script>
        <script src="include/js/bootstrap.js"></script> 
        <!--   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
           
        
           
   
     <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"  
        -->

        <style type="text/css">
            .bs-example{
                margin: 20px;
            }
        </style>
    </head>
    <body>
        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://code.jquery.com/jquery.js"></script>
        <!-- Include all compiled plugins (below), or include individual files 
              as needed -->
        <script src="include/js/bootstrap.min.js"></script>      

        <!-- Navigation bar  MANCA IL DROPDOWN-->
        <!-- Site header and navigation -->
        <header class="top" role="header">
            <div class="container">

                <a href="#" class="navbar-brand pull-left">
                    Gio
                </a>
                <button class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="glyphicon glyphicon-align-justify"></span>
                </button>
                <nav class="navbar-collapse collapse" role="navigation">
                    <ul class="nav nav-pills">                    
                        <li class="active"><a href="#">Home</a></li>
                        <li><a href="#">Profile</a></li>
                        <li class="dropdown">
                            <a href="#" data-toggle="dropdown" class="dropdown-toggle ">Messages <b class="caret"></b></a>
                            <ul class="dropdown-menu scrollable-menu">
                                <li><a href="#">Inbox</a></li>
                                <li><a href="#">Drafts</a></li>
                                <li><a href="#">Sent Items</a></li>
                                <li><a href="#">Sent Items</a></li>
                                <li><a href="#">Sent Items</a></li>
                                <li><a href="#">Sent Items</a></li> 
                                <li><a href="#">Sent Items</a></li>

                                <li class="divider"></li>
                                <li><a href="#">Trash</a></li>
                            </ul>
                        </li>
                        <li class="dropdown pull-right">
                            <a href="#" data-toggle="dropdown" class="dropdown-toggle">Admin <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Action</a></li>
                                <li><a href="#">Another action</a></li>
                                <li class="divider"></li>
                                <li><a href="#">Settings</a></li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </header>
        <div class="banner">
            <div class="container">
                <h1>Gio spike</h1>
                <p>  Web app Bootstrap 3.0</p>
            </div>
        </div>
        <!-- End of Navigation bar -->
    </body>
</html>
