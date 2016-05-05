jQuery(document).ready(function ($) {
    var NUM_ANNUNCI_X_PAGINA = 5;
    var first_page = 1, current_page = 1,
            last_page = Math.ceil(Number($('#visibili-length').text()) / NUM_ANNUNCI_X_PAGINA);
    var arch_current_page = 1,
            arch_last_page = Math.ceil(Number($('#archiviati-length').text()) / NUM_ANNUNCI_X_PAGINA);
    var axp_string = "axp=" + NUM_ANNUNCI_X_PAGINA + "&page=";

//    console.log("first_page ", first_page, "current_page ", current_page, "last_page ", last_page);
    
    /* Init */
    $(function () {
        $('#annunci-content').load("ServletController?action=locatore-getAnnunci&" + axp_string + current_page);
        $('#num_page').html(current_page + " of " + last_page);

        $('#archivio-content').load("ServletController?action=locatore-getArchivioAnnunci&" + axp_string + current_page);
        $('#archivio_num_page').html(arch_current_page + " of " + arch_last_page);
    });

    /* bottoni prev e next - caso annunci visibili */
    $('#prev_page').on('click', function () {
        if (current_page > first_page) {
            current_page--;
            console.log("request prev page..." + current_page);
            $('#annunci-content').load("ServletController?action=locatore-getAnnunci&" + axp_string + current_page);
            $('#num_page').html(current_page + " of " + last_page);
        }
    });
    
    /* event delegation */
    $(document).on('click', 'a.link-annuncio', function (event) {
        //var transaction_id = $(this).attr('id').replace('delete_', '');
        console.log("archivia annuncio -"+event.target.id); 
        return false;
    });    
    

    $('#next_page').on('click', function () {
        if (current_page < last_page) {
            current_page++;
            console.log("request next page..." + current_page);
            $('#annunci-content').load("ServletController?action=locatore-getAnnunci&" + axp_string + current_page);
            $('#num_page').html(current_page + " of " + last_page);
        }
    });
    /* bottini prev e next: archivio  */
    $('#archivio_prev_page').on('click', function () {
        if (arch_current_page > first_page) {
            arch_current_page--;
            console.log("request prev arch-page..." + arch_current_page);
            $('#archivio-content').load("ServletController?action=locatore-getArchivioAnnunci&" + axp_string + arch_current_page);
            $('#num_page').html(current_page + " of " + arch_last_page);
        }
    });

    $('#archivio_next_page').on('click', function () {
        if (arch_current_page < arch_last_page) {
            arch_current_page++;
            console.log("request next arch-page..." + arch_current_page);
            $('#archivio-content').load("ServletController?action=locatore-getArchivioAnnunci&" + axp_string + arch_current_page);
            $('#num_page').html(arch_current_page + " of " + arch_last_page);
        }
    });
    
    
});