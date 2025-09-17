# Git 指令筆記
目錄
- [Git 初始化與常用指令說明](#git-初始化與常用指令說明)
## Git 初始化與常用指令說明

### 初始化與設定
- **`git init`**  
  在目前資料夾建立一個新的 Git repository。  
  👉 開始追蹤這個專案的版本。

- **`git config --global appUser.name "Kevin"`**  
  設定 commit 時的作者名稱。  
  👉 `--global` 表示這台電腦所有專案都會用這個名稱。

- **`git config --global appUser.email "kevincleim123@gmail.com"`**  
  設定 commit 時的作者 email。  
  👉 通常使用 GitHub 綁定的 email，讓 commit 正確對應到 GitHub 帳號。

---

### 提交檔案
- **`git add .`**  
  把所有檔案加入到「暫存區 (staging area)」。  
  👉 代表這些檔案會包含在下一次的 commit。

- **`git commit -m "Initial commit"`**  
  建立一次提交 (commit)，訊息為 `"Initial commit"`。  
  👉 每個 commit 就像一個「存檔點」。

---

### 建立分支與設定遠端
- **`git branch -M main`**  
  把當前分支名稱改成 `main`。  
  👉 有些 Git 預設是 `master`，這裡改成更常用的 `main`。

- **`git remote add origin https://github.com/<帳號>/<repo>.git`**  
  新增一個遠端 repo，名稱為 `origin`，對應 GitHub 上的專案。  
  👉 建立本地專案與 GitHub 的連結。

---

### 推送到 GitHub
- **`git push -u origin main`**  
  把本地的 `main` 分支推送到 GitHub 的 `origin` repo。  
  👉 `-u` 表示之後 push/pull 預設都會用這個分支。

---

### 📌 總結流程
1. `git init` → 初始化版本控制
2. `git add .` → 選要追蹤的檔案
3. `git commit -m "訊息"` → 建立一次提交
4. `git branch -M main` → 設定主要分支
5. `git remote add origin ...` → 綁定 GitHub
6. `git push -u origin main` → 推送到 GitHub
