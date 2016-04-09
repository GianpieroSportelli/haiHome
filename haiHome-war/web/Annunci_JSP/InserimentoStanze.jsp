<%-- 
    Document   : InserimentoStanze
    Created on : Mar 11, 2016, 10:22:55 AM
    Author     : giacomocavallo
--%>

<div class="col-xs-6 col-md-offset-3">

    <h3> Info Stanze</h3>

    <!-- bottone di aggiunta nuova stanza -->
    <div class="form-group row">
        <div class="col-md-10"></div>
        <div id="aggiungia  " class="btn btn-success col-md-2" onclick="nuovaStanza()">+</div>

    </div>

    <!-- contenitore stanze -->
    <div  class="col-md-12" id="contenitoreStanze">


        <!-- Stanza prova DA CANCELLARE 
        <div class="col-md-12 formContainer Stanza" id="stanza"> 

            <div class="form-group col-md-6">
                <div class="form-group">
                    <label class="control-label">Stanza</label>
                    <select id='selStanza' class="form-control" >
                        <option value="1">Stanza da Letto</option>
                        <option value="2">Stanza Accessoria</option>                       
                    </select>
                </div>

                <div class="form-group">
                    <label class="control-label">Tipo di Stanza</label>
                    <select class="form-control" id="seltipoStanza">
                        <option>Camera da Letto</option>
                        <option>Camera Accessoria</option>                       
                    </select>
                </div>
            </div>
            <div class="form-group col-md-6">
                <div id="aggiungia " class="btn buttonElimina" onclick="eliminaStanza('stanza')">-</div><br />
                <label class="control-label">Metratura</label>
                <input id="inpMetratura" maxlength="100" type="text" required="required" class="form-control" placeholder="Metratura" /><br />
            </div>
            <div class="form-group col-md-12" id="dropcontainer">
                <label class="control-label">Foto</label>
                <div id="dropzone"><form action="../FotoUploadServlet" class="dropzone needsclick dz-clickable" id="demo-upload">
                        <div class="dz-message needsclick">
                            Drop files here or click to upload.<br>
                        </div>
                    </form></div>
            </div>
        </div>
        FINE STANZA -->

    </div>

    <div class="form-group row">
        <div class="col-md-10"></div>
        <button class="btn btn-primary nextBtn btn-lg pull-right col-md-2" id="NxtS" type="button" >Next</button>
    </div>
</div>
