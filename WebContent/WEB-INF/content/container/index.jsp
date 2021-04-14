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
<li>
<a href="<s:url action="index" namespace="/patio"/>">Pátios</a> <span class="divider">/</span>
</li>
<li>
<a href="<s:url action="index/%{yard.yardId}" namespace="/bloco"/>"><s:property value="yard.name"/></a> <span class="divider">/</span>
</li>
<li class="active"><s:property value="block.name"/> - Containers</li>
</ul>
<!-- /Navigation -->

<div class="page-header">
<h1>
Containers
<small><s:property value="block.name"/> - <s:property value="yard.name"/></small>
</h1>
</div>

<!-- Div Grid -->
<div class="row show-grid">

<!-- Left Column -->
<div class="span9">

<!-- Camadas do bloco -->
<s:iterator value="blockList" var="level" status="levelstatus">
<h2>Camada <s:property value="%{#levelstatus.index}"/></h2>
<table class="block">
<s:iterator value="#level" var="line" status="linestatus">

<!-- Header da camada -->
<s:if test="#linestatus.first == true">
<thead>
<tr>
<th>&nbsp;</th>
<s:iterator value="#line" var="container2" status="containerstatus">
<th><s:property value="%{#containerstatus.index}"/></th>
</s:iterator>
</tr>
</thead>
<tbody>
</s:if>
<!-- /Header da camada -->

<tr>
<th><s:property value="%{#linestatus.index}"/></th>
<s:iterator value="#line" var="container">
<td <s:if test="#container.containerId">class="filled removable"</s:if>><div><s:if test="#container.containerId">
<!-- <s:property value="#container.position"/> -->
<span class="badge badge-important" alt="<s:property value="#container.code"/>"><s:property value="#container.codeNumber"/></span>
</s:if>
<s:else>&nbsp;</s:else></div></td>
</s:iterator>
</tr>

</s:iterator>

</tbody>
</table>
</s:iterator>
<!-- /Camadas do bloco -->

</div>
<!-- /Left Column -->

<!-- Right Column -->
<div class="span3">

<input id="remove_containers" class="btn btn-large btn-primary" type="button" name="remove" value="Remover containers" onClick="javascript: window.location='<s:url action="retirar/%{blockId}"/>'">

<h2>Novo container</h2>

<s:fielderror cssClass="alert alert-block"/>

<form class="well" action="<s:url action="novo/%{blockId}"/>" method="post">
<label for="container_name">Código (ISO 6346)</label>
<input id="container_name" type="text" name="container.code" value="<s:property value="container.code"/>">
<h4>Posição</h4>
<table class="table position">
<thead>
<tr><th><label for="pos_x">Coluna</label></th><th><label for="pos_y">Linha</label></th><th><label for="pos_z">Camada</label></th></tr>
</thead>
<tbody>
<tr>
<td><input id="pos_x" type="text" name="container.posX" size="5" value="<s:property value="container.posX"/>"></td>
<td><input id="pos_y" type="text" name="container.posY" size="5" value="<s:property value="container.posY"/>"></td>
<td><input id="pos_z" type="text" name="container.posZ" size="5" value="<s:property value="container.posZ"/>"></td>
</tr>
</tbody>
</table>
<div class="form-actions">
<input class="btn btn-primary" type="submit" name="send" value="Gravar">
</div>
</form>

<h2>Upload de XML</h2>

<form class="well" action="<s:url action="xmlUpload/%{blockId}"/>" method="POST" enctype="multipart/form-data">
<p><a href="<s:url value="/xmlexample/block.xml"/>" target="_blank">Arquivo de exemplo</a></p>
<label><input type="checkbox" name="clearBlock" value="yes" checked="checked"> Limpar bloco</label>
<input type="file" name="xmlUpload" value="Buscar">
<div class="form-actions">
<input class="btn btn-primary" type="submit" name="send">
</div>
</form>

</div>
<!-- /Right Column -->

</div>
<!-- /Div Grid -->

<s:include value="/WEB-INF/content/includes/footer.inc.jsp"/>
<s:include value="/WEB-INF/content/includes/js.inc.jsp">
	<s:param name="baseUrl"><s:url value="/" encode="false" namespace="/"/></s:param>
</s:include>

</div>
<!-- /Container -->

</body>
</html>