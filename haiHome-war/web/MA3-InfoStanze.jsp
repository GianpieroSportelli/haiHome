<%-- 
    Document   : InserimentoStanze
    Created on : Mar 11, 2016, 10:22:55 AM
    Author     : giacomocavallo
--%>

<div class="col-xs-6 col-md-8 col-md-offset-2">

    <div class="">
    <h3> Info Stanze</h3> 
    <div id="aggiungia " class="btn btn-success col-md-2" onclick="nuovaStanza1()">+</div>

    </div>
    <div class="form-group">
        
        

    


        <!-- contenitore stanze -->
 <div class="col-md-6">
    <form action="ServletAnnuncio" method="post" id="formStanze">
        <input type="hidden" name="action" value="Annunci-newAnnuncio-infoStanze" /> 
        
        
        <ul id="contenitoreTab" class="nav nav-tabs">

        </ul>
        
        <div  class="tab-content" id="contenitoreStanze">

        </div>
    </form>
        
</div>
    
</div>

</div>