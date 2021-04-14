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
<li>
<a href="<s:url action="index/%{block.blockId}" namespace="/container"/>"><s:property value="block.name"/> - Containers</a> <span class="divider">/</span>
</li>
<li class="active">Remover containers</li>
</ul>
<!-- /Navigation -->

<div class="page-header">
<h1>
Remover containers
<small><s:property value="block.name"/> - <s:property value="yard.name"/></small>
</h1>
</div>

<s:if test="%{bestSequenceCodes!=null}">
<!-- Div Grid -->
<div class="row show-grid">

<div class="span12 remove_confirmation">

<!-- Confirmation -->
<div class="span6 box_sequence">

<h3>Sequência ótima de retirada</h3>
<ul class="sequence_to_remove">
<s:iterator value="bestSequenceCodes" var="code">
<li><s:property value="code"/></li>
</s:iterator>
</ul>
<%-- <p class="cost_to_remove">Custo: <s:property value="costToRemove"/></p> --%>

<form action="<s:url action="retirar/%{blockId}"/>" method="post">
<input type="hidden" name="containersStrIds" value="<s:property value="bestSequenceIds"/>"/>
<div class="buttons">
<input class="btn btn-large btn-primary" type="submit" name="btnConfirmation" value="Confirmar"> <input class="btn btn-large" type="submit" name="btnCancel" value="Cancelar">
</div>
</form>

</div>

<div class="span5 container_movement">
<h3>Movimentação dos containers</h3>
<s:iterator value="msgs" var="msg">
<p><s:property value="msg"/></p>
</s:iterator>
</div>
<!-- /Confirmation -->

</div>
</div>
<!-- /Div Grid -->
</s:if>

<!-- Div Grid -->
<div class="row show-grid">

<!-- Left Column -->
<div class="span12">
<form action="<s:url action="retirar/%{blockId}"/>" method="post">

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
<label>
<input type="checkbox" name="containersStrIds" value="<s:property value="#container.containerId"/>">
<!-- <s:property value="#container.position"/> -->
<span class="badge badge-important" title="<s:property value="#container.name"/>"><s:property value="#container.codeNumber"/></span>
</label>
</s:if>
<s:else>&nbsp;</s:else></div></td>
</s:iterator>
</tr>

</s:iterator>
</tbody>
</table>
</s:iterator>
<!-- /Camadas do bloco -->

<div class="form-actions">
<input class="btn btn-primary btn-large" type="submit" name="btnRemove" value="Remover">
</div>

<input type="hidden" value="" name="__multiselect_removeList" id="__multiselect_removeList">
</form>
</div>
<!-- /Left Column -->

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