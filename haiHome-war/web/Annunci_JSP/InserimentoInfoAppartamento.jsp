<%-- 
    Document   : InserimentoInfoAppartamento
    Created on : Mar 11, 2016, 10:22:13 AM
    Author     : giacomocavallo
--%>

<!-- NOTE:
ToDO:
    Riempire le select con i valori presenti nel DB
    Inserire l'autocompletamento di Google nel selezionate l'indirizzo
    Modificare qualcosina graficamente

-->
<!-- inizio pagine tutto cio esterno al commento va eliminato -->

<div class="col-xs-6 col-md-offset-3">
    <div class="col-md-12">
        <h3> Info Appartamento</h3>
        <div class="form-group">
            <label class="control-label">Citta</label>

            <select class="form-control" id="sel1">
                <option>Torino</option>
            </select>

        </div>
        <div class="form-group">
            <label class="control-label">Quartiere</label>
            <select class="form-control" id="sel1">
                <option>Aurora</option>
                <option>Altri Quartieri</option>                       
            </select>
        </div>
        <div class="form-group">

            <label class="control-label">Indirizzo</label>
            <input maxlength="100" type="text" required="required" class="form-control" placeholder="Inserisci Indirizzo" /><br />
            <input maxlength="100" type="text" required="required" class="form-control" placeholder="Civico" />

        </div>

        <button class="btn btn-primary nextBtn btn-lg pull-right" type="button" >Next</button>
    </div>
</div>

<!-- FINE -->