# Persistence Notes
目錄
- [Entity建立相關知識](#entity建立相關知識)
- [Repository建立相關知識](#repository建立相關知識)


# Entity建立相關知識
## 資料庫 (Database) 層
這些是純 SQL / 資料表設計的概念：

- **Primary Key (PK)**  
  唯一標識每筆資料，可用 `BIGINT AUTO_INCREMENT` 或 `UUID`。

- **Foreign Key (FK)**  
  建立表之間的關聯，例如 `status.board_id → board.id`。

- **關聯類型**
    - 一對多 (1:N) → `Board → Status`
    - 多對多 (M:N) → `User ↔ Task`（需要中介表）

- **中介表 (Join Table)**  
  多對多時用來存放兩邊的 id，例如：
  ```sql
  create table user_task (
      user_id uuid not null,
      task_id uuid not null,
      primary key (user_id, task_id),
      foreign key (user_id) references user(id),
      foreign key (task_id) references task(id)
  );
  
---
## JPA (Jakarta Persistence API) 層

Java ↔ 資料庫 的映射 (mapping) 規範：

- `@Entity`  
  表示一個類別對應到資料表。

- `@Id`  
  指定主鍵欄位。

- `@Column`
    - `nullable = false` → NOT NULL
    - `updatable = false` → 只能在 insert 時賦值，update 時忽略

- `@ManyToOne` / `@OneToMany(mappedBy = ...)`
    - 定義一對多、多對一的關係
    - `mappedBy` 指定「關聯維護方在對方 Entity 的欄位名稱」

- `@ManyToMany` / `@JoinTable`
    - 定義多對多關係，並指定中介表名稱、外鍵欄位
    - `joinColumns` → 自己這一方的外鍵
    - `inverseJoinColumns` → 對方的外鍵

- `cascade`
    - `CascadeType.ALL` → 父操作套用到子 (persist, merge, remove …)

- `orphanRemoval`
    - `true` → 如果子被從集合移除，DB 自動刪掉那筆資料

---

## Hibernate (JPA 的實作) 層
Hibernate 是 JPA 的常見實作，提供一些額外功能：

- `@UuidGenerator`  
  Hibernate 提供的 UUID 產生器（推薦在 Hibernate 6+ 使用）。

- `@GenericGenerator`  
  舊的 UUID 產生器，Hibernate 6.5 開始被標記為 deprecated。

- Hibernate 負責把 JPA Annotation 轉換成 SQL，並與底層資料庫互動。

---
# Repository建立相關知識
- 建立Repository須加上@Repository，必須是Interface
- 要extends JpaRepository<T, ID>，T代表對應Entity類別，ID則是Entity的ID類別
