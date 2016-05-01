$(document).ready(function () {
    getListaCitta();
});

function insertCitta() {
    var citta = document.getElementById("citta").value;
    $.post("ServletController",
            {action: "inserisci-citta", NomeCitta: citta},
            function (data) {
                if (data == "OK") {
                    alert('Città ' + citta + " inserita con successo.");
                } else
                    alert('ERRORE INSERIMENTO');
            });
}

function deleteCitta() {
    var citta = document.getElementById("citta2").value;
    $.post("ServletController",
            {action: "cancella-citta", NomeCitta: citta},
            function (data) {
                if (data == "OK") {
                    alert('Città ' + citta + " cancellata con successo.");
                } else
                    alert('ERRORE CANCELLAZIONE');
            });
}

function getListaCitta() {
    $.post("ServletController",
            {action: "get-lista-quartieri-citta", citta: "Torino"},
            function (responseJson) {
                if (responseJson == 'false') {
                    alert('Non è stato possibile recuperare i quartieri per la città selezionata.');
                } else {
                    console.log(responseJson);
                    responseJson = JSON.parse(responseJson);
                    var html = '';
                    html += '<option value=\"-\"> - </option>';
                    $.each(responseJson, function (index, item) {
                        html += '<option id=\"citta-' + item + '\" value=\"' + item + '\">' + item + '</option>';
                    });
                    $("#quartieri").append(html);
                }

            }
    );
}

