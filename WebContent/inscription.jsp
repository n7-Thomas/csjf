<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Inscription sur CSJF</title>
    </head>
    <body>
    	<% String status = (String) request.getAttribute("status");
		if(status != null){ %> <p> Status : <%=status %> </p> <% }
		%>
    
        <form method="post" action="ServeurConnexion">
            <fieldset>
                <legend>Inscription</legend>
                <p>Inscris-toi pour pouvoir rejoindre cette grande communauté sportive.</p>

                <label for="nom">Nom<span class="requis">*</span></label>
                <input type="text" id="nom" name="nom" value="${utilisateur.nom}" size="20" maxlength="60" />
                <span class="erreur">${form.erreurs['nom']}</span>
                <br />
                
                <label for="prenom">Prénom<span class="requis">*</span></label>
                <input type="text" id="prenom" name="prenom" value="${utilisateur.prenom}" size="20" maxlength="60" />
                <span class="erreur">${form.erreurs['prenom']}</span>
                <br />
                
                 <label for="email">Adresse email<span class="requis">*</span></label>
                <input type="email" id="email" name="email" value="${utilisateur.email}" size="20" maxlength="60" />
                <span class="erreur">${form.erreurs['email']}</span>
                <br />

                <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
                <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['motdepasse']}</span>
                <br />

                <input type="submit" value="Inscription" class="sansLabel" />
                <br />
                
                <input type="hidden" name= "action" value="inscription">
                
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
            </fieldset>
        </form>
	
</body>
</html>