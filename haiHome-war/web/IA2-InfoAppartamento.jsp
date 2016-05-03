<%-- 
    Document   : InserimentoInfoAnnuncio
    Created on : Mar 11, 2016, 10:21:14 AM
    Author     : giacomocavallo
--%>


<div class="col-xs-6 col-md-offset-3">
    <div class="col-md-12">

        <form action="ServletAnnuncio" method="post" id="form-info-annuncio"> 
            <input type="hidden" name="action" value="Annunci-newAnnuncio-infoAnnuncio" /> 
            <h3>Info Appartamento</h3>
            <div class="form-group">
                <label class="control-label">Descrizione</label>
                <textarea id='textDescrizione' name='Descrizione' required="required" class="form-control" ></textarea>
            </div>
            <div class="form-group">
                <label class="control-label">Metratura</label>
                <input id='inpMetratura' name='Metratura' type="number" class="form-control" />
            </div>

            <div class="form-group">
                <label class="control-label">Data inizio Affitto</label>
                <input id='inpDataInizio' name='DataInizioAffitto' type="date" required="required" class="" />
            </div>
            <button type="submit"  id="butt2" class="btn btn-primary nextBtn btn-lg pull-right"> Next</button>

        </form>

    </div>
</div>

