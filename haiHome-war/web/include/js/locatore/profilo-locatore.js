jQuery(document).ready(function ($) {
    /*
     * Nella tendina "modifica-archivia-elimina" di ogni annuncio, mettere due 
     * icone invisibili (V e X) per visualizzare l'esito dell'operazione 
     * (utile per archiviazione e eliminazione che sono istantanee)
     * */
    var login_type;
    var user_data;
    var stato_locatore; //bloccato / anche no
    var a_tot, a_num_vis, a_num_arch, a_num_osc;
    var backups = [];

    //   var NUM_ANNUNCI_X_PAGE = 5;
    //  var current_pag_vis = 1, last_pag_vis;
    //  var current_pag_arch = 1, last_pag_arch;
    //  var current_pag_osc = 1, last_pag_osc;

    //  var axp_string = "axp=" + NUM_ANNUNCI_X_PAGE + "&page=";

    /*
     function set_pager(id_element, pcurrent, plast) {
     plast == 0 ?
     $(id_element).css('display', 'none') :
     $(id_element).text(pcurrent + " of " + plast);
     } */

    /* aggiorna le variabili last_pag* (ed eventualmente current_pag*) 
     * dopo ogni operazione sugli annunci */ /*
      function update_variables() {
      last_pag_vis = Math.ceil(a_num_vis / NUM_ANNUNCI_X_PAGE);
      last_pag_arch = Math.ceil(a_num_arch / NUM_ANNUNCI_X_PAGE);
      last_pag_osc = Math.ceil(a_num_osc / NUM_ANNUNCI_X_PAGE);
      
      if (current_pag_vis > last_pag_vis)
      current_pag_vis = last_pag_vis;
      
      if (current_pag_arch > last_pag_arch)
      current_pag_arch = last_pag_arch;
      
      } */

    /* init profilo e variabili globali */
    $(function () {
        console.log("INIT profile...");

        $.post(
                "ServletController",
                {'action': 'locatore-get-session'},
                function (responseJSON) {
                    console.log("INIT - responseJSON: " + responseJSON);

                    login_type = responseJSON.user_access;
                    user_data = responseJSON.user_data;
                    a_tot = responseJSON.num_annunci;
                    a_num_vis = responseJSON.num_visibili;
                    a_num_arch = responseJSON.num_archiviati;
                    a_num_osc = responseJSON.num_oscurati;

                    //        last_pag_vis = Math.ceil(a_num_vis / NUM_ANNUNCI_X_PAGE);
                    //        last_pag_arch = Math.ceil(a_num_arch / NUM_ANNUNCI_X_PAGE);
                    //        last_pag_osc = Math.ceil(a_num_osc / NUM_ANNUNCI_X_PAGE);

                    if (login_type === "g+" || login_type === "fb") {
                        $('#rigapwd').remove();
//                        $('#rigapwd').css('display', 'none');
                    }
                    /* Info profilo */
                    $('#nomeLocatore').text(user_data.nome + " " + user_data.cognome);

                    $('#nome').val(user_data.nome);
                    $('#cognome').val(user_data.cognome);
                    $('#email').val(user_data.email);
                    $('#telefono').val(user_data.telefono);
                    $('#descrizione').val(user_data.descrizione);

                    stato_locatore = user_data.bloccato;

                    var $stato_locatore = $('#status-locatore');

                    if (user_data.bloccato === "false") {
                        $stato_locatore.text("ok");
                        $stato_locatore.css('color', 'green');
                    } else {
                        $stato_locatore.text("bloccato");
                        $stato_locatore.css('color', 'red');
                    }

//                    $('#status-locatore').val(stato_locatore == true)

                    /* Info annuncio */
                    $('#num-annunci-visibili').text("(" + a_num_vis + ")");
                    $('#num-annunci-archiviati').text("(" + a_num_arch + ")");
                    $('#num-annunci-oscurati').text("(" + a_num_osc + ")");
                    /* pagers */
                    //     update_variables();
                    //          set_pager('#num_page', 1, last_pag_vis);
                    //          set_pager('#archivio_num_page', 1, last_pag_arch);

                    // $('#num_page').text("1 of " + last_pag_vis);
                    // $('#archivio_num_page').text("1 of " + last_pag_arch);
                    /* ci sono da aggiornare i cosi delle pagine e ricaricare gli annunci forse ... */


                }
        );
        /*
         $('#annunci-content').load("ServletController?action=locatore-getAnnunci&" + axp_string + current_pag_vis);
         $('#archivio-content').load("ServletController?action=locatore-getArchivioAnnunci&" + axp_string + current_pag_arch);
         */
        loadAnnunci();
        loadArchivioAnnunci();
        loadAnnunciOscurati();
//        $('#annunci-content').load("ServletController?action=locatore-getAnnunci");
//        $('#archivio-content').load("ServletController?action=locatore-getArchivioAnnunci");
//        $('#oscurati-content').load("ServletController?action=locatore-getAnnunciOscurati");


        updateInfoAnnunci();

        backups["telefono"] = "";
        backups["descrizione"] = "";
        backups["password"] = "";

    });

    function updateInfoAnnunci() {
        console.log("Update sessione...");

        $.post(
                "ServletController",
                {'action': 'locatore-get-session'},
                function (responseJSON) {
                    console.log("Update annuncio:...");
                    console.log(responseJSON);

                    a_tot = responseJSON.num_annunci;
                    a_num_vis = responseJSON.num_visibili;
                    a_num_arch = responseJSON.num_archiviati;
                    a_num_osc = responseJSON.num_oscurati;

                    $('#num-annunci-visibili').text("(" + a_num_vis + ")");
                    $('#num-annunci-archiviati').text("(" + a_num_arch + ")");
                    $('#num-annunci-oscurati').text("(" + a_num_osc + ")");

                    //    $('#num_page').text("1 of " + last_pag_vis);
                    //    $('#archivio_num_page').text("1 of " + last_pag_arch);

                    //               $('#annunci-content').load("ServletController?action=locatore-getAnnunci");//&" + axp_string + current_pag_vis);
                    //               $('#archivio-content').load("ServletController?action=locatore-getArchivioAnnunci");//&" + axp_string + current_pag_arch);
                }
        );
    }

    function loadAnnunci() {
        $('#annunci-content').load("ServletController?action=locatore-getAnnunci");
    }

    function loadArchivioAnnunci() {
        $('#archivio-content').load("ServletController?action=locatore-getArchivioAnnunci");
    }

    function loadAnnunciOscurati() {
        $('#oscurati-content').load("ServletController?action=locatore-getAnnunciOscurati");
    }


    /* Bottoni per la modifica delle info nel profilo */
    $('.start-edit').on('click', function () {
        var target_name = $(this).parents(".panel-default").attr("id").replace("panel-", "");
//        var target_name = $(this).parent().parent().parent().attr("id").replace("panel-", "");
        var $input_target = $("#" + target_name);

        console.log("start-edit on -> " + target_name);

        backups[target_name] = $input_target.val();
        $input_target.prop("disabled", false);

        $(this).parent().children().each(function () {
            $(this).toggle();
        });
        console.log("current value of " + target_name + "... " + $input_target.val());
    });

    $('.cancel-edit').on('click', function () {
        var $panel = $(this).parent().parent().parent();
        var target_name = $panel.attr("id").replace("panel-", "");
        var $input_target = $("#" + target_name);

        console.log("cancel-edit on -> " + target_name);

        $(this).parent().children().each(function () {
            $(this).toggle();
        });

        $input_target.val(backups[target_name]);
        $panel.children(".panel-body").removeClass("has-error");
        $input_target.prop("disabled", "disabled");
    });

    $('.save-edit').on('click', function () {
        var $this2 = $(this);
        var $panel = $(this).parent().parent().parent();
        var target_name = $panel.attr("id").replace("panel-", "");
        var $input_target = $("#" + target_name);
        var new_content = $input_target.val();

        console.log("save-edit on -> " + target_name + ", new value: " + new_content);

        if (target_name === "password" &&
                $('#new-password').val() !== $('#new-password2').val()) {
            $panel.children(".panel-body").addClass("has-error");
            console.log("password mismatch - client side")
            return false;
        }

        $.post(
                "ServletController",
                {
                    'action': 'locatore-edit-info',
                    'field-name': target_name,
                    'field-value': new_content,
                    'new-pw': $('#new-password').val()
                },
                function (responseJson) {
                    if (responseJson.result) {
                        $this2.parent().children().each(function () {
                            $(this).toggle();
                        });
                        $input_target.prop("disabled", "disabled");
                        $panel.children(".panel-body").removeClass("has-error");
                        //        $panel.children(".panel-body").addClass("has-success");
                        console.log("done");
                    } else {
                        $panel.children(".panel-body").addClass("has-error");
                        console.log("error code: " + responseJson.error);
                    }
                }
        );
    });

    /* Link per le operazioni di gestione degli annunci da parte del locatore
     * - html generato sulla server 
     * */

    /* Modifica annuncio */
    $(document).on('click', 'a.edit-annuncio', function (event) {
        //var transaction_id = $(this).attr('id').replace('delete_', '');
        var oid = event.target.id.replace("edit-ann", "");

        window.location.replace("MA0-ModificaAnnunci.jsp?oid=" + oid);

        return false;
    });

    /* Eliminazione annuncio */
    $(document).on('click', 'a.delete-annuncio', function (event) {
        var oid = event.target.id.replace("delete-ann", "");
        console.log("> delete " + oid);

        $.post(
                "ServletController",
                {
                    'action': 'locatore-delete-annuncio',
                    'oid': oid
                },
                function (response) {
                    console.log("response - " + response);

                    if (response === "ok") {
                        // $("#ann-" + oid).css('display', 'none');
                        $("#ann-" + oid).remove();
                        updateInfoAnnunci();
                    }
                }
        );
    });

    /* Archiviazione di un annuncio */
    $(document).on('click', 'a.archivia-annuncio', function (event) {
        var oid = event.target.id.replace("select-ann-", "");
        console.log("> archivia " + oid);

        $.post(
                "ServletController",
                {
                    'action': 'locatore-archivia-annuncio',
                    'oid': oid
                },
                function (response) {
                    console.log("response - " + response);
                    console.log("oid: " + oid);

                    if (response === "ok") {
                        // $("#ann-" + oid).css('display', 'none');
                        $("#ann-" + oid).remove();
                        updateInfoAnnunci();
                        loadArchivioAnnunci();

                    }
                }
        );
    });

    /* Pubblicazione di un annuncio precedentemente archiviato */
    $(document).on('click', 'a.pubblica-annuncio', function (event) {
        var oid = event.target.id.replace("select-ann-", "");
        console.log("> pubblica " + oid);

        $.post(
                "ServletController",
                {
                    'action': 'locatore-pubblica-annuncio',
                    'oid': oid
                },
                function (response) {
                    console.log("response - " + response);

                    if (response === "ok") {
                        // $("#ann-" + oid).css('display', 'none');
                        $("#ann-" + oid).remove();

                        if (a_num_arch == 1) {
                            $('#archivio-content').html("No results");
                        }

                        updateInfoAnnunci();
                        loadAnnunci();
                    }
                }
        );
    });

    $(document).on('click', 'a.annuncio-view-details', function (event) {
//        var oid = event.target.parents(".annuncio").attr("id"); 
        var oid = event.target.id.replace("open-", "");
        console.log("> view " + oid);

        $.post(
                "ServletController",
                {
                    'action': 'asd',
                    'oid': oid
                }, function (responseJSON) {
                    console.log("response"); 

        });
    });




});