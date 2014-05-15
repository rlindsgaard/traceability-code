<!DOCTYPE html>

<%@page import="dk.diku.lindsgaard.prototype1.resources.GitCommit"%>
<%@page import="org.eclipse.lyo.oslc4j.core.model.ServiceProvider"%>
<%@ page contentType="text/html" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%
    List<GitCommit> commits = (List<GitCommit>) request.getAttribute("results");
    ServiceProvider serviceProvider = (ServiceProvider) request.getAttribute("serviceProvider");
    
    String nextPageUri = (String)request.getAttribute("nextPageUri");
    
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <title>Bugzilla OSLC Adapter: Service Provider for <%= serviceProvider.getTitle() + "(" + serviceProvider.getIdentifier() + ")" %></title>
    </head>
    <body onload="">
    
        <div id="header">
            <div id="banner"></div>
            <table border="0" cellspacing="0" cellpadding="0" id="titles">
                <tr>
                    <td id="title">
                        <p>
                            Bugzilla OSLC Adapter: Service Provider
                        </p>
                    </td>
                    <td id="information">
                        <p class="header_addl_info">
                            version 0.1
                        </p>
                    </td>
                </tr>
            </table>
        </div>
        
        <div id="bugzilla-body">  
            <div id="page-index">
            
                <img src="../../images/resources/bugzilla.gif" alt="icon" width="80" height="80" />
    
                <h1>Query Results</h1>

                <% for (GitCommit commit : commits) { %>         
                foo
                <p>Summary: <%= commit.getIdentifier() %><br /><a href="<%= commit.getAbout() %>">
                    <%= commit.getAbout() %></a></p>
                <% } %>
                <% if (nextPageUri != null) { %><a href="<%= nextPageUri %>">Next Page</a><% } %>

            </div>
        </div>
        
        <div id="footer">
            <div class="intro"></div>
            <div class="outro">
                <div style="margin: 0 1em 1em 1em; line-height: 1.6em; text-align: left">
                    <b>OSLC Tools Adapter Server 0.1</b> brought to you by <a href="http://eclipse.org/lyo">Eclipse Lyo</a><br />
                </div>
            </div>
        </div>
    </body>
</html>

