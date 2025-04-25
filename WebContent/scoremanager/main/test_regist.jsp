<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績登録</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

      <!-- 検索フォーム -->
      <form action="TestRegist.action" method="get" class="px-4 mb-4">
        <div class="row g-3 align-items-center">
          <div class="col">
            <label for="ent_year" class="form-label">入学年度</label>
            <select name="ent_year" id="ent_year" class="form-select">
              <c:forEach var="year" items="${yearList}">
                <option value="${year}" <c:if test="${year == entYear}">selected</c:if>>${year}</option>
              </c:forEach>
            </select>
          </div>
          <div class="col">
            <label for="class_num" class="form-label">クラス</label>
            <select name="class_num" id="class_num" class="form-select">
              <c:forEach var="cls" items="${classList}">
                <option value="${cls.classNum}" <c:if test="${cls.classNum == classNum}">selected</c:if>>${cls.classNum}</option>
              </c:forEach>
            </select>
          </div>
          <div class="col">
            <label for="subject_cd" class="form-label">科目</label>
            <select name="subject_cd" id="subject_cd" class="form-select">
              <c:forEach var="sub" items="${subjectList}">
                <option value="${sub.cd}" <c:if test="${sub.cd == subject.cd}">selected</c:if>>${sub.name}</option>
              </c:forEach>
            </select>
          </div>
          <div class="col">
            <label for="num" class="form-label">回数</label>
            <select name="num" id="num" class="form-select">
              <c:forEach var="i" begin="1" end="5">
                <option value="${i}" <c:if test="${i == num}">selected</c:if>>${i}</option>
              </c:forEach>
            </select>
          </div>
          <div class="col-auto mt-4">
            <button type="submit" class="btn btn-primary">検索</button>
          </div>
        </div>
      </form>

      <!-- 学生一覧と得点入力 -->
      <c:if test="${not empty testList}">
        <form action="TestRegistExecute.action" method="post" class="px-4">
          <input type="hidden" name="ent_year" value="${entYear}">
          <input type="hidden" name="class_num" value="${classNum}">
          <input type="hidden" name="subject_cd" value="${subject.cd}">
          <input type="hidden" name="num" value="${num}">

          <p>科目：${subject.name}（${num}回）</p>
          <table class="table table-hover">
            <tr>
              <th>入学年度</th>
              <th>クラス</th>
              <th>学生番号</th>
              <th>氏名</th>
              <th>点数</th>
            </tr>
            <c:forEach var="test" items="${testList}">
              <tr>
                <td>${test.student.entYear}</td>
                <td>${test.student.classNum}</td>
                <td>${test.student.no}</td>
                <td>${test.student.name}</td>
                <td>
                  <input type="number" name="points" class="form-control" value="${test.point}" min="0" max="100">
                  <input type="hidden" name="nos" value="${test.student.no}">
                </td>
              </tr>
            </c:forEach>
          </table>

          <div class="px-1">
            <button type="submit" class="btn btn-secondary mb-2">登録して終了</button><br>
            <a href="TestRegist.action">戻る</a>
          </div>
        </form>
      </c:if>
    </section>
  </c:param>
</c:import>
