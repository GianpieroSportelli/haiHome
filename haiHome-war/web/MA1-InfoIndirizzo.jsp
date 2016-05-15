<%-- 
    Document   : MA1-InfoIndirizzo
    Created on : May 4, 2016, 2:19:59 PM
    Author     : giacomocavallo
--%>

<div class="col-xs-6 col-md-offset-3">
    <div class="col-md-12">

        <form action="ServletController" method="post" id="form-edit-indirizzo">
            <input type="hidden" name="action" value="Annunci-editAnnuncio-infoIndirizzo" /> 

            <label class="control-label">Indirizzo</label>
            <div class="form-group row">
                
                
      
                <div class="col-md-10">
                        <input id='inpIndirizzo'maxlength="100" type="text" required="required" class="form-control"  name='Indirizzo' disabled="disabled"/><br />
                        
                </div>
                <div class="col-md-2">
                        <input id='inpCivico'maxlength="5" type="text" required="required" class="form-control" placeholder="n°" name='Civico' disabled="disabled" onclick="civicoFunction()"/>
                </div>

            </div>

            <div class="form-group">
                <label class="control-label">Quartiere</label>
                <select class="form-control" id="selQuartiere" name='Quartiere' disabled="disabled">

                </select>
            </div>

        </form>



    </div>
</div>