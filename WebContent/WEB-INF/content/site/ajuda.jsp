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
<li class="active">Ajuda</li>
</ul>
<!-- /Navigation -->

<div class="page-header">
<h1>Ajuda
<small>Manual do usu�rio</small></h1>
</div>

<!-- GRID -->
<div class="row show-grid">

<!-- Content (span16) -->
<div class="span16 manual">

<!-- Item -->
<div class="manual_item">
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/novo_patio.png" alt="Formul�rio para cria��o de um novo p�tio.">
<div class="description">Formul�rio para cria��o de um novo p�tio.</div>
</div>
<!-- /Figura -->
<h2>P�tios</h2>
<p>Os containers s�o armazenados temporariamente em p�tios, divididos em regi�es comumente conhecidas como blocos. Para criar um p�tio, clique na op��o <b>P�tios</b> na barra de navega��o superior. Na p�gina seguinte entre com o nome desejado para o p�tio e clique no bot�o <b>Gravar</b>.</p>
<div class="clear_both"></div>
</div>
<!-- Item -->

<div class="manual_item">
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/novo_bloco.png" alt="Formul�rio para cria��o de um novo p�tio.">
<div class="description">Formul�rio para cria��o de um novo bloco.</div>
</div>
<!-- /Figura -->
<h2>Blocos</h2>
<p>Cada bloco possui um determinado n�mero de linhas, colunas e camadas. Sua finalidade � armazenar cont�ineres em pilhas. Os blocos podem armazenar cont�ineres de diversos tipos e tamanhos. No YardPlanner � poss�vel utilizar dois tamanhos de cont�ineres: 1 ou 2 TEUs (<i>Twenty Feet Equivalent Unit container</i>). Para criar um bloco, clique sobre o p�tio desejado. Na p�gina seguinte informe o nome do cont�iner, o n�mero de linhas, colunas e camadas. Em seguida, selecione o tamanho desejado e clique no bot�o <b>Gravar</b>.</p>
<div class="clear_both"></div>
</div>

<div class="manual_item">
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/bloco.jpg" alt="Visualiza��o dos containers em um bloco de 3 colunas, 4 linhas e 2 camadas.">
<div class="description">Visualiza��o dos containers em um bloco de 3 colunas, 4 linhas e 2 camadas.</div>
</div>
<!-- /Figura -->
<h2>Containers</h2>
<p>Os cont�ineres est�o dispostos em linhas, colunas e camadas. Cada camada � representada por uma matriz (conforme figura). H� duas formas de popular um bloco com cont�ineres:<br>Manualmente por meio de inclus�o individual; ou em Lote por meio de um arquivo XML.</p>

<div class="separator"></div>
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/novo_container.png" alt="Formul�rio para a inser��o manual de um container.">
<div class="description">Formul�rio para a inser��o manual de um container.</div>
</div>
<!-- /Figura -->
<div class="indent2">
<h3>Inclus�o individual</h3>
<p>Possibilita a inclus�o de containers individuais no bloco. Informe o c�digo do cont�iner (ISO 6346) e a sua posi��o nos campos <b>coluna</b>, <b>linha</b> e <b>camada</b>. Em seguida, clique no bot�o <b>Gravar</b>. O cont�iner ser� inserido no bloco.</p>
</div>

<div class="separator"></div>
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/upload_xml.png" alt="Formul�rio upload de arquivo xml.">
<div class="description">Formul�rio upload de arquivo xml.</div>
</div>
<!-- /Figura -->
<div class="indent2">
<h3>Inclus�o em lote (arquivo XML)</h3>
<p>Possibilita informar os cont�ineres presentes em um bloco de maneira r�pida e pr�tica. Para isso, entre no bloco desejado. No formul�rio para upload selecione o arquivo e clique no bot�o <b>Gravar</b>.</p>

<h6>Obs: � poss�vel limpar todo o bloco no processo de upload de XML. Para isso, marque a op��o <b>Limpar bloco</b> no formul�rio de upload.</h6>
<h6>Obs: Caso n�o seja informado o c�digo para o container, o sistema gerar� um de forma aleat�ria.</h6>
</div>

<div class="clear_both"></div>
</div>

<div class="manual_item">
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/remover_containers.jpg" alt="Tela para marca��o dos containers que se deseja remover.">
<div class="description">Tela para marca��o dos containers que se deseja remover.</div>
</div>
<!-- /Figura -->
<h2>Remo��o de containers</h2>
<p>Entre no bloco desejado e clique sobre o bot�o <b>Remover cont�ineres</b>. Na tela seguinte (semelhante � tela do bloco), marque os containers desejados e clique sobre o bot�o <b>Remover</b>.<br>Ser� exibida a sequ�ncia �tima para remo��o, seu custo e a movimenta��o necess�ria dos cont�ineres.</p>

<div class="separator"></div>
<!-- Figura -->
<div class="fig">
<img src="<s:url value="/"/>images/ajuda/remocao.jpg" alt="Resultado da remo��o com a sequ�ncia �tima, seu custo e a movimenta��o necess�ria dos containers.">
<div class="description">Resultado da remo��o com a sequ�ncia �tima, seu custo e a movimenta��o<br>necess�ria dos containers.</div>
</div>
<!-- /Figura -->
<div class="indent2">
<h3>Resultado da remo��o</h3>
<p>O sistema efetuar� o processamento da remo��o calculando a melhor sequ�ncia de retirada e exibindo-a em seguida.<br>� poss�vel confirmar a remo��o (bot�o <b>Confirmar</b>) ou cancel�-la (bot�o <b>Cancelar</b>).</p>
<div class="clear_both"></div>
</div>
</div>

<div class="manual_item">
<h2>Video tutorial</h2>
<p>Baixe <a href="<s:url value="/xmlexample/block_video_tutorial.xml"/>" target="_blank">aqui</a> o xml utilzado para popular o p�tio visto no v�deo abaixo.</p>

<div class="video_tutorial">
<iframe width="840" height="630" src="http://www.youtube.com/embed/2wTYdQ7KiqM" allowFullScreen="true"></iframe>
</div>
</div>

</div>
<!-- /Content (span16) -->

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