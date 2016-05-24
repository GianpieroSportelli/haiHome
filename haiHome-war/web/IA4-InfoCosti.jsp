<%-- 
    Document   : InserimentoCosti
    Created on : Mar 18, 2016, 10:16:17 AM
    Author     : giacomocavallo
--%>


<div class="col-md-12">

    <h3> Info Costi</h3>
    <form action="ServletController" method="post" id="form-info-costi"> 
        <input type="hidden" name="action" value="Annunci-newAnnuncio-infoCosti" /> 

        <div class="form-group">
            <label class="control-label">Modello di Costi</label>

            <select id="costi" onchange="cambiaSpecifiche()" class="form-control" name="Tipo Costo">
                <option id="nothing" selected="selected" value="0">Selezione modello di costi</option>
                <option  value="1">Prezzo intero Appartamento</option>
                <option  value="2">Prezzo singole Stanze</option>
            </select>

        </div> 
        
        <hr>


        <div class="form-group" id="prezzoAppartamento" hidden>

            <div class="row col-md-12">
                <label class="control-label">Prezzo Appartamento</label>
                <input id = 'prezzoA' name = 'prezzoA' class="form-control" />
            </div>
            
            <div class="row col-md-12">
                <div class="col-md-4 verticalCenter">
                    <label>Compreso Condominio</label>
                </div>
                <div class="col-md-5 verticalCenter">
                    <input name='compresoCondominioA' type="checkbox"  value="true">  
                </div>
            </div>

            <div class="row col-md-12">
                <div class="col-md-4 verticalCenter">
                    <label>Compreso Riscaldamento</label>
                </div>
                <div class="col-md-5 verticalCenter">
                    <input name='compresoRiscaldamentoA' type="checkbox"  value="true"> 
                </div>
            </div>
            
            
        </div>
        
        <div id="prezzoStanze" hidden>

        </div>
        <div class="row">
            <button id="submitButtom" class="btn btn-success btn-lg pull-right"style="display: none;" > Conferma</button>
            </div>
    </form>

</div>
