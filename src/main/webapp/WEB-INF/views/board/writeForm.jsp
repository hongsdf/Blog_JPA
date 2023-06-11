<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- heaer.jsp 파일 포함-->
<%@ include file="../layout/header.jsp" %>

<div class="container">
<br>
    <form>
   <div class="form-group">
   		<input type="text"  class="form-control" placeholder="Enter title" id="title">
   	</div>

   <div class="form-group">
     <textarea class="form-control summernote" rows="5" id="content"></textarea>
   </div>

   </form>
    <button id="btn-save" class="btn btn-primary">글 등록</button>
</div>

 <script>
      $('.summernote').summernote({
        tabsize: 2,
        height: 300
      });
 </script>



<!-- footer 파일 포함 -->
<script src="/js/board.js"></script>
<%@ include file ="../layout/footer.jsp" %>
