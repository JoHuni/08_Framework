const bookList = document.querySelector("#bookList");
const searchInput = document.querySelector("#searchInput");
const searchBtn = document.querySelector("#searchBtn");


searchBtn.addEventListener("click", () => {
    const inputVal = searchInput.value;

    if(inputVal.trim().length == 0){
        alert("책 제목을 입력해주세요.");
        return;
    }
    fetch(`/book/searchBook?searchInput=${inputVal}`,{
        method : "GET"
    })
    .then(resp => resp.json())
    .then(result => {
        bookList.innerHTML = "";

        for (let bookList2 of result) {
            const tr = document.createElement("tr");
        
            const arr = ['bookNo', 'bookTitle', 'bookWriter', 'bookPrice', 'regDate'];
        
            for (let i of arr) {
                const td = document.createElement("td");
                td.innerHTML = bookList2[i];
                tr.append(td);
            }
        
            const alterButtonTd = document.createElement("td");
            const alterButton = document.createElement("button");
            alterButton.innerText = "수정 버튼";
            alterButtonTd.append(alterButton);
            tr.append(alterButtonTd);
            
            alterButton.addEventListener("click", e => {
                const bookNo = bookList2.bookNo;
                let bookPrice = prompt("수정할 가격 입력");

                if(bookPrice == null){
                    alert("입력이 취소되었습니다.");
                }

                if(bookPrice.valueOf().trim().length == 0 || isNaN(bookPrice) || bookPrice == 0 || bookPrice % 1 !== 0){
                    alert("올바른 값을 입력해주세요.");
                    return;
                }
                else if(!isNaN(bookPrice)){
                    const obj = {"bookNo" : bookNo, "bookPrice" : bookPrice}
    
                    fetch("/book/updatePrice",{
                        method : "PUT",
                        headers : {"Content-Type" : "application/json"},
                        body : JSON.stringify(obj)
                    })
                    .then(resp => resp.text())
                    .then(result => {
                        if(result > 0 ){
                            alert("수정되었습니다.");
                            location.reload();
                        }
                        else{
                            console.log(result)
                            alert("수정 실패 ㅠ");
                        }
                    })
                }
            });
            const deleteButtonTd = document.createElement("td");
            const deleteButton = document.createElement("button");
            deleteButton.innerText = "삭제 버튼";
            deleteButtonTd.append(deleteButton);
            tr.append(deleteButtonTd);

            deleteButton.addEventListener("click", () => {
                const yesorNo = confirm("삭제하시겠습니까?");
                if(yesorNo == true){
                    const bookNo = bookList2.bookNo;
                    console.log(bookNo);
                    fetch("/book/deleteBook",{
                        method : "POST",
                        headers : {"Content-Type" : "application/json"},
                        body : bookNo
                    })
                    .then(resp => resp.text())
                    .then(result => {
                        if(result > 0 ){
                            alert("삭제되었습니다.");
                            location.reload();
                        }
                        else{
                            alert("삭제 실패 ㅠ");
                        }
                    })
                }
                else{
                    alert("삭제 취소");
                }
            });     
            bookList.append(tr);
        }
        
    });
});

