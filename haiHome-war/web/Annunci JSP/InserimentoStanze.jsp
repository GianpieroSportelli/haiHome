<%-- 
    Document   : InserimentoStanze
    Created on : Mar 11, 2016, 10:22:55 AM
    Author     : giacomocavallo
--%>

<div class="col-xs-6 col-md-offset-3">
    <div class="col-md-12">
        <h3> Info Stanze</h3>

        <div class="form-group col-md-6">


            <div class="form-group">
                <label class="control-label">Stanza</label>
                <select class="form-control" id="sel1">
                    <option>Stanza da Letto</option>
                    <option>Stanza Accessoria</option>                       
                </select>
            </div>

            <div class="form-group">
                <label class="control-label">Tipo di Stanza</label>
                <select class="form-control" id="sel1">
                    <option>Camera da Letto</option>
                    <option>Camera Accessoria</option>                       
                </select>
            </div>
            <label class="control-label">Metratura</label>
            <input maxlength="100" type="text" required="required" class="form-control" placeholder="Metratura" /><br />


        </div>
        <div class="form-group col-md-6">
            <label class="control-label">Foto</label>
            
            <div class="container-fluid">
                <div class="row">
                    <div class="col-sm-12">
                        <h3>DropzoneJS Upload Example - http://www.dropzonejs.com/</h3>  
                    </div>
                </div><!--/row-->
                <hr>
                <div> 
                    <form action="/upload" class="dropzone" drop-zone="" id="file-dropzone"></form>
                </div>
            </div>


        </div>



        <div class="form-group">
            <div id="aggiungi" onclick="nuovaStanza()">+</div>
            <div class=container" id="elencoStanze">

            </div>
        </div>
    </div>
</div>
