<%-- 
    Document   : InserimentoInfoAppartamento
    Created on : Mar 11, 2016, 10:22:13 AM
    Author     : giacomocavallo
--%>

<div class="col-xs-6 col-md-offset-3">
    <div class="col-md-12">

        <form action="ServletAnnuncio" method="post" id="form-info-appartamento">
            <input type="hidden" name="action" value="Annunci-newAnnuncio-infoAppartamento" /> 
            <h3> Info Annuncio</h3>
            <div class="form-group">
                <label class="control-label">Citta</label>

                <select class="form-control citta" name='Città' id="selCitta">
                    <option>Torino</option>
                </select>

            </div>

            <label class="control-label">Indirizzo</label>
            <div class="form-group row">
                
                
      
                <div class="col-md-10">
                        <input id='inpIndirizzo'maxlength="100" type="text" required="required" class="form-control" placeholder="Inserisci Indirizzo"  name='Indirizzo' onkeypress="return event.keyCode != 13"/><br />
                        
                </div>
                <div class="col-md-2">
                        <input id='inpCivico'maxlength="5" type="text" required="required" class="form-control" placeholder="n°" name='Civico' onkeypress="return event.keyCode != 13" onclick="civicoFunction()"  />
                </div>

            </div>

            <div class="form-group">
                <label class="control-label">Quartiere</label>
                <select class="form-control" id="selQuartiere" name='Quartiere'>

                </select>
            </div>

            <button id="butt1" type="submit" class="btn btn-primary nextBtn btn-lg pull-right"> Next</button>

        </form>



    </div>
</div>
