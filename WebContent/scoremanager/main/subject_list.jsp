<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">科目管理</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="me-3">

      <!-- ① タイトル -->
      <h2 class="mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目管理</h2>

      <!-- ② 新規登録リンク -->
      <div class="text-end px-4 mb-2">
        <a href="SubjectCreate.action">新規登録</a>
      </div>

      <!-- ③ 一覧表示 -->
      <table class="table table-hover mx-4">
        <tr>
          <!-- ④ ⑤ タイトル行 -->
          <th>科目コード</th>
          <th>科目名</th>
          <th></th> <!-- 変更用 -->
          <th></th> <!-- 削除用 -->
        </tr>

        <!-- ⑥ ⑦ ⑧ ⑨ データ表示 -->
        <c:forEach var="subject" items="${subjectList}">
          <tr>
            <td>${subject.cd}</td> <!-- ⑥ 科目コード -->
            <td>${subject.name}</td> <!-- ⑦ 科目名 -->
            <td><a href="SubjectUpdate.action?cd=${subject.cd}">変更</a></td> <!-- ⑧ 変更リンク -->
            <td><a href="SubjectDelete.action?cd=${subject.cd}">削除</a></td> <!-- ⑨ 削除リンク -->
          </tr>
        </c:forEach>
      </table>

      <!-- 科目が存在しない場合 -->
      <c:if test="${empty subjectList}">
        <div class="px-4">科目情報が存在しません。</div>
      </c:if>

    </section>
  </c:param>
</c:import>
s