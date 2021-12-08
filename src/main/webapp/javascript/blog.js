function addNewBlog() {
    //lay du lieu
    let title = $('#title').val();
    let content = $('#content').val();
    let author = $('#author').val();
    let image = $('#images').val();
    let category = $('#category').val();
    let newBlog = {
        title: title,
        content: content,
        author: author,
        image: image,
        category: {
            id: category
        }
    };
    //goi ajax
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        data: JSON.stringify(newBlog),
        enctype: 'multipart/form-data',
        //ten Api
        url: "/api/blog",
        success: successHandler
    })
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();
}

function getBlog(blog) {
    return `<tr>
                        <td >${blog.title}</td>
                        <td >${blog.content}</td>
                        <td >${blog.author}</td>
                        <td><img src= "/image/${blog.image}"></td>
                        <td >${blog.category?.name}</td>` +
        `<td><button value="${blog.id}" onclick="deleteBlog(this)">Xoa</button></td>` +
        ` <td><button value="${blog.id}" onclick="openForm(this)">Edit</button></td>`

}

function successHandler() {
    $.ajax({
        type: "GET",
        //ten Api
        url: "/api/blog",
        success: function (data) {
            let content = '   <tr>\n' +
                '        <th>Title</th>\n' +
                '        <th>Content</th>\n' +
                '        <th>Author</th>\n' +
                '        <th>Image</th>\n' +
                '        <th>Category</th>\n' +
                '        <th>Delete</th>\n' +
                '        <th>Edit</th>\n' +
                '    </tr>';
            for (let i = 0; i < data.length; i++) {
                content += getBlog(data[i]);
            }
            document.getElementById('blogList').innerHTML = content;
        }
    })
}

//delete
function deleteBlog(a) {
    let id = a.getAttribute("value");
    $.ajax({
        type: "DELETE",
        url: "/api/blog/" + id,
        success: successHandler
    })
    event.preventDefault();
}

//hiển thị ảnh khi add
function ImagesFileAsURL() {
    let fileSelected = document.getElementById('images').files;
    if (fileSelected.length > 0) {
        let fileToUpload = fileSelected[0];
        let fileReader = new FileReader();
        fileReader.onload = function (fileLoaderEvent) {
            let srcData = fileLoaderEvent.target.result;
            let newImage = document.createElement('img');
            newImage.src = srcData;
            document.getElementById('displayImg').innerHTML = newImage.outerHTML;
        }
        fileReader.readAsDataURL(fileToUpload)
    }
}

//form edit
function openForm(a) {
    // document.getElementById("edit-blog").style.display = "block";
    let id = a.getAttribute("value");
    $("#edit-blog").show()
    $.ajax({
        type: "GET",
        url: "/api/blog/" + id,
        success: function (blog) {
            $('#idEdit').val(blog.id);
            $('#titleEdit').val(blog.title);
            $('#contentEdit').val(blog.content);
            $('#authorEdit').val(blog.author);
            $('#imagesEdit').val(blog.image);
            $('#categoryEdit').val(blog.category?.name);
        }
    })
    event.preventDefault();
}

function closeForm() {
    document.getElementById("edit-blog").style.display = "none";
}


function editBlog() {
    //lay du lieu
    let id = $('#idEdit').val();
    let title = $('#titleEdit').val();
    let content = $('#contentEdit').val();
    let author = $('#authorEdit').val();
    let image = $('#imagesEdit').val();
    let category = $('#categoryEdit').val();
    let newBlog = {
        id: id,
        title: title,
        content: content,
        author: author,
        image: image,
        category: {
            id: category
        }
    };
    //goi ajax
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "PUT",
        data: JSON.stringify(newBlog),
        //ten Api
        url: "/api/blog/" + id,
        success: successHandler
    })
    //chặn sự kiện mặc định của thẻ
    event.preventDefault();
}

//search
function FormSearchTitle() {
    let title = $('#titles').val();
    $.ajax({
        type: "GET",
        url: "/api/blog/title/" + title,
        success: function (blog) {
            let content = '   <tr>\n' +
                '        <th>Title</th>\n' +
                '        <th>Content</th>\n' +
                '        <th>Author</th>\n' +
                '        <th>Image</th>\n' +
                '        <th>Category</th>\n' +
                '        <th>Delete</th>\n' +
                '        <th>Edit</th>\n' +
                '    </tr>';
            for (let i = 0; i < blog.length; i++) {
                content += getBlog(blog[i]);
            }
            document.getElementById('blogList').innerHTML = content;
        }
    })
}
