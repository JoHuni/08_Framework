/* 좋아요 버튼(하트) 클릭 시 비동기로 좋아요 INSERT/DELETE */

// Thymeleaf는 아래와 같이 이루어져 있음
// - html 코드(+css, js)
// - th:코드(java), Spring EL
// Thymeleaf는 html 코드, th:코드(java)로 이루어져 있음

// Thymeleaf 코드 해석 순서
// 1. th: 코드(java) + Spring EL
// 2. html 코드 (+ css, js)

// 1) 로그인한 회원 번호
//    --> session에서 얻어오기
//        session은 서버에서 관리 -> JS로 바로 얻어올 수 있는 방법 없음
//        -> 타임리프를 이용해서 가져옴

// 2) 현재 게시글 번호

// 3) 좋아요 여부

// 1. #boardLike가 클릭되었을 때
const boardLike = document.querySelector("#boardLike");

boardLike.addEventListener("click", e => {
    // 2. 로그인 상태가 아닌 경우
    if(loginMemberNo == null){
        alert("로그인 후 이용해주세요.");
        return;
    }

    // 3. 준비된 세 개의 변수를 객체로 저장
    const obj = {
        "memberNo" : loginMemberNo,
        "boardNo" : boardNo,
        "likeCheck" : likeCheck
    };

    fetch("/board/like", {
        method : "POST",
        headers : {"Content-Type" : "application/json"},
        body : JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(count => {
        if(count == -1){
            console.log('좋아요 처리 실패');
            return;
        }
        // result == 첫 번째 then의 파싱되어 반환된 값
        // console.log("result : ", result);

        // 5. likeCheck 값 0 <-> 1
        // (왜? 클릭 될 때마다 INSERT/DELETE 동작을 번갈아가면서 할 수 있음)

        likeCheck = likeCheck == 0 ? 1 : 0;
        // 6. 하트를 채웠다/비웠다
        e.target.classList.toggle("fa-regular");
        e.target.classList.toggle("fa-solid");

        // 7. 게시글 좋아요 수 업데이트
        e.target.nextElementSibling.innerText = count;
    })
});


const deleteBtn = document.querySelector("#deleteBtn")

deleteBtn.addEventListener("click", () => {
    if(confirm("삭제하시겠습니까?")){
        const deleteForm = document.querySelector("#deleteForm")
        deleteForm.submit();
    }
    else{
        alert("삭제 취소");
        return;
    }
});
