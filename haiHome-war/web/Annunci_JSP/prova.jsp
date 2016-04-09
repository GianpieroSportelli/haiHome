<%-- 
    Document   : prova
    Created on : Apr 6, 2016, 10:10:07 AM
    Author     : giacomocavallo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="../include/css/InserimentoAnnunci/dropzone1.css">  

        <title>Example</title>
    </head>
    <body>

        <form id="my-awesome-dropzone" class="dropzone" enctype='multipart/form-data'>
            <div class="dropzone-previews"></div> 

            
            <input type="email" name="username" />
            <input type="password" name="password" />

            <button type="submit">Submit data and files!</button>
        </form>

    </body>
    <script type="text/javascript" src="../include/js/InserimentoAnnunci/dropzone1.js"></script>
    <script type="text/javascript">
        Dropzone.options.myAwesomeDropzone = { // The camelized version of the ID of the form element

            // The configuration we've talked about above
            url: "../ServletAnnuncio",
            autoProcessQueue: false,
            uploadMultiple: true,
            parallelUploads: 100,
            maxFiles: 100,
            hiddenInputContainer: "form#my-awesome-dropzone",
            // The setting up of the dropzone
            init: function () {
                var myDropzone = this;

                // First change the button to actually tell Dropzone to process the queue.
                this.element.querySelector("button[type=submit]").addEventListener("click", function (e) {
                    // Make sure that the form isn't actually being sent.
                    e.preventDefault();
                    e.stopPropagation();
                    myDropzone.processQueue();
                });

                // Listen to the sendingmultiple event. In this case, it's the sendingmultiple event instead
                // of the sending event because uploadMultiple is set to true.
                this.on("sendingmultiple", function () {
                    // Gets triggered when the form is actually being sent.
                    // Hide the success button or the complete form.
                });
                this.on("successmultiple", function (files, response) {
                    // Gets triggered when the files have successfully been sent.
                    // Redirect user or notify of success.
                });
                this.on("errormultiple", function (files, response) {
                    // Gets triggered when there was an error sending the files.
                    // Maybe show form again, and notify user of error
                });
            }

        }

    </script>


</html>
