<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ticket" value="${Ticket}"/>
<!DOCTYPE HTML>
<html>
<head>
    <title>Ticket</title>
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
                        <li><a href="<c:url value="/pages/admin.jsp"/>" id="home">Home</a></li>
                        <li><a href="<c:url value="/pages/theatre.jsp"/>" id="theatreTable">Theatre</a></li>
                        <li><a href="<c:url value="/pages/troupes.jsp"/>" id="troupeTable">Troupe</a></li>
                        <li><a href="<c:url value="/pages/spectacle.jsp"/>" id="spectacleTable">Spectacle</a></li>
                        <li><a href="<c:url value="/pages/performance.jsp"/>" id="perftable">Performance</a></li>
                        <li><a href="<c:url value="/pages/playReport.jsp"/>" id="playReport">PlayReport</a></li>
                        <li class="active"><a href="<c:url value="/admin/ticket"/>" id="ticket">Ticket</a></li>
                        <li><a href="<c:url value="/pages/editor.jsp"/>" id="editor">Editor</a></li>
                        <li><a href="<c:url value="/pages/pit.jsp"/>" id="pit">PlaysInTheatres</a></li>
                        <li><a href="<c:url value="/pages/troupeStatistics.jsp"/>" id="troupeSt">Troupe Statistics</a></li>
                        <li><a href="<c:url value="/pages/ticketStatistics.jsp"/>" id="ticketSt">Ticket Statistics</a></li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
    <div class="ticket">
        <div class="play">
            <h1><c:out value="${ticket.play}"/></h1>
            <div class="right">
                <p><c:out value="${ticket.date}"/></p>
                <p><c:out value="${ticket.time}"/></p>
                <p><c:out value="${ticket.barcode}"/></p>
            </div>
            <p>Театр: <span><c:out value="${ticket.theatre}"/></span></p>
            <p>Труппа: <span><c:out value="${ticket.troupe}"/></span></p>
            <p>Актёры: <span><c:out value="${ticket.actors}"/></span></p>
            <div class="price"><p>Цена:<span> <c:out value="${ticket.price}"/>грн</span></p></div>
            <p>Ряд: <c:out value="${ticket.row}"/></p><p>Место: <c:out value="${ticket.place}"/></p>
        </div>
    </div>
</div>
<script src="<c:url value="/javascripts/libs/bower_components/underscore/underscore.js"/>"></script>
<script src="<c:url value="/javascripts/libs/bower_components/jquery/dist/jquery.js"/>"></script>
<script src="<c:url value="/javascripts/libs/bower_components/bootstrap/dist/js/bootstrap.js"/>"></script>
<script src="<c:url value="/javascripts/libs/bower_components/backbone/backbone.js"/>"></script>
<script src="<c:url value="/javascripts/custom/admin/ticket.js"/>"></script>
</body>
</html>
