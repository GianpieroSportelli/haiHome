<%-- 
    Document   : InserimentoStanze
    Created on : Mar 11, 2016, 10:22:55 AM
    Author     : giacomocavallo
--%>

<div class="col-xs-6 col-md-offset-3">

<div class="form-group">
    <div id="aggiungia" onclick="nuovaStanza()">+</div>

  </div>

    <h3> Info Stanze</h3>
    
    <div id="contenitoreStanze">

        <div class="col-md-12 formContainer" id="stanza"> 
              
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
        </div>
        <div class="form-group col-md-6">
            <label class="control-label">Metratura</label>
            <input maxlength="100" type="text" required="required" class="form-control" placeholder="Metratura" /><br />
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
  
    </div>


</div>




<!-- aggiungi stanze

        <div class="form-group">
      <div id="aggiungi" onclick="nuovaStanza()">+</div>
      <div class=container" id="elencoStanze">

      </div>
  </div>

        <form action="/file-upload"
class="dropzone"
id="my-awesome-dropzone"></form>

-->