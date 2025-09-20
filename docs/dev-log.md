# 開發日誌
目錄
- [2025-09-13](#2025-09-13)
- [2025-09-14](#2025-09-14)
- [2025-09-15](#2025-09-15)
- [2025-09-16](#2025-09-16)
- [2025-09-17](#2025-09-17)
- [2025-09-20](#2025-09-20)
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
- 設定 Git identity（appUser.name 與 appUser.email）
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
---
## 2025-09-15
今天練習寫目前CRUD的Service的Test，並且發現我UserRepository原本寫的Projection
```JAVA
Optional<User> findUserItemById(UUID id);
```
會導致repository bean create不起來
我先把這個function和有用到他的都先mark掉，不然ProjectmanagerApplicationTests不會過。
---
## 2025-09-16
今天把昨天的
```JAVA
Optional<User> findUserItemById(UUID id);
```
改成
```JAVA
Optional<UserItem> getUserItemById(@Param("id") UUID id);
```
就解決了repository bean create不起來的問題，應該是因為findAByB的A和B都要是Entity的欄位才行。
改成getAByB的A就可以隨便取，並且上面那串也不會跟findById重覆到。
今天把UserServiceTest補上，沒時間了，明天再看看下一步要做甚麼
---
## 2025-09-17
今天把UserTest補上，也把ProjectStatus的update改成直接根據他的id修改，
不需要從board抓出來改在塞回去存board。

也加上了swagger，基本上就只要加依賴到pom檔就可以了，在Controller
最上方可以加@Tag("這邊寫Controller說明)網頁上就會顯示出來。
在API上面加上 @Operation(summary = "API功能", description = "詳細描述")也會顯示
在網頁上，預設的網址是http://localhost:8080/swagger-ui/index.html

今天也有建假資料，我現在用的是H2資料庫，是用Memory存資料的，在resources裡建一個data.sql他就會跑了。
然後我忘記怎麼定義Entity在資料庫裡的名字，查了後知道是@Table(name = \"資料庫Table名稱\")。

在建假資料的時候我不知道要怎麼將ProjectStatus與ProjectBoard關聯，查後知道sql要這樣寫：
```SQL
INSERT INTO PROJECT_STATUS (id, name, project_board_id (-- entity裡有定義ProjectBoard projectBoard欄位上面有加annotation: @JoingColumn("project_board_id"))) VALUES(RANDOM_UUID(), 'DONE', 要關聯的boardId);
projectBoard不用特別設定
```

Sql寫完後啟動遇到這個問題：

Caused by: org.h2.jdbc.JdbcSQLSyntaxErrorException: Table "PROJECT_BOARD" not found (this database is empty); SQL statement:
INSERT INTO PROJECT_BOARD (id, name) VALUES (RANDOM_UUID(), 'Board A') [42104-232]

yml加這個就好了：spring.jpa.defer-datasource-initialization=true

在第一次createStatus的時候出現下面的錯誤
org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role: com.justin.projectmanager.entity.ProjectBoard.projectStatuses: could not initialize proxy - no Session

查了之後知道關聯的Table的預設FetchType是lazy fetch，代表不會真的把關聯得表查出來，除非
session還在，否則若取直接從Table.get關聯table就會出這個錯，在function加上@Transactional
就會保證session在function執行中會一直存在解決了這個錯誤。

ManyToMany的關聯要建立中介表，以user跟task為例：
這是user_task中介表，代表這兩個id的user和task相互關聯
INSERT INTO user_task (user_id, task_id) VALUES ('11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222');

另外我想找userId底下的所有task時不知道JPA可以怎麼寫，後來知道可以在taskRepository底下加
````JAVA
List<ProjectTask> findByAppUsers_Id(UUID userId);
````
因為Task Entity有
````JAVA
    @ManyToMany(mappedBy = "projectTasks")
    private Set<AppUser> appUsers;
````
JPA會自己生成sql

## 2025-09-20
今天增加了三個API：
1. 取得所有狀態資訊
2. 變更專案任務狀態
3. 指派任務給使用者

在寫2跟3的時候有點不確定Restful API要怎麼寫，我知道要用PutMapping，因為本質上是修改，
且專案任務(ProjectTask)跟專案狀態(ProjectStatus)不算是有主從關係，或相互依存，所以我決定這樣寫：
````JAVA
@PutMapping("/projectTasks/{uuid}/projectStatuses/{projectStatusId}")
@Operation(summary = "修改狀態", description = "將uuid的projectTask 的狀態改為 id為projectStatusId的projectStatus")
public void changeStatus(@PathVariable UUID uuid, @PathVariable UUID projectStatusId) {
  service.changeStatus(uuid, projectStatusId);
}
````
因為語意上是去改task的狀態，所以把projectStatuses放在前面。
3也是一樣的想法：
````JAVA
    @PutMapping("/projectTasks/{uuid}/appUsers/{appUserId}")
    @Operation(summary = "指派專案任務", description = "指派專案任務給特定使用者")
    public void assignTask(@PathVariable UUID uuid, @PathVariable UUID appUserId) {
        service.assignTask(uuid, appUserId);
    }
````
還有改寫取得專案任務資訊的格式，直接改映射物件TaskItem，我把TaskItem改成向下面這樣：
````JAVA
public interface TaskItem {
    UUID getId();
    String getName();
    /*---以下是新加上的程式片段---*/
    StatusInfo getProjectStatus();//getProjectStatus()的ProjectStatus對應的是ProjectTask裡關聯的ProjectStatus欄位名稱

    interface StatusInfo {//這裡的欄位就看要取ProjectStatus的哪個欄位就好
        UUID getId();
        String getName();
    }
    /*------------------------*/
}
````
這樣幾乎就不用改太多，只是StatusInfo不能直接用BeanUtils.copyProperties()複製到我新的TaskResponse上，
TaskResponse有多家StatusResponse來傳遞任務狀態資訊，必須再加上將StatusInfo傳進StatusResponse的程式，
所以有用到TaskItem映射物件的地方都要改，否則不會有狀態資訊，我把TaskItem轉成TaskResponse的程式整合到一起
放到TaskUtils裡面，就不用一改TaskItem就要改很多地方