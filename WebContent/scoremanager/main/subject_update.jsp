<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">科目情報変更</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">

      <!-- ① 見出し -->
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

      <!-- エラーメッセージ -->
      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>

      <!-- 科目情報変更フォーム -->
      <form action="SubjectUpdateExecute.action" method="post" class="px-4">

        <!-- ②③ 科目コード（表示のみ） -->
        <div class="mb-3">
          <label class="form-label fw-bold">科目コード</label>
          <p class="form-control-plaintext">${subject.cd}</p>
          <input type="hidden" name="cd" value="${subject.cd}">
        </div>

        <!-- ④⑤ 科目名 -->
        <div class="mb-3">
          <label for="name" class="form-label fw-bold">科目名</label>
          <input type="text" name="name" id="name" value="${subject.name}" class="form-control" required>
        </div>

        <!-- ⑥ 変更ボタン -->
        <div class="px-1">
          <button type="submit" class="btn btn-primary mb-2">変更</button><br>
          <!-- ⑦ 戻るリンク -->
          <a href="SubjectList.action">戻る</a>
        </div>

      </form>
    </section>
  </c:param>
</c:import>
