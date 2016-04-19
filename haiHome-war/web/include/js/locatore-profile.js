jQuery(document).ready(function ($) {
    var $phone = $('input[name=phone]'), $description = $('textarea[name=description]');
    var backup_phone, backup_description;

    /* fa comparire i bottoni per modificare le info */
    $('#edit').on('click', function () {
        $('#edit').toggle();
        $('#save-edit').toggle();
        $('#cancel-edit').toggle();

        $phone.prop('disabled', false);
        $description.prop('disabled', false);

        backup_phone = $phone.val();
        backup_description = $description.val();
    });

    /* annulla modifiche */
    $('#cancel-edit').on('click', function () {
        $('#edit').toggle();
        $('#save-edit').toggle();
        $('#cancel-edit').toggle();

        $phone.val(backup_phone);
        $description.val(backup_description);

        $phone.prop('disabled', 'disabled');
        $description.prop('disabled', 'disabled');
    });

    /* persiste modifiche */
    $('#save-edit').on('click', function () {
        var phone_regex = /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/,
                phone_val = $phone.val().trim(),
                description_val = $description.val().trim();

        if (phone_val === "" || phone_regex.test(phone_val)) {
            $.post(
                    "ServletController",
                    {
                        'action': 'locatore-edit-profile',
                        'phone': $phone.val().trim(),
                        'description': $description.val().trim()
                    },
                    function (response) {
                        backup_phone = $phone.val();
                        backup_description = $description.val();
                        console.log(response);
                    }
            );
            $('#edit').toggle();
            $('#save-edit').toggle();
            $('#cancel-edit').toggle();
            $phone.prop('disabled', 'disabled');
            $description.prop('disabled', 'disabled');
        } else {
            console.log("dio cane ");
        }
    });

    $description.focus(function () {
        if ($description.val().trim() === "")
            $description.val("");
    });


}); 