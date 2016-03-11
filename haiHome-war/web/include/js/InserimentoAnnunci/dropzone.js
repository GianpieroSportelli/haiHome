/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//DropzoneJS snippet - js

$.getScript('http://cdnjs.cloudflare.com/ajax/libs/dropzone/3.8.4/dropzone.min.js',function(){
  // instantiate the uploader
  $('#file-dropzone').dropzone({ 
    url: "/upload",
    maxFilesize: 100,
    paramName: "uploadfile",
    maxThumbnailFilesize: 5,
    init: function() {
      
      this.on('success', function(file, json) {
      });
      
      this.on('addedfile', function(file) {
        
      });
      
      this.on('drop', function(file) {
        alert('file');
      }); 
    }
  });
});

$(document).ready(function() {});
