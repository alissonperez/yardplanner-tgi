<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<s:include value="/WEB-INF/content/includes/head.inc.jsp">
    <s:param name="title"><s:property value="title" escape="true"/></s:param>
    <s:param name="baseUrl"><s:url value="/" encode="false" namespace="/"/></s:param>
</s:include>

<body>

<!-- <s:property value="title"/> Teste -->

<s:action name="navbar" namespace="/" executeResult="true"/>

<!-- Container -->
<div class="container register">

<!-- Navigation -->
<ul class="breadcrumb">
<li>
<a href="<s:url action="index" encode="false" namespace="/"/>">Home</a> <span class="divider">/</span>
</li>
<li class="active">Usuários</li>
</ul>
<!-- /Navigation -->

<div class="page-header">
<h1>Usuários</h1>
</div>

<s:if test="%{users.size() > 0}">

<table class="table table-striped">
<thead>
<tr>
<th>#</th>
<th>Nome</th>
<th>E-mail</th>
<th>Admnistrador</th>
<th>Bloqueado</th>
<th>&nbsp;</th>
</tr>
</thead>
<tbody>
<s:iterator value="users">
<tr>
<td><s:property value="userId"/></td>
<td><s:property value="firstName"/> <s:property value="lastName"/></td>
<td><s:property value="email"/></td>
<td><s:if test="root"><span class="label label-info">Sim</span></s:if><s:else><span class="label">Não</span></s:else></td>
<td><s:if test="blocked"><span class="label label-important">Sim</span></s:if><s:else><span class="label">Não</span></s:else><td>
<td><a href="<s:url action="usuario/%{userId}" namespace="/usuario"/>">Editar</a><td>
</tr>
</s:iterator>
</tbody>
</table>

</s:if>
<s:else>
<div class="alert alert-success">Não há usuários</div>
</s:else>

<s:include value="/WEB-INF/content/includes/footer.inc.jsp"/>
<s:include value="/WEB-INF/content/includes/js.inc.jsp">
	<s:param name="baseUrl"><s:url value="/" encode="false" namespace="/"/></s:param>
</s:include>

</div>
<!-- /Container -->

</body>
</html>