jQuery(document).ready(function ($) {
    /*
     * Nella tendina "modifica-archivia-elimina" di ogni annuncio, mettere due 
     * icone invisibili (V e X) per visualizzare l'esito dell'operazione 
     * (utile per archiviazione e eliminazione che sono istantanee)
     * */


    var login_type = $('#user-access').text();
    var backups = [];

    /* init*/
    $(function () {
        if (login_type === "g+" || login_type === "fb") {
            $('#rigapwd').css('display', 'none');
        }

        backups["telefono"] = $('#telefono').val();
        backups["descrizione"] = $('#descrizione').val();
        backups["password"] = ""; //non serve a nulla (se non a evitare errori a runtime)

    });


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
        console.log("edit " + oid);
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
                    
                    if (response === "ok") 
                       // $("#ann-" + oid).css('display', 'none');
                           $("#ann-" + oid).remove();
                }
        );

        return false;
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
                    
                    if (response === "ok") 
                       // $("#ann-" + oid).css('display', 'none');
                           $("#ann-" + oid).remove();
                }
        );

        return false;
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
                    
                    if (response === "ok") 
                       // $("#ann-" + oid).css('display', 'none');
                           $("#ann-" + oid).remove();
                }
        );

        return false;
    });
});