<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <title>Connexion</title>
    </head>
    <body>
    	<% String status = (String) request.getAttribute("status");
		if(status != null){ %> <p> Status : <%=status %> </p> <% }
		%>
        <form method="post" action="ServeurConnexion">
            <fieldset>
                <legend>Connexion</legend>
                <p>Connectez-vous à votre compte pour continuer votre progression.</p>

                <label for="nom">Adresse email <span class="requis">*</span></label>
                <input type="text" id="email" name="email" value="${utilisateur.email}" size="20" maxlength="60" />
                <span class="erreur">${form.erreurs['email']}</span>
                <br />

                <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
                <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['motdepasse']}</span>
                <br />

                <input type="submit" value="Connexion" class="sansLabel" />
                <br />
                
                <input type="hidden" name= "action" value="testconnexion">
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
            </fieldset>
        </form>
	
</body>
</html>