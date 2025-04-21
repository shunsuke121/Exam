<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
  <c:param name="title">学生登録完了</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">

      <!-- 見出し -->
      <h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">学生登録完了</h2>

      <!-- 成功メッセージ -->
      <div class="mb-4 mx-4 px-3 py-2 bg-success bg-opacity-50 text-dark text-center rounded">
        学生の登録が完了しました。
      </div>

      <!-- リンク形式で横並び -->
      <div class="px-4 mb-3">
        <a href="StudentCreate.action" class="me-3">戻る</a>
        <a href="StudentList.action">学生一覧</a>
      </div>

    </section>
  </c:param>
</c:import>
