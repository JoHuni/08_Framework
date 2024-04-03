/* 회원 정보 수정 */
const updateInfo = document.querySelector("#updateInfo");

// #updateInfo 요소가 존재할 때만 수행
if(updateInfo != null){
    // form 제출 시
    updateInfo.addEventListener("submit", e => {
        const memberNickname = document.querySelector("#memberNickname");
        const memberTel = document.querySelector("#memberTel");
        const memberAddress  = document.querySelectorAll("[name='memberAddress']");

        // 닉네임 유효성 검사
        if(memberNickname.value.trim().length == 0){
            alert("닉네임을 입력해주세요.");
            e.preventDefault(); // 제출 막기
            return;
        }

        let regExp = /^[가-힣\w\d]{2,10}$/;
        if(!regExp.test(memberNickname.value)){ // 정규식에 맞지 않음
            alert("닉네임이 유효하지 않습니다.");
            e.preventDefault(); // 제출 막기
            return;
        }

        // 중복 검사는 나중에 추가 예정....................
        // (테스트 시 닉네임 중복 안 되게 조심)
        
        // 전화번호 유효성 검사
        if(memberTel.value.trim().length == 0){
            alert("전화번호를 입력해주세요.");
            e.preventDefault(); // 제출 막기
            return;
        }

        regExp = /01[016789][^0][0-9]{2,3}[0-9]{3,4}/;
        if(!regExp.test(memberTel.value)){ // 정규식에 맞지 않음
            alert("전화번호가 유효하지 않습니다.");
            e.preventDefault(); // 제출 막기
            return;
        }

        // 주소 유효성 검사
        // 입력을 안 하면 전부 안 해야 되고
        // 입력하면 전부 해야 된다
        const addr0 = memberAddress[0].value.trim().length == 0;
        const addr1 = memberAddress[1].value.trim().length == 0;
        const addr2 = memberAddress[2].value.trim().length == 0;

        // 모두 true인 경우 true 저장
        const result1 = addr0 && addr1 && addr2;

        // 모두 false 일 때만 true 저장
        const result2 = !(addr0 || addr1 || addr2);

        if(!(result1 || result2)){
            alert("주소를 모두 작성 또는 미작성 해주세요.");
            e.preventDefault();
        }
    })
}


/* 다음 주소 API 활용 */
function execDaumPostcode() {
    new daum.Postcode({
      oncomplete: function (data) {
        // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
  
        // 각 주소의 노출 규칙에 따라 주소를 조합한다.
        // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
        var addr = ''; // 주소 변수
       
  
        //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
        if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
          addr = data.roadAddress;
        } else { // 사용자가 지번 주소를 선택했을 경우(J)
          addr = data.jibunAddress;
        }
  
        // 우편번호와 주소 정보를 해당 필드에 넣는다.
        document.getElementById('postcode').value = data.zonecode;
        document.getElementById("address").value = addr;
        // 커서를 상세주소 필드로 이동한다.
        document.getElementById("detailAddress").focus();
      }
    }).open();
  }
  
  
  /* 주소 검색 버튼 클릭 시 */
  document.querySelector("#searchAddress").addEventListener("click", execDaumPostcode);