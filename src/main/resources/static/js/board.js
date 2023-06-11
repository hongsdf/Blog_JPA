let index = {
		init: function(){
			$("#btn-save").on("click", ()=>{
				this.save();
			});
			$("#btn-delete").on("click", ()=>{
            	this.deleteById();
            });
            $("#btn-update").on("click", ()=>{
                 this.update();
            });
              $("#reply-save").on("click", ()=>{
                   this.replySave();
            });







		},
		save: function(){

			let data = {
					title: $("#title").val(),
					content: $("#content").val()
			};

			$.ajax({ 
				type: "POST",
				url: "/api/writting",
				data: JSON.stringify(data), // http body데이터
				contentType: "application/json; charset=utf-8",// body데이터가 어떤 타입인지(MIME)
				dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
			}).done(function(resp){
                     // 정상
                     alert("글쓰기 완료가 되었습니다.")
                     location.href="/";
                    }).fail(function(error){
                    // 실패
                    alert(JSON.stringify(error));
            });
			
		},


		deleteById: function(){
		        let  id =$("#id").text();

        			$.ajax({
        				type: "DELETE",
        				url: "/api/board/"+id,
        				dataType: "json", // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
                        contentType: "application/json; charset=utf-8"// body데이터가 어떤 타입인지(MIME)
        			}).done(function(resp){
                             // 정상
                             alert("글쓰기 삭제가 되었습니다.")
                             location.href="/";
                            }).fail(function(error){
                            // 실패
                            alert(JSON.stringify(error));
                    });

        		},



        		update: function(){

                        let id = $("#id").val();


                			let data = {
                					title: $("#title").val(),
                					content: $("#content").val()
                			};

                			$.ajax({
                				type: "PUT",
                				url: "/api/board/"+id,
                				data: JSON.stringify(data), // http body데이터
                				contentType: "application/json; charset=utf-8",// body데이터가 어떤 타입인지(MIME)
                				dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
                			}).done(function(resp){
                                     // 정상
                                     alert("글수정 완료가 되었습니다.")
                                     location.href="/";
                                    }).fail(function(error){
                                    // 실패
                                    alert(JSON.stringify(error));
                            });

                		},

                		replySave: function(){

                        		let data = {
                        		        userId : $("#userId").val(),
                        		        boardId : $("#boardId").val(),
                        				content: $("#reply-content").val()
                        		};
                        		let boardId = $("#boardId").val();


                                console.log(data);

                        			$.ajax({
                        				type: "POST",
                        				url: `/api/board/${data.boardId}/reply`,
                        				data: JSON.stringify(data), // http body데이터
                        				contentType: "application/json; charset=utf-8",// body데이터가 어떤 타입인지(MIME)
                        				dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경
                        			}).done(function(resp){
                                             // 정상
                                             alert("댓글 작성이 완료가 되었습니다.")
                                             location.href=`/board/${boardId}`;
                                            }).fail(function(error){
                                            // 실패
                                            alert(JSON.stringify(error));
                                    });

                       		},


                       		replyDelete: function(boardId,replyId){
                                    		$.ajax({
                                    			type: "DELETE",
                                    			url: `/api/reply/${boardId}/reply/${replyId}`,
                                    			dataType: "json", // 요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경

                                    		}).done(function(resp){
                                                         // 정상
                                                         alert("댓글이 삭제가 되었습니다.")
                                                           location.href=`/board/${boardId}`;
                                                        }).fail(function(error){
                                                        // 실패
                                                        alert(JSON.stringify(error));
                                                });

                                    		},



}

index.init();
