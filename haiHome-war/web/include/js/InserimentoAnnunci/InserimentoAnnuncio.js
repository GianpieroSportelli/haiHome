/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var jsonObj = [];

function prova(){


        var citt = $("#sel1").val();
            console.log(citt);
        item = {};
        item ["citta"] = citt;

        jsonObj.push(item);


    console.log(jsonObj);
    }