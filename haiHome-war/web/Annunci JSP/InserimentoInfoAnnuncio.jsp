<%-- 
    Document   : InserimentoInfoAnnuncio
    Created on : Mar 11, 2016, 10:21:14 AM
    Author     : giacomocavallo
--%>

<!-- Note:
ToDo:
    Migliorare lo stile del datepiker
    piccole migliorie metratura e descrizione 
-->
<div class="col-xs-6 col-md-offset-3">
    <div class="col-md-12">

        <h3>Info Appartamento</h3>
        <div class="form-group">
            <label class="control-label">Descrizione</label>
            <textarea required="required" class="form-control" placeholder="Enter your address" ></textarea>
        </div>
        <div class="form-group">
            <label class="control-label">Metratura</label>
            <input maxlength="200" type="text" required="required" class="form-control" placeholder="Enter Company Address"  />
        </div>
        <div class="form-group">
            <label class="control-label">Data inizio Affitto</label>

            <input id="test" />

            <div class="well">


                <div id="datetimepicker4" class="input ">
                    <!--
                    <input data-format="yyyy-MM-dd" type="text"></input>
                    <span class="add-on glyphicon glyphicon-calendar">
                        <i data-time-icon="icon-time" data-date-icon="icon-calendar">
                        </i>
                    </span>
                    -->
                </div>

            </div>

        </div>
        <button class="btn btn-primary nextBtn btn-lg pull-right" type="button" >Next</button>
    </div>
</div>
