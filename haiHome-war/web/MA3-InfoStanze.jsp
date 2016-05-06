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
        <div id="aggiungia " class="btn btn-success col-md-2" onclick="nuovaStanza1()">+</div>

    </div>


        <!-- contenitore stanze -->
    <form action="ServletAnnuncio" method="post" id="formStanze">
        <input type="hidden" name="action" value="Annunci-newAnnuncio-infoStanze" /> 
        <div  class="col-md-12" id="contenitoreStanze">



        </div>
    </form>
    
</div>
