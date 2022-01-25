<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MURA :: Q&A 게시판 글 보기</title>
<style type="text/css">

.blank
{
width : 100px;
height : 20px;
border: thin;
border-color: black;
padding : 6px;
margin: 5px;
	
}
.dat{
font-size: 25px;
text-align: center;
width: 80px;
height: 30px;
background-color: #fff;
border: medium;
border-color: black;
border-style: solid;
right: 310px;
position: relative;
}

.table{
color : #fff;
background-color: #a84781;
border-radius: 5px;
}

.s2{
height: 25px;
border: thin;
border-radius: 5px;
border-color: aqua;
background-color: #330033;
color: white;
font-weight: bold;
cursor: pointer;
}

.s2:hover{
background-color: orange;
color:black;
}

.td1{
border: 1px solid;
border-radius: 5px;
}

</style>

<link rel="icon" type="image/x-icon" href="../images/mura_logo.png">
<link href="../page/css/style.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="${bodyback_c }">

	<!--N 네비메뉴 -->
	<div align="center">
	<%@ include file="../page/nav.jsp" %>
	<br><br>

	<!-- 상단 로고 -->
	<div class="logo">
	  <a href="/MURA2/page/index.mur"> 
	  <img src="../page/images/mura_logo2.png" width="230" height="230" border="0" alt=""></a>
	</div>
	
	<!-- 상단 메뉴바 -->
	<%@ include file="../page/menubar.jsp" %>
	<br>
	<hr style="width:100%;height:3px;border:none;background-color:#a84781;">
	<br><br>
	</div>

<!--  <body bgcolor="${bodyback_c }">
<div align="center"> <b>글 상세 보기</b> <br><br>  -->


<form>

<table width="700" border="0" cellpadding="6" cellspacing="6" align="center" 
bgcolor="#fff" style="border-radius:8px; border-collapse: separate; border-spacing: 3px 3px;">


<tr height="30">
<td class="table" align="center" width="80" bgcolor="${value_c }">글제목</td>
<td class="td1" align="center" width="460" colspan="4">
<pre>${qaArticle.wsubject_qt }</pre></td>
<td class="table" align="center" width="80" bgcolor="${value_c }">작성일</td>
<td class="td1" align="center" width="80">${qaArticle.date_qt }</td>
</tr>


<tr height="30">
<td class="table" align="center" width="80" bgcolor="${value_c }">작성자</td>
<td class="td1" align="center" width="80" colspan="4">${qaArticle.nn_mem }</td>
<td class="table" align="center" width="80" bgcolor="${value_c }">조회수</td>
<td class="td1" align="center" width="80">${qaArticle.readcount_qt }</td>

</tr>


<tr height="30">
<td class="table" align="center" width="80" bgcolor="${value_c }">글내용</td>
<td class="td1" width="700" height="500" colspan="7" >
<pre>${qaArticle.wcontent_qt }</pre></td>
</tr>

</table>


<table width="700" border="0" cellpadding="6" cellspacing="6" align="center" 
bgcolor="#fff" style="border-radius:8px; border-collapse: separate; border-spacing: 3px 3px;">
<tr height="30">
<td colspan="7" bgcolor="${value_c }" align="right">

<c:if test="${id_mem != null }">
<c:if test="${un_mem eq qaArticle.un_mem}">
<input class="s2" type="button" value="글수정"
onclick="document.location.href='/MURA2/userboard/qaUpdateForm.mur?idx_qt=${qaArticle.idx_qt }&pageNum=${pageNum }'">
&nbsp;&nbsp;&nbsp;&nbsp;

<a onclick="return confirm('정말로 삭제하시겠습니까?')"
href="/MURA2/userboard/qaDeletePro.mur?idx_qt=${qaArticle.idx_qt }&pageNum=${pageNum}">
<input class="s2" type="button" value="글삭제"></a>

&nbsp;&nbsp;&nbsp;&nbsp;
</c:if>
</c:if>


<input class="s2" type="button" value="답변쓰기"
onclick="document.location.href='/MURA2/userboard/qaWriteForm.mur?idx_qt=${qaArticle.idx_qt }&ref_qt=${qaArticle.ref_qt}&step_qt=${qaArticle.step_qt}&depth_qt=${qaArticle.depth_qt}'">
&nbsp;&nbsp;&nbsp;&nbsp;

<input class="s2" type="button" value="글목록"
onclick="document.location.href='/MURA2/userboard/qaboardList.mur?pageNum=${pageNum }'">


</td>
</tr>
</table>
</form>

<%@ include file="../page/footer.jsp" %>
</body>
</html>
