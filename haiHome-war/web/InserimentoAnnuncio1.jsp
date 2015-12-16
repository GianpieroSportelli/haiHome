<%-- 
    Document   : InserimentoAnnuncio1
    Created on : Dec 14, 2015, 10:14:17 AM
    Author     : giacomocavallo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" href="include/css/InserimentoAnnuncio.css">
        <link rel="stylesheet" href="include/css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="include/css/main.css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
        <!-- non serve, è dello script del calendario
        <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap-combined.min.css" rel="stylesheet">
        -->
        <link rel="stylesheet" type="text/css" media="screen"
                href="http://tarruda.github.com/bootstrap-datetimepicker/assets/css/bootstrap-datetimepicker.min.css">
       
        
        <link rel="stylesheet" href="include/css/bootstrap.min.css">
        <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
    
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <title>Nuovo Annuncio</title>
    </head>
    <body>

    <div class="container">
    
                <!-- crea i diversi "step" che si vedono in alto -->    
        <div class="stepwizard">
            <div class="stepwizard-row setup-panel">
                <div class="stepwizard-step">
                    <a href="#step-1" type="button" class="btn btn-primary btn-circle">1</a>
                    <p>Info Indirizzo</p>
                </div>
                <div class="stepwizard-step">
                    <a href="#step-2" type="button" class="btn btn-default btn-circle" disabled="disabled">2</a>
                    <p>Info Annuncio</p>
                </div>
                <div class="stepwizard-step">
                    <a href="#step-3" type="button" class="btn btn-default btn-circle" disabled="disabled">3</a>
                    <p>Info Stanze</p>
                </div>
                <div class="stepwizard-step">
                    <a href="#step-4" type="button" class="btn btn-default btn-circle" disabled="disabled">4</a>
                    <p>SInfo Costi</p>
                </div>
            </div>
        </div>
                
      <!-- racchiude tutte le form che si possono avere -->      
      <!--   <form role="form"> -->
        
        
        <!-- form 1 info indirizzo-->

        <div class="row setup-content" id="step-1">
            <div class="col-xs-12">
                <div class="col-md-12">
                    
                    <h3> Info Indirizzo</h3>
                    
                    
                    <form name="form_indirizzo" method="post">
                    <div class="form-group">
                        <label for="sel1">Select list:</label>
                        <select name="citta" class="form-control" id="sel1">
                                <option>Torino</option>
                                <option>Da inserire in automatico</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                          <label for="sel1">Select list:</label>
                            <select name="quartiere" class="form-control" id="sel1">
                                <option>Aurora</option>
                                <option>San Donato</option>
                                <option>Centro</option>
                                <option>Da inserire in automatico</option>
                            </select>
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label">Indirizzo</label>
                        <input name ="indirizzo" maxlength="100" type="text" required="required" class="form-control" placeholder="Inserire Indirizzo" />
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label">Civico</label>
                        <input name ="civico" maxlength="100" type="text" required="required" class="form-control" placeholder="Civico" />
                    </div>
              
                    </form>
                    <button class="btn btn-primary nextBtn btn-lg pull-right"  type="button" >Next</button>
                </div>
            </div>
        </div>
            
      

            <!-- form 2  info annuncio  -->
        <div class="row setup-content" id="step-2">
            <div class="col-xs-12">
                <div class="col-md-12">
                    
                    <h3> Informazioni Annuncio</h3>
                    
                    <div class="form-group">
                        <label class="control-label">Descrizione</label>
                        <input maxlength="200" type="textarea" required="required" class="form-control" placeholder="Inserire una descrizione generale sull'immobile" />
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label">Metratura</label>
                        <input maxlength="200" type="text" required="required" class="form-control" placeholder="m&sup2"  />
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label">Data Inizio Affitto</label>
                         <div class="well">
                            <div id="datetimepicker4" class="input ">
                              <input data-format="yyyy-MM-dd" type="text"></input>
                              <span class="add-on glyphicon glyphicon-calendar">
                                <i data-time-icon="icon-time" data-date-icon="icon-calendar">
                                </i>
                              </span>
                            </div>
                          </div>
                    </div>    
                    
                    <div class="form-group">
                        <label class="control-label">Numero stanze</label>
                        <input maxlength="200" type="text" required="required" class="form-control" placeholder="Da modificare"  />
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label">Atomico</label>   
                        <input type="checkbox" required="required" />
                    </div>
                    
                    <button class="btn btn-primary nextBtn btn-lg pull-right" type="button" >Next</button>
                    
                </div>
            </div>
        </div>

                <!-- form 3 stanze TODO-->
        <div class="row setup-content" id="step-3">
            <div class="col-xs-12">
                <div class="col-md-12">
                    
                    <h3> Stanze </h3>
                    
      
                    
                    <button class="btn btn-primary nextBtn btn-lg pull-right" type="button">Next</button>
                </div>
            </div>
        </div>

                    <!-- form 4 info costi-->
        <div class="row setup-content" id="step-4">
            <div class="col-xs-12">
                <div class="col-md-12">
                    
                    <h3> Info costi</h3>
                    
                    <div class="form-group">
                        <label class="control-label">Prezzo</label>
                        <input maxlength="200" type="text" required="required" class="form-control" placeholder="Inserire Prezzo" />
                    </div>
                    
                        <div class="form-group">
                        <label class="control-label">Compreso Condominio</label>   
                        <input type="checkbox" required="required" />
                    </div>
                    
                    <div class="form-group">
                        <label class="control-label">Compreso Riscaldamento</label>   
                        <input type="checkbox" required="required" />
                    </div>
                    
                    <button class="btn btn-success btn-lg pull-right" onclick="inviaIndirizzo()" type="button">Finish!</button>
                    
                </div>
            </div>
        </div>
    <!--</form>-->
</div>
        
<script type="text/javascript" src="include/js/InserimentoAnnuncio.js"></script>  

<script type="text/javascript" src="include/js/InserimentoData.js"></script>

<script type="text/javascript"
     src="http://cdnjs.cloudflare.com/ajax/libs/jquery/1.8.3/jquery.min.js">
    </script> 
    
<script type="text/javascript"
     src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/js/bootstrap.min.js">
    </script>
    
<script type="text/javascript"
     src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.min.js">
    </script>
    
<script type="text/javascript"
     src="http://tarruda.github.com/bootstrap-datetimepicker/assets/js/bootstrap-datetimepicker.pt-BR.js">
    </script>

</body>
</html>
