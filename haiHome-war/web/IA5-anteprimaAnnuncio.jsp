<%-- 
    Document   : prova
    Created on : Apr 6, 2016, 10:10:07 AM
    Author     : giacomocavallo
--%>


<!-- Modal -->
<div id="myPreviewModal" class="modal fade">
    <div class="modal-dialog modal-lg">

        <!-- Modal content-->
        <div class="modal-content mod-prw-style">
            

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Anteprima Annuncio</h4>
            </div>
            
            
            <div class="modal-body " id="modalPreviewBody">

            </div>
            
            
            <div class="modal-footer">
                <div class="row col-md-12">
                    <div class="col-md-2">
                       <button id="annullaAnteprima" class="btn btn-danger btn-lg" data-dismiss="modal">Annulla</button>

                    </div>
                    <div class="col-md-8">
                    </div>
                    <div class="col-md-2">
                        <button id="confermaAnteprima" class="btn btn-success btn-lg pull-right" onclick="rendiAnnuncioPersistente()">Conferma</button>

                    </div>
                </div>
            </div>

        </div>
    </div>
</div>

