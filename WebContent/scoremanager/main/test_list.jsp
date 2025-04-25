<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績参照</c:param>
  <c:param name="scripts"></c:param>
  <c:param name="content">
    <section class="container mt-5">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績参照</h2>

      <!-- ▼ 科目情報での検索フォーム -->
      <form action="TestListSubjectExecute.action" method="get" class="px-4 mb-4">
        <input type="hidden" name="f" value="sj">
        <input type="hidden" name="f15" value="sj">
        <div class="row g-3 align-items-center border-bottom pb-3 mb-4">
          <p class="mb-2">科目情報</p>

          <div class="col">
            <label class="form-label">入学年度</label>
            <select name="ent_year" class="form-select">
              <option value="">------</option>
              <c:forEach var="y" items="${yearList}">
                <option value="${y}" <c:if test="${y == entYear}">selected</c:if>>${y}</option>
              </c:forEach>
            </select>
          </div>

          <div class="col">
            <label class="form-label">クラス</label>
            <select name="class_num" class="form-select">
              <option value="">------</option>
              <c:forEach var="cls" items="${classList}">
                <option value="${cls.classNum}" <c:if test="${cls.classNum == classNum}">selected</c:if>>${cls.classNum}</option>
              </c:forEach>
            </select>
          </div>

          <div class="col">
            <label class="form-label">科目</label>
            <select name="subject_cd" class="form-select">
              <option value="">------</option>
              <c:forEach var="sub" items="${subjectList}">
                <option value="${sub.cd}" <c:if test="${sub.cd == subjectCd}">selected</c:if>>${sub.name}</option>
              </c:forEach>
            </select>
          </div>


          <div class="col-auto mt-4">
            <button type="submit" class="btn btn-primary">検索</button>
          </div>
        </div>
      </form>

      <!-- ▼ 学生情報での検索フォーム -->
      <form action="TestListStudentExecute.action" method="get" class="px-4 mb-4">
        <input type="hidden" name="f" value="st">
        <input type="hidden" name="f16" value="st">
        <div class="row g-3 align-items-end">
          <p class="mb-2">学生情報</p>

          <div class="col">
            <label class="form-label">学生番号</label>
            <input type="text" class="form-control" name="student_key"
                   placeholder="学生番号を入力してください" value="${studentKey}">
          </div>

          <div class="col-auto">
            <button type="submit" class="btn btn-primary">検索</button>
          </div>
        </div>
      </form>

      <!-- ▼ メッセージ or タイトル 表示切替 -->
      <c:if test="${empty testList && empty subjectName && empty student}">
        <div class="px-4 mb-3">
          <p class="text-info">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>
        </div>
      </c:if>
      <c:if test="${not empty subjectName}">
        <p class="px-4">科目：${subjectName}</p>
      </c:if>
      <c:if test="${not empty student}">
        <p class="px-4">氏名：${student.name}（${student.no}）</p>
      </c:if>

      <!-- ▼ 成績一覧表示 -->
      <c:if test="${not empty testList}">
        <table class="table table-hover px-4">
          <thead>
            <tr>
              <th>学生番号</th>
              <th>氏名</th>
              <th>科目</th>
              <th>回数</th>
              <th>点数</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="test" items="${testList}">
              <tr>
                <td>${test.student.no}</td>
                <td>${test.student.name}</td>
                <td>${test.subject.name}</td>
                <td>${test.num}</td>
                <td>${test.point}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </c:if>

      <c:if test="${empty testList && (not empty entYear || not empty studentKey)}">
        <p class="text-muted px-4">該当する成績情報がありません。</p>
      </c:if>

    </section>
  </c:param>
</c:import>
