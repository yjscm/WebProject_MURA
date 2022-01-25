<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="id_mem" value="${sessionScope.id_mem }" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MURA :: 로그인</title>
<script type="text/javascript" src="script.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<link rel="icon" type="image/x-icon" href="../images/mura_logo.png">
<link href="../css/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
<!--===============================================================================================-->
<link rel="icon" type="image/png" href="../images/icons/favicon.ico" />
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="../vendor/animate/animate.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../vendor/select2/select2.min.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css"
	href="../vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
<link rel="stylesheet" type="text/css" href="../css/util.css">
<link rel="stylesheet" type="text/css" href="../css/main.css">
<!--===============================================================================================-->

</head>

<body bgcolor="#FFFFFF">

	<c:choose>
		<c:when test="${id_mem ne null && admin_mem eq 'Y'}">
		<script type="text/javascript">
				alert("관리자로 로그인합니다.");
		</script>
			<meta http-equiv="refresh"
				content="0;url=/MURA2/page/index.mur">
		</c:when>
		
		<c:when test="${id_mem ne null }">
			<script type="text/javascript">
				alert("${id_mem }님 환영합니다. 메인 페이지로 이동합니다.");
			</script>
			<meta http-equiv="refresh"
				content="0;url=/MURA2/page/index.mur">
			<!-- 로그아웃 인덱스로 바뀔예정 -->

		</c:when>
		
		<c:otherwise>

			<c:if test="${requestScope.check eq 0 }">
				<script type="text/javascript">
					alert("비밀번호가 틀렸습니다.");
				</script>
			</c:if>

			<c:if test="${requestScope.check eq -1 }">
				<script type="text/javascript">
					alert("아이디가 존재하지 않습니다.");
				</script>
			</c:if>

			<!-- 상단 로고 -->
			<div class="logo">
			  <a href="/MURA2/page/index.mur"> 
			  <img src="../images/mura_logo2.png" width="230" height="230" border="0" alt=""></a>
			</div>



			<div class="limiter">
				<div class="container-login100">
					<div class="wrap-login100 p-t-50 p-b-90">
						<form action="login.mur" method="post"
							class="login100-form validate-form flex-sb flex-w">
							<span class="login100-form-title p-b-51"> Login </span>


							<div class="wrap-input100 validate-input m-b-16"
								data-validate="Username is required">
								<input class="input100" type="text" name="id_mem"
									placeholder="Username"> <span class="focus-input100"></span>
							</div>


							<div class="wrap-input100 validate-input m-b-16"
								data-validate="Password is required">
								<input class="input100" type="password" name="pw_mem"
									placeholder="Password"> <span class="focus-input100"></span>
							</div>


							<div class="flex-sb-m w-full p-t-3 p-b-24">
								
								<div style="width: 150px;"></div>

								<div>
									<a href="findId.mur" class="txt1"> 아이디 찾기 </a>
								</div>
								|
								<div>
									<a href="findPw.mur" class="txt1"> 비밀번호 찾기 </a>
								</div>


							</div>

							<div class="container-login100-form-btn m-t-17">
								<input type="submit" class="login100-form-btn" value="로 그 인">


							</div>



						</form>
						<div align="right">
							<a href="signinForm.mur" class="txt1"> <b>회원가입</b>
							</a>
						</div>
					</div>
				</div>
			</div>


			<div id="dropDownSelect1"></div>

			<!--===============================================================================================-->
			<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
			<!--===============================================================================================-->
			<script src="vendor/animsition/js/animsition.min.js"></script>
			<!--===============================================================================================-->
			<script src="vendor/bootstrap/js/popper.js"></script>
			<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
			<!--===============================================================================================-->
			<script src="vendor/select2/select2.min.js"></script>
			<!--===============================================================================================-->
			<script src="vendor/daterangepicker/moment.min.js"></script>
			<script src="vendor/daterangepicker/daterangepicker.js"></script>
			<!--===============================================================================================-->
			<script src="vendor/countdowntime/countdowntime.js"></script>
			<!--===============================================================================================-->
			<script src="js/main.js"></script>

		</c:otherwise>
	</c:choose>

</body>
</html>