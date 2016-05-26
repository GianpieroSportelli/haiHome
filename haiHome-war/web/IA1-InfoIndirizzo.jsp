<%-- 
    Document   : InserimentoInfoAppartamento
    Created on : Mar 11, 2016, 10:22:13 AM
    Author     : giacomocavallo
--%>

<div class="col-md-12" >
    <!--<div class="col-md-12" style="background: red">-->

        <form action="ServletController" method="post" id="form-info-appartamento" role="form">
            <input type="hidden" name="action" value="Annunci-newAnnuncio-infoAppartamento" /> 
            <h3> Info Indirizzo</h3>
            <div class="form-group">
                <label class="control-label">Citta</label>

                <div class="select-style">
                <select class="citta selectpicker" name='Città' id="selCitta">
                    <option>Torino</option>
                </select>
                </div>
            </div>

            <label class="control-label">Indirizzo</label>
            <div class="row">
                
                
      
                <div class="col-md-10 form-group ">
                        <input id='inpIndirizzo'maxlength="100" type="text" required="required" class="form-control input-lg" placeholder="Inserisci Indirizzo"  name='Indirizzo' onkeypress="return event.keyCode != 13 "/>
                        
                </div>
                <div class="col-md-2 form-group ">
                        <input id='inpCivico'maxlength="5"  type="text" required="required" class="form-control input-lg" placeholder="n°" name='Civico' onkeypress="return event.keyCode != 13 && event.keyCode != 9" onclick="civicoFunction()"  />
                </div>

            </div>

            <div class="form-group">
                <label class="control-label">Quartiere</label>
                                <div class="select-style">
                <select class="" id="selQuartiere" name='Quartiere'>

                </select>
            </div>
            </div>

            <div class="row pull-right">
                 <button id="butt1" type="submit" class="btn btn-primary nextBtn"> Next</button>
            </div>


        </form>



   <!-- </div>-->
</div>
