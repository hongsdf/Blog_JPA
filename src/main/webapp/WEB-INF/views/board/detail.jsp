<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- heaer.jsp 파일 포함-->
<%@ include file="../layout/header.jsp" %>

<!-- container에 감싸 중앙 공간배치-->
<div class="container">
<br>
    <!-- 버튼 디자인 수정하기 -->
    <button class="btn btn-info" onclick="history.back()"> 돌아가기 </button>
     <c:if test="${board.user.id == principal.user.id}">
     		<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
     		<button id="btn-delete" class="btn btn-danger">삭제</button>
     </c:if>

    <br>
    <div>
    <br>
        글 번호: <span id ="id"><i>${board.id}</i></span>
        작성자: <span><i>${board.user.username} </i></span>
    </div>
    <br>
   	    <h2>${board.title}</h2>

    <br>
        <div>${board.content}</div>
        <hr/>


        <div class ="card">
        <form>
        <input type="hidden" id="userId" value="${principal.user.id}" />
        <input type="hidden" id="boardId" value="${board.id}" />
            <div>
                <div class="card-body">
                <textarea id = "reply-content" rows="1" class="form-control"></textarea></div>
                <div class="card-footer"><button type="button"  id = "reply-save" class ="btn btn-primary">등록</button></div>
            </div>
            </form>
        </div>

        <!-- &nbsp : 한칸 띄우기-->
        <!--  := 서버로 부터 받아오는 내용 -->
        <div class ="card">
                <div class ="card-header">댓글 리스트</div>
                <ul id="reply--items"class="list-group">
                    <c:forEach var="reply" items="${board.replys}">
                         <li id ="reply-${reply.id}"class="list-group-item d-flex justify-content-between">
                             <div>${reply.content}</div>
                             <div class = " d-flex">
                             <!-- 글 관리자만 지울수 있음 || 댓글 관리자만-->
                             <div> 작성자: ${reply.user.username} &nbsp</div>
                                  <c:if test="${board.user.id == principal.user.id || reply.user.id == principal.user.id}">
                                     <button onclick="index.replyDelete(${board.id}, ${reply.id})" class="badge">삭제</button>
                                   </c:if>
                            </div>
                       </li>

                    </c:forEach>
            </ul>


        </div>

</div>
<hr>
 <script>
      $('.summernote').summernote({
        tabsize: 2,
        height: 300
      });
 </script>



<!-- footer 파일 포함 -->
<!-- 수정 삭제를 위한  js파일-->
<script src="/js/board.js"></script>
<%@ include file ="../layout/footer.jsp" %>
