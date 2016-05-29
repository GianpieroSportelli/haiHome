<%-- 
    Document   : InserimentoInfoAnnuncio
    Created on : Mar 11, 2016, 10:21:14 AM
    Author     : giacomocavallo
--%>



<div class="col-md-12">

    <form action="ServletController" method="post" id="form-info-annuncio"> 
        <input type="hidden" name="action" value="Annunci-newAnnuncio-infoAnnuncio" /> 
        <h3>Info Annuncio</h3>
        <div class="form-group col-md-12 row">
            <label class="control-label">Descrizione</label>
            <textarea id='textDescrizione' name='Descrizione' required="required" class="form-control" ></textarea>
        </div>

        <div class="form-group col-md-12 row">
            <label class="control-label">Metratura</label>
            <input id='inpMetratura' name='Metratura' type="number" class="form-control input-lg" />
        </div>

        <div class="row col-md-12 form-group">

            <div class="col-md-3 verticalCenter">

                    <label class="control-label">Data inizio Affitto</label>

            </div>

            <div class="col-md-5">
                <input id='inpDataInizio' name='DataInizioAffitto' type="text" required="required" class="input-lg" />
            </div>

        </div>

        <div class="row col-md-12">

            <div class="col-md-3 verticalCenter">

                    <label>Arredato</label>

            </div>

            <div class="col-md-5 verticalCenter">
            <input name='Arredato' type="checkbox" value="true">   
            </div>

        </div>


            <div class="row pull-right">
                 <button id="butt2" type="submit" class="btn btn-primary nextBtn"> Avanti</button>
            </div>

    </form>

</div>


