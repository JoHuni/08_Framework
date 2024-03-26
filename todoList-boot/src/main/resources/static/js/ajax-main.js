const totalCount = document.querySelector("#totalCount");
const completeCount = document.querySelector("#completeCount");
const reloadBtn = document.querySelector("#reloadBtn");

const todoTitle = document.querySelector("#todoTitle");
const todoContent = document.querySelector("#todoContent");
const addBtn = document.querySelector("#addBtn");

const tbody = document.querySelector("#tbody");

const popupLayer = document.querySelector("#popupLayer");
const popupTodoNo = document.querySelector("#popupTodoNo");
const popupTodoTitle = document.querySelector("#popupTodoTitle");
const popupComplete = document.querySelector("#popupComplete");
const popupRegDate = document.querySelector("#popupRegDate");
const popupTodoContent = document.querySelector("#popupTodoContent");

const popupClose = document.querySelector("#popupClose");

const deleteBtn = document.querySelector("#deleteBtn");

const completeBtn = document.querySelector("#completeBtn");


const updateLayer = document.querySelector("#updateLayer");
const updateView = document.querySelector("#updateView");

const updateTitle = document.querySelector("#updateTitle");
const updateContent = document.querySelector("#updateContent");

const updateBtn = document.querySelector("#updateBtn");
const cancelBtn = document.querySelector("#cancelBtn");

// 전체 Todo 개수 조회 및 출력하는 함수
function getTotalCount(){ // 함수 정의

    // 비동기로 서버(DB)에서 전체 Todo 개수 조회하는
    // fetch() API 코드 작성
    // (fetch : 가지고 오다)

    fetch("/ajax/totalCount") // 비동기 요청 수행 -> Promise객체 반환
    .then(response =>{
        
        // promise : 비동기 요청에 대한 응답이 담긴 객체

        // response.text() : 응답 데이터를 문자열/숫자 형태로 변환한 결과를 가지는 Promise 객체 반환

        console.log(response);

        return response.text();
    })
    // 두 번째 then의 매개변수 == 첫 번째 then에서 반환된 Promise 객체의 promiseResult 값
    .then(result =>{
        // 
        console.log("result : ", result);
        totalCount.innerText = result;
    });
}

function getCompleteCount(){

    // 비동기로 요청해서 결과 데이터 가져오기
    fetch("/ajax/completeCount")
    .then(response => {
        return response.text()
    })
    .then(result => {
        completeCount.innerText = result;
    })
}

reloadBtn.addEventListener("click", () => {
    getTotalCount();    // 비동기로 전체 할 일 개수 조회
    getCompleteCount(); // 비동기로 전체 완료된 할 일 개 수 조회
});

addBtn.addEventListener("click", ()=>{
    // 비동기로 할 일 추가하는 fetch() API 코드 작성
    // - 요청 주소 : "/ajax/add"
    // - 데이터 전달 방식 : "POST" 방식

    // 파라미터를 저장한 JS 객체
    const param = {
        "todoTitle" : todoTitle.value,
        "todoContent" : todoContent.value
    }
    fetch("/ajax/add", {
        // K : V
        method : "POST", // POST 방식 요청
        headers : {"Content-Type" : "application/json"}, // 요청 데이터의 형식 JSON으로 지정 
        body : JSON.stringify(param) // param 객체를 JSON으로 변환
    })
    .then(resp => resp.text() ) // 반환된 값을 text로 변환
    // 첫 번쨰 then에서 반환된 값 중 변환된 text를 temp에 저장
    .then(temp => {
        if(temp > 0){
            alert("추가 성공");

            // 추가 성공한 제목, 내용 지우기
            todoTitle.value = "";
            todoContent.value = "";

            // 할 일이 추가되었기 떄문에 전체 Todo 개수 다시 조회
            getTotalCount();

            selectTodoList();
        }
        else{
            alert("추가 실패...");
        }
    })
})

