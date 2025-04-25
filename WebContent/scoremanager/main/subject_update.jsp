<!DOCTYPE html>
<html>
<head>
  <title>学生別成績一覧</title>
</head>
<body>
  <h2>学生別成績一覧</h2>

  <c:if test="${empty table}">
    <p>科目情報を選択または学生情報を入力して検索ボタンをクリックしてください。</p>
  </c:if>

  <c:if test="${not empty table}">
    <div>
      <p>氏名：${student.name}（${student.no}）</p>
      <table border="1">
        <tr>
          <th>科目コード</th>
          <th>科目名</th>
          <th>回数</th>
          <th>点数</th>
        </tr>
        <c:forEach var="row" items="${table}">
          <tr>
            <td>${row.subjectCd}</td>
            <td>${row.subjectName}</td>
            <td>${row.count}</td>
            <td>${row.point}</td>
          </tr>
        </c:forEach>
      </table>
    </div>
  </c:if>
</body>
</html>
