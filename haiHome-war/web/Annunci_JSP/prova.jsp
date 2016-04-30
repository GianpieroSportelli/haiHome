<%-- 
    Document   : prova
    Created on : Apr 26, 2016, 4:32:13 PM
    Author     : giacomocavallo
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Home</title>
        
                <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        
        
        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <!-- FINE caricamento bootstrap mediante MaxCDN -->


        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap-theme.min.css">
        <!--Tema bootstrap -->
        
                <!--INIZIO - Form ajax plugin -->
        <script src="http://malsup.github.com/jquery.form.js"></script> 
        <!--FINE- Form ajax plugin -->
        
        
        <style type="text/css"> 
            .buttonDiv {border-style: solid;}
  
        </style>
  
    </head>
    <body>
                        <input id='inpIndirizzoProva'maxlength="100" type="text" required="required" class="form-control" placeholder="Inserisci Indirizzo"  name='Indirizzo'/><br />
                        

                                <!-- script miei -->
        <script type="text/javascript" src="../include/js/InserimentoAnnunci/googleAutocomplete.js"></script>
        
                               <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC2yod6637sOZqbmDNOZSUh-30b6xTchBE&signed_in=true&libraries=places&callback=initAutocomplete" async defer></script>
        <!-- fine script miei -->
                        
    </body>
</html>