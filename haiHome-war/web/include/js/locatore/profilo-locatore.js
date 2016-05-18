jQuery(document).ready(function ($) {
    var login_type;
    var user_data;
    var a_tot, a_num_vis, a_num_arch, a_num_osc;
    var backups = [];

    /* init profilo e variabili globali */
    $(function () {
        console.log("INIT locatore-profile...");

        $.post(
                "ServletController",
                {'action': 'locatore-get-session'},
                function (responseJSON) {
                    login_type = responseJSON.user_access;
                    user_data = responseJSON.user_data;
                    a_tot = responseJSON.num_annunci;
                    a_num_vis = responseJSON.num_visibili;
                    a_num_arch = responseJSON.num_archiviati;
                    a_num_osc = responseJSON.num_oscurati;

                    if (login_type === "g+" || login_type === "fb") {
                        $('#panel-password').css('display', 'none');
//                        $('#rigapwd').css('display', 'none');
                    }
                    /* Info profilo */
                    $('#nomeLocatore').text(user_data.nome + " " + user_data.cognome);

                    $('#nome').val(user_data.nome);
                    $('#cognome').val(user_data.cognome);
                    $('#email').val(user_data.email);
                    $('#telefono').val(user_data.telefono);
                    $('#descrizione').val(user_data.descrizione);

                    var $stato_locatore = $('#status-locatore');

                    if (user_data.bloccato === "true") {
                        $stato_locatore.text("bloccato");
                        $stato_locatore.css('color', 'red');

                        $('#button-new-annuncio').addClass('disabled');
                    } else {
                        $stato_locatore.text("ok");
                        $stato_locatore.css('color', 'green');
                    }

                    /* Info annuncio */
                    $('#num-annunci-visibili').text("(" + a_num_vis + ")");
                    $('#num-annunci-archiviati').text("(" + a_num_arch + ")");
                    $('#num-annunci-oscurati').text("(" + a_num_osc + ")");
                }
        );
        loadAnnunci();
        loadArchivioAnnunci();
        loadAnnunciOscurati();

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
                    console.log("Update annuncio:..." + responseJSON);

                    a_tot = responseJSON.num_annunci;
                    a_num_vis = responseJSON.num_visibili;
                    a_num_arch = responseJSON.num_archiviati;
                    a_num_osc = responseJSON.num_oscurati;

                    $('#num-annunci-visibili').text("(" + a_num_vis + ")");
                    $('#num-annunci-archiviati').text("(" + a_num_arch + ")");
                    $('#num-annunci-oscurati').text("(" + a_num_osc + ")");
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
        var $input_target = $("#" + target_name);

        console.log("Iniziata modifica sul campo '" + target_name + "'");

        if (target_name === "password") {
            $('#panel-password').children(".panel-body").children().each(function () {
                $(this).prop("disabled", false);
                $(this).show();
            });
        }

        backups[target_name] = $input_target.val();
        $input_target.prop("disabled", false);
        toggle_edit_buttons($(this));

        console.log("Valore corrente del campo '" + target_name + "': '" + $input_target.val() + "'");
    });

    $('.cancel-edit').on('click', function () {
        var $panel = $(this).parents(".panel-default");
        var target_name = $panel.attr("id").replace("panel-", "");

        var $input_target = $("#" + target_name);

        console.log("Annullo modifica sul campo '" + target_name + "'");

        clear_pwd_fields(target_name);
        toggle_edit_buttons($(this));

        $input_target.val(backups[target_name]);
        $panel.removeClass("panel-danger panel-success");
        $panel.children(".panel-body").removeClass("has-error");
        $input_target.prop("disabled", "disabled");


    });

    $('.save-edit').on('click', function () {
        var $this2 = $(this);
        var $panel = $(this).parents(".panel-default");
        var target_name = $panel.attr("id").replace("panel-", "");
        var $input_target = $("#" + target_name);
        var new_content = $input_target.val();

        console.log("Iniziato salvataggio del campo '" + target_name + "'. Nuovo valore: " + new_content);

        if (target_name === "password" &&
                $('#new-password').val() !== $('#new-password2').val()) {
            setClasses($panel, "panel-danger");
            //       $panel.children(".panel-body").addClass("has-error");
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
                        $input_target.prop("disabled", "disabled");
                        $panel.children(".panel-body").removeClass("has-error");
                        //        $panel.children(".panel-body").addClass("has-success");

                        //$panel.removeClass("panel-default panel-danger panel-success"); 
                        $panel.removeClass("panel-danger");
//                        $panel.addClass("panel-success"); 
                        setClasses($panel, "panel-success");



                        toggle_edit_buttons($this2);
                        clear_pwd_fields(target_name);
                    } else {
                        setClasses($panel, "panel-danger", "has-error");
//                        $panel.addClass("panel-danger"); 

                        //                      $panel.children(".panel-body").addClass("has-error");
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
        var oid = event.target.id.replace("edit-ann", "");
        window.location.replace("MA0-ModificaAnnunci.jsp?oid=" + oid);
        return false;
    });

    /* Eliminazione annuncio */
    $(document).on('click', 'a.delete-annuncio', function (event) {
        var oid = event.target.id.replace("delete-ann", "");
        console.log("> delete " + oid);

        $.confirm({
            title: "Conferma cancellazione",
            text: "Sei sicuro di voler eliminare l'annuncio?",
            confirm: function () {
                console.log("confirmed...");

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
            }
        });
    });

    /* Archiviazione di un annuncio */
    $(document).on('click', 'a.archivia-annuncio', function (event) {
        var oid = event.target.id.replace("select-ann-", "");
        console.log("> archivia " + oid);

        $.confirm({
            title: "Conferma archiviazione annuncio",
            text: "Sei sicuro di voler archiviare l'annuncio?",
            confirm: function () {
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
                                
                                if (a_num_vis === 1) {
                                    $('#annunci-content').html("Non sono presenti annunci al momento!");
                                }
                                
                                updateInfoAnnunci();
                                loadArchivioAnnunci();
                            }
                        }
                );
            }
        });
    });

    /* Pubblicazione di un annuncio precedentemente archiviato */
    $(document).on('click', 'a.pubblica-annuncio', function (event) {
        var oid = event.target.id.replace("select-ann-", "");
        console.log("> pubblica " + oid);

        $.confirm({
            title: "Conferma pubblicazione annuncio",
            text: "Sei sicuro di voler pubblicare l'annuncio?",
            confirm: function () {
                $.post(
                        "ServletController",
                        {
                            'action': 'locatore-pubblica-annuncio',
                            'oid': oid
                        },
                        function (response) {
                            console.log("response - " + response);

                            if (response === "ok") {
                                $("#ann-" + oid).remove();

                                if (a_num_arch === 1) {
                                    $('#archivio-content').html("Non sono presenti annunci al momento!");
                                }

                                updateInfoAnnunci();
                                loadAnnunci();
                            }
                        }
                );
            }
        });
    });

    $(document).on('click', 'a.annuncio-view-details', function (event) {
        var oid = event.target.id.replace("open-", "");
        console.log("> view " + oid);

        $.post(
                "ServletController",
                {
                    'action': 'Annuncio-getJSONByOid',
                    'oid': oid
                },
                function (responseJSON) {
                    if (responseJSON.response === "OK") {
                        console.log(responseJSON.data);
                        /* code by gianp */
                        var url = "/haiHome-war/dettagliAnnuncio.jsp";
                        $.session.set('dettagli', JSON.stringify(responseJSON.data));
                        $.session.set('admin', false);
                        window.open(url);
                    } else {
                        console.log("Si e' verificato un errore inatteso");
                    }
                });
    });

});

// Cancella il contenuto e disabilita gli input relativi alla modifica della password
function clear_pwd_fields(target_name) {
    if (target_name === "password") {
        $('#panel-password').children(".panel-body").children().each(function () {
            $(this).val("");
            $(this).prop("disabled", "disabled");
            $(this).hide();
        });
    }
}

// Nasconde e visualizza i bottoni di edit 
function toggle_edit_buttons($this_element) {
    $this_element.parent().children().each(function () {
        $(this).toggle();
    });
}

function setClasses($panel, panel_classes) {
    $panel.removeClass("panel-danger panel-success");
    $panel.addClass(panel_classes);
    setTimeout(function () { $panel.removeClass(panel_classes); }, 3000); 
}