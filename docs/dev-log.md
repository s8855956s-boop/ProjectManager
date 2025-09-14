# 開發日誌
目錄
- [2025-09-13](#2025-09-13)
- [2025-09-14](#2025-09-14)
## 2025-09-13
- 初始化專案
- 初始化Git
### 詳細記錄
#### Spring Initializr
今天使用了Spring Initializer產生了空的Spring Boot 專案。
Spring Initializer的介面很簡單，以下是我選擇的專案參數：
- **Language**: Java
    - 因為我要用 Java 開發 Spring Boot 專案
- **Build Tool**: Maven
    - 因為它穩定、支援度廣，公司專案多半用 Maven
- **Packaging**: Jar
    - 適合本地開發，直接 `java -jar` 就能執行
- **Java Version**: 17
    - Spring Boot 3 最低需求版本，兼顧穩定與相容性

#### Git 初始化
- 在專案資料夾執行 `git init`，初始化 Git
- 設定 Git identity（user.name 與 user.email）
- 新增所有檔案並提交，訊息為 "Initial commit"
- 連接遠端 GitHub repo 並推送到 main 分支
- （詳細請見 [Git 初始化指令](./git-commands.md#git-初始化與常用指令說明)）

#### Entity 初始化
我建了幾個專案管理需要用到的Entity：使用者User, 看板Board, 狀態Status, 任務Task，
學了些有關資料庫的一些知識，像是PK，FK，還有關聯的相關知識
- （詳細請見 [Entity建立相關知識](./persistence-notes.md#entity建立相關知識)）

---
## 2025-09-14
- 建立Repository [Repository建立相關知識](./persistence-notes.md#repository建立相關知識)
- 採用MVC，及Restful的設計風格建立基本的CRUD API(尚未測試)
### 今日問題與解決方案及知識點：
- 開起專案時發現專案的檔案都從左邊的Project視窗消失了

今天剛開啟IntelliJ時，發現專案的檔案好像都沒被IntelliJ認出來，都沒有出現在左邊的Project的tree上。
後來開啟Maven選單再選擇Reload All Maven Projects就解決了，是因為 IntelliJ 沒正確辨識 Maven 專案。
Reload之後 IntelliJ 就重新抓 pom.xml，把 src 標記回 Sources Root。

- Controller可以用@RequestMappint(\"URL路徑\")作為底下所有API的根路徑
- Restful API的規則是以資源為主體：

CRUD 對應
POST /tasks → 新增
GET /tasks → 查詢全部
GET /tasks/{id} → 查詢單一
PUT /tasks/{id} → 更新整筆
PATCH /tasks/{id} → 更新部分
DELETE /tasks/{id} → 刪除

父子關係：
GET /boards/{boardId}/statuses