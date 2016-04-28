<%-- 
    Document   : InserimentoCosti
    Created on : Mar 18, 2016, 10:16:17 AM
    Author     : giacomocavallo
--%>

<div class="col-xs-6 col-md-offset-3">
    <div class="col-md-12">
        <form action="../ServletAnnuncio" method="post" id="form-info-costi"> 
            <input type="hidden" name="action" value="Annunci-newAnnuncio-infoCosti" /> 
            
        <div class="form-group">
            <label class="control-label">Modello di Costi</label>

            <select id="costi" onchange="cambiaSpecifiche()" class="form-control" name="Tipo Costo">
                <option id="nothing" selected="selected">-</option>
                <option  value="1">Prezzo intero Appartamento</option>
                <option  value="2">Prezzo singole Stanze</option>
            </select>

        </div>                       

        <div>
            <div class="form-group" id="prezzoAppartamento" hidden>
            <label class="control-label">Prezzo Appartamento</label>
            <input id = 'prezzoA' name = 'prezzoA' type="number" required="required" class="form-control" /><br />
            <input name='compresoCondominioA' type="checkbox"  value="true">Compreso Condominio<br>
            <input name='compresoRiscaldamentoA' type="checkbox"  value="true">Compreso Riscaldamento<br>
            
        </div>
        </div>
        
        <div id="prezzoStanze" hidden>
            
        </div>




            <button id="submitButtom" class="btn btn-success btn-lg pull-right" > Conferma</button>
        </form>
    </div>
</div>

