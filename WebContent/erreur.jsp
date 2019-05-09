<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.*,controleur.*,modele.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="ISO-8859-1" />
        <title>Erreur</title>
        <link type="text/css" rel="stylesheet" href="CSS/profil.css" />
        <link type="text/css" rel="stylesheet" href="CSS/header.css" />
    </head>
    <body>

		<header>
        	<h1>Erreur</h1>
        	<%@ include file="navigationBar.jsp" %> 
        	<%@ include file="statusBar.jsp" %> 
		</header>

  		<div id="contenu">
        	<p> Nous sommes désolés, il y a un problème : <%=request.getAttribute("erreur")%></p>
        </div>
</body>
</html>