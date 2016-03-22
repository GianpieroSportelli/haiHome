<%-- 
    Document   : InserimentoCosti
    Created on : Mar 18, 2016, 10:16:17 AM
    Author     : giacomocavallo
--%>

<div class="col-xs-6 col-md-offset-3">
    <div class="col-md-12">

        <div class="form-group">
            <label class="control-label">Modello di Costi</label>

            <select id="costi" onchange="cambiaSpecifiche()" class="form-control">
                <option selected="selected">-</option>
                <option  value="1">Prezzo intero Appartamento</option>
                <option  value="2">Prezzo singole Stanze</option>
            </select>

        </div>                       

        <div>
            <div class="form-group" id="prezzoAppartamento" hidden>
            <label class="control-label">Prezzo Appartamento</label>
            <input maxlength="100" type="text" required="required" class="form-control" placeholder="Inserisci Indirizzo" /><br />
            <input type="checkbox" name="vehicle" value="Bike">Compreso Condominio<br>
            <input type="checkbox" name="vehicle" value="Car" checked>Compreso Riscaldamento<br>
            
        </div>
        </div>
        
        <div id="prezzoStanze" hidden>
            <!--
            <div class="form-group">
            <label class="control-label">Prezzo Stanza</label>
            <input maxlength="100" type="text" required="required" class="form-control" placeholder="Inserisci Indirizzo" /><br />
            <input type="checkbox" name="vehicle" value="Bike">Compreso Condominio<br>
            <input type="checkbox" name="vehicle" value="Car" checked>Compreso Riscaldamento<br>
            
        </div>
            -->
        </div>
        



        <button class="btn btn-success btn-lg pull-right" type="submit">Submit</button>
    </div>
</div>