// 비동기(ajax)로 할 일 상세조회 하는 함수
const selectTodo = (url) => {

    // 매개변수 url == "/ajax/detail?todoNo=10" 형태의 문자열

    // response.json() :
    // - 응답 데이터가 JSON인 경우
    // 이를 자동으로 Object 형태로 반환하는 메서드
    // == JSON.parse(JSON 데이터)
    fetch(url)
    .then(response => response.json())
    .then(todo => {
        // 매개변수 todo : 
        // 서버 응답(JSON)이 Object로 변환된 값 (첫 번째 then 반환 결과)

        console.log(todo);

        popupTodoNo.innerText = todo.todoNo;
        popupTodoTitle.innerText = todo.todoTitle;
        popupComplete.innerText = todo.complete;
        popupRegDate.innerText = todo.regDate;
        popupTodoContent.innerText = todo.todoContent;
        
        // popupLayer 보이게 하기
        // 요소.classList.toggle("클래스명")
        // 클래스가 있으면 제거, 없으면 추가

        // popup layer 보이게하기
        popupLayer.classList.remove("popup-hidden");

        // update layer가 혹시라도 열려있으면 숨기기
        updateLayer.classList.add("popup-hidden")
    });
};

// 비동기(ajax)로 할 일 목록을 조회하는 함수
const selectTodoList = () => {
    fetch("/ajax/selectList")
    .then(response => response.text()) // 응답 데이터를 text로 파싱
    .then(result => {
        console.log(result)
        console.log(typeof result)
        // JSON은 객체가 아니라 문자열이다
        // 문자열은 가공은 할 수 있는데 힘들다...
        // -> JSON.parse(JSON데이터)

        // JSON.parse(JSON데이터) : string -> object
        // - string 형태의 JSON 데이터를 JS Object 타입으로 변환
        
        // JSON.stringify(JS Object) : object - > string
        // - JS Object 타입을 string 형태의 JSON 데이터로 변환

        const todoList = JSON.parse(result);
        console.log(todoList); // 객체 배열 형태
        console.log(typeof todoList);

        /* 기존에 출력 되어있떤 할 일 목록을 모두 삭제 */
        tbody.innerHTML = "";

        // #tbody에 tr/td 요소를 생성해서 내용 추가
        for(let todo of todoList){
            const tr = document.createElement("tr");
            
            const arr = ['todoNo', 'todoTitle', 'complete', 'regDate'];
            for(let key of arr){
                const td = document.createElement("td");
                if(key === 'todoTitle'){
                    const a = document.createElement("a");
                    a.innerText = todo[key];

                    a.href = "/ajax/detail?todoNo=" + todo.todoNo;
                    td.append(a);
                    tr.append(td);
                    
                    // a태그 클릭 시 기본 이벤트(페이지 이동) 막기
                    a.addEventListener("click", e => {
                        e.preventDefault();
                        selectTodo(e.target.href);
                        // 할 일 상세 조회 비동기 요청
                    });

                    continue;
                }
                td.innerText = todo[key];
                tr.append(td);
            }
            tbody.append(tr);
        }
    })
};

popupClose.addEventListener("click", () => {
    popupLayer.classList.add("popup-hidden");
});

