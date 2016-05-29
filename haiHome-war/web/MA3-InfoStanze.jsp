<%-- 
    Document   : InserimentoStanze
    Created on : Mar 11, 2016, 10:22:55 AM
    Author     : giacomocavallo
--%>

<div class="col-xs-6 col-md-8 col-md-offset-3">

    <div class="container col-md-8">

        <div class="row">
            <h3> Info Stanze</h3> 
            <!--<div id="aggiungia " class="btn btn-success span1" onclick="nuovaStanza1()">+</div>-->
        </div>

        <div class=" row">
            <ul id="contenitoreTab" class="nav nav-tabs">

            </ul> 
        </div>

        <div class="row col-md-1 col-md-offset-11">
            <a class="aggiungiStanza">
                <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>

            </a>

        </div>


        <div class="row">

            <div class="col-md-8">

                    <div  class="tab-content" id="contenitoreStanze">

                    </div>

            </div>

            <div class="col-md-4 ">


                <div>
                    <a class="editStanza">
                        <span class="glyphicon glyphicon-edit" aria-hidden="true"></span>Modifica     
                    </a></br>
                    <a class="deleteStanza">
                        <span class="glyphicon glyphicon-remove-circle" data-toggle="confirmation" aria-hidden="true"></span>Elimina
                    </a>
                    </br>
                </div>
                
                <div hidden>
                    <a class="confermaStanza" >
                        <span class="glyphicon glyphicon-saved" aria-hidden="true"></span>Conferma     
                    </a>
                    </br>
                    <a class="annullaStanza" >
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>Annulla
                    </a>
                </div>
                
            <div hidden>
                    <a class="salvaNewStanza" >
                        <span class="glyphicon glyphicon-saved" aria-hidden="true"></span>Salva     
                    </a>
                    </br>
                    <a class="annullanewStanza" >
                        <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>Elimina
                    </a>
                </div>
            </div>


        </div>




        <!-- contenitore stanze -->
        <!-- <div class="col-md-6 row"> -->

    </div>

</div>

