/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


                if ($('#test')[0].type !== 'date')
                    $('#test').datepicker();



  $(function() {
    $('#datetimepicker4').datetimepicker({
      pickTime: false

    });
  });