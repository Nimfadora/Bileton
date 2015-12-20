<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML>
<html>
    <head>
        <title>Bileton</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="<c:url value="/javascripts/libs/bower_components/bootstrap/dist/css/bootstrap.min.css"/>"/>
        <link rel="stylesheet" href="<c:url value="/stylesheets/style.css" />">
        <link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
    </head>
    <body>
        <div class="upper"><img src="<c:url value="/images/logoBlack.png"/>" id="logo"></div>
        <div class="leftCurtain"><img src="<c:url value="/images/kurtain.jpg"/>"></div>
        <div class="sortMenu"></div>
        <div class="rightCurtain"><img src="<c:url value="/images/rkurtain.jpg"/>"></div>
        <div id="plot"></div>
        <script type="text/template" id="myModalTemplate"><jsp:include page="javascripts/templates/myModalTemplate.js"/></script>
        <script type="text/template" id="minPlayTemplate"><jsp:include page="javascripts/templates/minPlayTemplate.js"/></script>
        <script type="text/template" id="maxPlayTemplate"><jsp:include page="javascripts/templates/maxPlayTemplate.js"/></script>
        <script type="text/template" id="formTemplate"><jsp:include page="javascripts/templates/formTemplate.js"/></script>
        <%--<script type="text/template" id="audience"><jsp:include page="javascripts/templates/audienceTemplate.js"/></script>--%>

        <script src="<c:url value="/javascripts/libs/bower_components/underscore/underscore.js"/>"></script>
        <script src="<c:url value="/javascripts/libs/bower_components/jquery/dist/jquery.js"/>"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
        <script src="<c:url value="/javascripts/libs/bower_components/backbone/backbone.js"/>"></script>
        <script src="<c:url value="/javascripts/libs/bower_components/bootstrap/dist/js/bootstrap.js"/>"></script>
        <script src="<c:url value="/javascripts/libs/bower_components/jquery-bootstrap-modal-steps/src/jquery-bootstrap-modal-steps.js"/>"></script>
        <script src="<c:url value="/javascripts/libs/bower_components/backbone.validation/src/backbone-validation.js"/>"></script>
        <script src="<c:url value="/javascripts/custom/prompts.js"/>"></script>
        <script src="<c:url value="/javascripts/custom/model.js"/>"></script>
        <script src="<c:url value="/javascripts/custom/view.js"/>"></script>
        <script src="<c:url value="/javascripts/custom/sort.js"/>"></script>
        <script src="<c:url value="/javascripts/custom/controller.js"/>"></script>
    </body>
</html>