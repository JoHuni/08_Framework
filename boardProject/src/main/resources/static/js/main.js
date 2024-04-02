/* 쿠키에서 key가 일치하는 value 얻어오기 함수 */

// 쿠키는 "K=V; K=V;" 형식

// 배열.map(함수) : 배열의 각 요소를 이용해 함수 수행 후 결과 값으로 새로운 배열을 만들어 반환
const getCookie = (key) => {
    const cookies = document.cookie; // "K=V; K=V;"

    // cookies 문자열을 배열 형태로 변환
    const cookieList = cookies.split("; ").map(el => el.split("=")); // ["K=V", "K=V"]
    console.log(cookieList); 

    // 배열 -> 객체로 변환(그래야 다루기 쉬움)
    const obj = {};

    for(let i=0; i<cookieList.length; i++){
        const k = cookieList[i][0]; // key
        const v = cookieList[i][1]; // value
        obj[k] = v; // 객체에 추가
    }
    
    console.log("obj", obj);

    return obj[key]; // 매개변수로 전달 받은 key와
                     // obj 객체에 저장된 키가 일치하는 요소의 값 반환
}
const loginEmail = document.querySelector("#loginForm input[name='memberEmail']");
const loginPw = document.querySelector("#loginForm input[name='memberPw']");

// 로그인 안 된 상태인 경우에만 수행
if(loginEmail != null){ // 로그인 창의 이메일 입력 부분이 있을 때
    // 쿠키 중 key 값이 "saveId"인 요소의 value 얻어오기
    const saveId = getCookie("saveId"); // undefined 또는 이메일

    // saveId 값이 있을 경우
    if(saveId != undefined){
        loginEmail.value = saveId; // 쿠키에서 얻어온 값을 대입

        document.querySelector("input[name='saveId']").checked = true;
    }
}

const loginForm = document.querySelector("#loginForm");

if(loginForm != null){
    loginForm.addEventListener("submit", e => {
        // if(loginEmail.value.trim().length. == 0 || loginPw.value.trim().length == 0){
        //     e.preventDefault();
        //     alert("제대로 입력");
        // }
        // if(loginEmail.value == "" || loginPw.value == ""){
        //     e.preventDefault();
        //     alert("아이디 또는 비밀번호를 제대로 입력해주세요.");
        // }

        if(loginEmail.value == ""){
            alert("아이디를 제대로 입력해주세요");
            e.preventDefault();
            loginEmail.focus();
        }
        else if(loginPw.value == ""){
            alert("비밀번호를 제대로 입력해주세요.");
            e.preventDefault();
            loginPw.focus();
        }
    });
}

const quickLoginBtns = document.querySelectorAll(".quick-login");

quickLoginBtns.forEach((item, index) => {
    // item : 현재 반복시 꺼내온 객체
    // index : 현재 반복 중인 인덱스

    item.addEventListener("click", e => {
        const email = item.innerText; // 버튼에 작성된 이메일 얻어오기
        
        location.href = "/member/quickLogin?memberEmail=" + email;
    });
});

/* 회원 목록 조회 (비동기) */

// 조회 버튼
const selectMemberList = document.querySelector("#selectMemberList");

// tbody
const memberList = document.querySelector("#memberList");

selectMemberList.addEventListener("click", () => {
    // 1) 비동기로 회원 목록 조회
    //    (포함될 회원 정보 : 회원 번호, 이메일, 닉네임, 탈퇴여부)
    fetch("/member/showMember")
    .then(resp => resp.json())
    .then(result =>{
        memberList.innerHTML = "";
        console.log(result);
        for(let memList of result){
            const tr = document.createElement("tr");
            console.log(memList);
            const arr = ['memberNo', 'memberEmail', 'memberNickname', 'memberDelFl'];

            for(let i of arr){
                const td = document.createElement("td");
                td.innerHTML = memList[i];
                tr.append(td);
            }
            memberList.append(tr);
        }
    })
    // 첫 번째 then(resp => resp.json()) ->
    // JSON Array -> JS 객체 배열로 뱐환 [{}, {}, {}, {}]

    // 2) 두 번째 then
    //    tbody에 이미 작성되어 있던 내용(이전에 조회한 목록) 삭제

    // 3) 두 번째 then
    //    조회된 JS 객체 배열을 이용해
    //    tbody에 들어갈 요소를 만들고 값 세팅 후 추가

})
