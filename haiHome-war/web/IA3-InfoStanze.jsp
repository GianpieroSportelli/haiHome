<%-- 
    Document   : InserimentoStanze
    Created on : Mar 11, 2016, 10:22:55 AM
    Author     : giacomocavallo
--%>


    <div class="col-md-12">

    <h3> Info Stanze</h3>

    <!-- bottone di aggiunta nuova stanza 
    <div class="row pull-right">
        <div id="aggiungia " class="btn btn-success col-md-2" onclick="nuovaStanza1()">
            <span class="glyphicon glyphicon-plus"></span>
        </div>

    </div>-->


        <!-- contenitore stanze -->
    <form action="ServletController" method="post" id="formStanze">
        <input type="hidden" name="action" value="Annunci-newAnnuncio-infoStanze" /> 
        <div  class="cont-stanze col-md-12" id="contenitoreStanze">
            
        </div>
    </form>
    

    <div class="row">
        
        <div id="aggiungia " class="btn btn-success col-md-1" onclick="nuovaStanza1()">
            <span class="glyphicon glyphicon-plus"></span>
        </div>
        
        <div class="col-md-10"></div>
        
        <button class="btn btn-primary nextBtn pull-right col-md-1" id="buttStanze" type="button" >Avanti</button>

    </div>
        
    </div>
