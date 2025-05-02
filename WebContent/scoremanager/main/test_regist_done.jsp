<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績登録完了</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-bold bg-secondary bg-opacity-10 py-2 px-4">成績登録完了</h2>
      <div class="mb-4 mx-4 px-3 py-2 bg-success bg-opacity-50 text-dark text-center rounded">
        登録が完了しました。
      </div>
      <div class="px-4">
        <a href="TestRegist.action" class="me-3">戻る</a>
        <a href="TestList.action" class="me-3">成績参照</a>
      </div>
    </section>
  </c:param>
</c:import>
