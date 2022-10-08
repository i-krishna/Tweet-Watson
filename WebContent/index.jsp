<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ResourceBundle" %>

<%
ResourceBundle res = ResourceBundle.getBundle("TweetTranslate", request.getLocale());
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%=res.getString("title")%></title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/bootstrap-table.min.css" rel="stylesheet">
    <link href="css/grid.css" rel="stylesheet">
    <link href="css/tweettranslate.css" rel="stylesheet">
</head>

<body>
    <script src="js/jquery-1.11.1.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/bootstrap-table.min.js"></script>
    <script src="js/eventsource.min.js"></script>
    
    <div class="panel panel-default">
    <form class="panel-body">
    <div class="form-group">
    <label>Tweet a question to IBM Watson by logging into the twitter user account app_queswatson</label>
    </div>
    </form>
    </div>

</body>
</html>
