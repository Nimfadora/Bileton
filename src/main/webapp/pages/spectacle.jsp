<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix ="c" %>
<!DOCTYPE HTML>
<html>
<head>
  <title>Spectacle</title>
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
            <li class="active"><a href="<c:url value="/pages/spectacle.jsp"/>" id="spectacleTable">Spectacle</a></li>
            <li><a href="<c:url value="/pages/performance.jsp"/>" id="perftable">Performance</a></li>
            <li><a href="<c:url value="/pages/playReport.jsp"/>" id="playReport">PlayReport</a></li>
            <li><a href="<c:url value="/admin/ticket"/>" id="ticket">Ticket</a></li>
            <li><a href="<c:url value="/pages/editor.jsp"/>" id="editor">Editor</a></li>
            <li><a href="<c:url value="/pages/pit.jsp"/>" id="pit">PlaysInTheatres</a></li>
            <li><a href="<c:url value="/pages/troupeStatistics.jsp"/>" id="troupeSt">Troupe Statistics</a></li>
            <li><a href="<c:url value="/pages/ticketStatistics.jsp"/>" id="ticketSt">Ticket Statistics</a></li>
          </ul>
        </div>
      </div>
    </nav>
  </div>
  <div class="data">
    <div id="filters"></div>
    <div class="container" id="table"></div>
  </div>
  <div class="formContainer">
    <div class="container" id="form"></div>
  </div>
</div>
<script type="text/template" id="searchFilterTemplate"><jsp:include page="/javascripts/templates/searchFilterTemplate1.js"/></script>
<script type="text/template" id="spectacleFormTemplate"><jsp:include page="/javascripts/templates/spectacleFormTemplate.js"/></script>
<script src="<c:url value="/javascripts/libs/bower_components/underscore/underscore.js"/>"></script>
<script src="<c:url value="/javascripts/libs/bower_components/jquery/dist/jquery.js"/>"></script>
<script src="<c:url value="/javascripts/libs/bower_components/bootstrap/dist/js/bootstrap.js"/>"></script>
<script src="<c:url value="/javascripts/libs/bower_components/backbone/backbone.js"/>"></script>
<script src="<c:url value="/javascripts/custom/admin/spectacle.js"/>"></script>
</body>
</html>