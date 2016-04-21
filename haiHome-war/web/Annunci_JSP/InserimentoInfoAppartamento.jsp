<%-- 
    Document   : InserimentoInfoAppartamento
    Created on : Mar 11, 2016, 10:22:13 AM
    Author     : giacomocavallo
--%>

<div class="col-xs-6 col-md-offset-3">
    <div class="col-md-12">

        <form action="../ServletAnnuncio" method="post" id="form-info-appartamento">
            <input type="hidden" name="action" value="Annunci-newAnnuncio-infoAppartamento" /> 
            <h3> Info Annuncio</h3>
            <div class="form-group">
                <label class="control-label">Citta</label>

                <select class="form-control citta" name='Città' id="selCitta">
                    <option>Torino</option>
                </select>

            </div>
            <div class="form-group">
                <label class="control-label">Quartiere</label>
                <select class="form-control" id="selQuartiere" name='Quartiere'>
                    <option>Aurora</option>
                    <option>Altri Quartieri</option>                       
                </select>
            </div>
            <div class="form-group">

                <label class="control-label">Indirizzo</label>
                <input id='inpIndirizzo'maxlength="100" type="text" required="required" class="form-control" placeholder="Inserisci Indirizzo"  name='Indirizzo'/><br />
                <input id='inpCivico'maxlength="100" type="text" required="required" class="form-control" placeholder="Civico" name='Civico' />

            </div>

            <button id="butt1" type="submit" class="btn btn-primary nextBtn btn-lg pull-right"> Next</button>
           
        </form>
        

         
    </div>
</div>
