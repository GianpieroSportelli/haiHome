


function openModalMessage(title, body, footer) {
    var messModal = $("#messageModal");
    var modalHeader = messModal.find(".modal-header");
    var modalBody = messModal.find(".modal-body");
    var modalFooter = messModal.find(".modal-footer");
    modalHeader.append("<h4 class=\"modal-title\">" + title + "</h4>");
    modalBody.append("<div>" + body + "</div>");
    if (footer == null) {
        modalFooter.append("<button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">Chiudi</button>");

    } else {
        modalFooter.append(footer);
    }


    messModal.modal({keyboard: true});
    messModal.modal('show');

}

function clearModalMessage() {
    console.log("clear???");
    var messModal = $("#messageModal");
    var modalHeader = messModal.find(".modal-header");
    var modalBody = messModal.find(".modal-body");
    var modalFooter = messModal.find(".modal-footer");
    modalHeader.empty();
    modalBody.empty();
    modalFooter.empty();
    return true;
}
$(document).ready(function () {
    $("#messageModal").on('hide.bs.modal', function () {
        var messModal = $(this);
        var modalHeader = messModal.find(".modal-header");
        var modalBody = messModal.find(".modal-body");
        var modalFooter = messModal.find(".modal-footer");
        modalHeader.empty();
        modalBody.empty();
        modalFooter.empty();
    });
});
