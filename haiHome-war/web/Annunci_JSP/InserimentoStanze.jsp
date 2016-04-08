<%-- 
    Document   : InserimentoStanze
    Created on : Mar 11, 2016, 10:22:55 AM
    Author     : giacomocavallo
--%>

<div class="col-xs-6 col-md-offset-3">

    <h3> Info Stanze</h3>

    <!-- bottone di aggiunta nuova stanza -->
    <div class="form-group row">
        <div class="col-md-10"></div>
        <div id="aggiungia  " class="btn btn-success col-md-2" onclick="nuovaStanza()">+</div>

    </div>

    <form action="../ServletAnnuncio" method="post" id="form-info-stanze">
        <input type="hidden" name="action" value="Annunci-newAnnuncio-infoStanze" /> 

        <!-- contenitore stanze -->
        <div  class="col-md-12" id="contenitoreStanze">



        </div>

    

    <div class="form-group row">
        <div class="col-md-10"></div>
        <button class="btn btn-primary nextBtn btn-lg pull-right col-md-2" id="butt3" type="submit" >Next</button>
    </div>
    </form>
</div>