deleteBtn.addEventListener("click", () => {
    if(!confirm("정말 삭제하시겠습니까?")){
        return;
    }

    // 삭제할 할 일 번호(pk) 얻어오기

    const todoNo = popupTodoNo.innerText; // #popupTodoNo 내용 얻어오기

    fetch("/ajax/delete", {
        method : "DELETE",
        // 데이터 하나를 전달해도 application/json 작성
        headers : {"Content-Type" : "application/json"},
        body : todoNo // todoNo 값을 body에 담아서 전달
                      // -> @RequestBody로 꺼냄
    })

    .then(response => response.text()) //요쳥 결과를 text 형태로 변환
    .then(result => {
        if(result > 0){
            alert("삭제 되었습니다.");

            // 상세 조회 창 닫기
            popupLayer.classList.add("popup-hidden");

            // 전체, 완료된 할 일 개수 다시 조회
            // + 할 일 목록 다시 조회
            getTotalCount();
            getCompleteCount();
            selectTodoList();
        }
        else{
            alert("삭제 실패...");
        }
    })
})
completeBtn.addEventListener("click", () => {
    const todoNo = popupTodoNo.innerText;
    const complete = popupComplete.innerText === 'Y' ? 'N' : 'Y';
    const obj = {"todoNo" : todoNo , "complete" : complete};
    fetch("/ajax/changeComplete", {
        method : 'PUT',
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(response => response.json())
    .then(result => {
        if(result > 0){
            popupComplete.innerText = complete;

            const count = Number(completeCount.innerText);
            if(complete === 'Y'){
                completeCount.innerText = count + 1;
            }
            else{
                completeCount.innerText = count - 1;           
            }

            selectTodoList();
        }
        else{
            alert("변경 실패...");
        }
    })
})

updateView.addEventListener("click", () => {
    popupLayer.classList.add("popup-hidden");
    updateLayer.classList.remove("popup-hidden");

    updateTitle.value = popupTodoTitle.innerHTML;
    updateContent.value = popupTodoContent.innerHTML.replaceAll("<br>", "\n");
    // HTML 화면에서는 줄 바꿈이 <br>로 인식되고 있는데,
    // textarea에서는 \n으로 바꿔야 줄바꿈 인식 됨


    // 수정 레이어 -> 수정 버튼에 data-todo-no 속성 추가
});






// 상세조회에서 수정 버튼(#updateView) 클릭 시
// updateView.addEventListener("click", () => {

//     // 기존 팝업 레이어는 숨기고
//     popupLayer.classList.add("popup-hidden");
  
//     // 수정 레이어 보이게
//     updateLayer.classList.remove("popup-hidden");
  
  
  
//     // 수정 레이어 보일 때
//     // 팝업 레이어에 작성된 제목, 내용 얻어와 세팅
//     updateTitle.value = popupTodoTitle.innerText;
  
//     updateContent.value 
//       = popupTodoContent.innerHTML.replaceAll("<br>", "\n");
//         // HTML 화면에서 줄 바꿈이 <br>로 인식되고 있는데
//         // textarea에서는 \n으로 바꿔야 줄 바꿈으로 인식된다!
  
//     // 수정 레이어 -> 수정 버튼에 data-todo-no 속성 추가
//     updateBtn.setAttribute("data-todo-no", popupTodoNo.innerText);
//   });





/* 수정 레이어 -> 수정 버튼(#updateBtn) 클릭 시 */





// updateBtn.addEventListener("click", e => {

//     // 서버로 전달해야되는 값을 객체로 묶어둠
//     const obj = {
//       "todoNo"      : e.target.dataset.todoNo,
//       "todoTitle"   : updateTitle.value,
//       "todoContent" : updateContent.value
//     };
  
//     fetch("/ajax/update", {
//         method : 'PUT',
//         headers : {"Content-Type" : "application/json"},
//         body : JSON.stringify(obj)
//     })
//     .then(resp => resp.text())
//     .then(result => {
//         console.log(result);
//         if(result > 0){ // 성공
//             alert("수정 성공");
      
//             // 수정 레이어 숨기기
//             updateLayer.classList.add("popup-hidden");
            
      
//             // selectTodo();
//             // -> 성능 개선
//             popupTodoTitle.innerText = updateTitle.value;
      
//             popupTodoContent.innerHTML 
//               = updateContent.value.replaceAll("\n", "<br>")
      
//             popupLayer.classList.remove("popup-hidden");
      
      
//             selectTodoList();
      
//             updateTitle.value = "";   // 남은 흔적 제거
//             updateContent.value = ""; // 남은 흔적 제거
//             updateBtn.removeAttribute("data-todo-no"); // 속성 제거
//         }
//         else{
//             alert("바이");
//         }
//     })
// });


updateBtn.addEventListener("click", () => {
    const todoNo = popupTodoNo.innerText;

    const title = updateTitle.value;
    const content = updateContent.value;

    const obj = {"todoNo" : todoNo , "todoTitle" : title, "todoContent" : content};
    

    fetch("/ajax/update",{
        method : 'PUT',
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(result => {
        console.log(result);
        if(result > 0){
            alert("수정 완료되었습니다.");
            updateLayer.classList.add("popup-hidden");
            popupLayer.classList.remove("popup-hidden");

            popupTodoTitle.innerHTML = updateTitle.value;
            popupTodoContent.innerHTML = updateContent.value.replaceAll("\n", "<br>")

            //  popupTodoTitle.innerText = updateTitle.value;
      
//             popupTodoContent.innerHTML 
//               = updateContent.value.replaceAll("\n", "<br>")
            selectTodoList();
        }
        else{
            alert("바이");
        }
    })
})







cancelBtn.addEventListener("click", () => {
    updateLayer.classList.add("popup-hidden");
    popupLayer.classList.remove("popup-hidden");
});



// js 파일에 함수 호출 코드 작성 -> 페이지 로딩 시 바로 실행
getTotalCount();
getCompleteCount();
selectTodoList();