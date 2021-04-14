<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<s:include value="/WEB-INF/content/includes/head.inc.jsp">
    <s:param name="title"><s:property value="title" escape="true"/></s:param>
    <s:param name="baseUrl"><s:url value="/" encode="false" namespace="/"/></s:param>
</s:include>

<body>

<s:action name="navbar" namespace="/" executeResult="true"/>

<!-- Container -->
<div class="container">

<!-- Navigation -->
<ul class="breadcrumb">
<li>
<a href="<s:url action="index" namespace="/"/>">Home</a> <span class="divider">/</span>
</li>
<li class="active">Pátios</li>
</ul>
<!-- /Navigation -->

<div class="page-header">
<h1>Pátios</h1>
</div>

<!-- GRID -->
<div class="row show-grid">

<!-- Left Column -->
<div class="span8">

<s:if test="%{yards.size() > 0}">

<table class="table table-striped">
<thead>
<tr>
<th>Nome</th>
<th>&nbsp;</th>
</tr>
</thead>
<tbody>
<s:iterator value="yards">
<tr>
<td><a href="<s:url action="index/%{yardId}" namespace="/bloco"/>"><s:property value="name"/></a></td>
<td><a href="<s:url action="remover/%{yardId}" namespace="/patio"/>">Remover</a><td>
</tr>
</s:iterator>
</tbody>
</table>

</s:if>
<s:else>
<div class="alert alert-success">Você não possui pátios</div>
</s:else>

</div>
<!-- /Left Column -->

<!-- Right Column -->
<div class="span4">
	
<h2>Novo pátio</h2>

<s:fielderror cssClass="alert alert-block"/>

<form class="well" action="<s:url action="novo" namespace="/patio/"/>">
<label for="yard_name">Nome</label>
<input id="yard_name" type="text" name="yard.name" value=""><br>
<input class="btn btn-primary" type="submit" name="send" value="Gravar">
</form>

</div>
<!-- /Right Column -->

</div>
<!-- /GRID -->

<s:include value="/WEB-INF/content/includes/footer.inc.jsp"/>
<s:include value="/WEB-INF/content/includes/js.inc.jsp">
	<s:param name="baseUrl"><s:url value="/" encode="false" namespace="/"/></s:param>
</s:include>
</div>
<!-- /Container -->

</body>
</html>