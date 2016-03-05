<%-- 
    Document   : search
    Created on : 11-dic-2015, 16.58.42
    Author     : gianp_000
--%>
<%@page import="org.json.JSONObject"%> 
<%@page import="java.util.ArrayList"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>Search Page</title>
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
        <!--<link rel="stylesheet" href="include/css/prova_list.css">-->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js" ></script>
        <script src="include/js/prova_list.js"></script>
        <!-- INIZIO caricamento bootstrap mediante MaxCDN -->
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
        <!-- FINE caricamento bootstrap mediante MaxCDN -->
        <%
            ArrayList<String> quartieri = (ArrayList<String>) request.getAttribute("quartieri");
        %>
    </head>
    <body>
        <!-- bootstap necessita di container ne esistono di 2 tipi container e container-fluid -->
        <div class="container">
            <%@include file="/jspf/header.jspf" %>
            <div class="row">
                <div class="col-sm-9"></div>
                <div class="col-sm-3">
                    <div class="well">
                        <h3 align="center">Filtro di Ricerca</h3>
                        <form class="form-horizontal" method="POST" action="ServletController" id="searchForm" >
                            <input type="hidden" name="action" value="search">
                            <div class="form-group">
                                <h5>Quartieri</h5>
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="all" name="quartieri">
                                        all
                                    </label>
                                </div>

                                <% for (String quart : quartieri) {%>
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="<%= quart%>" name="quartieri">
                                        <%= quart%>
                                    </label>
                                </div>
                                <% }%>
                                <!--<div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="all" name="quartieri">
                                        all
                                    </label>
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="Mirafiori" name="quartieri" ><!--disabled>
                                        Mirafiori
                                    </label>
                                </div>
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="Centro" name="quartieri" ><!--disabled>
                                        Centro
                                    </label>        
                                </div>-->
                            </div>

                            <div class="form-group">
                                <label for="tipo" class="control-label">Tipo Annuncio</label>
                                <select class="form-control" name="tipo" id="tipo">
                                    <option value="all">-</option>
                                    <option value="Appartamento">Appartamento</option>
                                    <option value="Stanza">Stanza</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="pricefrom" class="control-label">Min Price</label>
                                <div class="input-group">
                                    <div class="input-group-addon" id="basic-addon1">Euro</div>
                                    <input type="text" class="form-control" id="pricefrom" name="pricefrom" aria-describedby="basic-addon1">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="true" name="compCondomino">
                                        Compreso Condominio
                                    </label>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="checkbox">
                                    <label>
                                        <input type="checkbox" value="true" name="compRiscaldamento">
                                        Compreso Riscaldamento
                                    </label>
                                </div>
                            </div>
                            <!--<div class="form-group">
                                <label for="priceto" class="control-label">Max Price</label>
                                <div class="input-group">
                                    <div class="input-group-addon" id="basic-addon2">Euro</div>
                                    <input type="text" class="form-control" id="priceto" name="priceto" aria-describedby="basic-addon1">
                                </div>
                            </div>-->
                            <script>
                                $("select").change(function () {
                                    if($("#tipo").val()==="Appartamento"){
                                        //alert("Appartamento");
                                        $("#divTipoStanza").hide();
                                        $("#divNLocali").show("slow");
                                        $("#divNCamere").show("slow");
                                        $("#divNBagni").show("slow");
                                        $("#divMetratura").show("slow");
                                    }else if($("#tipo").val()==="Stanza"){
                                        //alert("Stanza");
                                        $("#divTipoStanza").show("slow");
                                        $("#divNLocali").hide();
                                        $("#divNCamere").hide();
                                        $("#divNBagni").hide();
                                        $("#divMetratura").hide();
                                    }else{
                                        //alert("Lascia perdere");
                                        $("#divTipoStanza").hide();
                                        $("#divNLocali").hide();
                                        $("#divNCamere").hide();
                                        $("#divNBagni").hide();
                                        $("#divMetratura").hide();
                                    }
                                    
                                });
                            </script>
                            <div class="form-group" id="divTipoStanza" style="display:none">
                                <label for="tipo" class="control-label">Tipo Stanza</label>
                                <select class="form-control" name="tipoStanza" id="tipoStanza">
                                    <option value="all">-</option>
                                    <option value="Singola">Singola</option>
                                    <option value="Doppia">Doppia</option>
                                </select>
                            </div>
                            
                            <div class="form-group" id="divNLocali" style="display:none">
                                <label for="numeroLocali" class="control-label">Numero Locali</label>
                                <div class="input-group">
                                    <div class="input-group-addon" id="NLocali" >#</div>
                                    <input type="text" class="form-control" id="numeroLocali" name="numeroLocali" aria-describedby="NLocali">
                                </div>
                            </div>
                            <div class="form-group" id="divMetratura" style="display:none">
                                <label for="metratura" class="control-label">Metratura</label>
                                <div class="input-group">
                                    <div class="input-group-addon" id="divmet" >#</div>
                                    <input type="text" class="form-control" id="metratura" name="metratura" aria-describedby="divmet">
                                </div>
                            </div>
                            <div class="form-group" id="divNCamere" style="display:none">
                                <label for="numeroCamere" class="control-label">Numero Camere</label>
                                <div class="input-group">
                                    <div class="input-group-addon" id="NCamere" >#</div>
                                    <input type="text" class="form-control" id="numeroCamere" name="numeroCamere" aria-describedby="NCamere">
                                </div>
                            </div>
                            <div class="form-group" id="divNBagni" style="display:none">
                                <label for="numeroLocali" class="control-label">Numero Bagni</label>
                                <div class="input-group">
                                    <div class="input-group-addon" id="NBagni" >#</div>
                                    <input type="text" class="form-control" id="numeroBagni" name="numeroBagni" aria-describedby="NBagni">
                                </div>
                            </div>
                            <button type="submit" class="btn btn-danger glyphicon glyphicon-search"></button>
                        </form>
                    </div>
                </div>
            </div>
            <%@include file="/jspf/footer.jspf" %>
        </div>

    </body>
</html>
