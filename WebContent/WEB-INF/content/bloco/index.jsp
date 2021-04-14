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
<a href="<s:url action="index" encode="false" namespace="/"/>">Home</a> <span class="divider">/</span>
</li>
<li>
<a href="<s:url action="index" encode="false" namespace="/patio"/>">Pátios</a> <span class="divider">/</span>
</li>
<li class="active"><s:property value="yard.name"/> - Blocos</li>
</ul>
<!-- /Navigation -->

<div class="page-header">
<h1>
Blocos
<small><s:property value="yard.name"/></small>
</h1>
</div>

<!-- GRID -->
<div class="row show-grid">

<!-- Left Column -->
<div class="span8">

<s:if test="%{blocks.size() > 0}">

<table class="table">
<thead>
<tr>
<th>Nome</th>
<th>Linhas</th>
<th>Colunas</th>
<th>Camadas</th>
<th>&nbsp;</th>
</tr>
</thead>
<tbody>
<s:iterator value="blocks">
<tr>
<td><a href="<s:url action="index/%{blockId}" encode="false" namespace="/container"/>"><s:property value="name"/></a></td>
<td><s:property value="lines"/></td>
<td><s:property value="columns"/></td>
<td><s:property value="layers"/></td>
<td><a href="<s:url action="remover/%{blockId}" encode="false"/>">Remover</a></td>
</tr>
</s:iterator>
</tbody>
</table>

</s:if>
<s:else>
<div class="alert alert-success">Você não possui blocos</div>
</s:else>

</div>
<!-- /Left Column -->

<!-- Right Column -->
<div class="span4">

<h2>Novo bloco</h2>

<s:fielderror cssClass="alert alert-block"/>

<form class="well" action="<s:url action="novo/%{yardId}" encode="false"/>" method="post">
<label for="block_name">Nome</label>
<input id="block_name" type="text" name="block.name" value="<s:property value="block.name"/>"><br>
<label for="block_name">Linhas (num de containers)</label>
<input id="block_name" type="text" name="block.lines" value="<s:property value="block.lines"/>"><br>
<label for="block_name">Colunas (num de containers)</label>
<input id="block_name" type="text" name="block.columns" value="<s:property value="block.columns"/>"><br>
<label for="block_name">Camadas (num de containers)</label>
<input id="block_name" type="text" name="block.layers" value="<s:property value="block.layers"/>"><br>
<label for="block_name">Tamanho dos containers</label>
<s:select name="containerSizeId" headerKey="-1" headerValue="- Tamanho dos Containers -" list="containerSizes" listKey="sizeId" listValue="teuFormated" value="containerSizeId"/><br>
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