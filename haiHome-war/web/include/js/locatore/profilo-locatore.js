jQuery(document).ready(function ($) {
    var $div_modify_pwd = $('#modify-pwd-stuff');
    var $old_pwd = $('input[name=old-pwd]'),
            $new_pwd = $('input[name=pwd]'),
            $new_pwd2 = $('input[name=pwd-confirm]'),
            $phone = $('input[name=phone]'),
            $description = $('textarea[name=description]');

    var login_by_credentials = $('#user-access').html().trim() === "";
    var backup_phone, backup_description;
    
    /* init*/
    $(function() {
      if (!login_by_credentials) {
          $('#rigapwd').css('display', 'none');
      }  
    });
    
    /* fa comparire i bottoni per modificare le info */
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

    /* annulla modifiche */
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

    /* persiste modifiche */
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
    });
    
    /* robe sugli annunci */
    $(document).on('click', 'a.edit-annuncio', function (event) {
        //var transaction_id = $(this).attr('id').replace('delete_', '');
        var oid = event.target.id.replace("edit-ann", "");
        console.log("edit "+oid); 
        return false;
    });    
    
    $(document).on('click', 'a.something-annuncio', function (event) {
        var oid = event.target.id.replace("something-ann", "");
        console.log("Something on"+oid); 
        return false;
    });    
    
    $(document).on('click', 'a.delete-annuncio', function (event) {
        var oid = event.target.id.replace("delete-ann", "");
        console.log("delete "+oid); 
        return false;
    });    
    
    
    
    
});