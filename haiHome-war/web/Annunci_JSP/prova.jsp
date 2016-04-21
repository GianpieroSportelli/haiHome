<%-- 
    Document   : prova
    Created on : Apr 6, 2016, 10:10:07 AM
    Author     : giacomocavallo
--%>

<!--
<div id="stanza1" class="col-md-12 formContainer Stanza">
    <form action="../ServletAnnuncio" method="post" id="prova">  
        <input type="hidden" name="action" value="prova" />
        
        <div class="form-group col-md-6">
        <div class="form-group">
                <label class="control-label">Stanza</label>
                <select name='TipologiaStanza' class="form-control" id="selStanza" onchange="cambiaSpecificheTipologiaStanza('stanza1')">
                    <option value="1">Stanza da Letto</option>
                    <option value="2">Stanza Accessoria</option>
                </select>
            </div>
            <div class="form-group">
                <label class="control-label">Tipo di Stanza</label>
                <select name='TipoL' class="form-control" id="seltipoLetto">
                    <option value="Singola">Singola</option>
                    <option value="Doppia">Doppia</option>
                    <option value="Altro">Altro</option>
                </select>
                <select name='TipoA' class="form-control" id="seltipoAcc" style="display:none" >
                    <option>Bagno</option><option>Cucina</option>
                    <option>Soggiorno</option>
                    <option>Altro</option>
                </select>
            </div>

        </div>
        <div class="form-group col-md-6">
            <div id="aggiungia" class="btn buttonElimina" onclick="eliminaStanza('stanza1'">-</div><br />
            <label class="control-label">Metratura</label> <input name='MetraturaS' id="inpMetratura"maxlength="100" type="text" class="form-control" placeholder="Metratura" /><br />
        </div>
    </form>
    <div class="form-group col-md-12">
        <label class="control-label">Foto</label>

        <div  id="mydropzone1"  class="dropzone needsclick dz-clickable" >  
            <div class="dz-message needsclick">Drop files here or click to upload.<br></div>

        </div>

    </div>
    <button type="button"  id="buttProva" class="btn btn-primary nextBtn btn-lg pull-right"> Next</button>
</div>


<html lang="en">
<head>
  <meta charset="utf-8">
  <title>datepicker demo</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
  <script src="//code.jquery.com/jquery-1.10.2.js"></script>
  <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
</head>
<body>
 
<div id="datepicker"></div>
 
<script>
$( "#datepicker" ).datepicker();
</script>
 
</body>
</html>

-->

<button type="button" class="btn btn-info btn-lg" data-toggle="modal" data-target="#myModal">Open Modal</button>

<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog modal-lg">

    <!-- Modal content-->
    <div class="modal-content">
        
       
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Anteprima Annuncio</h4>
      </div>
      <div class="modal-body" id="modalBody">
          
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
        
        
    </div>

  </div>
</div>
