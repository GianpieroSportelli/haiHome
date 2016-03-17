jQuery(document).ready(function($) {
    var validate_login_studente = $('#submit-login-stud'), 
        validate_reg_studente = $('#submit-reg-stud'), 
        validate_login_locatore = $('#submit-login-loc'), 
        validate_reg_locatore = $('#submit-reg-loc'); 
        
    var form_studente_login = $('#studente-login'), 
        form_studente_reg = $('#studente-reg'), 
        form_locatore_login = $('#locatore-login'), 
        form_locatore_reg = $('#locatore-reg'); 
    
    var email_pattern = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/; 
           
    validate_reg_studente.on('click', function() {
        var name = form_studente_reg.find('input[name="user-name"]'),
        surname = form_studente_reg.find('input[name="user-surname"]'),
        email = form_studente_reg.find('input[name="user-email"]'),
        pwd = form_studente_reg.find('input[name="user-pw"]'),
        pwd2 = form_studente_reg.find('input[name="user-pw-repeat"]'); 
        
        var check_name = name.val().trim() !== "", 
            check_surname = surname.val().trim() !== "", 
            check_email = email_pattern.test(email.val().trim()),
            check_pwd = pwd.val().trim() !== "" && pwd.val().trim() === pwd2.val().trim(); 
    
        if (check_name && check_surname && check_email && check_pwd) {
            console.log('submit');form_studente_reg.submit(); 
        }
        else {
            if (!check_name) {
                console.log('name');
                
            }
            if (!check_surname) {
                surname.focus();
                console.log('surname');
            }
            if (!check_email) console.log('email');
            if (!check_pwd) console.log('password'); 
        }
    }); 
    
    validate_reg_locatore.on('click', function() {
        var name = form_locatore_reg.find('input[name="user-name"]'),
        surname = form_locatore_reg.find('input[name="user-surname"]'),
        email = form_locatore_reg.find('input[name="user-email"]'),
        pwd = form_locatore_reg.find('input[name="user-pw"]'),
        pwd2 = form_locatore_reg.find('input[name="user-pw-repeat"]'), 
        phone = form_locatore_reg.find('input[name="user-phone"]');
        
        var check_name = name.val().trim() !== "", 
            check_surname = surname.val().trim() !== "", 
            check_email = email_pattern.test(email.val().trim()),
            check_pwd = pwd.val().trim() !== "" && pwd.val().trim() === pwd2.val().trim(), 
            check_phone = phone.val().trim() !== "";
    
        if (check_name && check_surname && check_email && check_pwd) {
            console.log('submit');form_locatore_reg.submit(); 
        }
        else {
            if (!check_name) {
                console.log('name');
                
            }
            if (!check_surname) {
                surname.focus();
                console.log('surname');
            }
            if (!check_email) console.log('email');
            if (!check_pwd) console.log('password'); 
            if (!check_phone) console.log('phone'); 
        }
    }); 
    
    validate_login_studente.on('click', function() {
        console.log('click su submit login studente'); 
    }); 
    
    validate_login_locatore.on('click', function() {
        console.log('click su submit login locatore'); 
    }); 
    
    
    
    
});