<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="isAuthenticated()">
   <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Bootstrap Example</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">

   <!--  js 파일은 밑에 작성 := 인터프리터 언어 이므로-->
    <script src="https://code.jquery.com/jquery-3.5.1.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
    <!-- summer note form 태그 -->
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>


</head>
<body>
<!--  시큐리티 값 확인  ${principal} -->


<nav class="navbar navbar-expand-sm bg-primary navbar-dark">
  <ul class="navbar-nav">
    <li class="nav-item active">
      <a class="nav-link"  href="/">Home</a>
    </li>

   <c:choose>
        <c:when test="${empty principal}">
                    <li class="nav-item">
                      <a class="nav-link" href="/auth/loginForm">Login</a> <!-- 풀 url 입력 -->
                    </li>
                    <li class="nav-item">
                      <a class="nav-link" href="/auth/joinForm">Sign Up</a>
                    </li>
        </c:when>
        <c:otherwise>
        <!-- 세션정보가 있다면-->
                <li class="nav-item">
                    <a class="nav-link" href="/board/writeForm">글쓰기</a> <!-- 풀 url 입력 -->
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/user/updateForm">회원정보</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/logout">로그아웃</a>
                </li>
        </c:otherwise>
    </c:choose>

  </ul>
</nav>
