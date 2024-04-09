const showBookList = document.querySelector("#showBookList");
const bookList = document.querySelector("#bookList");

showBookList.addEventListener("click", () => {
    fetch("book/showBook")
    .then(resp => resp.json())
    .then(result => {
        bookList.innerHTML = "";
        console.log(result);
        for(let bookList2 of result){
            const tr = document.createElement("tr");

            const arr = ['bookNo', 'bookTitle', 'bookWriter', 'bookPrice', 'regDate'];

            for(let i of arr){
                const td = document.createElement("td");
                td.innerHTML = bookList2[i];
                tr.append(td);
            }
            bookList.append(tr);
        }
    });
});