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

<!-- GRID -->
<div class="row show-grid">

<!-- Left Column -->
<div class="span8">
<div class="page-header">
<h1>Home</h1>
</div>

<p>
A partir da década de 1960 os contêineres ganharam muita importância no comércio internacional como meio para a armazenagem da carga a ser transportada entre portos. Os navios cresceram substancialmente de tamanho e hoje podem chegar a quase 15.000 TEUs (<i>Twenty Feet Equivalent Unit container</i>). Para usar estes navios e fazer o transporte da carga eficientemente, o tempo do navio no terminal deve ser o menor possível, o que implica que uma grande quantidade de contêineres deve ser manipulada em um pequeno intervalo de tempo e com um mínimo uso de pátio e equipamentos. Como consequência, os Terminais de Contêineres (TECONs) enfrentam o desafio de aumentar sua capacidade de atendimento (armazenagem, carregamento e descarregamento) e diminuir os tempos de carregamento e descarregamento dos navios que atracam no porto. Para atingir estas metas, os terminais de contêineres devem empregar equipamentos modernos e otimizar seus processos logísticos. O objetivo do YARDPLANNER é contribuir para a otimização da operação de um TECON oferecendo um serviço web para a <b><i>programação da sequência de retirada de contêineres</i></b>.</p>

</div>
<!-- /Left Column -->

<!-- Right Column -->
<div class="span4">

<s:if test="%{#session.user_session}">
Bem-vindo <s:property value="#session.user_session.firstName"/>, <a href="<s:url action="logout" namespace="/usuario" encode="false"/>">Sair</a>
</s:if>
<s:else>
<!-- Login form -->
<h2>Login</h2>

<form class="well" action="<s:url action="login" namespace="/usuario" />" method="post">

<label class="control-label" for="user_email">E-mail</label>
<input id="user_email" type="text" name="user.email">

<label class="control-label" for="user_password">Senha</label>
<input id="user_password" type="password" name="user.passwordMD5">

<p><a href="<s:url action="registro" encode="false" namespace="/usuario"/>">Cadastrar-se</a></p>

<div class="form-actions">
<input type="submit" class="btn btn-primary" name="send" value="Logar">
</div>

</form>
<!-- Login form -->
</s:else>

</div>
<!-- /Right Column -->

</div>
<!-- /GRID -->

<!-- Incluindo -->
<s:include value="/WEB-INF/content/includes/footer.inc.jsp"/>
<s:include value="/WEB-INF/content/includes/js.inc.jsp">
	<s:param name="baseUrl"><s:url value="/" encode="false" namespace="/"/></s:param>
</s:include>

</div>
<!-- /Container -->

</body>
</html>