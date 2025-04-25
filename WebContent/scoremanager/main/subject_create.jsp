<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">科目情報登録</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">

      <!-- ① 見出し -->
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>

      <!-- エラーメッセージ -->
      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>

      <!-- 登録フォーム -->
      <form action="SubjectCreateExecute.action" method="post" class="px-4">

        <!-- ②③ 科目コード -->
        <div class="mb-3">
          <label for="cd" class="form-label fw-bold">科目コード</label>
          <input type="text" name="cd" id="cd" value="${cd}" class="form-control" placeholder="科目コードを入力してください" required>
        </div>

        <!-- ④⑤ 科目名 -->
        <div class="mb-3">
          <label for="name" class="form-label fw-bold">科目名</label>
          <input type="text" name="name" id="name" value="${name}" class="form-control" placeholder="科目名を入力してください" required>
        </div>

        <!-- ⑥ ボタン -->
        <div class="px-1">
          <button type="submit" class="btn btn-primary mb-2">登録</button><br>
          <!-- ⑦ 戻るリンク -->
          <a href="SubjectList.action">戻る</a>
        </div>

      </form>
    </section>
  </c:param>
</c:import>
