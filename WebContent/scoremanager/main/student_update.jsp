<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">学生情報変更</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

      <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
      </c:if>

      <form action="StudentUpdateExecute.action" method="post" class="px-4">

        <!-- 学生番号（表示のみ） -->
        <div class="mb-3">
          <label class="form-label fw-bold">学生番号</label>
          <p class="form-control-plaintext">${student.no}</p>
          <input type="hidden" name="no" value="${student.no}">
        </div>

        <!-- 入学年度（表示のみ） -->
        <div class="mb-3">
          <label class="form-label fw-bold">入学年度</label>
          <p class="form-control-plaintext">${student.entYear}</p>
          <input type="hidden" name="ent_year" value="${student.entYear}">
        </div>

        <!-- 氏名（入力可） -->
        <div class="mb-3">
          <label for="name" class="form-label fw-bold">氏名</label>
          <input type="text" name="name" id="name" value="${student.name}" class="form-control" />
        </div>

        <!-- クラス（選択） -->
        <div class="mb-3">
          <label for="class_num" class="form-label fw-bold">クラス</label>
          <select name="class_num" id="class_num" class="form-select">
            <option value="">-----</option>
            <c:forEach var="c" items="${classList}">
              <option value="${c.classNum}" <c:if test="${student.classNum == c.classNum}">selected</c:if>>${c.classNum}</option>
            </c:forEach>
          </select>
        </div>

        <!-- 在学中（チェック） -->
        <div class="form-check mb-3">
          <input class="form-check-input" type="checkbox" id="is_attend" name="is_attend" <c:if test="${student.attend}">checked</c:if>>
          <label class="form-check-label fw-bold" for="is_attend">在学中</label>
        </div>

        <!-- ボタン -->
<!-- ボタン（左寄せ） -->
		<div class="px-1">
  		<button type="submit" class="btn btn-primary mb-2">変更</button><br>
  		<a href="StudentList.action">戻る</a>
		</div>

      </form>
    </section>
  </c:param>
</c:import>
