/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


  $(function() {
    $('#datetimepicker4').datetimepicker({
      pickTime: false
    });
  });
  
    function inviaIndirizzo() {
      document.form_indirizzo.action("TestInserimentoAnnuncio.java")
      document.form_indirizzo.submit();
      alert("ciao");
  }



