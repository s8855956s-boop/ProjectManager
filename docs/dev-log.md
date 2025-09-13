# 開發日誌
目錄
- [2025-09-13](#2025-09-13)
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
