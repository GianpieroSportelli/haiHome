jQuery(document).ready(function ($) {
    /*
     * Nella tendina "modifica-archivia-elimina" di ogni annuncio, mettere due 
     * icone invisibili (V e X) per visualizzare l'esito dell'operazione 
     * (utile per archiviazione e eliminazione che sono istantanee)
     * */
    var login_type;
    var user_data;
    var a_tot, a_num_vis, a_num_arch, a_num_osc;
    var backups = [];

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

                    if (login_type === "g+" || login_type === "fb") {
                        $('#rigapwd').css('display', 'none');
                    }

                    $('#nome').val(user_data.nome);
                    $('#cognome').val(user_data.cognome);
                    $('#email').val(user_data.email);
                    $('#telefono').val(user_data.telefono);
                    $('#descrizione').val(user_data.descrizione);
                    $('#num-annunci-visibili').text("(" + a_num_vis + ")");
                    $('#num-annunci-archiviati').text("(" + a_num_arch + ")");
                    $('#num-annunci-oscurati').text("(" + a_num_osc + ")");
                }
        );

        backups["telefono"] = "";//$('#telefono').val();
        backups["descrizione"] = "";//$('#descrizione').val();
        backups["password"] = ""; //non serve a nulla (se non a evitare errori a runtime)
    });

    function updateAnnunci() {
        $.post(
                "ServletController",
                {'action': 'locatore-get-session'},
                function (responseJSON) {
                    console.log("Update annuncio: " + responseJSON); 
                    
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

    $('.start-edit').on('click', function () {
        var target_name = $(this).parent().parent().parent().attr("id").replace("panel-", "");
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
                    'field-value': new_content, /* credo */
                    'new-pw': $('#new-password').val()/*,
                     'new-pw-confirm': $('#new-password2').val()*/
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
        console.log("edit " + oid + "...work in progress");
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
                        updateAnnunci();
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
                        updateAnnunci();
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
                        updateAnnunci();
                    }
                }
        );
    });
});