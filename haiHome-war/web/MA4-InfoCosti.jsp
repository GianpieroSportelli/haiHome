<%-- 
    Document   : InserimentoCosti
    Created on : Mar 18, 2016, 10:16:17 AM
    Author     : giacomocavallo
--%>

<div class="col-xs-6 col-md-offset-3">
    <div class="col-md-12">
        <form action="ServletAnnuncio" method="post" id="form-edit-costi"> 
            <input type="hidden" name="action" value="Annunci-editAnnuncio-infoCosti" /> 

            <div class="form-group">

                <label class="control-label">Modello di Costi</label>

                <select id="modelloCosti" onchange="cambiaSpecificheCosti()" class="form-control" name="Tipo Costo" disabled="disabled">
                    <option  value="1">Prezzo intero Appartamento</option>
                    <option  value="2">Prezzo singole Stanze</option>
                </select>
            </div>                       


            <div class="form-group" id="prezzoAppartamento" hidden>
                <label class="control-label">Prezzo Appartamento</label>
                <input id = 'prezzoA' name = 'prezzoA' class="form-control" type='number' disabled="disabled" /><br />
            </div>
            
            <div id="prezzoStanze" hidden>

            </div>
            </br>
            <div class="form-group">
            <input name='compresoCondominio' id="CC" type="checkbox"  value="true" disabled="disabled">Compreso Condominio<br>
            <input name='compresoRiscaldamento' id="CR" type="checkbox"  value="true" disabled="disabled">Compreso Riscaldamento<br>
            </div>





        </form>
    </div>
</div>

