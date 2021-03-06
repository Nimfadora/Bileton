<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="<c:url value="/stylesheets/admin.css" />">
    <link rel="stylesheet" href="<c:url value="/javascripts/libs/bower_components/bootstrap/dist/css/bootstrap.min.css"/>"/>
</head>
<body>
<div id="plot">
    <div class="menu">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div>
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="<c:url value="/pages/admin.jsp" />">Home</a></li>
                        <li><a href="<c:url value="/pages/theatre.jsp" />" id="theatreTable">Theatre</a></li>
                        <li><a href="<c:url value="/pages/troupes.jsp" />" id="troupeTable">Troupe</a></li>
                        <li><a href="<c:url value="/pages/spectacle.jsp" />" id="spectacleTable">Spectacle</a></li>
                        <li><a href="<c:url value="/pages/performance.jsp" />" id="perftable">Performance</a></li>
                        <li><a href="<c:url value="/pages/playReport.jsp" />" id="playReport">PlayReport</a></li>
                        <li><a href="<c:url value="/pages/ticket.jsp" />" id="ticket">Ticket</a></li>
                        <li><a href="<c:url value="/pages/editor.jsp"/>" id="editor">Editor</a></li>
                        <li><a href="<c:url value="/pages/pit.jsp"/>" id="pit">PlaysInTheatres</a></li>
                        <li><a href="<c:url value="/pages/troupeStatistics.jsp"/>" id="troupeSt">Troupe Statistics</a></li>
                        <li><a href="<c:url value="/pages/ticketStatistics.jsp"/>" id="ticketSt">Ticket Statistics</a></li>
                        <li><a href="<c:url value="/pages/Automatisation.jsp"/>" id="automatisation">Automatisation</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
    <div class="data">
        <div class="container" id="table"></div>
    </div>
    <div class="formContainer">
        <div class="container" id="form"></div>
    </div>
</div>
<script src="<c:url value="/javascripts/libs/bower_components/underscore/underscore.js"/>"></script>
<script src="<c:url value="/javascripts/libs/bower_components/jquery/dist/jquery.js"/>"></script>
<script src="<c:url value="/javascripts/libs/bower_components/bootstrap/dist/js/bootstrap.js"/>"></script>
<script src="<c:url value="/javascripts/libs/bower_components/backbone/backbone.js"/>"></script>
<script src="<c:url value="/javascripts/custom/admin/adminjs.js"/>"></script>
</body>
</html>

