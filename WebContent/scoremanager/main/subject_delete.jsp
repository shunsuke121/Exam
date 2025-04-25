<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">科目情報削除</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">

      <!-- ① 見出し -->
      <h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>

      <!-- ② 削除確認メッセージ -->
      <p class="px-4">「${subject.name}（${subject.cd}）」を削除してよろしいですか？</p>

      <!-- 削除フォーム -->
      <form action="SubjectDeleteExecute.action" method="post" class="px-4">
        <!-- 削除対象のコードを渡す -->
        <input type="hidden" name="cd" value="${subject.cd}">

        <!-- ③ 削除ボタン -->
        <div class="px-1">
          <button type="submit" class="btn btn-danger mb-2">削除</button><br>
          <!-- ④ 戻るリンク -->
          <a href="SubjectList.action">戻る</a>
        </div>
      </form>

    </section>
  </c:param>
</c:import>
