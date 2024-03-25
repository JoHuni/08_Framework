const totalCount = document.querySelector("#totalCount");
const completeCount = document.querySelector("#completeCount");
const reloadBtn = document.querySelector("#reloadBtn");

const todoTitle = document.querySelector("#todoTitle");
const todoContent = document.querySelector("#todoContent");
const addBtn = document.querySelector("#addBtn");

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
        }
        else{
            alert("추가 실패...");
        }
    })
})



// js 파일에 함수 호출 코드 작성 -> 페이지 로딩 시 바로 실행
getTotalCount();
getCompleteCount();

