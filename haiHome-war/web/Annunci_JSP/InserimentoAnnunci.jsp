<%-- 
    Document   : InserimentoAnnunci
    Created on : Mar 11, 2016, 10:19:16 AM
    Author     : giacomocavallo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!--da modificare con riferimento interno -->
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">

        <script src="//code.jquery.com/jquery-1.10.2.min.js"></script>



        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
        

        

        <link rel="stylesheet" href="../include/css/InserimentoAnnunci/InserimentoAnnuncio.css">
        <link rel="stylesheet" href="../include/css/InserimentoAnnunci/dropzone1.css">  
        
        <!-- datapicker CSS -->
          <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">


        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


    </head>
    <body>

        <div class="container">

            <!-- Step superiori -->
            <div class="stepwizard col-md-offset-3">
                <div class="stepwizard-row setup-panel">
                    <div class="stepwizard-step">
                        <a href="#step-1" type="button" class="btn btn-primary btn-circle">1</a>
                        <p>Info Indirizzo</p>
                    </div>
                    <div class="stepwizard-step">
                        <a href="#step-2" type="button" class="btn btn-default btn-circle" disabled="disabled">2</a>
                        <p>Info Annuncio </p>
                    </div>
                    <div class="stepwizard-step">
                        <a href="#step-3" type="button" class="btn btn-default btn-circle" disabled="disabled">3</a>
                        <p>Inserimento Stanze</p>
                    </div>
                    <div class="stepwizard-step">
                        <a href="#step-4" type="button" class="btn btn-default btn-circle" disabled="disabled">4</a>
                        <p>Specifica Costi</p>
                    </div>
                </div>
            </div> <!-- fine step superiori-->

            <!-- inizio pagine step -->

            
                
            
            
                <div class="row setup-content" id="step-1"> 

                    <%@include file="InserimentoInfoAppartamento.jsp" %>  <!-- ORIGINALE   prova.jsp -->
                    
                    
                </div>

                <div class="row setup-content" id="step-2">
                    
                    <%@include file="InserimentoInfoAnnuncio.jsp" %>
                    
                </div>

                <div class="row setup-content" id="step-3">

                    <%@include file="InserimentoStanze.jsp" %>
                                                                        
                </div>
   
                <div class="row setup-content" id="step-4">
                    
                   <%@include file="InserimentoCosti.jsp" %>
                    
                </div>

         <!--   </form>  -->



        </div> <!-- fine container -->


        <script type="text/javascript">

        </script>



        <script type="text/javascript"
                src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.min.js">
        </script>

        <script type="text/javascript"
                src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.pt-BR.js">
        </script>
        
                <!-- Datapicker JQuery-->
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        
        
        
        <!--INIZIO - Form ajax plugin -->
        <script src="http://malsup.github.com/jquery.form.js"></script> 
        <!--FINE- Form ajax plugin -->

        <script type="text/javascript" src="../include/js/InserimentoAnnunci/dataPicker.js"></script>

        <script type="text/javascript" src="../include/js/InserimentoAnnunci/stanzeUtily.js"></script>

        <script type="text/javascript" src="../include/js/InserimentoAnnunci/dropzone1.js"></script>
        
        <script type="text/javascript" src="../include/js/InserimentoAnnunci/InserimentoAnnuncio.js"></script>
        


    </body>
</html>