<%-- 
    Document   : InserimentoInfoAnnuncio
    Created on : Mar 11, 2016, 10:21:14 AM
    Author     : giacomocavallo
--%>


<div class="col-xs-6 col-md-offset-3">
    <div class="col-md-12">

        <form action="ServletController" method="post" id="form-info-appartamento"> 
            <input type="hidden" name="action" value="Annunci-editAnnuncio-infoAppartamento" /> 
            <div class="form-group">
                <label class="control-label">Descrizione</label>
                <textarea id='textDescrizione' name='Descrizione' required="required" class="form-control" disabled="disabled" ></textarea>
            </div>
            <div class="form-group">
                <label class="control-label">Metratura</label>
                <input id='inpMetratura' name='Metratura' type="number" class="form-control" disabled="disabled"/>
            </div>

            <div class="form-group">
                <label class="control-label">Data inizio Affitto</label>
                <input id='inpDataInizio' name='DataInizioAffitto' type="text" required="required" disabled="disabled" />
            </div>
            <div class="form-group">
                <input id='inpArredato' name='Arredato' type="checkbox"  value="true" disabled="disabled" />Arredato<br>
            </div>

        </form>

    </div>
</div>

