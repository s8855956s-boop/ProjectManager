
# 🚀 六個月 Side Project Roadmap

## 📅 第 1 個月：打底與架構
- [x] 使用 Spring Initializr 建立專案（Maven、Java 17、Spring Boot 3）
- [x] 建立 GitHub repo，規劃好分支策略
- [x] 設計資料庫 schema（至少一個主表 + 關聯子表）
- [ ] 實作 Entity + Repository + 基本 CRUD API

🎯 成果：能新增/查詢/更新/刪除一個核心資料表（例如 Board + Status）

---

## 📅 第 2 個月：API 與測試
- [ ] 擴充更多 API（例如 Task、User 與 Board 的關聯）
- [ ] 學習並實作 DTO / Request / Response 分層
- [ ] 加上 JUnit / Spring Boot Test，學會怎麼寫單元測試
- [ ] 整合 Swagger/OpenAPI，讓 API 有文件可看

🎯 成果：一組清晰的 REST API，並且能跑測試驗證

---

## 📅 第 3 個月：權限與安全
- [ ] 學習 Spring Security（至少能做 JWT 登入/驗證）
- [ ] 設計基本的 使用者/角色/權限 模型
- [ ] 在 API 層級加上角色控制（例如 Admin 可以刪，User 只能新增/查詢）

🎯 成果：一個能登入、能驗證身分的後端 API

---

## 📅 第 4 個月：前端雛型
- [ ] 選一個前端框架（React / Vue，挑一個就好）
- [ ] 學會呼叫 REST API，至少能操作 CRUD 功能
- [ ] 做一個最小可用 UI（例如 Kanban Board 基本畫面）

🎯 成果：能從前端畫面新增/查詢/刪除資料（透過 API）

---

## 📅 第 5 個月：部署與 DevOps
- [ ] 學習 Docker，把 Spring Boot 專案包成容器
- [ ] 嘗試部署到雲端（Heroku、Render、Railway，或自己弄 AWS EC2）
- [ ] 在 GitHub 加上 CI/CD（例如 push 就自動跑測試）

🎯 成果：專案能線上跑起來，面試時可以 demo 網址

---

## 📅 第 6 個月：打磨與展示
- [ ] 加上更完整的功能（篩選、排序、搜尋）
- [ ] 撰寫 README.md（專案介紹、架構圖、技術棧、使用方式）
- [ ] 準備面試 Demo（可以帶筆電操作，或給面試官看線上版本）
- [ ] 把學到的東西整理成 Blog/日誌，附在履歷上

🎯 成果：一個可 demo 的完整專案（API 文件、前端 UI、線上部署、GitHub 原始碼）