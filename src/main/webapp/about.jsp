<%@ page pageEncoding="UTF-8" contentType="text/html;charest=UTF8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="base_path" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title></title>
    <style>
        html{
            height: 100%;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="${base_path}/css/mainRiver.css"/>
    <link rel="stylesheet" type="text/css" href="${base_path}/css/webStyle.css"/>

    <script type="text/javascript" src="${base_path}/js/jquery-1.8.3.min.js"></script>
    <style type="text/css">
        .wenImg{
            position: relative;
            top:-3px;
            cursor: pointer;
        }
        .tishi{
            font-size: 10px;
        }
        .footer{
            position:fixed;
            bottom:0;
        }
    </style>
    <script type="text/javascript">

        $(function () {
            $(".tishi").hide();

            $("#up_file").change(function () {
                checkType();
            })
        })

        function checkType() {

            //检测上传文件的类型
            var imgName = document.all.up_file.value;
            var ext, idx;
            if (imgName == '') {
                document.all.submit_upload.disabled = true;
                alert("请选择需要上传的文件!");
                return;
            } else {
                idx = imgName.lastIndexOf(".");
                if (idx != -1) {
                    ext = imgName.substr(idx + 1).toUpperCase();
                    ext = ext.toLowerCase();
                    // alert("ext="+ext);
                    if (ext != 'doc' && ext != 'docx' && ext != 'txt') {
                        document.all.submit_upload.disabled = true;
                        $("#up_file").val("");
                        alert("只能上传.doc，.ocx，.txt类型的文件!");
                        return;
                    }
                } else {
                    document.all.submit_upload.disabled = true;
                    alert("只能上传.doc，.ocx，.txt类型的文件!");
                    return;
                }
            }
        }

        function showFont(){
            $(".tishi").show();
        }
    </script>
</head>
<body>

<jsp:include page="top.jsp"/>
<div class="section_text" style=" height: 379px;margin-top: 100px;width: 80%;align-content: center">
    <h1>&nbsp;&nbsp;&nbsp;&nbsp;${requestScope.contents[0]}</h1>
</div>
<div class="footer">
    <jsp:include page="footer.jsp"/>
</div>
</body>
</html>