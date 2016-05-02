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
                $('#citta').val('');
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
                $('#citta2').val('');
            });
}

function getListaCitta() {
    $("#cittaDB").empty();
    $.post("ServletController",
            {action: "get-lista-citta"},
            function (responseJson) {
                if (responseJson == 'false') {
                    alert('Non è stato possibile recuperare le città presenti nel Database.');
                } else {
                    console.log(responseJson);
                    responseJson = JSON.parse(responseJson);
                    var html = '';
                    html += '<option value=\"-\"> - </option>';
                    $.each(responseJson, function (index, item) {
                        html += '<option id=\"citta-' + item + '\" value=\"' + item + '\">' + item + '</option>';
                    });
                    $("#cittaDB").append(html);
                }

            }
    );
}
/*
 function getQuartieriCitta(citta) {
 $.post("ServletController",
 {action: "get-lista-quartieri-citta", citta: citta},
 function (responseJson) {
 if (responseJson == 'false') {
 alert('Non è stato possibile recuperare i quartieri della città: ' + citta);
 } else {
 console.log(responseJson);
 responseJson = JSON.parse(responseJson);
 var html = '';
 $.each(responseJson, function (index, item) {
 html += '<option id=\"citta-' + item + '\" value=\"' + item + '\">' + item + '</option>';
 });
 $("#quartieri").append(html);
 }
 
 }
 );
 }
 */
function inserisciCAP() {
    var capInput = $('#capQuartiere').val();
    var cittaInput = $('#cittaDB').val();
    var quartiereInput = $('#quartiereNome').val();

    if (quartiereInput.length === 0) {
        alert('Nome inserito per il quartiere non valido.');
    } else {

        var correct = true;

        var caps = capInput.split("-");
        var trattiniNecessari = (capInput.split("-").length - 1);

        if ((caps.length === 1 && trattiniNecessari !== 0) || (caps.length === 0)) {
            correct = false;//1 cap e ci sono dei trattini oppure 0 cap
        } else if (caps.length !== trattiniNecessari + 1) {
            correct = false;//più trattini di quelli che servono
        }

        for (var i = 0; i < caps.length; i++) {
            if (caps[i].length !== 5) {
                correct = false;
                break;
            }
        }

        if (!correct) {
            alert('Il formato dei CAP non è valido. Riprova.')
        } else {

            $.post("ServletController",
                    {action: "add-cap", citta: cittaInput, quartiere: quartiereInput, cap: caps.toString()},
                    function (responseJson) {
                        if (responseJson == 'false') {
                            alert('Non è stato possibile inserire il cap specificato.');
                        } else {
                            $("#capQuartiere").val('');
                            alert('Città: ' + cittaInput + " - Quartiere: " + quartiereInput + " - CAP: " + caps.toString() + " Inserito con successo.")
                        }

                    }
            );
        }
    }
}

