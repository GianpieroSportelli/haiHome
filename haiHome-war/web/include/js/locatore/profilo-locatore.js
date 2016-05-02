jQuery(document).ready(function ($) {/*
 var $div_modify_pwd = $('#modify-pwd-stuff');
 var $old_pwd = $('input[name=old-pwd]'),
 $new_pwd = $('input[name=pwd]'),
 $new_pwd2 = $('input[name=pwd-confirm]'),
 $phone = $('input[name=phone]'),
 $description = $('textarea[name=description]');*/

//    var login_by_credentials = $('#user-access').html().trim() === "";
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

    /* robe sugli annunci */
    $(document).on('click', 'a.edit-annuncio', function (event) {
        //var transaction_id = $(this).attr('id').replace('delete_', '');
        var oid = event.target.id.replace("edit-ann", "");
        console.log("edit " + oid);
        return false;
    });

    $(document).on('click', 'a.archivia-annuncio', function (event) {
        var oid = event.target.id.replace("archivia-ann", "");
        console.log("Something on" + oid);
        return false;
    });

    $(document).on('click', 'a.delete-annuncio', function (event) {
        var oid = event.target.id.replace("delete-ann", "");
        console.log("delete " + oid);
        
        $.post(
                "ServletController",
                {
                    'action': 'locatore-delete-annuncio',
                    'oid': oid
                },
                function (response) {
                    console.log("locatore-delete-annuncio -> response : " + response); 
                    if (response === "ok") {
                        $("#ann-" + oid).css('display', 'none');
                    }
                }
        );

        return false;
    });



    /*
     // fa comparire i bottoni per modificare le info
     $('#edit').on('click', function () {
     $('#edit').toggle();
     $('#save-edit').toggle();
     $('#cancel-edit').toggle();
     
     if (login_by_credentials)
     $div_modify_pwd.css('display', 'inline');
     
     $phone.prop('disabled', false);
     $old_pwd.prop('disabled', false);
     $description.prop('disabled', false);
     
     backup_phone = $phone.val();
     backup_description = $description.val();
     });
     
     // annulla modifiche 
     $('#cancel-edit').on('click', function () {
     $('#edit').toggle();
     $('#save-edit').toggle();
     $('#cancel-edit').toggle();
     
     if (login_by_credentials)
     $div_modify_pwd.toggle();
     
     $phone.val(backup_phone);
     $description.val(backup_description);
     
     $phone.prop('disabled', 'disabled');
     $phone.parent().removeClass("has-error");
     
     $old_pwd.prop('disabled', 'disabled');
     $description.prop('disabled', 'disabled');
     
     });
     
     // persiste modifiche 
     $('#save-edit').on('click', function () {
     var phone_regex = /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/,
     phone_val = $phone.val().trim(), description_val = $description.val().trim();
     var pwd = $new_pwd.val(), pwd_confirm = $new_pwd2.val();
     
     var check_phone = phone_val === "" || phone_regex.test(phone_val);
     var check_pwd = $old_pwd.val() === "" || pwd === $new_pwd2.val();
     
     //if (phone_val === "" || phone_regex.test(phone_val)) {
     if (check_phone && check_pwd) {
     $.post(
     "ServletController",
     {
     'action': 'locatore-edit-profile',
     'old-pwd': $old_pwd.val(),
     'new-pwd': pwd.trim(),
     'phone': $phone.val().trim(),
     'description': $description.val().trim()
     },
     function (response) {
     console.log(response);
     
     if (response === "ok") {
     backup_phone = $phone.val();
     backup_description = $description.val();
     
     } else {
     ;
     }
     }
     );
     
     if (login_by_credentials) {
     $div_modify_pwd.toggle();
     $old_pwd.val("");
     $new_pwd.val("");
     $new_pwd2.val("");
     }
     $('#edit').toggle();
     $('#save-edit').toggle();
     $('#cancel-edit').toggle();
     $phone.prop('disabled', 'disabled');
     $description.prop('disabled', 'disabled');
     $old_pwd.prop('disabled', 'disabled');
     $phone.parent().removeClass("has-error");
     
     } else {
     if (!check_phone)
     $phone.parent().addClass("has-error");
     if (!check_pwd)
     console.log("sei un coglione");
     }
     });*/


});