<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!-- heaer.jsp 파일 포함-->
<%@ include file="layout/header.jsp" %>

<div class="container">

    <!-- 센터로 이동 -->

  <!-- div class="video-container" -->
         <!-- iframe width="853" height="480" src="//www.youtube.com/embed/Q8TXgCzxEnw?rel=0" frameborder="0" allowfullscreen></iframe -->
   <!-- /div -->


 <!-- 이미지 -->
    <br>
     <div><img width="1110" height="380"src = "/image/blog.jpeg"/></div>
    <br>

     <!-- git 주소 이동-->
   <div class="card" >
     <div class="card-body">
       <h4 class="card-title">홍성우</h4>
       <p class="card-text">Git hub 링크</p>
       <a href="https://github.com/hongsdf" class="btn btn-primary">이동 버튼</a>
      </div>
    </div>

    <!-- jstl -->
       <c:forEach var="board"  items="${boards.content}">
       <div class="card" >
         <div class="card-body"> <h4 class="card-title">${board.title}</h4>
           <a href="/board/${board.id}" class="btn btn-primary">상세보기</a>
                </div>
           </div>
        </c:forEach>

   <br>

<!-- bootstrap 디자인 pagination버튼 특성상 blcok이므로 한줄을 차지  justify-content-center를 추가하여 가운데 정렬하였음-->
<!--  next := 현재 페이지 + 1 ,   Previous := 현재페이지 - 1  하지만 -1,페이지가 될수 없으므로 조치   -->
<! --  disabled : 비활성화-->

<ul class="pagination justify-content-center">
    <c:choose>
             <c:when test="${boards.first}">
               <li class="page-item disabled" ><a class="page-link" href="?page=${boards.number - 1}">Previous</a></li>
             </c:when>
            <c:otherwise>
               <li class="page-item " ><a class="page-link" href="?page=${boards.number - 1}">Previous</a></li>
            </c:otherwise>
    </c:choose>

     <c:choose>
        <c:when test="${boards.last}">
          <li class="page-item disabled"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
        </c:when>
        <c:otherwise>
           <li class="page-item "><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
        </c:otherwise>
      </c:choose>
</ul>
</div>

<!-- footer 파일 포함 -->
<%@ include file ="layout/footer.jsp" %>
