<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
  <c:param name="title">学生登録</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生登録</h2>

      <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">${error}</div>
      </c:if>

      <form action="StudentCreateExecute.action" method="post" class="px-4">
        <!-- 入学年度 -->
        <div class="mb-3">
          <label for="ent_year" class="form-label">入学年度</label>
          <select name="ent_year" id="ent_year" class="form-select">
            <c:forEach var="year" items="${yearList}">
              <option value="${year}" ${year == ent_year ? 'selected' : ''}>${year}</option>
            </c:forEach>
          </select>
        </div>

        <!-- 学生番号 -->
        <div class="mb-3">
          <label for="no" class="form-label">学生番号</label>
          <input type="text" name="no" id="no" value="${no}" class="form-control" maxlength="10" required>
        </div>

        <!-- 氏名 -->
        <div class="mb-3">
          <label for="name" class="form-label">氏名</label>
          <input type="text" name="name" id="name" value="${name}" class="form-control" maxlength="30" required>
        </div>

        <!-- クラス -->
        <div class="mb-3">
          <label for="class_num" class="form-label">クラス</label>
          <select name="class_num" id="class_num" class="form-select">
            <c:forEach var="cls" items="${classList}">
              <option value="${cls.classNum}" ${cls.classNum == class_num ? 'selected' : ''}>${cls.classNum}</option>
            </c:forEach>
          </select>
        </div>

        <!-- 在学中チェック -->
        <div class="form-check mb-3">
          <input class="form-check-input" type="checkbox" name="is_attend" id="is_attend" value="true" checked>
          <label class="form-check-label" for="is_attend">在学中</label>
        </div>

        <!-- ボタン -->
<!-- 略：formタグの中略 -->

<!-- ボタン -->
<!-- ボタン -->
		<div class="px-1">
  		<button type="submit" class="btn btn-secondary mb-2">登録して終了</button><br>
  		<a href="StudentList.action">戻る</a>
		</div>


      </form>
    </section>
  </c:param>
</c:import>
